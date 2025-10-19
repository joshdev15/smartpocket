package com.joshdev.smartpocket.domain.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

data class Ledger(
    val id: String = "",
    val name: String,
    val initialCapital: Double,
    val totalBalance: Double,
    val creationDate: Long
) {
    fun toLedgerRealm(): LedgerRealm {
        return LedgerRealm().apply {
            name = this@Ledger.name
            initialCapital = this@Ledger.initialCapital
            totalBalance = this@Ledger.totalBalance
            creationDate = this@Ledger.creationDate
        }
    }
}

class LedgerRealm() : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.invoke()
    var name: String = ""
    var initialCapital = 0.0
    var totalBalance = 0.0
    var creationDate: Long = 0L

    fun toLedger(): Ledger {
        return Ledger(
            id = id.toHexString(),
            name = name,
            initialCapital = initialCapital,
            totalBalance = totalBalance,
            creationDate = creationDate
        )
    }
}
