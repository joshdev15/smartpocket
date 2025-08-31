package com.joshdev.smartpocket.domain.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "invoices",
    foreignKeys = [
        ForeignKey(
            entity = GeneralRecord::class,
            parentColumns = ["id"],
            childColumns = ["recordId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Invoice(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val recordId: Int = 0,
    val name: String,
    val author: String,
    val creationDate: Long,
    val modificationDate: Long,
    var total: Double?
)