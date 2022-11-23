package com.weather.route

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.weather.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RouteFragment : Fragment(R.layout.fragment_route) {

    private val viewModel by viewModels<RouteFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun navigationToHomePage() {
        val navDirections = RouteFragmentDirections.actionRouteFragmentToHomePageFragment()
        findNavController().navigate(navDirections)
    }
}
