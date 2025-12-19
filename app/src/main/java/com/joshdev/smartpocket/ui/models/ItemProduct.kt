package com.joshdev.smartpocket.ui.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.joshdev.smartpocket.domain.arching.ArcProduct

data class ItemProduct(
    val arcProduct: ArcProduct,
    val quantity: MutableState<Int> = mutableIntStateOf(0),
    val isSelected: MutableState<Boolean> = mutableStateOf(false)
)