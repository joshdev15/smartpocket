package com.joshdev.smartpocket.domain.currency

import com.joshdev.smartpocket.repository.entities.currency.CurrencyRealm
import com.joshdev.smartpocket.repository.mappers.ToRealm
import org.mongodb.kbson.BsonObjectId

data class Currency(
    val id: String = "",
    val name: String,
    val symbol: String,
    val rate: Double
) : ToRealm<CurrencyRealm> {
    override fun toRealm(): CurrencyRealm {
        return CurrencyRealm().apply {
            id = if (this@Currency.id != "") BsonObjectId.Companion(this@Currency.id) else BsonObjectId.Companion()
            name = this@Currency.name
            symbol = this@Currency.symbol
            rate = this@Currency.rate
        }
    }
}