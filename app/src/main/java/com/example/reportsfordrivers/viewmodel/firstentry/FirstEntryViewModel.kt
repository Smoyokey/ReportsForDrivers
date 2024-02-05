package com.example.reportsfordrivers.viewmodel.firstentry

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FirstEntryViewModel : ViewModel(){

    private val _uiState = MutableStateFlow(FirstEntryUiState())
    val uiState: StateFlow<FirstEntryUiState> = _uiState.asStateFlow()

    fun setLastName(lastName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                lastName = lastName
            )
        }
    }
}