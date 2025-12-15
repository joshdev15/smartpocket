package com.joshdev.smartpocket.domain.arching

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arcCategory")
class ArcCategory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val level: Int,
    val description: String,
    val color: String
)