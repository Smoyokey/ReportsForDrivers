package com.example.reportsfordrivers.viewmodel.setting

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.viewmodel.setting.uistate.PersonalDataUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class PersonalDataViewModel @Inject constructor(
    private val fioPreferencesRepository: FioFirstEntryRepository
) : ViewModel() {

    var uiState = mutableStateOf(openScreen())
        private set

    private fun openScreen(): PersonalDataUiState = runBlocking {
        return@runBlocking PersonalDataUiState(
            lastName = fioPreferencesRepository.getLastName().getOrDefault("Test"),
            firstName = fioPreferencesRepository.getFirstName().getOrDefault("Test"),
            patronymic = fioPreferencesRepository.getPatronymic().getOrDefault("Test")
        )
    }

    fun updateLastName(lastName: String) {
        uiState.value = uiState.value.copy(lastName = lastName)
    }

    fun updateFirstName(firstName: String) {
        uiState.value = uiState.value.copy(firstName = firstName)
    }

    fun updatePatronymic(patronymic: String) {
        uiState.value = uiState.value.copy(patronymic = patronymic)
    }

    fun saveButton() = runBlocking {
        fioPreferencesRepository.setFirstName(uiState.value.firstName)
        fioPreferencesRepository.setLastName(uiState.value.lastName)
        fioPreferencesRepository.setPatronymic(uiState.value.patronymic)
    }
}