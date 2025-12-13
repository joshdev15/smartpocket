package com.joshdev.smartpocket.ui.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.joshdev.smartpocket.domain.arching.Product

data class ItemProduct(
    val product: Product,
    val quantity: MutableState<Int> = mutableStateOf(0),
    val isSelected: MutableState<Boolean> = mutableStateOf(false)
)