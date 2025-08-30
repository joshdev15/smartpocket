package com.joshdev.smartpocket.src.database.entity.invoice

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface InvoiceDao {
    @Insert
    suspend fun insert(invoice: Invoice)

    @Update
    suspend fun update(invoice: Invoice)

    @Delete
    suspend fun delete(invoice: Invoice)

    @Query("SELECT * FROM invoices WHERE id = :invoiceId")
    suspend fun getInvoiceById(invoiceId: Long): Invoice?

    @Query("SELECT * FROM invoices ORDER BY creationDate ASC")
    fun getAllInvoices(): Flow<List<Invoice>>

    @Query("DELETE FROM invoices")
    suspend fun deleteAllInvoices()
}
