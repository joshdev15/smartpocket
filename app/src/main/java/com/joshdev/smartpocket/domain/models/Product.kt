package com.joshdev.smartpocket.domain.models

import io.realm.kotlin.types.annotations.PrimaryKey

data class Product(
    @PrimaryKey
    val id: Int = 0,
    val invoiceId: Int = 0,
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

//@Entity(
//    tableName = "products",
//    foreignKeys = [
//        ForeignKey(
//            entity = Invoice::class,
//            parentColumns = ["id"],
//            childColumns = ["invoiceId"],
//            onDelete = ForeignKey.CASCADE
//        ),
//    ]
//)
//data class Product(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int = 0,
//    val invoiceId: Int = 0,
//    val name: String,
//    val quantity: Int,
//    val cost: Double,
//    val currency: Int?,
//    val customRate: Double,
//    val order: Int,
//    val categoryId: Int? = null,
//    val subCategoryId: Int? = null,
//    val baseCost: Double,
//)
