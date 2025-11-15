package com.joshdev.smartpocket.ui.models

data class HomeOption(
    val id: IDs,
    val name: String,
    val icon: Int,
) {
    enum class IDs {
        LEDGERS,
        ARCHING,
        COINS
    }
}


