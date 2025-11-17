package com.joshdev.smartpocket.repository.models

import com.joshdev.smartpocket.domain.models.LedgerTransaction
import com.joshdev.smartpocket.repository.interfaces.ToData
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class LedgerTransactionRealm() : RealmObject, ToData<LedgerTransaction> {
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
    var products: RealmList<LedgerProductRealm> = realmListOf()

    override fun toData(): LedgerTransaction {
        return LedgerTransaction(
            id = id.toHexString(),
            name = name,
            type = LedgerTransaction.TxType.valueOf(type),
            amount = amount,
            date = date,
            description = description,
            ledgerId = ledgerId,
            currencyId = currencyId,
            postBalance = postBalance,
            hasProducts = hasProducts,
            ledgerProducts = products.map { it.toData() }
        )
    }
}
