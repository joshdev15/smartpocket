package com.joshdev.smartpocket.ui.activities.archingProducts

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshdev.smartpocket.domain.models.ArchingProduct
import com.joshdev.smartpocket.repository.database.Operations
import com.joshdev.smartpocket.repository.database.RealmDatabase
import com.joshdev.smartpocket.repository.database.entities.ArchingProductRealm
import kotlinx.coroutines.launch

class ArchingProductsViewModel: ViewModel() {
    val database = RealmDatabase.getInstance()
    private val activity = mutableStateOf<ArchingProductsActivity?>(null)
    private val context = mutableStateOf<Context?>(null)
    private val operations = Operations(database)

    private val _products = mutableStateOf<List<ArchingProduct>>(emptyList())
    val products: State<List<ArchingProduct>> = _products

    fun start(act: ArchingProductsActivity, ctx: Context) {
        activity.value = act
        context.value = ctx
        observeLedgers()
    }

    // Operations
    private fun observeLedgers() {
        viewModelScope.launch {
            operations.observeItems<ArchingProduct, ArchingProductRealm>().collect { products ->
                _products.value = products
            }
        }
    }
}