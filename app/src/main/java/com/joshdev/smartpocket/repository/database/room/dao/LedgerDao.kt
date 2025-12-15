package com.joshdev.smartpocket.repository.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.joshdev.smartpocket.domain.ledger.Ledger
import kotlinx.coroutines.flow.Flow

@Dao
interface LedgerDao {
    @Insert
    suspend fun insert(ledger: Ledger)

    @Update
    suspend fun update(ledger: Ledger)

    @Delete
    suspend fun delete(ledger: Ledger)

    @Query("SELECT * FROM ledgers WHERE id = :ledgerId")
    suspend fun getLedgerById(ledgerId: Long): Ledger?

    @Query("SELECT * FROM ledgers ORDER BY creationDate DESC")
    fun getAllRecords(): Flow<List<Ledger>>

    @Query("DELETE FROM ledgers")
    suspend fun deleteAllRecords()
}
