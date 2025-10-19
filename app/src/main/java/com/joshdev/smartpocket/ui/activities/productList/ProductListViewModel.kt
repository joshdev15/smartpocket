package com.joshdev.smartpocket.ui.activities.productList

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshdev.smartpocket.domain.models.Product
import com.joshdev.smartpocket.domain.models.ProductRealm
import com.joshdev.smartpocket.domain.models.Transaction
import com.joshdev.smartpocket.domain.models.TransactionRealm
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
    private val transactionId = mutableStateOf<String?>(null)

    private val _transaction = mutableStateOf<Transaction?>(null)
    val transaction: State<Transaction?> = _transaction;

    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: State<List<Product>> = _products;

    private val _showNewProductDialog = mutableStateOf(false)
    val showNewProductDialog: State<Boolean> = _showNewProductDialog

    fun start(act: ProductListActivity, ctx: Context, txId: String) {
        activity.value = act
        context.value = ctx
        transactionId.value = txId
        viewModelScope.launch(Dispatchers.IO) {
            val invoice =
                database.query<TransactionRealm>("id == $0", ObjectId(txId)).find().firstOrNull()
            invoice?.let {
                _transaction.value = it.toTransaction()
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
                        val tmp = results.list.filter { it.invoiceId == transactionId.value }
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

    fun updateTransactionAmount(amount: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            database.writeBlocking {
                transactionId.value?.let { id ->
                    val invoice = this.query<TransactionRealm>("id == $0", ObjectId(id))
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