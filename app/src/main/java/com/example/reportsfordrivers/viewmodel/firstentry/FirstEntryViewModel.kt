package com.example.reportsfordrivers.viewmodel.firstentry

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

private const val TAG = "FirstEntryViewModel"

@HiltViewModel
class FirstEntryViewModel @Inject constructor (
    private val fioFirstEntryPreferencesRepository: FioFirstEntryRepository
): ViewModel(
){

    var uiState = mutableStateOf(FirstEntryUiState())
        private set

    fun updateFio(itemDetails: FioItemDetails) {
        uiState.value = uiState.value.copy(itemDetails = itemDetails)
    }

    fun selectedPosition(isSelected: IsSelectedVehicleAndTrailer) {
        uiState.value = uiState.value.copy(isSelected = isSelected)
    }

    fun updateMakeRn(makeRnItemDetails: MakeRnItemDetails) {
        uiState.value = uiState.value.copy(makeRnItemDetails = makeRnItemDetails)
    }


    //    fun selectedPosition(isSelected: IsSelectedVehicleAndTrailer) {
////        _uiState.isSelected = isSelected
//        _uiState.update { i ->
//            i.copy (
//                isSelected = isSelected
//            )
//        }
//    }

//    fun updateMakeRnUiState(makeRnItemDetails: MakeRnItemDetails) {
//        uiState = FirstEntryUiState(makeRnItemDetails = makeRnItemDetails)
//        _uiState.makeRnItemDetails = makeRnItemDetails
//        _uiState.update { i ->
//            i.copy (
//                makeRnItemDetails = makeRnItemDetails
//            )
//        }
//    }
//
//    fun validateInput(itemUiState: MakeRnItemDetails = _uiState.value.makeRnItemDetails): Boolean {
//        return with(itemUiState) {
//            make.isNotBlank() && rn.isNotBlank()
//        }
//    }
//


    fun onFirstEntry() = runBlocking {
        fioFirstEntryPreferencesRepository.setFirstEntry(false)
    }


}
