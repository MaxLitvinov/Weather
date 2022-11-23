package com.weather.foundation.dialog

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import javax.inject.Inject

class ProgressDialogImpl @Inject constructor() : ProgressDialog {

    companion object {

        private const val PROGRESS_DIALOG_TAG = "ProgressDialogTag"
    }

    override fun show(manager: FragmentManager) {
        val fragment = ProgressDialogFragment()
        val transaction = manager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragment.show(transaction, PROGRESS_DIALOG_TAG)
    }

    override fun hide(manager: FragmentManager) {
        val fragment = manager.findFragmentByTag(PROGRESS_DIALOG_TAG) as? ProgressDialogFragment
        fragment?.dismissAllowingStateLoss()
    }
}
