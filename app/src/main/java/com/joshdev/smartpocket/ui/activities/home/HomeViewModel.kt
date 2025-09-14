package com.joshdev.smartpocket.ui.activities.home

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshdev.smartpocket.domain.models.Ledger
import com.joshdev.smartpocket.repository.database.realm.RealmDBSingleton
import com.joshdev.smartpocket.ui.activities.invoiceList.InvoiceListActivity
import io.realm.kotlin.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val activity = mutableStateOf<HomeActivity?>(null)
    private val context = mutableStateOf<Context?>(null)
    private val database = mutableStateOf<Realm?>(null)

    private val _showNewRecordDialog = mutableStateOf(false)
    val showNewRecordDialog: State<Boolean> = _showNewRecordDialog

    val records: State<List<Ledger>> = mutableStateOf(listOf())

    fun start(act: HomeActivity, ctx: Context) {
        activity.value = act
        context.value = ctx
        database.value = RealmDBSingleton.getInstance(ctx)
    }

    fun addRecord(record: Ledger) {
        viewModelScope.launch(Dispatchers.IO) {
//            database.value?.recordDao()?.insert(record)
        }
    }

    fun toggleNewRecordDialog(value: Boolean? = null) {
        if (value != null) {
            _showNewRecordDialog.value = value
        } else {
            _showNewRecordDialog.value = !_showNewRecordDialog.value
        }
    }

    fun goToRecord(recordId: Int) {
        val goToInvoiceList = Intent(context.value, InvoiceListActivity::class.java)
        goToInvoiceList.putExtra("recordId", recordId)
        activity.value?.startActivity(goToInvoiceList)
    }
}

