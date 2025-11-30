package com.joshdev.smartpocket.domain.ledger

import com.joshdev.smartpocket.repository.mappers.ToRealm
import com.joshdev.smartpocket.repository.entities.ledger.LedgerTransactionRealm
import io.realm.kotlin.ext.toRealmList
import org.mongodb.kbson.ObjectId

data class Transaction(
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
    val products: List<Product>,
) : ToRealm<LedgerTransactionRealm> {
    override fun toRealm(): LedgerTransactionRealm {
        val newLedgerLedgerTransactionRealm = LedgerTransactionRealm().apply {
            id = if (this@Transaction.id != "") ObjectId(this@Transaction.id) else ObjectId()
            name = this@Transaction.name
            type = this@Transaction.type.toString()
            amount = this@Transaction.amount
            date = this@Transaction.date
            description = this@Transaction.description
            ledgerId = this@Transaction.ledgerId
            currencyId = this@Transaction.currencyId
            postBalance = this@Transaction.postBalance
            hasProducts = this@Transaction.hasProducts
            products = this@Transaction.products.map { it.toRealm() }.toRealmList()
        }

        return newLedgerLedgerTransactionRealm
    }

    enum class TxType {
        INCOME,
        EGRESS,
    }
}