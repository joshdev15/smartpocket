package com.joshdev.smartpocket.src.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joshdev.smartpocket.src.database.entity.invoice.Invoice
import com.joshdev.smartpocket.src.database.entity.invoice.InvoiceDao

@Database(entities = [Invoice::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun invoiceDao(): InvoiceDao
}