package com.weather.feature.home_page

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.weather.feature.home_page.core.HomePageInteractor
import com.weather.feature.home_page.model.DayForecast
import com.weather.feature.home_page.model.WeatherModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class HomePageViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var interactor: HomePageInteractor
    private lateinit var viewModel: HomePageViewModel

    @Before
    fun setup() {
        interactor = mockk()
        viewModel = HomePageViewModel(interactor)
    }

    @Test
    fun `Successful fetch request`() = runBlocking {
        val weatherModel = WeatherModel(
            "Chernihiv",
            "https://openweathermap.org/img/wn/04d@2x.png",
            "11.5",
            "Overcast clouds",
            listOf(
                DayForecast(1L, "Today", "11.52°C / 10.16°C"),
                DayForecast(2L, "Tomorrow", "16.1°C / 11.97°C"),
                DayForecast(3L, "15.09 Thursday", "17.9°C / 12.89°C"),
                DayForecast(null, "16.09 Friday", "17.68°C / 16.12°C"),
                DayForecast(null, "17.09 Saturday", "13.14°C / 10.54°C"),
                DayForecast(null, "18.09 Sunday", "13.34°C / 13.15°C"),
                DayForecast(null, "19.09 Monday", "10.93°C / 10.92°C"),
                DayForecast(null, "20.09 Tuesday", "14.94°C / 11.3°C")
            )
        )
        coEvery { interactor.fetchWeather() } returns HomePageViewModel.UiState.Success(weatherModel)

        Assert.assertEquals(null, viewModel.uiState.value)

        viewModel.fetchWeather()

        coVerify { interactor.fetchWeather() }

        val expectedWeatherModel = HomePageViewModel.UiState.Success(weatherModel)

        Assert.assertEquals(expectedWeatherModel, viewModel.uiState.value)
    }

    @Test
    fun `Failure fetch request`() = runBlocking {
        val errorMessage = "Some error"
        coEvery { interactor.fetchWeather() } returns HomePageViewModel.UiState.Failure(errorMessage)

        Assert.assertEquals(null, viewModel.uiState.value)

        viewModel.fetchWeather()

        coVerify { interactor.fetchWeather() }

        val expectedWeatherModel = HomePageViewModel.UiState.Failure(errorMessage)

        Assert.assertEquals(expectedWeatherModel, viewModel.uiState.value)
    }
}
