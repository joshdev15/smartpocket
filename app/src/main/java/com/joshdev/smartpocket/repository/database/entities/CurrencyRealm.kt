package com.joshdev.smartpocket.repository.database.entities

import com.joshdev.smartpocket.domain.models.Currency
import com.joshdev.smartpocket.repository.mappers.ToData
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class CurrencyRealm : RealmObject, ToData<Currency> {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var name: String = ""
    var symbol: String = ""
    var rate: Double = 0.0

    override fun toData(): Currency {
        return Currency(
            id = id.toHexString(),
            name = name,
            symbol = symbol,
            rate = rate
        )
    }
}
