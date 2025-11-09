package com.joshdev.smartpocket.ui.activities.ledger.subcomponents

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joshdev.smartpocket.R
import com.joshdev.smartpocket.domain.models.Ledger
import com.joshdev.smartpocket.domain.models.Transaction
import com.joshdev.smartpocket.ui.components.AppText
import com.joshdev.smartpocket.ui.utils.UiUtils

@SuppressLint("ResourceAsColor")
@Composable
fun LedgerResume(ledger: Ledger, transactions: List<Transaction>) {
    val capitalColor = MaterialTheme.colorScheme.onBackground.copy(0.3f)
    val incomeColor = colorResource(id = R.color.income)
    val egressColor = colorResource(id = R.color.egress)

    val income = transactions.filter { it.type == Transaction.TxType.INCOME }.sumOf { it.amount }
    val egress = transactions.filter { it.type == Transaction.TxType.EGRESS }.sumOf { it.amount }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surfaceBright)
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            BarChart(
                totalBalance = ledger.initialCapital + income,
                income = income,
                egress = egress,
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    DescriptorWithColor(
                        text = "Capital inicial",
                        color = capitalColor,
                        amount = ledger.initialCapital
                    )

                    DescriptorWithColor(
                        text = "Ingresos",
                        color = incomeColor,
                        amount = income
                    )

                    DescriptorWithColor(
                        text = "Egresos",
                        color = egressColor,
                        amount = egress
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Top,
                ) {
                    AppText("Saldo actual: ${ledger?.totalBalance ?: 0}", fontSize = 10.sp)
                    AppText("Transacciones: ${transactions.size}", fontSize = 10.sp)
                }
            }
        }
    }
}