package com.joshdev.smartpocket.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joshdev.smartpocket.domain.models.Category
import com.joshdev.smartpocket.domain.models.Currency
import com.joshdev.smartpocket.domain.models.GeneralRecord
import com.joshdev.smartpocket.domain.models.Invoice
import com.joshdev.smartpocket.domain.models.Product
import com.joshdev.smartpocket.repository.database.dao.CategoryDao
import com.joshdev.smartpocket.repository.database.dao.CurrencyDao
import com.joshdev.smartpocket.repository.database.dao.InvoiceDao
import com.joshdev.smartpocket.repository.database.dao.ProductDao
import com.joshdev.smartpocket.repository.database.dao.GeneralRecordDao

@Database(
    entities = [GeneralRecord::class, Invoice::class, Product::class, Category::class, Currency::class],
    version = 13,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordDao(): GeneralRecordDao
    abstract fun invoiceDao(): InvoiceDao
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun currencyDao(): CurrencyDao
}