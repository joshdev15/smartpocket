package com.joshdev.smartpocket.repository.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.joshdev.smartpocket.domain.ledger.LedProduct
import com.joshdev.smartpocket.domain.ledger.LedTransaction
import kotlinx.coroutines.flow.Flow

@Dao
interface LedgerProductDao {
    @Insert
    suspend fun insert(ledProduct: LedProduct)

    @Update
    suspend fun update(ledProduct: LedProduct)

    @Delete
    suspend fun delete(ledProduct: LedProduct)

    @Query("SELECT * FROM ledProducts  WHERE id = :productId")
    suspend fun getTxById(productId: Long): LedProduct?

    @Query("SELECT * FROM ledProducts ORDER BY id DESC")
    fun getAllCategories(): Flow<List<LedProduct>>

    @Query("DELETE FROM ledProducts")
    suspend fun deleteAllCategories()
}
