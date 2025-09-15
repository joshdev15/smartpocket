package com.joshdev.smartpocket.ui.activities.currency

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshdev.smartpocket.domain.models.Currency
import com.joshdev.smartpocket.domain.models.CurrencyRealm
import com.joshdev.smartpocket.repository.database.realm.RealmDatabase
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {
    private val database = RealmDatabase.getInstance()
    private val activity = mutableStateOf<CurrencyActivity?>(null)
    private val context = mutableStateOf<Context?>(null)

    private val _currencies = mutableStateOf<List<Currency>?>(null)
    val currencies: State<List<Currency>?> = _currencies;

    fun start(act: CurrencyActivity, ctx: Context) {
        activity.value = act
        context.value = ctx

        observeLedgers()
    }

    private fun observeLedgers() {
        viewModelScope.launch {
            database.let { realm ->
                realm.query<CurrencyRealm>()
                    .asFlow()
                    .map { results ->
                        results.list.map { currencyRealm ->
                            currencyRealm.toCurrency()
                        }
                    }
                    .collect { ledgerList ->
                        _currencies.value = ledgerList
                    }
            }
        }
    }

    suspend fun setCurrency(currency: Currency) {
        Log.i("CurrencyScreen", "Set currency")
//        database.value?.currencyDao()?.insert(currency)
    }
}