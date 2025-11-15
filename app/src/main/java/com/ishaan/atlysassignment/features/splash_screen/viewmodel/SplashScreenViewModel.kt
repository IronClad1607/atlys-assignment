package com.ishaan.atlysassignment.features.splash_screen.viewmodel

import androidx.lifecycle.viewModelScope
import com.ishaan.atlysassignment.base.BaseViewModel
import com.ishaan.atlysassignment.features.splash_screen.data.SplashScreenUIEvent
import com.ishaan.atlysassignment.features.splash_screen.data.SplashScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor() : BaseViewModel<
        SplashScreenUIState,
        SplashScreenUIEvent
        >(SplashScreenUIState) {

    fun loadApp() {
        viewModelScope.launch {
            delay(300)
            _events.emit(SplashScreenUIEvent.Success)
        }
    }
}