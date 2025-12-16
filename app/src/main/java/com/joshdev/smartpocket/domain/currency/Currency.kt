package com.joshdev.smartpocket.domain.currency

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class Currency(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    val symbol: String,
    val rate: Double
)