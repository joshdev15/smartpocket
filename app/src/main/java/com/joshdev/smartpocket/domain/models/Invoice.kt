package com.joshdev.smartpocket.domain.models

import io.realm.kotlin.types.annotations.PrimaryKey

data class Invoice(
    @PrimaryKey
    val id: Int = 0,
    val recordId: Int = 0,
    val name: String,
    val author: String,
    val creationDate: Long,
    val modificationDate: Long,
    var total: Double?
)

//@Entity(
//    tableName = "invoices",
//    foreignKeys = [
//        ForeignKey(
//            entity = Ledger::class,
//            parentColumns = ["id"],
//            childColumns = ["recordId"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
//)
//data class Invoice(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int = 0,
//    val recordId: Int = 0,
//    val name: String,
//    val author: String,
//    val creationDate: Long,
//    val modificationDate: Long,
//    var total: Double?
//)