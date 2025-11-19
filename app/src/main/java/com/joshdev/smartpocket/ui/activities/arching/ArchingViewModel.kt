package com.joshdev.smartpocket.ui.activities.arching

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshdev.smartpocket.domain.models.Arching
import com.joshdev.smartpocket.repository.database.Operations
import com.joshdev.smartpocket.repository.database.RealmDatabase
import com.joshdev.smartpocket.repository.models.ArchingRealm
import com.joshdev.smartpocket.ui.activities.archingProducts.ArchingProductsActivity
import com.joshdev.smartpocket.ui.models.FastPanelOption
import com.joshdev.smartpocket.ui.utils.UiUtils.getIntentByFastOptionID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArchingViewModel : ViewModel() {
    private val database = RealmDatabase.getInstance()
    private val activity = mutableStateOf<ArchingActivity?>(null)
    private val context = mutableStateOf<Context?>(null)
    private val operations = Operations(database)

    private val _showNewArchingDialog = mutableStateOf(false)
    val showNewArchingDialog: State<Boolean> = _showNewArchingDialog

    private val _showArchingOptionsDialog = mutableStateOf(false)
    val showArchingOptionsDialog: State<Boolean> = _showArchingOptionsDialog

    private val _archings = mutableStateOf<List<Arching>>(listOf())
    val archings: State<List<Arching>> = _archings

    private val _selectedArching = mutableStateOf<Arching?>(null)
    val selectedArching: State<Arching?> = _selectedArching

    fun start(act: ArchingActivity, ctx: Context) {
        activity.value = act
        context.value = ctx
        observeArchings()
    }

    // UI Actions
    fun toggleNewArchingDialog(value: Boolean? = null) {
        _showNewArchingDialog.value = value ?: !_showNewArchingDialog.value
    }

    fun toggleArchingOptionsDialog(arching: Arching?, value: Boolean? = null) {
        _selectedArching.value = arching
        _showArchingOptionsDialog.value = value ?: false
    }

    fun goToArchingProducts(archingId: String) {
        val goToProductList = Intent(context.value, ArchingProductsActivity::class.java)
        goToProductList.putExtra("archingId", archingId)
        activity.value?.startActivity(goToProductList)
    }

    // Operations
    private fun observeArchings() {
        viewModelScope.launch {
            operations.observeItems<Arching, ArchingRealm>().collect { archingList ->
                _archings.value = archingList
            }
        }
    }

    fun addArching(arching: Arching) {
        viewModelScope.launch(Dispatchers.IO) {
            operations.addItem<Arching, ArchingRealm>(arching)
        }
    }

    fun deleteArching() {
        selectedArching.value?.let { arching ->
            viewModelScope.launch(Dispatchers.IO) {
                operations.deleteItem<Arching, ArchingRealm>(arching.id)
            }
        }
    }

    fun goTo(id: FastPanelOption.IDs) {
        getIntentByFastOptionID(id, context.value)?.let {
            activity.value?.startActivity(it)
        }
    }
}