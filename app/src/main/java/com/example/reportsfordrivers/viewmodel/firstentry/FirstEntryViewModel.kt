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

    var isOpenDialog = mutableStateOf(false)

    /**
     * Метод для удаления элемента в списке транспорта (хрен знает как работает, но работает)
     */
    fun deletePositionVehicle(item: ObjectVehicle) {
        uiState.value.listVehicles.remove(item)
    }

    /**
     * Метод для обновления значения ФИО в текстовых полях
     */
    private fun updateFio(itemDetails: FioItemDetails) {
        uiState.value = uiState.value.copy(fioItemDetails = itemDetails)
    }

    fun updateLastName(lastName: String) {
        updateFio(uiState.value.fioItemDetails.copy(lastName = lastName))
    }

    fun updateFirstName(firstName: String) {
        updateFio(uiState.value.fioItemDetails.copy(firstName = firstName))
    }

    fun updatePatronymic(patronymic: String) {
        updateFio(uiState.value.fioItemDetails.copy(patronymic = patronymic))
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
    private fun updateMakeRn(makeRnItemDetails: MakeRnItemDetails) {
        vehicleUiState.value = vehicleUiState.value.copy(makeRnItemDetails = makeRnItemDetails)
    }

    fun updateMake(make: String) {
        updateMakeRn(vehicleUiState.value.makeRnItemDetails.copy(make = make))
    }

    fun updateRn(rn: String) {
        updateMakeRn(vehicleUiState.value.makeRnItemDetails.copy(rn = rn))
    }

    /**
     * Метод для активации кнопки Add при условии если текстовые поля MAKE и RN заполненные
     */
    fun validateAddVehicle(): Boolean {
        return vehicleUiState.value.makeRnItemDetails.make != "" && vehicleUiState.value.makeRnItemDetails.rn != ""
    }

    /**
     * Метод для активации кнопки Save при условии если текстовые поля First, Last, Patronymic
     * заполненные
     */
    fun validateSave(): Boolean {
        return uiState.value.fioItemDetails.firstName != "" &&
                uiState.value.fioItemDetails.lastName != "" &&
                uiState.value.fioItemDetails.patronymic != ""
    }

    /**
     * Добавление элемента в список [uiState] при условии что [validateAddVehicle()] возвращает true
     * Так же после добавления элемента полностью обновляются поля MAKE и RN, а так же RadioButton
     * сбрасывается до значения VEHICLE
     */
    fun addElementVehicle() {
        uiState.value.listVehicles.add(createObjectVehicle())
        resetVehicle()
        Log.i(TAG, "Add Element vehicle")
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
