package com.joshdev.smartpocket.ui.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import com.joshdev.smartpocket.ui.models.HomeOption
import com.joshdev.smartpocket.R
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

    fun ellipsis(text: String, length: Int): String {
        return if (text.length <= length) {
            text
        } else {
            "${text.take(length)}..."
        }
    }

    fun getHomeOptions(): List<HomeOption> {
        return listOf(
            HomeOption(
                id = HomeOption.IDs.LEDGERS,
                name = "Cuentas",
                icon = R.drawable.card,
            ),
            HomeOption(
                id = HomeOption.IDs.ARCHING,
                name = "Arqueo",
                icon = R.drawable.arching,
            ),
            HomeOption(
                id = HomeOption.IDs.COINS,
                name = "Divisas",
                icon = R.drawable.coins,
            ),
        )
    }
}