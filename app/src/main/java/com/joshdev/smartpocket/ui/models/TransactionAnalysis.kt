package com.joshdev.smartpocket.ui.models

import com.joshdev.smartpocket.domain.models.Transaction

data class TransactionAnalysis(
    val value: Double,
    val type: Transaction.TxType
)
