package com.joshdev.smartpocket.src.activities.home

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshdev.smartpocket.src.database.AppDatabase
import com.joshdev.smartpocket.src.database.AppDatabaseSingleton
import com.joshdev.smartpocket.src.database.entity.invoice.Invoice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _database = mutableStateOf<AppDatabase?>(null)
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
        _database.value = AppDatabaseSingleton.getInstance(context)
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
}

