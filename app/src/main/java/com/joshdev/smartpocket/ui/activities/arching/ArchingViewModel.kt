package com.joshdev.smartpocket.ui.activities.arching

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.joshdev.smartpocket.ui.models.FastPanelOption
import com.joshdev.smartpocket.ui.utils.UiUtils.getIntentByFastOptionID

class ArchingViewModel : ViewModel() {
    private val activity = mutableStateOf<ArchingActivity?>(null)
    private val context = mutableStateOf<Context?>(null)

    private var _showNewArchingDialog = mutableStateOf<Boolean>(false)
    val showNewArchingDialog: State<Boolean> = _showNewArchingDialog

    fun start(act: ArchingActivity, ctx: Context) {
        activity.value = act
        context.value = ctx
    }

    // UI Actions
    fun toggleNewArchingDialog(value: Boolean? = null) {
        if (value != null) {
            _showNewArchingDialog.value = value
        } else {
            _showNewArchingDialog.value = !_showNewArchingDialog.value
        }
    }

    // Operations
    fun goTo(id: FastPanelOption.IDs) {
        getIntentByFastOptionID(id, context.value)?.let {
            activity.value?.startActivity(it)
        }
    }
}