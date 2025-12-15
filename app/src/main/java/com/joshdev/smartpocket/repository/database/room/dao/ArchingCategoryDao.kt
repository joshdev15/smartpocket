package com.joshdev.smartpocket.repository.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.joshdev.smartpocket.domain.arching.ArcCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ArchingCategoryDao {
    @Insert
    suspend fun insert(arcCategory: ArcCategory)

    @Update
    suspend fun update(arcCategory: ArcCategory)

    @Delete
    suspend fun delete(arcCategory: ArcCategory)

    @Query("SELECT * FROM arcCategory WHERE id = :categoryId")
    suspend fun getCategoryById(categoryId: Long): ArcCategory?

    @Query("SELECT * FROM arcCategory ORDER BY id DESC")
    fun getAllCategories(): Flow<List<ArcCategory>>

    @Query("DELETE FROM arcCategory")
    suspend fun deleteAllCategories()
}