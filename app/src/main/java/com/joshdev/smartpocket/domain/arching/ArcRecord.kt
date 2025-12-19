package com.joshdev.smartpocket.domain.arching

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arcRecord")
data class ArcRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val archingId: Long? = null,
    val dayName: String,
    val weekOfYear: Int,
    val monthOfYear: Int,
    val totalAmount: Double
)