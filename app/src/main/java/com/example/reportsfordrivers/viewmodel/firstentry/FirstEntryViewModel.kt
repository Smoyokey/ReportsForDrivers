package com.example.reportsfordrivers.viewmodel.firstentry

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

private const val TAG = "FirstEntryViewModel"

@HiltViewModel
class FirstEntryViewModel @Inject constructor (
    private val fioFirstEntryPreferencesRepository: FioFirstEntryRepository
): ViewModel(){

    /**
     * [uiState] - хранилище ФИО и списка машин с прицепами типа [FirstEntryUiState()].
     * [vehicleUiState] - Хранилище введенных данных о машине или прицепе типа [VehicleObject()]
     */
    var uiState = mutableStateOf(FirstEntryUiState())
        private set
    var vehicleUiState = mutableStateOf(VehicleObject())
        private set

    fun deletePositionVehicle(listVehicles: MutableList<ObjectVehicle>) {
        uiState.value = uiState.value.copy(listVehicles = listVehicles)
    }

    /**
     * Метод для обновления значения ФИО в текстовых полях
     */
    fun updateFio(itemDetails: FioItemDetails) {
        uiState.value = uiState.value.copy(fioItemDetails = itemDetails)
    }

    /**
     * Метод для выбора VEHICLE(true) или TRAILER(false)
     */
    fun selectedPosition(isSelected: IsSelectedVehicleAndTrailer) {
        vehicleUiState.value = vehicleUiState.value.copy(isSelected = isSelected)
    }

    /**
     * Метод для обновления значения данных в текстовых полях MAKE и RN
     */
    fun updateMakeRn(makeRnItemDetails: MakeRnItemDetails) {
        vehicleUiState.value = vehicleUiState.value.copy(makeRnItemDetails = makeRnItemDetails)
    }

    /**
     * Метод для активации кнопки Add при условии если текстовые поля MAKE и RN заполненные
     */
    fun validateAddVehicle(): Boolean {
        return vehicleUiState.value.makeRnItemDetails.make != "" && vehicleUiState.value.makeRnItemDetails.rn != ""
    }

    /**
     * Добавление элемента в список [uiState] при условии что [validateAddVehicle()] возвращает true
     * Так же после добавления элемента полностью обновляются поля MAKE и RN, а так же RadioButton
     * сбрасывается до значения VEHICLE
     */
    fun addElementVehicle() {
        if(validateAddVehicle()) {
            uiState.value.listVehicles.add(createObjectVehicle())
            resetVehicle()

            Log.i(TAG, "Add Element vehicle")
        } else {

        }
    }

    /**
     * Метод, который сбрасывает значения текстовых полей MAKE и RN, а так же RadioButton сбрасывается
     * до значения VEHICLE, путем создания нового объекта VehicleObject()
     */
    private fun resetVehicle() {
        vehicleUiState.value = VehicleObject()
    }

    /**
     * Создает объект для списка [uiState]
     */
    private fun createObjectVehicle(): ObjectVehicle {
        return ObjectVehicle(
            make = vehicleUiState.value.makeRnItemDetails.make,
            rn = vehicleUiState.value.makeRnItemDetails.rn,
            vehicleOrTrailer = if(vehicleUiState.value.isSelected.stateRadioGroup)
                VehicleOrTrailer.VEHICLE
            else
                VehicleOrTrailer.TRAILER
        )
    }

    fun onFirstEntry() = runBlocking {
        fioFirstEntryPreferencesRepository.setFirstEntry(false)
    }


}
