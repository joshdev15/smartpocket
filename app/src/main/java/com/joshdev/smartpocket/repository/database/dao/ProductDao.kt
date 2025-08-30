package com.joshdev.smartpocket.repository.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.joshdev.smartpocket.domain.models.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert
    suspend fun insert(product: Product)

    @Update
    suspend fun update(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("SELECT * FROM products WHERE invoiceId = :invoiceId")
    fun getProductsByInvoiceId(invoiceId: Int): Flow<List<Product>>?

    @Query("SELECT * FROM products ORDER BY `order` ASC")
    fun getAllProducts(): Flow<List<Product>>

    @Query("DELETE FROM products")
    suspend fun deleteAllProducts()
}
