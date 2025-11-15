package com.ishaan.atlysassignment.features.splash_screen.data

data object SplashScreenUIState

sealed class SplashScreenUIEvent {
    data object Success : SplashScreenUIEvent()
}