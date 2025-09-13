package com.joshdev.smartpocket.ui.activities.currency

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.joshdev.smartpocket.domain.models.Currency
import com.joshdev.smartpocket.repository.database.AppDatabase
import com.joshdev.smartpocket.repository.database.AppDatabaseSingleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {
    private val activity = mutableStateOf<CurrencyActivity?>(null)
    private val context = mutableStateOf<Context?>(null)
    private val database = mutableStateOf<AppDatabase?>(null)

    private val _currencies = mutableStateOf<List<Currency>?>(null)
    val currencies: State<List<Currency>?> = _currencies;

    fun start(act: CurrencyActivity, ctx: Context) {
        activity.value = act
        context.value = ctx
        database.value = AppDatabaseSingleton.getInstance(ctx)

        CoroutineScope(Dispatchers.IO).launch {
            database.value?.currencyDao()?.getAllCurrencies()?.collect { currencies ->
                Log.i("CurrencyScreen", "${currencies.size}")
                _currencies.value = currencies
            }
        }
    }

    fun getCurrencyList() {
        CoroutineScope(Dispatchers.IO).launch {
            database.value?.currencyDao()?.getAllCurrencies()?.collect { currencies ->
                _currencies.value = currencies
            }
        }
    }

    suspend fun setCurrency(currency: Currency) {
        Log.i("CurrencyScreen", "Set currency")
        database.value?.currencyDao()?.insert(currency)
    }
}