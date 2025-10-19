package com.joshdev.smartpocket.ui.activities.invoiceList

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshdev.smartpocket.domain.models.Ledger
import com.joshdev.smartpocket.domain.models.LedgerRealm
import com.joshdev.smartpocket.domain.models.Transaction
import com.joshdev.smartpocket.domain.models.TransactionRealm
import com.joshdev.smartpocket.repository.database.realm.RealmDatabase
import com.joshdev.smartpocket.ui.activities.productList.ProductListActivity
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class TransactionListViewModel : ViewModel() {
    private val activity = mutableStateOf<TransactionListActivity?>(null)
    private val context = mutableStateOf<Context?>(null)
    private val ledgerId = mutableStateOf<String?>(null)
    private val database = RealmDatabase.getInstance()

    private val _showNewTransactionDialog = mutableStateOf(false)
    val showNewTransactionDialog: State<Boolean> = _showNewTransactionDialog

    private val _showTransactionOptionsDialog = mutableStateOf(false)
    val showTransactionOptionsDialog: State<Boolean> = _showTransactionOptionsDialog

    private val _selectedTransaction = mutableStateOf<Transaction?>(null)
    val selectedTransaction: State<Transaction?> = _selectedTransaction

    private val _ledger = mutableStateOf<Ledger?>(null)
    val ledger: State<Ledger?> = _ledger

    private val _transactions = mutableStateOf<List<Transaction>>(listOf())
    val transactions: State<List<Transaction>> = _transactions

    fun start(act: TransactionListActivity, ctx: Context, ledgerId: String) {
        activity.value = act
        context.value = ctx
        this@TransactionListViewModel.ledgerId.value = ledgerId
        viewModelScope.launch(Dispatchers.IO) {
            val ledger = database.query<LedgerRealm>(
                "id == $0",
                ObjectId(ledgerId)
            ).find().firstOrNull()

            ledger?.let {
                _ledger.value = ledger.toLedger()
                observeTransactions()
            }
        }
    }

    fun observeTransactions() {
        viewModelScope.launch {
            database.let { realm ->
                realm.query<TransactionRealm>()
                    .asFlow()
                    .map { results ->
                        val tmp = results.list.filter { it.ledgerId == ledgerId.value }
                        tmp.map { it.toTransaction() }
                    }
                    .collect { transactionList ->
                        _transactions.value = transactionList
                    }
            }
        }
    }

    fun addTransaction(transaction: Transaction) {
        database.writeBlocking {
            val newTransactionRealm = TransactionRealm().apply {
                id = ObjectId.invoke()
                name = transaction.name
                type = transaction.type.toString()
                amount = transaction.amount
                date = transaction.date
                description = transaction.description
                ledgerId = transaction.ledgerId
                currencyId = transaction.currencyId
                postBalance = transaction.postBalance
                hasProducts = transaction.hasProducts
                products = transaction.products.map { it.toProductRealm() }.toRealmList()
            }

            copyToRealm(newTransactionRealm)
        }
    }

    fun toggleNewTransactionDialog(value: Boolean?) {
        if (value != null) {
            _showNewTransactionDialog.value = value
        } else {
            _showNewTransactionDialog.value = !_showNewTransactionDialog.value
        }
    }

    fun goToTransaction(txId: String) {
        val goToProductList = Intent(context.value, ProductListActivity::class.java)
        goToProductList.putExtra("txId", txId)
        activity.value?.startActivity(goToProductList)
    }

    fun toggleTransactionOptionsDialog(tx: Transaction?, value: Boolean) {
        _selectedTransaction.value = tx
        _showTransactionOptionsDialog.value = value
    }

    fun deleteTransaction() {
        selectedTransaction.value?.let { tx ->
            val id = ObjectId(tx.id)

            viewModelScope.launch(Dispatchers.IO) {
                database.writeBlocking {
                    val txToDelete = this.query<TransactionRealm>("id == $0", id).first().find()
                    txToDelete?.let {
                        delete(it)
                    }
                }
            }
        }
    }
}