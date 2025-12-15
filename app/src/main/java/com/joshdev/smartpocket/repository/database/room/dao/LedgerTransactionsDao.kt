package com.joshdev.smartpocket.repository.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.joshdev.smartpocket.domain.ledger.LedTransaction
import kotlinx.coroutines.flow.Flow

@Dao
interface LedgerTransactionsDao {
    @Insert
    suspend fun insert(tx: LedTransaction)

    @Update
    suspend fun update(tx: LedTransaction)

    @Delete
    suspend fun delete(tx: LedTransaction)

    @Query("SELECT * FROM ledTransactions  WHERE id = :txId")
    suspend fun getTxById(txId: Long): LedTransaction?

    @Query("SELECT * FROM ledTransactions ORDER BY id DESC")
    fun getAllCategories(): Flow<List<LedTransaction>>

    @Query("DELETE FROM ledTransactions")
    suspend fun deleteAllCategories()
}