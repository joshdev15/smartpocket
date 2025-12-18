package com.joshdev.smartpocket.ui.modules.home.activity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {
    private val _allowExit = mutableStateOf(false)
    val allowExit: State<Boolean> = _allowExit

    private var _cameraAllowed = mutableStateOf(false)
    val cameraAllowed: State<Boolean> = _cameraAllowed

    fun setAllowExit(value: Boolean) {
        _allowExit.value = value
    }

    fun setCameraAllowed(value: Boolean) {
        _cameraAllowed.value = value
    }
}