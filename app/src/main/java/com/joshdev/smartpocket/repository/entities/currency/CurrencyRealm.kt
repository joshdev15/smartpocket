package com.joshdev.smartpocket.repository.entities.currency

import com.joshdev.smartpocket.domain.currency.Currency
import com.joshdev.smartpocket.repository.mappers.ToData
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class CurrencyRealm : RealmObject, ToData<Currency> {
    @PrimaryKey
    var id: ObjectId = BsonObjectId.Companion()
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