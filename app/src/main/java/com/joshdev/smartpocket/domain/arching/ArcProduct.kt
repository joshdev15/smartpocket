package com.joshdev.smartpocket.domain.arching

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arcProduct")
data class ArcProduct(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    val categoryId: String = "",
    val subCategoryId: String = "",
    val price: Double = 0.0,
)