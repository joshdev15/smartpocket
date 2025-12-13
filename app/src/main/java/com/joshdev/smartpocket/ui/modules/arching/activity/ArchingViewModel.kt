package com.joshdev.smartpocket.ui.modules.arching.activity

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.joshdev.smartpocket.domain.arching.Arching
import com.joshdev.smartpocket.domain.arching.Product
import com.joshdev.smartpocket.domain.arching.Record
import com.joshdev.smartpocket.domain.arching.RecordItem
import com.joshdev.smartpocket.repository.database.Operations
import com.joshdev.smartpocket.repository.database.RealmDatabase
import com.joshdev.smartpocket.repository.entities.arching.ArchingProductRealm
import com.joshdev.smartpocket.repository.entities.arching.ArchingRealm
import com.joshdev.smartpocket.repository.entities.arching.ArchingRecordItemRealm
import com.joshdev.smartpocket.repository.entities.arching.ArchingRecordRealm
import com.joshdev.smartpocket.ui.models.FastPanelOption
import com.joshdev.smartpocket.ui.utils.UiUtils.getIntentByFastOptionID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import java.util.Calendar
import java.util.Locale

class ArchingViewModel : ViewModel() {
    private val database = RealmDatabase.getInstance()
    private val activity = mutableStateOf<ArchingActivity?>(null)
    private val context = mutableStateOf<Context?>(null)
    private var navController = mutableStateOf<NavController?>(null)
    private val operations = Operations(database)

    // Jobs
    private val archingJob = mutableStateOf<Job?>(null)
    private val recordJob = mutableStateOf<Job?>(null)
    private val recordItemsJob = mutableStateOf<Job?>(null)
    private val productJob = mutableStateOf<Job?>(null)

    fun start(act: ArchingActivity, ctx: Context, nav: NavController) {
        activity.value = act
        context.value = ctx
        navController.value = nav

        observeProducts()
        observeArchingList()
//        observeRecords()
//        observeRecordsItems()
    }

    //////////////
    // Arching //
    /////////////

    // Declarations
    private val _archingList = mutableStateOf<List<Arching>>(listOf())
    val archingList: State<List<Arching>> = _archingList

    private val _selectedArching = mutableStateOf<Arching?>(null)
    val selectedArching: State<Arching?> = _selectedArching

    private val _showNewArching = mutableStateOf(false)
    val showNewArching: State<Boolean> = _showNewArching

    private val _showArchingOptions = mutableStateOf(false)
    val showArchingOptionsDialog: State<Boolean> = _showArchingOptions

    // UI Actions
    fun navToRecords(archingId: String) {
        navController.value?.navigate("records/$archingId")
    }

    fun findArchingById(id: String) {
        _selectedArching.value = _archingList.value.find { it.id == id }
    }

    fun toggleNewArchingDialog(value: Boolean? = null) {
        _showNewArching.value = value ?: !_showNewArching.value
    }

    fun toggleArchingOptionsDialog(arching: Arching?, value: Boolean? = null) {
        _selectedArching.value = arching
        _showArchingOptions.value = value ?: false
    }

    // Operations
    private fun observeArchingList() {
        archingJob.value = viewModelScope.launch {
            operations.observe<Arching, ArchingRealm>().collect { archingList ->
                _archingList.value = archingList
            }
        }
    }

    fun addArching(arching: Arching) {
        viewModelScope.launch(Dispatchers.IO) {
            operations.add<Arching, ArchingRealm>(arching)
        }
    }

    fun deleteArching() {
        selectedArching.value?.let { arching ->
            viewModelScope.launch(Dispatchers.IO) {
                operations.delete<Arching, ArchingRealm>(arching.id)
            }
        }
    }

    ////////////
    // Record //
    ////////////

    // Declarations
    private val _records = mutableStateOf<List<Record>>(listOf())
    val records: State<List<Record>> = _records

    private val _selectedRecord = mutableStateOf<Record?>(null)
    val selectedRecord: State<Record?> = _selectedRecord

    private val _showNewRecordDialog = mutableStateOf(false)
    val showNewRecordDialog: State<Boolean> = _showNewRecordDialog

    private val _showRecordOptionsDialog = mutableStateOf(false)
    val showRecordOptionsDialog: State<Boolean> = _showRecordOptionsDialog

    // UI Actions
    fun navToRecordItem(recordId: String) {
        navController.value?.navigate("recordItems/$recordId")
    }

    fun toggleRecordOptionsDialog(record: Record?, value: Boolean? = null) {
        _selectedRecord.value = record
        _showRecordOptionsDialog.value = value ?: false
    }

    fun toggleNewRecordDialog(value: Boolean? = null) {
        _showNewRecordDialog.value = value ?: !_showNewRecordDialog.value
    }

    // Operations
    fun observeRecords(archingId: String) {
        if (recordJob.value?.isActive == true) {
            recordJob.value?.cancel()
        }

        recordJob.value = viewModelScope.launch {
            operations.observeWithQuery<Record, ArchingRecordRealm>(
                "archingId == $0",
                ObjectId(archingId)
            ).collect { records ->
                _records.value = records
            }
        }
    }

    fun addRecord(archingId: String) {
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
            operations.add<Record, ArchingRecordRealm>(record)
        }
    }

    fun deleteArchingRecord() {
        selectedRecord.value?.let { archingRecord ->
            viewModelScope.launch(Dispatchers.IO) {
                operations.delete<Record, ArchingRecordRealm>(archingRecord.id)
            }
        }
    }

    fun goTo(id: FastPanelOption.IDs) {
        navController.value?.let { nav ->
            getIntentByFastOptionID(id, context.value, nav)?.let { currentIntent ->
                activity.value?.startActivity(currentIntent)
            }
        }
    }

    fun cleanRecordStates() {
        navController.value?.popBackStack()
        viewModelScope.launch {
            delay(600)
            _records.value = listOf()
            _selectedRecord.value = null
            _showNewRecordDialog.value = false
            _showRecordOptionsDialog.value = false
            recordJob.value?.cancel()
        }
    }

    /////////////////
    // Record Item //
    /////////////////

    // Declarations
    private val _recordItems = mutableStateOf<List<RecordItem>>(listOf())
    val recordItems: State<List<RecordItem>> = _recordItems

    private val _selectedItem = mutableStateOf<RecordItem?>(null)
    val selectedItem: State<RecordItem?> = _selectedItem

    private val _showNewItem = mutableStateOf(false)
    val showNewItem: State<Boolean> = _showNewItem

    private val _showItemOptions = mutableStateOf(false)
    val showItemOptions: State<Boolean> = _showItemOptions

    // UI Actions
    fun toggleItemOptionsDialog(record: Record?, value: Boolean? = null) {
        _selectedRecord.value = record
        _showRecordOptionsDialog.value = value ?: false
    }

    fun toggleNewItemDialog(value: Boolean? = null) {
        _showNewItem.value = value ?: !_showNewItem.value
    }

    // Operations
    fun observeRecordsItems(recordId: String) {
        if (recordItemsJob.value?.isActive == true) {
            recordItemsJob.value?.cancel()
        }

        recordItemsJob.value = viewModelScope.launch {
            operations.observeWithQuery<RecordItem, ArchingRecordItemRealm>(
                "recordId == $0",
                ObjectId(recordId)
            ).collect { recordItems ->
                _recordItems.value = recordItems
            }
        }
    }

    fun addAllItems(itemList: List<RecordItem>, recordId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val idProductList = itemList.map { ObjectId(it.productId) }

            val dbResults = operations.getAllWithQuery<RecordItem, ArchingRecordItemRealm>(
                "recordId = $0 AND productId IN $1",
                ObjectId(recordId),
                idProductList
            ).first()

            val dbMap = dbResults.associateBy { it.productId }

            val finalList = itemList.map { incomingItem ->
                val existingItem = dbMap[incomingItem.productId]

                if (existingItem != null) {
                    incomingItem.copy(
                        id = existingItem.id,
                        quantity = existingItem.quantity + incomingItem.quantity
                    )
                } else {
                    incomingItem
                }
            }

            if (finalList.isNotEmpty()) {
                operations.addAll<RecordItem, ArchingRecordItemRealm>(finalList)
            }
        }
    }

    fun cleanRecordItemsStates() {
        navController.value?.popBackStack()
        viewModelScope.launch {
            delay(600)
            _recordItems.value = listOf()
            _selectedItem.value = null
            _showNewItem.value = false
            _showItemOptions.value = false
            recordItemsJob.value?.cancel()
        }
    }

    //////////////
    // Products //
    //////////////

    // Declarations
    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: State<List<Product>> = _products

    private val _showNewProductDialog = mutableStateOf(false)
    val showNewProductDialog: State<Boolean> = _showNewProductDialog

    // Operations
    private fun observeProducts() {
        viewModelScope.launch {
            operations.observe<Product, ArchingProductRealm>().collect { productList ->
                _products.value = productList
            }
        }
    }

    fun toggleNewProductDialog(value: Boolean? = null) {
        _showNewProductDialog.value = value ?: !_showNewProductDialog.value
    }

    fun addProduct(archingProduct: Product) {
        viewModelScope.launch {
            operations.add<Product, ArchingProductRealm>(archingProduct)
        }
    }
}