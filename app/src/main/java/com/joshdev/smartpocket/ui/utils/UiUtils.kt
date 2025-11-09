package com.joshdev.smartpocket.ui.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
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

    fun ellipsis(text: String, length: Int): String {
        return if (text.length <= length) {
            text
        } else {
            "${text.take(length)}..."
        }
    }
}