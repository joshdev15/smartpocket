package com.joshdev.smartpocket.ui.models

import com.joshdev.smartpocket.domain.ledger.LedTransaction

data class TransactionAnalysis(
    val value: Double,
    val type: LedTransaction.TxType
)
