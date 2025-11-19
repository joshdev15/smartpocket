package com.joshdev.smartpocket.domain.models

import com.joshdev.smartpocket.repository.interfaces.ToRealm
import com.joshdev.smartpocket.repository.models.LedgerRealm
import org.mongodb.kbson.ObjectId

data class Ledger(
    val id: String = "",
    val name: String,
    val initialCapital: Double,
    val totalBalance: Double,
    val creationDate: Long
) : ToRealm<LedgerRealm> {
    override fun toRealm(): LedgerRealm {
        return LedgerRealm().apply {
            id = if (this@Ledger.id != "") ObjectId(this@Ledger.id) else ObjectId()
            name = this@Ledger.name
            initialCapital = this@Ledger.initialCapital
            totalBalance = this@Ledger.totalBalance
            creationDate = this@Ledger.creationDate
        }
    }
}