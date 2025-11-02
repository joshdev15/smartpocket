package com.joshdev.smartpocket.domain.models

import com.joshdev.smartpocket.repository.interfaces.ToRealm
import com.joshdev.smartpocket.repository.models.LedgerRealm

data class Ledger(
    val id: String = "",
    val name: String,
    val initialCapital: Double,
    val totalBalance: Double,
    val creationDate: Long
) : ToRealm<LedgerRealm> {
    override fun toRealm(): LedgerRealm {
        return LedgerRealm().apply {
            name = this@Ledger.name
            initialCapital = this@Ledger.initialCapital
            totalBalance = this@Ledger.totalBalance
            creationDate = this@Ledger.creationDate
        }
    }
}