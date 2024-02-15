package com.example.reportsfordrivers.viewmodel.createreports

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.viewmodel.createreports.uistate.ProgressDetails
import com.example.reportsfordrivers.viewmodel.createreports.uistate.ProgressReportsUiState

class ProgressReportsViewModel: ViewModel() {

    var uiState = mutableStateOf(ProgressReportsUiState())
        private set

    fun updateParamTextField(itemDetails: ProgressDetails) {
        uiState.value = uiState.value.copy(progressDetails = itemDetails)
    }
}