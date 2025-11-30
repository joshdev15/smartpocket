package com.joshdev.smartpocket.ui.modules.ledger.activity

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.joshdev.smartpocket.domain.ledger.Ledger
import com.joshdev.smartpocket.domain.ledger.Transaction
import com.joshdev.smartpocket.repository.database.Operations
import com.joshdev.smartpocket.repository.database.RealmDatabase
import com.joshdev.smartpocket.repository.entities.ledger.LedgerRealm
import com.joshdev.smartpocket.repository.entities.ledger.LedgerTransactionRealm
import com.joshdev.smartpocket.ui.activities.productList.ProductListActivity
import com.joshdev.smartpocket.ui.models.FastPanelOption
import com.joshdev.smartpocket.ui.utils.UiUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LedgerViewModel : ViewModel() {
    private val database = RealmDatabase.getInstance()
    private val activity = mutableStateOf<LedgerActivity?>(null)
    private val context = mutableStateOf<Context?>(null)
    private val navController = mutableStateOf<NavController?>(null)
    private val operations = Operations(database)

    // Ledger Declarations
    private val _showNewLedgerDialog = mutableStateOf(false)
    val showNewLedgerDialog: State<Boolean> = _showNewLedgerDialog

    private val _showLedgerOptionsDialog = mutableStateOf(false)
    val showLedgerOptionsDialog: State<Boolean> = _showLedgerOptionsDialog

    private val _ledgers = mutableStateOf<List<Ledger>>(listOf())
    val ledgers: State<List<Ledger>> = _ledgers

    private val _selectedLedger = mutableStateOf<Ledger?>(null)
    val selectedLedger: State<Ledger?> = _selectedLedger

    // Transaction Declarations
    private val _showNewTransactionDialog = mutableStateOf(false)
    val showNewTransactionDialog: State<Boolean> = _showNewTransactionDialog

    private val _showTransactionOptionsDialog = mutableStateOf(false)
    val showTransactionOptionsDialog: State<Boolean> = _showTransactionOptionsDialog

    private val _selectedLedgerTransaction = mutableStateOf<Transaction?>(null)
    val selectedLedgerTransaction: State<Transaction?> = _selectedLedgerTransaction

    private val _ledger = mutableStateOf<Ledger?>(null)
    val ledger: State<Ledger?> = _ledger

    private val _transactions = mutableStateOf<List<Transaction>>(listOf())
    val transactions: State<List<Transaction>> = _transactions

    fun start(act: LedgerActivity, ctx: Context, nav: NavController) {
        activity.value = act
        context.value = ctx
        navController.value = nav

        observeLedgers()
        observeTransactions()
    }

    // Ledger UI Actions
    fun toggleNewRecordDialog(value: Boolean? = null) {
        if (value != null) {
            _showNewLedgerDialog.value = value
        } else {
            _showNewLedgerDialog.value = !_showNewLedgerDialog.value
        }
    }

    fun toggleLedgerOptionsDialog(ledger: Ledger?, value: Boolean? = null) {
        _selectedLedger.value = ledger
        _showLedgerOptionsDialog.value = value ?: false
    }

    fun navToTransactions(ledgerId: String) {
        _ledger.value = _ledgers.value.find { it.id == ledgerId }
        navController.value?.navigate("transactions/${ledgerId}")
    }

    // Transactions UI Actions
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
        _selectedLedgerTransaction.value = tx
        _showTransactionOptionsDialog.value = value
    }

    // Ledger Operations
    private fun observeLedgers() {
        viewModelScope.launch {
            operations.observeItems<Ledger, LedgerRealm>().collect { ledgerList ->
                _ledgers.value = ledgerList
            }
        }
    }

    fun addLedger(ledger: Ledger) {
        viewModelScope.launch(Dispatchers.IO) {
            operations.addItem<Ledger, LedgerRealm>(ledger)
        }
    }

    fun deleteLedger() {
        selectedLedger.value?.let { ledger ->
            viewModelScope.launch(Dispatchers.IO) {
                operations.deleteItem<Ledger, LedgerRealm>(ledger.id)
            }
        }
    }

    fun goTo(id: FastPanelOption.IDs) {
        UiUtils.getIntentByFastOptionID(id, context.value)?.let {
            activity.value?.startActivity(it)
        }
    }

    // Transactions Operations
    fun observeTransactions() {
        viewModelScope.launch {
            operations.observeItems<Transaction, LedgerTransactionRealm>()
                .collect { transactionList ->
                    _transactions.value = transactionList
                }
        }
    }

    fun addTransaction(ledgerTransaction: Transaction) {
        operations.addItem<Transaction, LedgerTransactionRealm>(ledgerTransaction)
        updateLedgerBalance()
    }

    fun updateLedgerBalance() {
        ledger.value?.let {
            viewModelScope.launch(Dispatchers.IO) {
                operations.updateItem(it.id)
            }
        }
    }

    fun deleteTransaction() {
        selectedLedgerTransaction.value?.let { tx ->
            viewModelScope.launch(Dispatchers.IO) {
                operations.deleteItem<Transaction, LedgerTransactionRealm>(tx.id)
                updateLedgerBalance()
            }
        }
    }
}