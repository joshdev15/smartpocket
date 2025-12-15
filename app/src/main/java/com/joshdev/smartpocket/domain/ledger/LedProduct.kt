package com.joshdev.smartpocket.domain.ledger

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ledProducts")
data class LedProduct(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val invoiceId: String,
    val name: String,
    val quantity: Int,
    val cost: Double,
    val currency: Int?,
    val customRate: Double,
    val order: Int,
    val categoryId: Int? = null,
    val subCategoryId: Int? = null,
    val baseCost: Double,
)