package com.joshdev.smartpocket.ui.activities.currency

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshdev.smartpocket.domain.models.Currency
import com.joshdev.smartpocket.repository.database.Operations
import com.joshdev.smartpocket.repository.database.RealmDatabase
import com.joshdev.smartpocket.repository.models.CurrencyRealm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {
    private val database = RealmDatabase.getInstance()
    private val activity = mutableStateOf<CurrencyActivity?>(null)
    private val context = mutableStateOf<Context?>(null)
    private val operations = Operations(database)

    private val _showNewCurrencyDialog = mutableStateOf(false)
    val showNewCurrencyDialog: State<Boolean> = _showNewCurrencyDialog

    private val _showCurrencyOptionsDialog = mutableStateOf(false)
    val showCurrencyOptionsDialog: State<Boolean> = _showCurrencyOptionsDialog

    private val _selectedCurrency = mutableStateOf<Currency?>(null)
    val selectedCurrency: State<Currency?> = _selectedCurrency

    private val _currencies = mutableStateOf<List<Currency>?>(null)
    val currencies: State<List<Currency>?> = _currencies;

    fun start(act: CurrencyActivity, ctx: Context) {
        activity.value = act
        context.value = ctx

        observeLedgers()
    }

    // UI Actions
    fun toggleNewCurrencyDialog(value: Boolean? = null) {
        if (value != null) {
            _showNewCurrencyDialog.value = value
        } else {
            _showNewCurrencyDialog.value = !_showNewCurrencyDialog.value
        }
    }

    fun toggleCurrencyOptionsDialog(value: Boolean? = null, currency: Currency? = null) {
        _selectedCurrency.value = currency
        _showCurrencyOptionsDialog.value = value ?: false
    }

    // Operations
    private fun observeLedgers() {
        viewModelScope.launch {
            operations.observeItems<Currency, CurrencyRealm>().collect {
                _currencies.value = it
            }
        }
    }

    fun addCurrency(currency: Currency) {
        viewModelScope.launch {
            operations.addItem<Currency, CurrencyRealm>(currency)
        }
    }

    fun deleteCurrency() {
        selectedCurrency.value?.let { currency ->
            viewModelScope.launch(Dispatchers.IO) {
                operations.deleteItem<Currency, CurrencyRealm>(currency.id)
            }
        }
    }
}