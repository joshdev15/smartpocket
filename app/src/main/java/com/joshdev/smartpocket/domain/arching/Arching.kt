package com.joshdev.smartpocket.domain.arching

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arching")
data class Arching(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    val creationDate: Long,
)