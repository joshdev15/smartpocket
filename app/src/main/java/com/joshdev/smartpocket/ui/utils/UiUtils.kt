package com.joshdev.smartpocket.ui.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.joshdev.smartpocket.ui.models.HomeOption
import com.joshdev.smartpocket.R
import com.joshdev.smartpocket.ui.activities.archingProducts.ArchingProductsActivity
import com.joshdev.smartpocket.ui.activities.categoryList.CategoryListActivity
import com.joshdev.smartpocket.ui.modules.currency.activity.CurrencyActivity
import com.joshdev.smartpocket.ui.activities.productList.ProductListActivity
import com.joshdev.smartpocket.ui.models.FastPanelOption
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.navigation.NamedNavArgument

object UiUtils {
    val SCREEN_PADDING = 10.dp
    val SCREEN_FLOATING_PADDING = 75.dp

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
                name = "Cierres",
                icon = R.drawable.arching,
            ),
            HomeOption(
                id = HomeOption.IDs.COINS,
                name = "Divisas",
                icon = R.drawable.coins,
            ),
        )
    }

    fun getIntentByFastOptionID(id: FastPanelOption.IDs, context: Context?): Intent? {
        if (context == null) {
            return null
        }

        val goTo = when (id) {
            FastPanelOption.IDs.CURRENCIES -> {
                Intent(context, CurrencyActivity::class.java)
            }

            FastPanelOption.IDs.CATEGORIES_LEDGER -> {
                Intent(context, CategoryListActivity::class.java)
            }

            FastPanelOption.IDs.PRODUCTS_LEDGER -> {
                Intent(context, ProductListActivity::class.java)
            }

            FastPanelOption.IDs.CATEGORIES_ARCHING -> {
                Intent(context, CategoryListActivity::class.java)
            }

            FastPanelOption.IDs.PRODUCTS_ARCHING -> {
                Intent(context, ArchingProductsActivity::class.java)
            }
        }

        return goTo
    }
}

fun NavGraphBuilder.appComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable (AnimatedContentScope.(NavBackStackEntry) -> Unit)
) {
    val timing = 500
    val scale = 0.80f
    composable(
        route = route,
        arguments = arguments,
        enterTransition = {
            scaleIn(
                initialScale = scale,
                animationSpec = tween(timing)
            ) + fadeIn(animationSpec = tween(timing))
        },
        exitTransition = {
            scaleOut(
                targetScale = scale,
                animationSpec = tween(timing)
            ) + fadeOut(animationSpec = tween(timing))
        },
        popEnterTransition = {
            scaleIn(
                initialScale = scale,
                animationSpec = tween(timing)
            ) + fadeIn(animationSpec = tween(timing))
        },
        popExitTransition = {
            scaleOut(
                targetScale = scale,
                animationSpec = tween(timing)
            ) + fadeOut(animationSpec = tween(timing))
        },
        content = content
    )
}