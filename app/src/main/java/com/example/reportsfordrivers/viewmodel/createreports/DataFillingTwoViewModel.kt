package com.example.reportsfordrivers.viewmodel.createreports

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.viewmodel.createreports.uistate.DataDetails
import com.example.reportsfordrivers.viewmodel.createreports.uistate.DataFillingTwoUiState

class DataFillingTwoViewModel: ViewModel() {

    var uiState = mutableStateOf(DataFillingTwoUiState())
        private set

    fun updateParamTextField(itemDetails: DataDetails) {
        uiState.value = uiState.value.copy(dataDetails = itemDetails)
    }
}