package com.ishaan.atlysassignment.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

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

    /**
     * Safely updates the current UI state using the provided update lambda.
     * Catches and logs any exceptions that occur during the update to prevent crashes.
     */
    protected fun safeUpdateState(update: (S) -> S) {
        try {
            // Perform the state update using the provided lambda
            _uiState.update(update)
        } catch (e: Exception) {
            // Handle and record any exception that occurs during the update using the centralized error utility
            e.printStackTrace()
        }
    }
}