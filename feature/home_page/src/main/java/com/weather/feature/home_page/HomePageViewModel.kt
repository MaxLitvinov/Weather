package com.weather.feature.home_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.feature.home_page.core.HomePageInteractor
import com.weather.feature.home_page.model.WeatherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val interactor: HomePageInteractor
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState>
        get() = _uiState

    fun fetchWeather() {
        _uiState.postValue(UiState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.postValue(interactor.fetchWeather())
        }
    }

    fun retry() {
        _uiState.value = UiState.Loading
        fetchWeather()
    }

    sealed class UiState {

        object Loading : UiState()

        data class Success(val weatherModel: WeatherModel) : UiState()

        data class Failure(val message: String) : UiState()
    }
}
