package com.joshdev.smartpocket.ui.models

import com.joshdev.smartpocket.domain.models.LedgerTransaction

data class TransactionAnalysis(
    val value: Double,
    val type: LedgerTransaction.TxType
)
