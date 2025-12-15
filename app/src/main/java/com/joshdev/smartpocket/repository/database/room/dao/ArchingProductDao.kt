package com.joshdev.smartpocket.repository.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.joshdev.smartpocket.domain.arching.ArcProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface ArchingProductDao {
    @Insert
    suspend fun insert(arching: ArcProduct)

    @Update
    suspend fun update(arcProduct: ArcProduct)

    @Delete
    suspend fun delete(arcProduct: ArcProduct)

    @Query("SELECT * FROM arcProduct WHERE id = :productId")
    suspend fun getArchingById(productId: Long): ArcProduct?

    @Query("SELECT * FROM arcProduct ORDER BY id DESC")
    fun getAllArching(): Flow<List<ArcProduct>>

    @Query("DELETE FROM arcProduct")
    suspend fun deleteAllArching()
}