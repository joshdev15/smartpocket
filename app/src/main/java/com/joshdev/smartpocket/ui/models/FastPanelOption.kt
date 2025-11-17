package com.joshdev.smartpocket.ui.models

data class FastPanelOption(
    val id: IDs,
    val name: String,
) {
    enum class IDs {
        CURRENCIES,
        CATEGORIES_LEDGER,
        PRODUCTS_LEDGER,
        CATEGORIES_ARCHING,
        PRODUCTS_ARCHING
    }
}
