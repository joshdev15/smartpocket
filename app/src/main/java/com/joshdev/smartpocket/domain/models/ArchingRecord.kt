package com.joshdev.smartpocket.domain.models

data class ArchingRecord(
    val id: String = "",
    val dayName: String,
    val weekOfYear: Int,
    val monthOfYear: Int
)
