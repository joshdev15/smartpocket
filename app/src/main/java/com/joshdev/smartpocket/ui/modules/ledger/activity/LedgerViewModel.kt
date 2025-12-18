package com.joshdev.smartpocket.ui.modules.ledger.activity

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.joshdev.smartpocket.domain.ledger.LedTransaction
import com.joshdev.smartpocket.domain.ledger.Ledger
import com.joshdev.smartpocket.repository.database.room.AppDatabase
import com.joshdev.smartpocket.repository.database.room.AppDatabaseSingleton
import com.joshdev.smartpocket.ui.models.FastPanelOption
import com.joshdev.smartpocket.ui.utils.UiUtils.getIntentByFastOptionID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
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
    private val _showNewTransactionDialog = mutableStateOf(false)
    val showNewTransactionDialog: State<Boolean> = _showNewTransactionDialog

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
            _showNewTransactionDialog.value = value
        } else {
            _showNewTransactionDialog.value = !_showNewTransactionDialog.value
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
            database.value?.ledgerDao()?.getAllLedgers()?.collect { tmpLedgerList ->
                _ledgers.value = tmpLedgerList
            }
        }
    }

    fun addLedger(currentLedger: Ledger) {
        viewModelScope.launch(Dispatchers.IO) {
            database.value?.ledgerDao()?.insert(currentLedger)
        }
    }

    fun deleteLedger() {
        selectedLedger.value?.let { currentLedger ->
            viewModelScope.launch(Dispatchers.IO) {
                database.value?.ledgerDao()?.delete(currentLedger)
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
    fun observeTransactions(ledgerId: Long) {
        viewModelScope.launch {
            database.value?.ledTransactionDao()?.getAllTxByLedgerId(ledgerId)
                ?.collect { tmpTransactionList ->
                    _transactions.value = tmpTransactionList
                }
        }
    }

    fun addTransaction(tx: LedTransaction) {
        viewModelScope.launch {
            database.value?.ledTransactionDao()?.insert(tx)
            updateLedgerBalance()
        }
    }

    fun updateLedgerBalance() {
        ledger.value?.let { currentLedger ->
            viewModelScope.launch {
                val newLedgerAmount = getTransactionsAmountByLedgerId(currentLedger.id!!)
                val updatedLedger = currentLedger.copy(totalBalance = newLedgerAmount)
                database.value?.ledgerDao()?.update(updatedLedger)
            }
        }
    }

    suspend fun getTransactionsAmountByLedgerId(ledgerId: Long): Double {
        val txList = database.value?.ledTransactionDao()?.getAllTxByLedgerId(ledgerId)?.first()
        return txList?.sumOf { it.amount } ?: 0.0
    }

    fun deleteTransaction() {
        selectedLedgerLedTransaction.value?.let { tx ->
            viewModelScope.launch {
                database.value?.ledTransactionDao()?.delete(tx)
                updateLedgerBalance()
            }
        }
    }
}