package com.joshdev.smartpocket.domain.models

import com.joshdev.smartpocket.repository.interfaces.ToRealm
import com.joshdev.smartpocket.repository.models.TransactionRealm
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
) : ToRealm<TransactionRealm> {
    override fun toRealm(): TransactionRealm {
        val newTransactionRealm = TransactionRealm().apply {
            id = ObjectId.invoke()
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

        return newTransactionRealm
    }

    enum class TxType {
        INCOME,
        EGRESS,
    }
}