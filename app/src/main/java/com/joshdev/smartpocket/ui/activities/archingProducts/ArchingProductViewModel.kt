package com.joshdev.smartpocket.ui.activities.archingProducts

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshdev.smartpocket.domain.arching.Product
import com.joshdev.smartpocket.repository.database.Operations
import com.joshdev.smartpocket.repository.database.RealmDatabase
import com.joshdev.smartpocket.repository.entities.arching.ArchingProductRealm
import kotlinx.coroutines.launch

class ArchingProductViewModel : ViewModel() {
    private val database = RealmDatabase.getInstance()
    private val operations = Operations(database)
    private val activity = mutableStateOf<ArchingProductsActivity?>(null)
    private val context = mutableStateOf<Context?>(null)

    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: State<List<Product>> = _products

    private val _showNewProductDialog = mutableStateOf(false)
    val showNewProductDialog: State<Boolean> = _showNewProductDialog

    fun start(act: ArchingProductsActivity, ctx: Context) {
        activity.value = act
        context.value = ctx
        observeProducts()
    }

    private fun observeProducts() {
        viewModelScope.launch {
            operations.observeItems<Product, ArchingProductRealm>().collect { productList ->
                _products.value = productList
            }
        }
    }

    fun toggleNewProductDialog(value: Boolean? = null) {
        _showNewProductDialog.value = value ?: !_showNewProductDialog.value
    }

    fun addProduct(archingProduct: Product) {
        try {
            Log.i("--> Arching Product", archingProduct.toString())
            viewModelScope.launch {
                operations.addItem<Product, ArchingProductRealm>(archingProduct)
            }
        } catch (e: Exception) {
            Log.i("--> Arching Product", e.message.toString())
        }
    }
}