package com.joshdev.smartpocket.ui.modules.ledger.activity

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.joshdev.smartpocket.domain.ledger.Ledger
import com.joshdev.smartpocket.domain.ledger.LedTransaction
import com.joshdev.smartpocket.repository.database.room.AppDatabase
import com.joshdev.smartpocket.repository.database.room.AppDatabaseSingleton
import com.joshdev.smartpocket.ui.models.FastPanelOption
import com.joshdev.smartpocket.ui.utils.UiUtils.getIntentByFastOptionID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LedgerViewModel : ViewModel() {
    private val database = mutableStateOf<AppDatabase?>(null)
    private val activity = mutableStateOf<LedgerActivity?>(null)
    private val context = mutableStateOf<Context?>(null)
    private val navController = mutableStateOf<NavController?>(null)

    // Ledger Declarations
    private val _showNewLedgerDialog = mutableStateOf(false)
    val showNewLedgerDialog: State<Boolean> = _showNewLedgerDialog

    private val _showLedgerOptionsDialog = mutableStateOf(false)
    val showLedgerOptionsDialog: State<Boolean> = _showLedgerOptionsDialog

    private val _ledgers = mutableStateOf<List<Ledger>>(listOf())
    val ledgers: State<List<Ledger>> = _ledgers

    private val _selectedLedger = mutableStateOf<Ledger?>(null)
    val selectedLedger: State<Ledger?> = _selectedLedger

    // LedTransaction Declarations
    private val _showNewLedTransactionDialog = mutableStateOf(false)
    val showNewTransactionDialog: State<Boolean> = _showNewLedTransactionDialog

    private val _showTransactionOptionsDialog = mutableStateOf(false)
    val showTransactionOptionsDialog: State<Boolean> = _showTransactionOptionsDialog

    private val _selectedLedgerLedTransaction = mutableStateOf<LedTransaction?>(null)
    val selectedLedgerLedTransaction: State<LedTransaction?> = _selectedLedgerLedTransaction

    private val _ledger = mutableStateOf<Ledger?>(null)
    val ledger: State<Ledger?> = _ledger

    private val _transactions = mutableStateOf<List<LedTransaction>>(listOf())
    val transactions: State<List<LedTransaction>> = _transactions

    fun start(act: LedgerActivity, ctx: Context, nav: NavController) {
        activity.value = act
        context.value = ctx
        navController.value = nav
        database.value = AppDatabaseSingleton.getInstance(ctx)

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

    fun navToTransactions(ledgerId: Long) {
        _ledger.value = _ledgers.value.find { it.id == ledgerId }
        navController.value?.navigate("transactions/${ledgerId}")
    }

    // Transactions UI Actions
    fun toggleNewTransactionDialog(value: Boolean?) {
        if (value != null) {
            _showNewLedTransactionDialog.value = value
        } else {
            _showNewLedTransactionDialog.value = !_showNewLedTransactionDialog.value
        }
    }

    fun goToTransaction(txId: Long) {
//        val goToProductList = Intent(context.value, ProductListActivity::class.java)
//        goToProductList.putExtra("txId", txId)
//        activity.value?.startActivity(goToProductList)
    }

    fun toggleTransactionOptionsDialog(tx: LedTransaction?, value: Boolean) {
        _selectedLedgerLedTransaction.value = tx
        _showTransactionOptionsDialog.value = value
    }

    // Ledger Operations
    private fun observeLedgers() {
        viewModelScope.launch {
//            operations.observe<Ledger, LedgerRealm>().collect { ledgerList ->
//                _ledgers.value = ledgerList
//            }
        }
    }

    fun addLedger(ledger: Ledger) {
        viewModelScope.launch(Dispatchers.IO) {
//            operations.add<Ledger, LedgerRealm>(ledger)
        }
    }

    fun deleteLedger() {
        selectedLedger.value?.let { ledger ->
            viewModelScope.launch(Dispatchers.IO) {
//                operations.delete<Ledger, LedgerRealm>(ledger.id)
            }
        }
    }

    fun goTo(id: FastPanelOption.IDs) {
        navController.value?.let { nav ->
            getIntentByFastOptionID(id, context.value, nav)?.let {
                activity.value?.startActivity(it)
            }
        }
    }

    // Transactions Operations
    fun observeTransactions() {
        viewModelScope.launch {
//            operations.observe<LedTransaction, LedgerTransactionRealm>()
//                .collect { transactionList ->
//                    _transactions.value = transactionList
//                }
        }
    }

    fun addTransaction(ledgerLedTransaction: LedTransaction) {
//        operations.add<LedTransaction, LedgerTransactionRealm>(ledgerLedTransaction)
        updateLedgerBalance()
    }

    fun updateLedgerBalance() {
        ledger.value?.let {
            viewModelScope.launch(Dispatchers.IO) {
//                operations.update(it.id)
            }
        }
    }

    fun deleteTransaction() {
        selectedLedgerLedTransaction.value?.let { tx ->
            viewModelScope.launch(Dispatchers.IO) {
//                operations.delete<LedTransaction, LedgerTransactionRealm>(tx.id)
                updateLedgerBalance()
            }
        }
    }
}