package com.joshdev.smartpocket.repository.utils

object RepoUtils {
    val entityList = arrayOf(
        com.joshdev.smartpocket.domain.arching.Arching::class,
        com.joshdev.smartpocket.domain.arching.ArcRecord::class,
        com.joshdev.smartpocket.domain.arching.ArcRecordItem::class,
        com.joshdev.smartpocket.domain.arching.ArcCategory::class,
        com.joshdev.smartpocket.domain.arching.ArcProduct::class,
        com.joshdev.smartpocket.domain.ledger.LedCategory::class,
        com.joshdev.smartpocket.domain.ledger.Ledger::class,
        com.joshdev.smartpocket.domain.ledger.LedProduct::class,
        com.joshdev.smartpocket.domain.ledger.LedTransaction::class,
    )
}