package com.weather.progress

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.weather.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProgressDialogFragment : DialogFragment(R.layout.dialog_progress) {

    companion object {

        private const val PROGRESS_DIALOG_TAG = "ProgressDialogTag"

        fun start(manager: FragmentManager): ProgressDialogFragment {
            val dialog = newInstance()
            dialog.isCancelable = false

            val transaction = manager.beginTransaction()
            transaction.add(dialog, PROGRESS_DIALOG_TAG)
            transaction.commitAllowingStateLoss()
            return dialog
        }

        private fun newInstance() = ProgressDialogFragment()

        fun hide(manager: FragmentManager) {
            val fragment = manager.findFragmentByTag(PROGRESS_DIALOG_TAG) as? ProgressDialogFragment
            fragment?.dismissAllowingStateLoss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        return dialog
    }

    fun show(manager: FragmentManager) {
        val transaction = manager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        show(transaction, PROGRESS_DIALOG_TAG)
    }

    fun hide(manager: FragmentManager) {
        val fragment = manager.findFragmentByTag(PROGRESS_DIALOG_TAG) as? ProgressDialogFragment
        fragment?.dismissAllowingStateLoss()
    }
}
