package com.joshdev.smartpocket.repository.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.joshdev.smartpocket.domain.models.Category
import com.joshdev.smartpocket.domain.models.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert
    suspend fun insert(product: Category)

    @Update
    suspend fun update(product: Category)

    @Delete
    suspend fun delete(product: Category)

    @Query("SELECT * FROM categories WHERE level = :level")
    suspend fun getCategoryByLevel(level: Int): Category?

    @Query("SELECT * FROM categories WHERE level = :level ORDER BY name DESC")
    fun getAllCategoriesByLevel(level: Int): Flow<List<Category>>

    @Query("DELETE FROM categories")
    suspend fun deleteAllCategories()
}
