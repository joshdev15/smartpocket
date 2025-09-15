package com.joshdev.smartpocket.ui.activities.productList

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshdev.smartpocket.domain.models.Invoice
import com.joshdev.smartpocket.domain.models.InvoiceRealm
import com.joshdev.smartpocket.domain.models.Product
import com.joshdev.smartpocket.domain.models.ProductRealm
import com.joshdev.smartpocket.repository.database.realm.RealmDatabase
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
    private val invoiceId = mutableStateOf<String?>(null)

    private val _invoice = mutableStateOf<Invoice?>(null)
    val invoice: State<Invoice?> = _invoice;

    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: State<List<Product>> = _products;

    private val _showNewProductDialog = mutableStateOf(false)
    val showNewProductDialog: State<Boolean> = _showNewProductDialog

    fun start(act: ProductListActivity, ctx: Context, invId: String) {
        activity.value = act
        context.value = ctx
        invoiceId.value = invId
        viewModelScope.launch(Dispatchers.IO) {
            val invoice =
                database.query<InvoiceRealm>("id == $0", ObjectId(invId)).find().firstOrNull()
            invoice?.let {
                _invoice.value = it.toInvoice()
                observeProducts()
            }
        }
    }

    fun observeProducts() {
        viewModelScope.launch {
            database.let { realm ->
                realm.query<ProductRealm>()
                    .asFlow()
                    .map { results ->
                        val tmp = results.list.filter { it.invoiceId == invoiceId.value }
                        tmp.map { it.toProduct() }
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

    fun addProduct(product: Product) {
        database.writeBlocking {
            val newProductRealm = ProductRealm().apply {
                invoiceId = product.invoiceId
                name = product.name
                cost = product.cost
                quantity = product.quantity
            }

            copyToRealm(newProductRealm)
        }
    }

    fun updateInvoiceTotal(total: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            database.writeBlocking {
                invoiceId.value?.let { id ->
                    val invoice = this.query<InvoiceRealm>("id == $0", ObjectId(id))
                        .find()
                        .firstOrNull()

                    invoice?.apply {
                        this.total = total
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