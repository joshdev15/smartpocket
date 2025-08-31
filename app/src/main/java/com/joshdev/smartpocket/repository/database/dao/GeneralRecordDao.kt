package com.joshdev.smartpocket.repository.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.joshdev.smartpocket.domain.models.GeneralRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface GeneralRecordDao {
    @Insert
    suspend fun insert(record: GeneralRecord)

    @Update
    suspend fun update(record: GeneralRecord)

    @Delete
    suspend fun delete(record: GeneralRecord)

    @Query("SELECT * FROM generalRecords WHERE id = :recordId")
    suspend fun getRecordById(recordId: Int): GeneralRecord?

    @Query("SELECT * FROM generalRecords ORDER BY creationDate DESC")
    fun getAllRecords(): Flow<List<GeneralRecord>>

    @Query("DELETE FROM generalRecords")
    suspend fun deleteAllRecords()
}