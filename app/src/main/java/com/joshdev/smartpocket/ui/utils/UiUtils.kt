package com.joshdev.smartpocket.ui.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.joshdev.smartpocket.R
import com.joshdev.smartpocket.domain.models.Ledger
import com.joshdev.smartpocket.domain.models.Transaction
import com.joshdev.smartpocket.ui.models.TransactionAnalysis
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object UiUtils {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun formatDate(timestamp: Long): String {
        val date = Date(timestamp)
        val formatter = SimpleDateFormat("dd-MM-yy", Locale.getDefault())
        val formattedDate: String = formatter.format(date)
        return formattedDate
    }

    @SuppressLint("DefaultLocale")
    fun formatAmount(amount: Double): String {
        return String.format("%.2f", amount)
    }

//    fun transactionAnalysis(ledger: Ledger, transactions: List<Transaction>): Triple<List<TransactionAnalysis>, Double, Double> {
//        val income = transactions.filter { it.type == Transaction.TxType.INCOME }
//            .sumOf { it.amount }
//
//        val egress = transactions.filter { it.type == Transaction.TxType.EGRESS }
//            .sumOf { it.amount }
//
//        val analysisList = mutableListOf<TransactionAnalysis>()
//
//        when {
//            income > egress -> {
//                analysisList.add(
//                    TransactionAnalysis(
//                        value = income,
//                        type = Transaction.TxType.INCOME,
//                    )
//                )
//
//                analysisList.add(
//                    TransactionAnalysis(
//                        value = egress,
//                        type = Transaction.TxType.EGRESS,
//                    )
//                )
//            }
//
//            egress > income -> {
//                analysisList.add(
//                    TransactionAnalysis(
//                        value = egress,
//                        type = Transaction.TxType.EGRESS,
//                    )
//                )
//
//                analysisList.add(
//                    TransactionAnalysis(
//                        value = income,
//                        type = Transaction.TxType.INCOME,
//                    )
//                )
//            }
//
//            egress == income -> {
//                analysisList.add(
//                    TransactionAnalysis(
//                        value = egress + income,
//                        type = Transaction.TxType.INCOME,
//                    )
//                )
//            }
//        }
//
//        return Triple(analysisList, income, egress)
//    }

    fun amountAnalysis(
        totalBalance: Double,
        amount: Double,
        degrees: Int = 360
    ): Pair<Float, Float> {
        if (totalBalance == 0.0) {
            return Pair(0f, 0f)
        }

        val percentage: Float = (amount.toFloat() / totalBalance.toFloat()) * 100f
        val finalDegrees: Float = (amount.toFloat() / totalBalance.toFloat()) * degrees.toFloat()

        return Pair(percentage, finalDegrees)
    }
}