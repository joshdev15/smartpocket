package com.joshdev.smartpocket.ui.activities.archingProducts

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshdev.smartpocket.domain.arching.Product
import com.joshdev.smartpocket.repository.database.Operations
import com.joshdev.smartpocket.repository.database.RealmDatabase
import com.joshdev.smartpocket.repository.entities.arching.ArchingProductRealm
import kotlinx.coroutines.launch

class ArchingProductsViewModel: ViewModel() {
    val database = RealmDatabase.getInstance()
    private val activity = mutableStateOf<ArchingProductsActivity?>(null)
    private val context = mutableStateOf<Context?>(null)
    private val operations = Operations(database)

    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: State<List<Product>> = _products

    fun start(act: ArchingProductsActivity, ctx: Context) {
        activity.value = act
        context.value = ctx
        observeLedgers()
    }

    // Operations
    private fun observeLedgers() {
        viewModelScope.launch {
            operations.observeItems<Product, ArchingProductRealm>().collect { products ->
                _products.value = products
            }
        }
    }
}