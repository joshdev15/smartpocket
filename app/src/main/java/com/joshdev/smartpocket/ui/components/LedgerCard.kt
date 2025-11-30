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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joshdev.smartpocket.domain.ledger.Ledger
import com.joshdev.smartpocket.ui.utils.UiUtils.formatDate

@Composable
fun LedgerCard(ledger: Ledger, onClick: () -> Unit, onLongClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onLongClick() }
            )
            .border(2.dp, MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(30.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AppText(
                    text = ledger.name,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AppText(
                    text = "Capital inicial: ${ledger.initialCapital}",
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 12.sp,
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AppText(
                    text = "Total: ${ledger.totalBalance}",
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 12.sp,
                )

                AppText(
                    text = formatDate(ledger.creationDate),
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 12.sp,
                )
            }
        }
    }
}