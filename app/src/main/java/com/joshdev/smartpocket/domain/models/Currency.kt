package com.joshdev.smartpocket.domain.models

import com.joshdev.smartpocket.repository.mappers.ToRealm
import com.joshdev.smartpocket.repository.database.entities.CurrencyRealm
import org.mongodb.kbson.ObjectId

data class Currency(
    val id: String = "",
    val name: String,
    val symbol: String,
    val rate: Double
) : ToRealm<CurrencyRealm> {
    override fun toRealm(): CurrencyRealm {
        return CurrencyRealm().apply {
            id = if (this@Currency.id != "") ObjectId(this@Currency.id) else ObjectId()
            name = this@Currency.name
            symbol = this@Currency.symbol
            rate = this@Currency.rate
        }
    }
}