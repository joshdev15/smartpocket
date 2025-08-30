package com.joshdev.smartpocket.src.database.entity.invoice

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "invoices")
data class Invoice(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val author: String,
    val creationDate: String,
    val modificationDate: String,
    val total: Double?
)