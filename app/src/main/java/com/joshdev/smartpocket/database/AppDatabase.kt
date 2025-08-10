package com.joshdev.smartpocket.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joshdev.smartpocket.database.entity.invoice.Invoice
import com.joshdev.smartpocket.database.entity.invoice.InvoiceDao

@Database(entities = [Invoice::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun invoiceDao(): InvoiceDao
}