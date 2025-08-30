package com.joshdev.smartpocket.repository.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.joshdev.smartpocket.domain.models.Currency
import com.joshdev.smartpocket.domain.models.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Insert
    suspend fun insert(currency: Currency)

    @Update
    suspend fun update(currency: Currency)

    @Delete
    suspend fun delete(currency: Currency)

    @Query("SELECT * FROM currencies WHERE id = :id")
    suspend fun getCurrencyById(id: Int): Currency?

    @Query("SELECT * FROM currencies ORDER BY id DESC")
    fun getAllCurrencies(): Flow<List<Currency>>

    @Query("DELETE FROM currencies")
    suspend fun deleteAllCurrencies()
}
