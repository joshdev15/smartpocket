package com.joshdev.smartpocket.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joshdev.smartpocket.R
import com.joshdev.smartpocket.domain.models.LedgerTransaction
import com.joshdev.smartpocket.ui.utils.UiUtils.formatAmount
import com.joshdev.smartpocket.ui.utils.UiUtils.formatDate

@Composable
fun TransactionCard(tx: LedgerTransaction, onClick: () -> Unit, onLongClick: (tx: LedgerTransaction) -> Unit) {
    val borderColor = when (tx.type) {
        LedgerTransaction.TxType.EGRESS -> Color(0xFFFF6347)
        LedgerTransaction.TxType.INCOME -> Color(0xFF4CAF50)
    }

    val txIcon = when (tx.type) {
        LedgerTransaction.TxType.EGRESS -> R.drawable.arrow_egress
        LedgerTransaction.TxType.INCOME -> R.drawable.arrow_income
    }

    Row(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .combinedClickable(
                onClick = {
                    if (tx.type == LedgerTransaction.TxType.EGRESS && tx.ledgerProducts.isNotEmpty()) {
                        onClick()
                    }
                },
                onLongClick = { onLongClick(tx) }
            )
            .border(2.dp, MaterialTheme.colorScheme.tertiaryContainer, RoundedCornerShape(30.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                AppText(
                    text = tx.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp,
                )
                Icon(
                    painter = painterResource(txIcon),
                    contentDescription = "",
                    tint = borderColor,
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AppText(
                    text = "Monto: ${formatAmount(tx.amount)}",
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 12.sp,
                )
                AppText(
                    text = formatDate(tx.date),
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 12.sp,
                )
            }
        }
    }
}