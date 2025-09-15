package com.joshdev.smartpocket.ui.activities.invoiceList

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshdev.smartpocket.domain.models.Ledger
import com.joshdev.smartpocket.domain.models.Invoice
import com.joshdev.smartpocket.domain.models.InvoiceRealm
import com.joshdev.smartpocket.domain.models.LedgerRealm
import com.joshdev.smartpocket.repository.database.realm.RealmDatabase
import com.joshdev.smartpocket.ui.activities.productList.ProductListActivity
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class InvoiceListViewModel : ViewModel() {
    private val activity = mutableStateOf<InvoiceListActivity?>(null)
    private val context = mutableStateOf<Context?>(null)
    private val recordId = mutableStateOf<String?>(null)
    private val database = RealmDatabase.getInstance()

    private val _showNewInvoiceDialog = mutableStateOf(false)
    val showNewInvoiceDialog: State<Boolean> = _showNewInvoiceDialog

    private val _record = mutableStateOf<Ledger?>(null)
    val record: State<Ledger?> = _record

    private val _invoices = mutableStateOf<List<Invoice>>(listOf())
    val invoices: State<List<Invoice>> = _invoices

    fun start(act: InvoiceListActivity, ctx: Context, recId: String) {
        activity.value = act
        context.value = ctx
        recordId.value = recId
        viewModelScope.launch(Dispatchers.IO) {
            val ledger = database.query<LedgerRealm>(
                "id == $0",
                ObjectId(recId)
            ).find().firstOrNull()

            ledger?.let {
                _record.value = ledger.toLedger()
                observeInvoices()
            }
        }
    }

    fun observeInvoices() {
        viewModelScope.launch {
            database.let { realm ->
                realm.query<InvoiceRealm>()
                    .asFlow()
                    .map { results ->
                        val tmp = results.list.filter { it.recordId == recordId.value }
                        tmp.map { it.toInvoice() }
                    }
                    .collect { invoiceList ->
                        _invoices.value = invoiceList
                    }
            }
        }
    }

    fun addInvoice(invoice: Invoice) {
        database.writeBlocking {
            val newInvoiceRealm = InvoiceRealm().apply {
                recordId = invoice.recordId
                name = invoice.name
                author = invoice.author
                creationDate = invoice.creationDate
                modificationDate = invoice.modificationDate
                total = invoice.total
            }

            copyToRealm(newInvoiceRealm)
        }
    }

    fun toggleNewInvoiceDialog(value: Boolean?) {
        if (value != null) {
            _showNewInvoiceDialog.value = value
        } else {
            _showNewInvoiceDialog.value = !_showNewInvoiceDialog.value
        }
    }

    fun goToInvoice(invoiceId: String) {
        val goToProductList = Intent(context.value, ProductListActivity::class.java)
        goToProductList.putExtra("invoiceId", invoiceId)
        activity.value?.startActivity(goToProductList)
    }
}