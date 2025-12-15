package com.joshdev.smartpocket.repository.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.joshdev.smartpocket.domain.ledger.LedCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface LedgerCategoryDao {
    @Insert
    suspend fun insert(ledCategory: LedCategory)

    @Update
    suspend fun update(ledCategory: LedCategory)

    @Delete
    suspend fun delete(ledCategory: LedCategory)

    @Query("SELECT * FROM ledCategories WHERE id = :categoryId")
    suspend fun getCategoryById(categoryId: Long): LedCategory?

    @Query("SELECT * FROM ledCategories ORDER BY id DESC")
    fun getAllCategories(): Flow<List<LedCategory>>

    @Query("DELETE FROM ledCategories")
    suspend fun deleteAllCategories()
}
