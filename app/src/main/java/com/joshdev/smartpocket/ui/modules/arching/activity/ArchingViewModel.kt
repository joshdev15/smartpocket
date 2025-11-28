package com.joshdev.smartpocket.ui.modules.arching.activity

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.joshdev.smartpocket.domain.models.Arching
import com.joshdev.smartpocket.domain.models.ArchingRecord
import com.joshdev.smartpocket.repository.database.Operations
import com.joshdev.smartpocket.repository.database.RealmDatabase
import com.joshdev.smartpocket.repository.entities.ArchingRealm
import com.joshdev.smartpocket.repository.entities.ArchingRecordRealm
import com.joshdev.smartpocket.ui.activities.archingProducts.ArchingProductsActivity
import com.joshdev.smartpocket.ui.models.FastPanelOption
import com.joshdev.smartpocket.ui.utils.UiUtils.getIntentByFastOptionID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

class ArchingViewModel : ViewModel() {
    private val database = RealmDatabase.getInstance()
    private val activity = mutableStateOf<ArchingActivity?>(null)
    private val context = mutableStateOf<Context?>(null)
    private var navController = mutableStateOf<NavController?>(null)
    private val operations = Operations(database)

    private val _archingList = mutableStateOf<List<Arching>>(listOf())
    val archingList: State<List<Arching>> = _archingList

    private val _selectedArching = mutableStateOf<Arching?>(null)
    val selectedArching: State<Arching?> = _selectedArching

    private val _records = mutableStateOf<List<ArchingRecord>>(listOf())
    val records: State<List<ArchingRecord>> = _records

    private val _showNewArchingDialog = mutableStateOf(false)
    val showNewArchingDialog: State<Boolean> = _showNewArchingDialog

    private val _showArchingOptionsDialog = mutableStateOf(false)
    val showArchingOptionsDialog: State<Boolean> = _showArchingOptionsDialog

    private val _selectedRecord = mutableStateOf<ArchingRecord?>(null)
    val selectedRecord: State<ArchingRecord?> = _selectedRecord

    private val _showNewArchingRecordDialog = mutableStateOf(false)
    val showNewArchingRecordDialog: State<Boolean> = _showNewArchingRecordDialog

    private val _showArchingRecordOptionsDialog = mutableStateOf(false)
    val showArchingRecordOptionsDialog: State<Boolean> = _showArchingRecordOptionsDialog

    fun start(act: ArchingActivity, ctx: Context, nav: NavController) {
        activity.value = act
        context.value = ctx
        navController.value = nav
        observeArchings()
        observeArchingRecords()
    }

    fun navToRecords(archingId: String) {
        navController.value?.navigate("records/$archingId")
    }

    fun findArchingById(id: String) {
        _selectedArching.value = _archingList.value.find { it.id == id }
    }

    // Arching UI Actions
    fun toggleNewArchingDialog(value: Boolean? = null) {
        _showNewArchingDialog.value = value ?: !_showNewArchingDialog.value
    }

    fun toggleArchingOptionsDialog(arching: Arching?, value: Boolean? = null) {
        _selectedArching.value = arching
        _showArchingOptionsDialog.value = value ?: false
    }

    fun toggleArchingRecordOptionsDialog(record: ArchingRecord?, value: Boolean? = null) {
        _selectedRecord.value = record
        _showArchingRecordOptionsDialog.value = value ?: false
    }

    fun toggleNewArchingRecordDialog(value: Boolean? = null) {
        _showNewArchingRecordDialog.value = value ?: !_showNewArchingRecordDialog.value
    }

    fun goToArchingProducts(archingId: String) {
        val goToProductList = Intent(context.value, ArchingProductsActivity::class.java)
        goToProductList.putExtra("archingId", archingId)
        activity.value?.startActivity(goToProductList)
    }

    // Arching Operations
    private fun observeArchings() {
        viewModelScope.launch {
            operations.observeItems<Arching, ArchingRealm>().collect { archingList ->
                _archingList.value = archingList
            }
        }
    }

    fun addArching(arching: Arching) {
        viewModelScope.launch(Dispatchers.IO) {
            operations.addItem<Arching, ArchingRealm>(arching)
        }
    }

    private fun observeArchingRecords() {
        viewModelScope.launch {
            operations.observeItems<ArchingRecord, ArchingRecordRealm>().collect { records ->
                _records.value = records
            }
        }
    }

    fun addArchingRecord(archingId: String) {
        val calendar = Calendar.getInstance()
        val dayName =
            calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()) ?: ""
        val weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)
        val monthOfYear = calendar.get(Calendar.MONTH)

        val record = ArchingRecord(
            archingId = archingId,
            dayName = dayName,
            weekOfYear = weekOfYear,
            monthOfYear = monthOfYear
        )

        viewModelScope.launch(Dispatchers.IO) {
            operations.addItem<ArchingRecord, ArchingRecordRealm>(record)
        }
    }

    fun deleteArching() {
        selectedArching.value?.let { arching ->
            viewModelScope.launch(Dispatchers.IO) {
                operations.deleteItem<Arching, ArchingRealm>(arching.id)
            }
        }
    }

    fun deleteArchingRecord() {
        selectedRecord.value?.let { archingRecord ->
            viewModelScope.launch(Dispatchers.IO) {
                operations.deleteItem<ArchingRecord, ArchingRecordRealm>(archingRecord.id)
            }
        }
    }

    fun goTo(id: FastPanelOption.IDs) {
        getIntentByFastOptionID(id, context.value)?.let {
            activity.value?.startActivity(it)
        }
    }
}