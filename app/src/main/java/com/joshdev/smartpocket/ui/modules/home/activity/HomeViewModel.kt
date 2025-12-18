package com.joshdev.smartpocket.ui.modules.home.activity

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshdev.smartpocket.domain.currency.Currency
import com.joshdev.smartpocket.repository.database.room.AppDatabase
import com.joshdev.smartpocket.repository.database.room.AppDatabaseSingleton
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val database = mutableStateOf<AppDatabase?>(null)

    private val _allowExit = mutableStateOf(false)
    val allowExit: State<Boolean> = _allowExit

    private var _cameraAllowed = mutableStateOf(false)
    val cameraAllowed: State<Boolean> = _cameraAllowed

    fun start(context: Context) {
        database.value = AppDatabaseSingleton.getInstance(context)
        validateBaseCurrency()
    }

    private fun validateBaseCurrency() {
        val baseCurrency = Currency(
            name = "USD",
            symbol = "$",
            rate = 1.0
        )

        viewModelScope.launch {
            val currencyList = database.value?.currencyDao()?.getAllCurrencies()?.first()
            currencyList?.let { currencies ->
                val baseExist = currencies.any { it.name == "USD" && it.symbol == "$" }

                if (currencies.isEmpty()) {
                    database.value?.currencyDao()?.insert(baseCurrency)
                } else {
                    if (!baseExist) {
                        database.value?.currencyDao()?.insert(baseCurrency)
                    }
                }
            }
        }
    }

    fun setAllowExit(value: Boolean) {
        _allowExit.value = value
    }

    fun setCameraAllowed(value: Boolean) {
        _cameraAllowed.value = value
    }
}