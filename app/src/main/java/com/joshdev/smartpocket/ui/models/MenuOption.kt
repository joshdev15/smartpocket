package com.joshdev.smartpocket.ui.models

data class MenuOption(
    val icon: Int,
    val name: String,
    val onClick: () -> Unit
)
