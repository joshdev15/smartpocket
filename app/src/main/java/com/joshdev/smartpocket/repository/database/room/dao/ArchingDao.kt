package com.joshdev.smartpocket.repository.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.Query
import androidx.room.Update
import com.joshdev.smartpocket.domain.arching.Arching
import kotlinx.coroutines.flow.Flow

@Dao
interface ArchingDao {
    @Insert
    suspend fun insert(arching: Arching)

    @Update
    suspend fun update(product: Arching)

    @Delete
    suspend fun delete(product: Arching)

    @Query("SELECT * FROM arching WHERE id = :archingId")
    suspend fun getArchingById(archingId: Long): Arching?

    @Query("SELECT * FROM arching ORDER BY id DESC")
    fun getAllArching(): Flow<List<Arching>>

    @Query("DELETE FROM ledProducts")
    suspend fun deleteAllArching()

}