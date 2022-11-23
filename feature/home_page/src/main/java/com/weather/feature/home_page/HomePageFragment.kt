package com.weather.feature.home_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.weather.feature.home_page.adapter.DayListAdapter
import com.weather.feature.home_page.adapter.DayListDecoration
import com.weather.feature.home_page.databinding.FragmentHomePageBinding
import com.weather.feature.home_page.model.DayForecast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageFragment : Fragment(R.layout.fragment_home_page) {

    private val viewModel by viewModels<HomePageViewModel>()

    private lateinit var binding: FragmentHomePageBinding

    private lateinit var dayListAdapter: DayListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentHomePageBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dayListAdapter = DayListAdapter(::onDayClick)
        with(binding.rvWeatherForecast) {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DayListDecoration(requireContext()))
            adapter = dayListAdapter
        }

        viewModel.apply {
            uiState.observe(viewLifecycleOwner) { uiState ->
                when (uiState) {
                    HomePageViewModel.UiState.Loading -> {
                        // TODO: Show progress dialog
                    }
                    is HomePageViewModel.UiState.Success -> with(uiState.weatherModel) {
                        // TODO: Update model, hide progress dialog
                        binding.btnCity.text = city
                        binding.tvTemperature.text = temperature
                        binding.tvDescription.text = weatherDescription
                        setDailyForecasts(dailyForecasts)

                        Glide.with(requireContext())
                            .load(iconUrl)
                            .centerInside()
                            .into(binding.ivWeatherIcon)
                    }
                    is HomePageViewModel.UiState.Failure -> {
                        // TODO: Update model, hide progress dialog
                        val errorMessage = uiState.message
                        // TODO: Show error message
                    }
                }
            }
            fetchWeather()
        }
    }

    private fun onDayClick(id: Long) {
        // TODO: Navigate to hourly forecast page
    }

    private fun setDailyForecasts(forecast: List<DayForecast>) {
        dayListAdapter.setItems(forecast)
    }
}
