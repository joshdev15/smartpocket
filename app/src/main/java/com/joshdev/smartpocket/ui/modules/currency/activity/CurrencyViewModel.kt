package com.joshdev.smartpocket.ui.modules.currency.activity

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshdev.smartpocket.domain.currency.Currency
import com.joshdev.smartpocket.repository.database.room.AppDatabase
import com.joshdev.smartpocket.repository.database.room.AppDatabaseSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {
    private val database = mutableStateOf<AppDatabase?>(null)
    private val activity = mutableStateOf<CurrencyActivity?>(null)
    private val context = mutableStateOf<Context?>(null)

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
        database.value = AppDatabaseSingleton.getInstance(ctx)

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
            database.value?.currencyDao()?.getAllCurrencies()?.collect { tmpCurrencies ->
                _currencies.value = tmpCurrencies.filterNotNull()
            }
        }
    }

    fun addCurrency(currency: Currency) {
        viewModelScope.launch {
            database.value?.currencyDao()?.insert(currency)
        }
    }

    fun deleteCurrency() {
        selectedCurrency.value?.let { currency ->
            viewModelScope.launch(Dispatchers.IO) {
                if (currency.name != "USD") {
                    database.value?.currencyDao()?.delete(currency)
                }
            }
        }
    }
}