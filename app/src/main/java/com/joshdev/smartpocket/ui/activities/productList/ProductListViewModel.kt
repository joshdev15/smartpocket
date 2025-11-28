package com.joshdev.smartpocket.ui.activities.productList

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshdev.smartpocket.domain.models.LedgerProduct
import com.joshdev.smartpocket.domain.models.LedgerTransaction
import com.joshdev.smartpocket.repository.database.RealmDatabase
import com.joshdev.smartpocket.repository.entities.LedgerProductRealm
import com.joshdev.smartpocket.repository.entities.LedgerTransactionRealm
import com.joshdev.smartpocket.ui.activities.photoai.PhotoAIActivity
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class ProductListViewModel : ViewModel() {
    private val database = RealmDatabase.getInstance()
    private val activity = mutableStateOf<ProductListActivity?>(null)
    private val context = mutableStateOf<Context?>(null)
    private val transactionId = mutableStateOf<String?>(null)

    private val _Ledger_transaction = mutableStateOf<LedgerTransaction?>(null)
    val ledgerTransaction: State<LedgerTransaction?> = _Ledger_transaction

    private val _products = mutableStateOf<List<LedgerProduct>>(emptyList())
    val products: State<List<LedgerProduct>> = _products

    private val _showNewProductDialog = mutableStateOf(false)
    val showNewProductDialog: State<Boolean> = _showNewProductDialog

    fun start(act: ProductListActivity, ctx: Context, txId: String) {
        activity.value = act
        context.value = ctx
        transactionId.value = txId
        viewModelScope.launch(Dispatchers.IO) {
            val invoice =
                database.query<LedgerTransactionRealm>("id == $0", ObjectId(txId)).find().firstOrNull()
            invoice?.let {
                _Ledger_transaction.value = it.toData()
                observeProducts()
            }
        }
    }

    fun observeProducts() {
        viewModelScope.launch {
            database.let { realm ->
                realm.query<LedgerProductRealm>()
                    .asFlow()
                    .map { results ->
                        val tmp = results.list.filter { it.invoiceId == transactionId.value }
                        tmp.map { it.toData() }
                    }
                    .collect { productList ->
                        _products.value = productList
                    }
            }
        }
    }

    fun toggleNewInvoiceDialog(value: Boolean?) {
        if (value != null) {
            _showNewProductDialog.value = value
        } else {
            _showNewProductDialog.value = !_showNewProductDialog.value
        }
    }

    fun addProduct(ledgerProduct: LedgerProduct) {
        database.writeBlocking {
            val newLedgerProductRealm = LedgerProductRealm().apply {
                invoiceId = ledgerProduct.invoiceId
                name = ledgerProduct.name
                cost = ledgerProduct.cost
                quantity = ledgerProduct.quantity
            }

            copyToRealm(newLedgerProductRealm)
        }
    }

    fun updateTransactionAmount(amount: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            database.writeBlocking {
                transactionId.value?.let { id ->
                    val invoice = this.query<LedgerTransactionRealm>("id == $0", ObjectId(id))
                        .find()
                        .firstOrNull()

                    invoice?.apply {
                        this.amount = amount
                    }
                }
            }
        }
    }

    fun goToPhotoIA(invoiceId: String) {
        val goToProductList = Intent(context.value, PhotoAIActivity::class.java)
        goToProductList.putExtra("invoiceId", invoiceId)
        activity.value?.startActivity(goToProductList)
    }
}