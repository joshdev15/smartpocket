package com.joshdev.smartpocket.ui.activities.home

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshdev.smartpocket.domain.models.GeneralRecord
import com.joshdev.smartpocket.repository.database.AppDatabase
import com.joshdev.smartpocket.repository.database.AppDatabaseSingleton
import com.joshdev.smartpocket.domain.models.Invoice
import com.joshdev.smartpocket.ui.activities.records.RecordsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private lateinit var context: Context
    private val _database = mutableStateOf<AppDatabase?>(null)

    private val _showNewRecordDialog = mutableStateOf(false)
    val showNewRecordDialog: State<Boolean> = _showNewRecordDialog

    val records: StateFlow<List<GeneralRecord>> = flow {
        _database.value?.recordDao()?.getAllRecords()?.collect {
            emit(it)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val invoices: StateFlow<List<Invoice>> = flow {
        _database.value?.invoiceDao()?.getAllInvoices()?.collect {
            emit(it)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun start(context: Context) {
        this.context = context
        _database.value = AppDatabaseSingleton.getInstance(context)
    }

    fun addRecord(record: GeneralRecord) {
        viewModelScope.launch(Dispatchers.IO) {
            _database.value?.recordDao()?.insert(record)
        }
    }

    fun addInvoice(invoice: Invoice) {
        viewModelScope.launch(Dispatchers.IO) {
            _database.value?.invoiceDao()?.insert(invoice)
        }
    }

    fun deleteAllInvoices() {
        viewModelScope.launch(Dispatchers.IO) {
            _database.value?.invoiceDao()?.deleteAllInvoices()
        }
    }

    fun toggleNewRecordDialog(value: Boolean? = null) {
        if (value != null) {
            _showNewRecordDialog.value = value
        } else {
            _showNewRecordDialog.value = !_showNewRecordDialog.value
        }
    }

    fun goToRecord(recordId: Int) {
        val goToRecord = Intent(context, RecordsActivity::class.java)
        goToRecord.putExtra("recordId", recordId)
        context.startActivity(goToRecord)
    }
}

