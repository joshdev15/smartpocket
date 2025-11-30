package com.joshdev.smartpocket.ui.models

import com.joshdev.smartpocket.domain.ledger.Transaction

data class TransactionAnalysis(
    val value: Double,
    val type: Transaction.TxType
)
