package com.example.reportsfordrivers.viewmodel.createreports

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.viewmodel.createreports.uistate.DataFillingOneUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.FioDataFillingOne
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "DataFillingOneViewModel"

@HiltViewModel
class DataFillingOneViewModel @Inject constructor (
    private val fioPreferencesRepository: FioFirstEntryRepository
): ViewModel() {

    var uiState = mutableStateOf(DataFillingOneUiState())
        private set

    fun updateParam(itemDetails: FioDataFillingOne) {
        uiState.value = uiState.value.copy(fioDataFillingOne = itemDetails)
    }
}