package com.joshdev.smartpocket.domain.models

import io.realm.kotlin.types.annotations.PrimaryKey

data class Currency(
    @PrimaryKey
    val id: Int = 0,
    val name: String,
    val symbol: String,
    val rate: Double
)
