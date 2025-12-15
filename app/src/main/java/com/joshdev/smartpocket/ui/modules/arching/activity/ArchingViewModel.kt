package com.joshdev.smartpocket.ui.modules.arching.activity

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.joshdev.smartpocket.domain.arching.ArcProduct
import com.joshdev.smartpocket.domain.arching.ArcRecord
import com.joshdev.smartpocket.domain.arching.ArcRecordItem
import com.joshdev.smartpocket.domain.arching.Arching
import com.joshdev.smartpocket.domain.currency.Currency
import com.joshdev.smartpocket.repository.database.room.AppDatabase
import com.joshdev.smartpocket.repository.database.room.AppDatabaseSingleton
import com.joshdev.smartpocket.ui.models.FastPanelOption
import com.joshdev.smartpocket.ui.utils.UiUtils.getIntentByFastOptionID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

class ArchingViewModel : ViewModel() {
    private val database = mutableStateOf<AppDatabase?>(null)
    private val activity = mutableStateOf<ArchingActivity?>(null)
    private val context = mutableStateOf<Context?>(null)
    private var navController = mutableStateOf<NavController?>(null)

    // Jobs
    private val archingJob = mutableStateOf<Job?>(null)
    private val recordJob = mutableStateOf<Job?>(null)
    private val recordItemsJob = mutableStateOf<Job?>(null)
    private val productJob = mutableStateOf<Job?>(null)
    private val currencyJob = mutableStateOf<Job?>(null)

    fun start(act: ArchingActivity, ctx: Context, nav: NavController) {
        activity.value = act
        context.value = ctx
        navController.value = nav
        database.value = AppDatabaseSingleton.getInstance(ctx)

        getCurrencies()
        observeProducts()
        observeArchingList()
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
    fun navToRecords(archingId: Long) {
        navController.value?.navigate("records/$archingId")
    }

    fun findArchingById(id: String) {
//        _selectedArching.value = _archingList.value.find { it.id == id }
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
            database.value?.archingDao()?.getAllArching()?.collect { archingList ->
                _archingList.value = archingList
            }
        }
    }

    fun addArching(arching: Arching) {
        viewModelScope.launch(Dispatchers.IO) {
            database.value?.archingDao()?.insert(arching)
        }
    }

    fun deleteArching() {
        selectedArching.value?.let { arching ->
            viewModelScope.launch(Dispatchers.IO) {
                database.value?.archingDao()?.delete(arching)
            }
        }
    }

    ///////////////////
    //// ArcRecord ////
    ///////////////////

    // Declarations
    private val _records = mutableStateOf<List<ArcRecord>>(listOf())
    val records: State<List<ArcRecord>> = _records

    private val _selectedArcRecord = mutableStateOf<ArcRecord?>(null)
    val selectedArcRecord: State<ArcRecord?> = _selectedArcRecord

    private val _showNewRecordDialog = mutableStateOf(false)
    val showNewRecordDialog: State<Boolean> = _showNewRecordDialog

    private val _showRecordOptionsDialog = mutableStateOf(false)
    val showRecordOptionsDialog: State<Boolean> = _showRecordOptionsDialog

    // UI Actions
    fun navToRecordItem(recordId: Long) {
        navController.value?.navigate("arcRecordItems/$recordId")
    }

    fun toggleRecordOptionsDialog(arcRecord: ArcRecord?, value: Boolean? = null) {
        _selectedArcRecord.value = arcRecord
        _showRecordOptionsDialog.value = value ?: false
    }

    fun toggleNewRecordDialog(value: Boolean? = null) {
        _showNewRecordDialog.value = value ?: !_showNewRecordDialog.value
    }

    // Operations
    fun observeRecords(archingId: Long) {
        if (recordJob.value?.isActive == true) {
            recordJob.value?.cancel()
        }

        recordJob.value = viewModelScope.launch {
            database.value?.archingRecordDao()?.getRecordByArchingId(archingId)
                ?.collect { tmpRecords ->
                    _records.value = tmpRecords.filterNotNull()
                }
        }
    }

    fun addRecord(archingId: Long) {
        val calendar = Calendar.getInstance()
        val dayName = calendar.getDisplayName(
            Calendar.DAY_OF_WEEK,
            Calendar.LONG, Locale.getDefault()
        ) ?: ""
        val weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)
        val monthOfYear = calendar.get(Calendar.MONTH)

        val arcRecord = ArcRecord(
            archingId = archingId,
            dayName = dayName,
            weekOfYear = weekOfYear,
            monthOfYear = monthOfYear,
        )

        viewModelScope.launch(Dispatchers.IO) {
            database.value?.archingRecordDao()?.insert(arcRecord)
        }
    }

    fun deleteArchingRecord() {
        selectedArcRecord.value?.let { archingRecord ->
            viewModelScope.launch(Dispatchers.IO) {
                database.value?.archingRecordDao()?.delete(archingRecord)
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
            delay(500)
            _records.value = listOf()
            _selectedArcRecord.value = null
            _showNewRecordDialog.value = false
            _showRecordOptionsDialog.value = false
            recordJob.value?.cancel()
        }
    }

    /////////////////
    // ArcRecord Item //
    /////////////////

    // Declarations
    private val _recordItems = mutableStateOf<List<ArcRecordItem>>(listOf())
    val recordItems: State<List<ArcRecordItem>> = _recordItems

    private val _selectedItem = mutableStateOf<ArcRecordItem?>(null)
    val selectedItem: State<ArcRecordItem?> = _selectedItem

    private val _showNewItem = mutableStateOf(false)
    val showNewItem: State<Boolean> = _showNewItem

    private val _showItemOptions = mutableStateOf(false)
    val showItemOptions: State<Boolean> = _showItemOptions

    // UI Actions
    fun toggleItemOptionsDialog(arcRecord: ArcRecord?, value: Boolean? = null) {
        _selectedArcRecord.value = arcRecord
        _showRecordOptionsDialog.value = value ?: false
    }

    fun toggleNewItemDialog(value: Boolean? = null) {
        _showNewItem.value = value ?: !_showNewItem.value
    }

    // Operations
    fun observeRecordsItems(recordId: Long) {
        if (recordItemsJob.value?.isActive == true) {
            recordItemsJob.value?.cancel()
        }

        recordItemsJob.value = viewModelScope.launch {
//            operations.observeWithQuery<ArcRecordItem, ArchingRecordItemRealm>(
//                "recordId == $0",
//                ObjectId(recordId)
//            ).collect { recordItems ->
//                _Arc_recordItems.value = recordItems
//            }
        }
    }

    fun addAllItems(itemList: List<ArcRecordItem>, recordId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
//            val idProductList = itemList.map { ObjectId(it.productId) }
//
//            val dbResults = operations.getAllWithQuery<ArcRecordItem, ArchingRecordItemRealm>(
//                "recordId = $0 AND productId IN $1",
//                ObjectId(recordId),
//                idProductList
//            ).first()
//
//            val dbMap = dbResults.associateBy { it.productId }
//
//            val finalList = itemList.map { incomingItem ->
//                val existingItem = dbMap[incomingItem.productId]
//
//                if (existingItem != null) {
//                    incomingItem.copy(
//                        id = existingItem.id,
//                        quantity = existingItem.quantity + incomingItem.quantity
//                    )
//                } else {
//                    incomingItem
//                }
//            }
//
//            if (finalList.isNotEmpty()) {
//                operations.addAll<ArcRecordItem, ArchingRecordItemRealm>(finalList)
//            }
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
    private val _products = mutableStateOf<List<ArcProduct>>(emptyList())
    val products: State<List<ArcProduct>> = _products

    private val _showNewProductDialog = mutableStateOf(false)
    val showNewProductDialog: State<Boolean> = _showNewProductDialog

    // Operations
    private fun observeProducts() {
        viewModelScope.launch {
//            operations.observe<ArcProduct, ArchingProductRealm>().collect { productList ->
//                _products.value = productList
//            }
        }
    }

    fun toggleNewProductDialog(value: Boolean? = null) {
        _showNewProductDialog.value = value ?: !_showNewProductDialog.value
    }

    fun addProduct(archingArcProduct: ArcProduct) {
        viewModelScope.launch {
//            operations.add<ArcProduct, ArchingProductRealm>(archingArcProduct)
        }
    }

    ////////////////
    // Currencies //
    ////////////////

    // Declarations
    private val _currencies = mutableStateOf<List<Currency>>(emptyList())
    val currencies: State<List<Currency>> = _currencies

    private fun getCurrencies() {
        viewModelScope.launch {
//            operations.getAll<Currency, CurrencyRealm>()
//                .collect { currencies -> _currencies.value = currencies }
        }
    }
}