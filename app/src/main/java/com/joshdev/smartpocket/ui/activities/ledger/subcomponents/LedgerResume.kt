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
    val capitalColor = MaterialTheme.colorScheme.primaryContainer
    val incomeColor = colorResource(id = R.color.income)
    val egressColor = colorResource(id = R.color.egress)

    val income = transactions.filter { it.type == Transaction.TxType.INCOME }.sumOf { it.amount }
    val egress = transactions.filter { it.type == Transaction.TxType.EGRESS }.sumOf { it.amount }

    val incomeAnalysis = UiUtils.amountAnalysis(ledger.totalBalance, income)
    val egressAnalysis = UiUtils.amountAnalysis(ledger.totalBalance, egress)

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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .size(200.dp)
        ) {
            Canvas(modifier = Modifier.size(150.dp)) {
                val radius = size.minDimension / 2f
                val center = Offset(x = size.width / 2, y = size.height / 2)
                val strokeWidth = 50f
                val startAngle = -90f

                drawCircle(
                    color = capitalColor,
                    radius = radius,
                    center = center,
                    style = Stroke(width = strokeWidth)
                )

                drawArc(
                    color = incomeColor,
                    startAngle = startAngle,
                    sweepAngle = incomeAnalysis.second,
                    useCenter = false,
                    topLeft = Offset(center.x + 25 - radius, center.y + 25 - radius),
                    size = Size(2 * radius - 50, 2 * radius - 50),
                    style = Stroke(width = strokeWidth - 30)
                )

                drawArc(
                    color = egressColor,
                    startAngle = startAngle,
                    sweepAngle = egressAnalysis.second,
                    useCenter = false,
                    topLeft = Offset(center.x + 25 - (radius + 50), center.y + 25 - (radius + 50)),
                    size = Size(2 * radius + 50, 2 * radius + 50),
                    style = Stroke(width = strokeWidth - 30)
                )
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                        .height(10.dp)
                        .background(capitalColor)
                )
                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                        .height(10.dp)
                )
                AppText("Capital inicial: ${ledger.initialCapital}", fontSize = 10.sp)
            }

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                        .height(10.dp)
                        .background(incomeColor)
                )
                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                        .height(10.dp)
                )
                AppText("Ingresos: $income", fontSize = 10.sp)
            }

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                        .height(10.dp)
                        .background(egressColor)
                )
                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                        .height(10.dp)
                )
                AppText("Egresos: $egress", fontSize = 10.sp)
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )

            AppText("Saldo actual: ${ledger?.totalBalance ?: 0}", fontSize = 12.sp)
            AppText("Transacciones: ${transactions.size}", fontSize = 10.sp)
        }
    }
}