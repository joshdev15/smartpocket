package com.joshdev.smartpocket.ui.activities.product

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshdev.smartpocket.domain.models.Product
import com.joshdev.smartpocket.repository.database.AppDatabase
import com.joshdev.smartpocket.repository.database.AppDatabaseSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {
    private val activity = mutableStateOf<ProductListActivity?>(null)
    private val context = mutableStateOf<Context?>(null)
    private val invoiceId = mutableStateOf<Int?>(null)
    private val database = mutableStateOf<AppDatabase?>(null)

    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: State<List<Product>> = _products;

    private val _showNewProductDialog = mutableStateOf(false)
    val showNewProductDialog: State<Boolean> = _showNewProductDialog

    fun start(act: ProductListActivity, ctx: Context, invId: Int) {
        activity.value = act
        context.value = ctx
        invoiceId.value = invId
        database.value = AppDatabaseSingleton.getInstance(ctx)
    }

    fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            database.value?.productDao()?.getProductsByInvoiceId(invoiceId.value!!)
                ?.collect { products ->
                    _products.value = products
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
        viewModelScope.launch(Dispatchers.IO) {
            database.value?.productDao()?.insert(product)
        }
    }
}