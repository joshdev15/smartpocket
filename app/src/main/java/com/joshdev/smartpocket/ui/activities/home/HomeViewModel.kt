package com.joshdev.smartpocket.ui.activities.home

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshdev.smartpocket.domain.models.GeneralRecord
import com.joshdev.smartpocket.repository.database.AppDatabase
import com.joshdev.smartpocket.repository.database.AppDatabaseSingleton
import com.joshdev.smartpocket.ui.activities.invoice.InvoiceListActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val activity = mutableStateOf<HomeActivity?>(null)
    private val context = mutableStateOf<Context?>(null)
    private val database = mutableStateOf<AppDatabase?>(null)

    private val _showNewRecordDialog = mutableStateOf(false)
    val showNewRecordDialog: State<Boolean> = _showNewRecordDialog

    val records: StateFlow<List<GeneralRecord>> = flow {
        database.value?.recordDao()?.getAllRecords()?.collect {
            emit(it)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun start(act: HomeActivity, ctx: Context) {
        activity.value = act
        context.value = ctx
        database.value = AppDatabaseSingleton.getInstance(ctx)
    }

    fun addRecord(record: GeneralRecord) {
        viewModelScope.launch(Dispatchers.IO) {
            database.value?.recordDao()?.insert(record)
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

