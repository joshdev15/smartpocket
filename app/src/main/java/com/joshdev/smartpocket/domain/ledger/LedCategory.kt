package com.joshdev.smartpocket.domain.ledger

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ledCategories")
data class LedCategory(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    val level: Int,
    val description: String,
    val color: String
)