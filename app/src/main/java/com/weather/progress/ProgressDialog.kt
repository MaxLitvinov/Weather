package com.weather.progress

import androidx.fragment.app.FragmentManager

interface ProgressDialog {

    fun show(manager: FragmentManager)

    fun hide(manager: FragmentManager)
}
