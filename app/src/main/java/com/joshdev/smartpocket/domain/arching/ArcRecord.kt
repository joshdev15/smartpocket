package com.joshdev.smartpocket.domain.arching

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arcRecord")
data class ArcRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val archingId: Long? = null,
    val name: String,
    val type: RecType,
    val totalAmount: Double,
    val creationDate: Long
) {
    enum class RecType {
        WorkingDay,
        Deduction
    }
}