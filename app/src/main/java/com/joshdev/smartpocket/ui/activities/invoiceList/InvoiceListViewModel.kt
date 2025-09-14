package com.joshdev.smartpocket.ui.activities.invoiceList

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshdev.smartpocket.domain.models.Ledger
import com.joshdev.smartpocket.domain.models.Invoice
import com.joshdev.smartpocket.repository.database.realm.RealmDBSingleton
import com.joshdev.smartpocket.ui.activities.productList.ProductListActivity
import io.realm.kotlin.Realm
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
    private val database = mutableStateOf<Realm?>(null)

    private val _showNewInvoiceDialog = mutableStateOf(false)
    val showNewInvoiceDialog: State<Boolean> = _showNewInvoiceDialog

    private val _record = mutableStateOf<Ledger?>(null)
    val record: State<Ledger?> = _record

    val invoices: State<List<Invoice>> = mutableStateOf(listOf())

    fun start(act: InvoiceListActivity, ctx: Context, recId: Int) {
        activity.value = act
        context.value = ctx
        recordId.value = recId
        database.value = RealmDBSingleton.getInstance(ctx)
        viewModelScope.launch(Dispatchers.IO) {
//            database.value?.recordDao()?.getRecordById(recId)?.let { rec ->
//                _record.value = rec
//            }
        }
    }

    fun addInvoice(invoice: Invoice) {
        viewModelScope.launch(Dispatchers.IO) {
//            database.value?.invoiceDao()?.insert(invoice)
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