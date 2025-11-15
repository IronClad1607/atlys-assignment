package com.ishaan.atlysassignment.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Base ViewModel used for general action where there are state actions required:
 * @param S: State Class -> Data Class with default values
 * @param E: Event Class -> Sealed Class with all possible events
 */
abstract class BaseViewModel<S, E>(initialState: S) : ViewModel() {

    protected val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    protected val _events = MutableSharedFlow<E>()
    val events = _events.asSharedFlow()
}