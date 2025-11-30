package com.joshdev.smartpocket.ui.modules.arching.activity

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.joshdev.smartpocket.domain.arching.Arching
import com.joshdev.smartpocket.domain.arching.Record
import com.joshdev.smartpocket.repository.database.Operations
import com.joshdev.smartpocket.repository.database.RealmDatabase
import com.joshdev.smartpocket.repository.entities.arching.ArchingRealm
import com.joshdev.smartpocket.repository.entities.arching.ArchingRecordRealm
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

    // Arching Declarations
    private val _archingList = mutableStateOf<List<Arching>>(listOf())
    val archingList: State<List<Arching>> = _archingList

    private val _selectedArching = mutableStateOf<Arching?>(null)
    val selectedArching: State<Arching?> = _selectedArching

    private val _showNewArchingDialog = mutableStateOf(false)
    val showNewArchingDialog: State<Boolean> = _showNewArchingDialog

    private val _showArchingOptionsDialog = mutableStateOf(false)
    val showArchingOptionsDialog: State<Boolean> = _showArchingOptionsDialog

    // Record Declarations
    private val _records = mutableStateOf<List<Record>>(listOf())
    val records: State<List<Record>> = _records

    private val _selectedRecord = mutableStateOf<Record?>(null)
    val selectedRecord: State<Record?> = _selectedRecord

    private val _showNewRecordDialog = mutableStateOf(false)
    val showNewRecordDialog: State<Boolean> = _showNewRecordDialog

    private val _showRecordOptionsDialog = mutableStateOf(false)
    val showRecordOptionsDialog: State<Boolean> = _showRecordOptionsDialog

    fun start(act: ArchingActivity, ctx: Context, nav: NavController) {
        activity.value = act
        context.value = ctx
        navController.value = nav
        observeArchingList()
        observeArchingRecords()
    }

    // Arching UI Actions
    fun navToRecords(archingId: String) {
        navController.value?.navigate("records/$archingId")
    }

    fun findArchingById(id: String) {
        _selectedArching.value = _archingList.value.find { it.id == id }
    }

    fun toggleNewArchingDialog(value: Boolean? = null) {
        _showNewArchingDialog.value = value ?: !_showNewArchingDialog.value
    }

    fun toggleArchingOptionsDialog(arching: Arching?, value: Boolean? = null) {
        _selectedArching.value = arching
        _showArchingOptionsDialog.value = value ?: false
    }

    // Arching Record UI Actions
    fun toggleRecordOptionsDialog(record: Record?, value: Boolean? = null) {
        _selectedRecord.value = record
        _showRecordOptionsDialog.value = value ?: false
    }

    fun toggleNewRecordDialog(value: Boolean? = null) {
        _showNewRecordDialog.value = value ?: !_showNewRecordDialog.value
    }

    // Arching Operations
    private fun observeArchingList() {
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

    fun deleteArching() {
        selectedArching.value?.let { arching ->
            viewModelScope.launch(Dispatchers.IO) {
                operations.deleteItem<Arching, ArchingRealm>(arching.id)
            }
        }
    }

    // Arching Record Operations
    private fun observeArchingRecords() {
        viewModelScope.launch {
            operations.observeItems<Record, ArchingRecordRealm>().collect { records ->
                _records.value = records
            }
        }
    }

    fun addArchingRecord(archingId: String) {
        val calendar = Calendar.getInstance()
        val dayName = calendar.getDisplayName(
            Calendar.DAY_OF_WEEK,
            Calendar.LONG, Locale.getDefault()
        ) ?: ""
        val weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)
        val monthOfYear = calendar.get(Calendar.MONTH)

        val record = Record(
            archingId = archingId,
            dayName = dayName,
            weekOfYear = weekOfYear,
            monthOfYear = monthOfYear
        )

        viewModelScope.launch(Dispatchers.IO) {
            operations.addItem<Record, ArchingRecordRealm>(record)
        }
    }

    fun deleteArchingRecord() {
        selectedRecord.value?.let { archingRecord ->
            viewModelScope.launch(Dispatchers.IO) {
                operations.deleteItem<Record, ArchingRecordRealm>(archingRecord.id)
            }
        }
    }

    fun goTo(id: FastPanelOption.IDs) {
        getIntentByFastOptionID(id, context.value)?.let {
            activity.value?.startActivity(it)
        }
    }
}