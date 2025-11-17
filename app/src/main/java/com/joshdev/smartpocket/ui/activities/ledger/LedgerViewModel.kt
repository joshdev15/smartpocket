package com.joshdev.smartpocket.ui.activities.ledger

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshdev.smartpocket.domain.models.Ledger
import com.joshdev.smartpocket.repository.database.Operations
import com.joshdev.smartpocket.repository.database.RealmDatabase
import com.joshdev.smartpocket.repository.models.LedgerRealm
import com.joshdev.smartpocket.ui.activities.invoiceList.TransactionListActivity
import com.joshdev.smartpocket.ui.models.FastPanelOption
import com.joshdev.smartpocket.ui.utils.UiUtils.getIntentByFastOptionID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LedgerViewModel : ViewModel() {
    private val database = RealmDatabase.getInstance()
    private val activity = mutableStateOf<LedgerActivity?>(null)
    private val context = mutableStateOf<Context?>(null)
    private val operations = Operations(database)

    private val _showNewLedgerDialog = mutableStateOf(false)
    val showNewLedgerDialog: State<Boolean> = _showNewLedgerDialog

    private val _showLedgerOptionsDialog = mutableStateOf(false)
    val showLedgerOptionsDialog: State<Boolean> = _showLedgerOptionsDialog

    private val _ledgers = mutableStateOf<List<Ledger>>(listOf())
    val ledgers: State<List<Ledger>> = _ledgers

    private val _selectedLedger = mutableStateOf<Ledger?>(null)
    val selectedLedger: State<Ledger?> = _selectedLedger

    fun start(act: LedgerActivity, ctx: Context) {
        activity.value = act
        context.value = ctx
        observeLedgers()
    }

    // UI Actions
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

    fun goToLedger(ledgerId: String) {
        val goToTransactionList = Intent(context.value, TransactionListActivity::class.java)
        goToTransactionList.putExtra("ledgerId", ledgerId)
        activity.value?.startActivity(goToTransactionList)
    }

    // Operations
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
        getIntentByFastOptionID(id, context.value)?.let {
            activity.value?.startActivity(it)
        }
    }
}