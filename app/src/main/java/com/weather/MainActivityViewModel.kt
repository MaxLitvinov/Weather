package com.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {

    companion object {

        const val FAKE_DATA_LOADING_DELAY = 1200L
    }

    var shouldKeepSplashScreenVisible = true

    init {
        fakeDataLoading()
    }

    private val _isSplashScreenAnimationFinished = MutableLiveData(false)
    val isDataLoaded: LiveData<Boolean>
        get() = _isSplashScreenAnimationFinished

    // TODO: Imitate data loading
    private fun fakeDataLoading() =
        viewModelScope.launch {
            delay(FAKE_DATA_LOADING_DELAY)
            shouldKeepSplashScreenVisible = false
        }

    fun onAnimationEnd() =
        _isSplashScreenAnimationFinished.postValue(true)
}
