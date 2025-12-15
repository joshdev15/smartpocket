package com.joshdev.smartpocket.domain.arching

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arcRecordItem")
data class ArcRecordItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val recordId: Long? = null,
    val productId: Long? = null,
    val quantity: Int,
)