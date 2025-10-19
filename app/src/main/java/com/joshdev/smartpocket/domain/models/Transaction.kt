package com.joshdev.smartpocket.domain.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
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
) {
    enum class TxType {
        INCOME,
        EGRESS,
    }
}

class TransactionRealm() : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.invoke()
    var name: String = ""
    var type: String = ""
    var amount: Double = 0.0
    var date: Long = 0L
    var description: String = ""
    var ledgerId: String = ""
    var currencyId: Int = 0
    var postBalance: Double = 0.0
    var hasProducts: Boolean = false
    var products: RealmList<ProductRealm> = realmListOf()

    fun toTransaction(): Transaction {
        return Transaction(
            id = id.toHexString(),
            name = name,
            type = Transaction.TxType.valueOf(type),
            amount = amount,
            date = date,
            description = description,
            ledgerId = ledgerId,
            currencyId = currencyId,
            postBalance = postBalance,
            hasProducts = hasProducts,
            products = products.map { it.toProduct() }
        )
    }

}

private fun Product.toProductRealm() {
    TODO("Not yet implemented")
}
