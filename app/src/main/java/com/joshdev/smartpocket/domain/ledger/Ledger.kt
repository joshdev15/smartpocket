package com.joshdev.smartpocket.domain.ledger

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ledgers")
data class Ledger(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    val initialCapital: Double,
    val totalBalance: Double,
    val creationDate: Long
)