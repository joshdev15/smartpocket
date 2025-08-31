package com.joshdev.smartpocket.ui.activities.invoice

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshdev.smartpocket.domain.models.Invoice
import com.joshdev.smartpocket.repository.database.AppDatabase
import com.joshdev.smartpocket.repository.database.AppDatabaseSingleton
import com.joshdev.smartpocket.ui.activities.product.ProductListActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InvoiceListViewModel : ViewModel() {
    private val activity = mutableStateOf<InvoiceListActivity?>(null)
    private val context = mutableStateOf<Context?>(null)
    private val recordId = mutableStateOf<Int?>(null)
    private val database = mutableStateOf<AppDatabase?>(null)

    private val _showNewInvoiceDialog = mutableStateOf(false)
    val showNewInvoiceDialog: State<Boolean> = _showNewInvoiceDialog

    val invoices: StateFlow<List<Invoice>> = flow {
        database.value?.invoiceDao()?.getAllInvoices()?.collect {
            emit(it)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun start(act: InvoiceListActivity, ctx: Context, recId: Int) {
        activity.value = act
        context.value = ctx
        recordId.value = recId
        database.value = AppDatabaseSingleton.getInstance(ctx)
    }

    fun addInvoice(invoice: Invoice) {
        viewModelScope.launch(Dispatchers.IO) {
            database.value?.invoiceDao()?.insert(invoice)
        }
    }

    fun toggleNewInvoiceDialog(value: Boolean?) {
        if (value != null) {
            _showNewInvoiceDialog.value = value
        } else {
            _showNewInvoiceDialog.value = !_showNewInvoiceDialog.value
        }
    }

    fun goToInvoice(invoiceId: Int) {
        val goToProductList = Intent(context.value, ProductListActivity::class.java)
        goToProductList.putExtra("invoiceId", invoiceId)
        activity.value?.startActivity(goToProductList)
    }
}