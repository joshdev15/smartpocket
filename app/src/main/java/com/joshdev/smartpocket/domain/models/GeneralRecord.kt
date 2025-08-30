package com.joshdev.smartpocket.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "generalRecords")
data class GeneralRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val year: Int,
    val month: Int,
    val author: String,
    val creationDate: Long
)

