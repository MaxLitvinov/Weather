package com.weather.route

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.weather.MainActivityViewModel
import androidx.navigation.fragment.findNavController
import com.weather.R
import com.weather.foundation.dialog.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RouteFragment : Fragment(R.layout.fragment_route) {

    @Inject
    lateinit var progressDialog: ProgressDialog

    private val viewModel by viewModels<RouteFragmentViewModel>()
    private val activityViewModel by activityViewModels<MainActivityViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityViewModel.isDataLoaded.observe(viewLifecycleOwner) { isDataLoaded ->
            when (isDataLoaded) {
                true -> {
                    // TODO: Action when splash screen animation has finished
                }
                false -> {
                    // TODO: Action on splash screen animation is in progress
                }
            }
        }
    }

    private fun navigationToHomePage() {
        val navDirections = RouteFragmentDirections.actionRouteFragmentToHomePageFragment()
        findNavController().navigate(navDirections)
    }
}
