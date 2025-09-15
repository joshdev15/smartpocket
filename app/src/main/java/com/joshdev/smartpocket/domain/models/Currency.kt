package com.joshdev.smartpocket.domain.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

data class Currency(
    val id: String = "",
    val name: String,
    val symbol: String,
    val rate: Double
)

class CurrencyRealm : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.invoke()
    var name: String = ""
    var symbol: String = ""
    var rate: Double = 0.0

    fun toCurrency(): Currency {
        return Currency(
            id = id.toHexString(),
            name = name,
            symbol = symbol,
            rate = rate
        )
    }
}
