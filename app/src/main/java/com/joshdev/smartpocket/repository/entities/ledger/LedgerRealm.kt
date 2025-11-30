package com.joshdev.smartpocket.repository.entities.ledger

import com.joshdev.smartpocket.domain.ledger.Ledger
import com.joshdev.smartpocket.repository.mappers.ToData
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class LedgerRealm() : RealmObject, ToData<Ledger> {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var name: String = ""
    var initialCapital = 0.0
    var totalBalance = 0.0
    var creationDate: Long = 0L

    override fun toData(): Ledger {
        return Ledger(
            id = id.toHexString(),
            name = name,
            initialCapital = initialCapital,
            totalBalance = totalBalance,
            creationDate = creationDate
        )
    }
}
