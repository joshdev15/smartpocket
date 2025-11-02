package com.joshdev.smartpocket.repository.models

import com.joshdev.smartpocket.domain.models.Ledger
import com.joshdev.smartpocket.repository.interfaces.ToData
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class LedgerRealm() : RealmObject, ToData<Ledger> {
    @PrimaryKey
    var id: ObjectId = ObjectId.invoke()
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
