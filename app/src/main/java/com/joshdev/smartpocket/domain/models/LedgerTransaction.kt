package com.joshdev.smartpocket.domain.models

import com.joshdev.smartpocket.repository.mappers.ToRealm
import com.joshdev.smartpocket.repository.entities.LedgerTransactionRealm
import io.realm.kotlin.ext.toRealmList
import org.mongodb.kbson.ObjectId

data class LedgerTransaction(
    val id: String,
    val name: String,
    val type: TxType,
    val amount: Double,
    val date: Long,
    val description: String,
    val ledgerId: String,
    val currencyId: Int,
    val postBalance: Double,
    val hasProducts: Boolean,
    val ledgerProducts: List<LedgerProduct>,
) : ToRealm<LedgerTransactionRealm> {
    override fun toRealm(): LedgerTransactionRealm {
        val newLedgerLedgerTransactionRealm = LedgerTransactionRealm().apply {
            id = if (this@LedgerTransaction.id != "") ObjectId(this@LedgerTransaction.id) else ObjectId()
            name = this@LedgerTransaction.name
            type = this@LedgerTransaction.type.toString()
            amount = this@LedgerTransaction.amount
            date = this@LedgerTransaction.date
            description = this@LedgerTransaction.description
            ledgerId = this@LedgerTransaction.ledgerId
            currencyId = this@LedgerTransaction.currencyId
            postBalance = this@LedgerTransaction.postBalance
            hasProducts = this@LedgerTransaction.hasProducts
            products = this@LedgerTransaction.ledgerProducts.map { it.toRealm() }.toRealmList()
        }

        return newLedgerLedgerTransactionRealm
    }

    enum class TxType {
        INCOME,
        EGRESS,
    }
}