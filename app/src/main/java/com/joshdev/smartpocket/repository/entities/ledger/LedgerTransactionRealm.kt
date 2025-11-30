package com.joshdev.smartpocket.repository.entities.ledger

import com.joshdev.smartpocket.domain.ledger.Transaction
import com.joshdev.smartpocket.repository.mappers.ToData
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class LedgerTransactionRealm() : RealmObject, ToData<Transaction> {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var name: String = ""
    var type: String = ""
    var amount: Double = 0.0
    var date: Long = 0L
    var description: String = ""
    var ledgerId: String = ""
    var currencyId: Int = 0
    var postBalance: Double = 0.0
    var hasProducts: Boolean = false
    var products: RealmList<LedgerProductRealm> = realmListOf()

    override fun toData(): Transaction {
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
            products = products.map { it.toData() }
        )
    }
}
