package com.joshdev.smartpocket.ui.activities.ledger

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshdev.smartpocket.domain.models.Ledger
import com.joshdev.smartpocket.domain.models.LedgerRealm
import com.joshdev.smartpocket.repository.database.realm.RealmDatabase
import com.joshdev.smartpocket.ui.activities.invoiceList.InvoiceListActivity
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class LedgerViewModel : ViewModel() {
    private val database = RealmDatabase.getInstance()
    private val activity = mutableStateOf<LedgerActivity?>(null)
    private val context = mutableStateOf<Context?>(null)

    private val _showNewRecordDialog = mutableStateOf(false)
    val showNewRecordDialog: State<Boolean> = _showNewRecordDialog

    private val _records = mutableStateOf<List<Ledger>>(listOf())
    val records: State<List<Ledger>> = _records

    fun start(act: LedgerActivity, ctx: Context) {
        activity.value = act
        context.value = ctx

        observeLedgers()
    }

    private fun observeLedgers() {
        viewModelScope.launch {
            database.let { realm ->
                realm.query<LedgerRealm>()
                    .asFlow()
                    .map { results ->
                        results.list.map { ledgerRealm ->
                            ledgerRealm.toLedger()
                        }
                    }
                    .collect { ledgerList ->
                        _records.value = ledgerList
                    }
            }
        }
    }

    fun addLedger(record: Ledger) {
        database.writeBlocking {
            val newLedger = LedgerRealm().apply {
                name = record.name
                author = record.author
                year = record.year
                month = record.month
                creationDate = record.creationDate
            }

            copyToRealm(newLedger)
        }
    }

    fun toggleNewRecordDialog(value: Boolean? = null) {
        if (value != null) {
            _showNewRecordDialog.value = value
        } else {
            _showNewRecordDialog.value = !_showNewRecordDialog.value
        }
    }

    fun goToRecord(recordId: String) {
        val goToInvoiceList = Intent(context.value, InvoiceListActivity::class.java)
        goToInvoiceList.putExtra("recordId", recordId)
        activity.value?.startActivity(goToInvoiceList)
    }
}

