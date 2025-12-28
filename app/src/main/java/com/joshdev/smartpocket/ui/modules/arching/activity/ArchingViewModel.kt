package com.joshdev.smartpocket.ui.modules.arching.activity

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.joshdev.smartpocket.domain.arching.ArcCategory
import com.joshdev.smartpocket.domain.arching.ArcProduct
import com.joshdev.smartpocket.domain.arching.ArcRecord
import com.joshdev.smartpocket.domain.arching.ArcRecordDetails
import com.joshdev.smartpocket.domain.arching.ArcRecordItem
import com.joshdev.smartpocket.domain.arching.Arching
import com.joshdev.smartpocket.domain.currency.Currency
import com.joshdev.smartpocket.repository.database.room.AppDatabase
import com.joshdev.smartpocket.repository.database.room.AppDatabaseSingleton
import com.joshdev.smartpocket.ui.models.FastPanelOption
import com.joshdev.smartpocket.ui.utils.UiUtils.getIntentByFastOptionID
import com.joshdev.smartpocket.ui.utils.UiUtils.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
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

    fun toast(message: String) {
        context.value?.let {
            showToast(it, message)
        }
    }

    //////////////
    // Arching //
    /////////////

    // Declarations
    private val _archingList = mutableStateOf<List<Arching>>(listOf())
    val archingList: State<List<Arching>> = _archingList

    private val _currentArching = mutableStateOf<Arching?>(null)
    val currentArching: State<Arching?> = _currentArching

    private val _selectedArching = mutableStateOf<Arching?>(null)
    val selectedArching: State<Arching?> = _selectedArching

    private val _showNewArching = mutableStateOf(false)
    val showNewArching: State<Boolean> = _showNewArching

    private val _showArchingOptions = mutableStateOf(false)
    val showArchingOptionsDialog: State<Boolean> = _showArchingOptions

    // UI Actions
    fun navToRecords(archingId: Long) {
        _currentArching.value = archingList.value.find { it.id == archingId }
        navController.value?.navigate("records/$archingId")
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

    /////////////////
    //// Records ////
    /////////////////

    // Declarations
    private val _records = mutableStateOf<List<ArcRecord>>(listOf())
    val records: State<List<ArcRecord>> = _records

    private val _currentRecord = mutableStateOf<ArcRecord?>(null)
    val currentRecord: State<ArcRecord?> = _currentRecord

    private val _selectedRecord = mutableStateOf<ArcRecord?>(null)
    val selectedRecord: State<ArcRecord?> = _selectedRecord

    private val _showNewRecordDialog = mutableStateOf(false)
    val showNewRecordDialog: State<Boolean> = _showNewRecordDialog

    private val _showRecordOptionsDialog = mutableStateOf(false)
    val showRecordOptionsDialog: State<Boolean> = _showRecordOptionsDialog

    private val _showRecordTotalizerDialog = mutableStateOf(false)
    val showRecordTotalizerDialog: State<Boolean> = _showRecordTotalizerDialog

    private val _totalsMap = mutableStateOf<Map<String?, Double?>>(mapOf())
    val totalsMap: State<Map<String?, Double?>> = _totalsMap

    // UI Actions
    fun navToRecordItem(recordId: Long) {
        navController.value?.navigate("recordItems/$recordId")
    }

    fun toggleRecordOptionsDialog(arcRecord: ArcRecord?, value: Boolean? = null) {
        _selectedRecord.value = arcRecord
        _showRecordOptionsDialog.value = value ?: false
    }

    fun toggleNewRecordDialog(value: Boolean? = null) {
        _showNewRecordDialog.value = value ?: !_showNewRecordDialog.value
    }

    fun toggleRecordTotalizerDialog(value: Boolean? = null) {
        _showRecordTotalizerDialog.value = value ?: false

        calculateArchingTotalAmount(currentArching.value?.id ?: 0)

        viewModelScope.launch(Dispatchers.IO) {
            getArchingCategoriesTotals(currentArching.value?.id ?: 0)
        }
    }

    // Operations
    fun observeRecords(archingId: Long) {
        _currentArching.value = archingList.value.find { it.id == archingId }

        if (recordJob.value?.isActive == true) {
            recordJob.value?.cancel()
        }

        recordJob.value = viewModelScope.launch {
            database.value?.arcRecordDao()?.getRecordByArchingId(archingId)?.collect { tmpRecords ->
                _records.value = tmpRecords
            }
        }
    }

    suspend fun getArchingCategoriesTotals(archingId: Long) {
        database.value?.arcRecordDao()?.getCategoryTotals(archingId)?.first()?.let { totals ->
            _totalsMap.value = totals
        }
    }

    fun addRecord(
        archingId: Long,
        name: String? = null,
        isDeduction: Boolean = true,
        deductionAmount: Double = 0.0
    ) {
        val calendar = Calendar.getInstance()
        val dayName = calendar.getDisplayName(
            Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()
        ) ?: ""

        val arcRecord = ArcRecord(
            name = name ?: dayName,
            archingId = archingId,
            totalAmount = if (isDeduction) deductionAmount else 0.0,
            creationDate = calendar.timeInMillis,
            type = if (isDeduction) ArcRecord.RecType.Deduction else ArcRecord.RecType.WorkingDay
        )

        viewModelScope.launch(Dispatchers.IO) {
            database.value?.arcRecordDao()?.insert(arcRecord)
        }
    }

    fun deleteArchingRecord() {
        selectedRecord.value?.let { archingRecord ->
            viewModelScope.launch(Dispatchers.IO) {
                database.value?.arcRecordDao()?.delete(archingRecord)
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
            _selectedRecord.value = null
            _showNewRecordDialog.value = false
            _showRecordOptionsDialog.value = false
            _totalsMap.value = mapOf()
            recordJob.value?.cancel()
        }
    }

    /////////////////////
    //// Record Item ////
    /////////////////////

    // Declarations
    private val _recordItems = mutableStateOf<List<ArcRecordItem>>(listOf())
    val recordItems: State<List<ArcRecordItem>> = _recordItems

    private val _selectedItem = mutableStateOf<ArcRecordItem?>(null)
    val selectedItem: State<ArcRecordItem?> = _selectedItem

    private val _showNewItem = mutableStateOf(false)
    val showNewItem: State<Boolean> = _showNewItem

    private val _showItemOptions = mutableStateOf(false)
    val showItemOptions: State<Boolean> = _showItemOptions

    private val _showItemTotalizer = mutableStateOf(false)
    val showItemTotalizer: State<Boolean> = _showItemTotalizer

    private val _itemTotals = mutableStateOf<List<ArcRecordDetails>>(listOf())
    val itemTotals: State<List<ArcRecordDetails>> = _itemTotals

    // UI Actions
    fun toggleItemOptionsDialog(arcRecordItem: ArcRecordItem?, show: Boolean? = null) {
        _selectedItem.value = arcRecordItem
        _showRecordOptionsDialog.value = show ?: false
    }

    fun toggleNewItemDialog(value: Boolean? = null) {
        _showNewItem.value = value ?: !_showNewItem.value
    }

    fun toggleItemTotalizer(value: Boolean? = null) {
        _showItemTotalizer.value = value ?: false
        viewModelScope.launch {
            currentArching.value?.id?.let { archingId ->
                currentRecord.value?.id?.let { recordId ->
                    getRecordCategoriesTotals(archingId, recordId)
                }
            }
        }
    }

    // Operations
    fun observeRecordsItems(recordId: Long) {
        _currentRecord.value = records.value.find { it.id == recordId }

        if (recordItemsJob.value?.isActive == true) {
            recordItemsJob.value?.cancel()
        }

        recordItemsJob.value = viewModelScope.launch {
            database.value?.arcRecordItemDao()?.getAllRecordItemsByRecordId(recordId)
                ?.collect { tmpRecordItems ->
                    _recordItems.value = tmpRecordItems
                }
        }

        calculateTotalAmount(recordId)
    }

    fun calculateArchingTotalAmount(archingId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val recordsInArching =
                database.value?.arcRecordDao()?.getRecordByArchingId(archingId)?.first()

            recordsInArching?.forEach { tmpRecord ->
                var totalAmount = 0.0
                val allRecordItems = database.value?.arcRecordItemDao()
                    ?.getAllRecordItemsByRecordId(tmpRecord.id ?: 0L)?.first()

                allRecordItems?.let { tmpRecordItems ->
                    tmpRecordItems.forEach { recItem ->
                        val productRelated = products.value.find { it.id == recItem.productId }
                        productRelated?.let {
                            totalAmount += it.price * recItem.quantity
                        }
                    }
                }

                val updatedRecord = tmpRecord.copy(totalAmount = totalAmount)
                database.value?.arcRecordDao()?.update(updatedRecord)
            }
        }
    }

    suspend fun getRecordCategoriesTotals(archingId: Long, recordId: Long) {
        database.value?.arcRecordItemDao()?.getCategoryTotals(archingId, recordId)?.first()
            ?.let { totals ->
                _itemTotals.value = totals
            }
    }

    fun addAllItems(itemList: List<ArcRecordItem>, recordId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val idProductList = itemList.mapNotNull { it.productId }

            val dbResults = database.value?.arcRecordItemDao()
                ?.getAllByRecordIdAndProductId(recordId, idProductList)?.first()

            dbResults?.let {
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
                    database.value?.arcRecordItemDao()?.insertAll(finalList)
                }
            }
        }

        calculateTotalAmount(recordId)
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

    fun calculateTotalAmount(recordId: Long) {
        viewModelScope.launch {
            var totalAmount = 0.0
            val allRecordItems =
                database.value?.arcRecordItemDao()?.getAllRecordItemsByRecordId(recordId)?.first()
            allRecordItems?.let { tmpRecordItems ->

                tmpRecordItems.forEach { recItem ->
                    val productRelated = products.value.find { it.id == recItem.productId }
                    productRelated?.let {
                        totalAmount += it.price * recItem.quantity
                    }
                }
            }

            currentRecord.value?.let {
                val tmpCurrentRecord = it.copy(totalAmount = totalAmount)
                _currentRecord.value = tmpCurrentRecord
                database.value?.arcRecordDao()?.update(tmpCurrentRecord)
            }
        }
    }

    //////////////
    // Products //
    //////////////

    // Declarations
    private val _products = mutableStateOf<List<ArcProduct>>(emptyList())
    val products: State<List<ArcProduct>> = _products

    private val _selectedProduct = mutableStateOf<ArcProduct?>(null)
    val selectedProduct: State<ArcProduct?> = _selectedProduct

    private val _showNewProductDialog = mutableStateOf(false)
    val showNewProductDialog: State<Boolean> = _showNewProductDialog

    private val _showProductOptionsDialog = mutableStateOf(false)
    val showProductOptionsDialog: State<Boolean> = _showProductOptionsDialog

    // Operations
    private fun observeProducts() {
        viewModelScope.launch {
            database.value?.arcProductDao()?.getAllProducts()?.collect { tmpProducts ->
                _products.value = tmpProducts.filterNotNull()
            }
        }
    }

    fun toggleNewProductDialog(value: Boolean? = null) {
        _showNewProductDialog.value = value ?: !_showNewProductDialog.value
    }

    fun toggleProductOptionsDialog(product: ArcProduct?, value: Boolean? = null) {
        _selectedProduct.value = product
        _showProductOptionsDialog.value = value ?: false
    }

    fun addProduct(archingArcProduct: ArcProduct) {
        viewModelScope.launch {
            database.value?.arcProductDao()?.insert(archingArcProduct)
        }
    }

    fun deleteProduct() {
        selectedProduct.value?.let { product ->
            viewModelScope.launch(Dispatchers.IO) {
                database.value?.arcProductDao()?.delete(product)
            }
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
            database.value?.currencyDao()?.getAllCurrencies()?.collect { tmpCurrencies ->
                _currencies.value = tmpCurrencies
            }
        }
    }

    ////////////////////
    //// Categories ////
    ////////////////////

    // Categories Declarations
    private val _showNewCategory = mutableStateOf(false)
    val showNewCategory: State<Boolean> = _showNewCategory

    private val _showCategoryOptions = mutableStateOf(false)
    val showCategoryOptions: State<Boolean> = _showCategoryOptions

    private val _categories = mutableStateOf<List<ArcCategory>>(emptyList())
    val categories: State<List<ArcCategory>> = _categories

    private val _selectedCategory = mutableStateOf<ArcCategory?>(null)
    val selectedCategory: State<ArcCategory?> = _selectedCategory

    // Transactions UI Actions
    fun toggleNewCategory(value: Boolean?) {
        if (value != null) {
            _showNewCategory.value = value
        } else {
            _showNewCategory.value = !_showNewCategory.value
        }
    }

    fun toggleCategoryOptions(category: ArcCategory?, value: Boolean) {
        _selectedCategory.value = category
        _showCategoryOptions.value = value
    }

    // Transactions Operations
    fun observeCategories() {
        viewModelScope.launch {
            database.value?.arcCategoryDao()?.getAllCategories()?.collect { tmpCategoryList ->
                _categories.value = tmpCategoryList
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            database.value?.arcCategoryDao()?.getAllCategories()?.first()?.let { tmpCategories ->
                _categories.value = tmpCategories
            }
        }
    }

    fun addCategory(category: ArcCategory) {
        viewModelScope.launch {
            database.value?.arcCategoryDao()?.insert(category)
        }
    }

    fun deleteCategory() {
        selectedCategory.value?.let { category ->
            viewModelScope.launch(Dispatchers.IO) {
                database.value?.arcCategoryDao()?.delete(category)
            }
        }
    }
}