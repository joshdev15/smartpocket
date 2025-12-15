package com.joshdev.smartpocket.domain.arching

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arcRecordItem")
data class ArcRecordItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val recordId: Long = 0,
    val productId: Long = 0,
    val quantity: Int,
)