package com.joshdev.smartpocket.domain.ledger

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ledTransactions")
data class LedTransaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: TxType,
    val amount: Double,
    val date: Long,
    val description: String,
    val ledgerId: Long,
    val currencyId: Int,
    val postBalance: Double,
    val hasProducts: Boolean,
) {
    enum class TxType {
        INCOME,
        EGRESS,
    }
}