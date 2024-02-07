package com.example.reportsfordrivers.viewmodel.firstentry

import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.datastore.fio.FioRepository
import com.example.reportsfordrivers.datastore.firstentry.FirstEntryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FirstEntryViewModel @Inject constructor (
    private val firstEntryPreferencesRepository: FirstEntryRepository,
    private val fioPreferencesRepository: FioRepository
): ViewModel(
){

    private val _uiState = MutableStateFlow(FirstEntryUiState())
    val uiState: StateFlow<FirstEntryUiState> = _uiState.asStateFlow()

    fun onFirstEntry() = runBlocking {
        firstEntryPreferencesRepository.setFirstEntry(false)
    }

    fun setLastName(lastName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                lastName = lastName
            )
        }
    }
}