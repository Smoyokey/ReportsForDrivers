package com.example.reportsfordrivers.viewmodel.createreports

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateReports
import com.example.reportsfordrivers.viewmodel.createreports.uistate.DataFillingOne
import com.example.reportsfordrivers.viewmodel.createreports.uistate.DataFillingTwo
import com.example.reportsfordrivers.viewmodel.createreports.uistate.ProgressDetails
import com.example.reportsfordrivers.viewmodel.createreports.uistate.ProgressReports
import com.example.reportsfordrivers.viewmodel.createreports.uistate.TripExpensesDetails
import com.example.reportsfordrivers.viewmodel.createreports.uistate.TripExpensesReports
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "DataFillingOneViewModel"

@HiltViewModel
class CreateReportsViewModel @Inject constructor (
    private val fioPreferencesRepository: FioFirstEntryRepository
): ViewModel() {

    var uiState = mutableStateOf(CreateReports())
        private set

    var uiStateProgressReports = mutableStateOf(ProgressReports())
        private set

    var uiStateTripExpenses = mutableStateOf(TripExpensesReports())
        private set

    var openDialogDateFillingOne = mutableStateOf(false)

    var openDialogDataFillingTwoDateDeparture = mutableStateOf(false)
    var openDialogDataFillingTwoDateReturn = mutableStateOf(false)
    var openDialogDataFillingTwoDateCrossingDeparture = mutableStateOf(false)
    var openDialogDataFillingTwoDateCrossingReturn = mutableStateOf(false)

    var openDialogProgressReportsDate = mutableStateOf(false)

    var tabIndex = mutableStateOf(0)
    val tabs = listOf("1", "2", "3", "4", "5", "6")

    private fun updateDataFillingOne(itemDetails: DataFillingOne) {
        uiState.value = uiState.value.copy(dataFillingOne = itemDetails)
    }

    fun updateDataFillingOneDate(date: String) {
        updateDataFillingOne(uiState.value.dataFillingOne.copy(date = date))
    }

    fun updateDataFillingOneLastName(lastName: String) {
        updateDataFillingOne(uiState.value.dataFillingOne.copy(lastName = lastName))
    }

    fun updateDataFillingOneFirstName(firstName: String) {
        updateDataFillingOne(uiState.value.dataFillingOne.copy(firstName = firstName))
    }

    fun updateDataFillingOnePatronymic(patronymic: String) {
        updateDataFillingOne(uiState.value.dataFillingOne.copy(patronymic = patronymic))
    }

    fun updateDataFillingOneMakeVehicle(makeVehicle: String) {
        updateDataFillingOne(uiState.value.dataFillingOne.copy(makeVehicle = makeVehicle))
    }

    fun updateDataFillingOneRnVehicle(rnVehicle: String) {
        updateDataFillingOne(uiState.value.dataFillingOne.copy(rnVehicle = rnVehicle))
    }

    fun updateDataFillingOneMakeTrailer(makeTrailer: String) {
        updateDataFillingOne(uiState.value.dataFillingOne.copy(makeTrailer = makeTrailer))
    }

    fun updateDataFillingOneRnTrailer(rnTrailer: String) {
        updateDataFillingOne(uiState.value.dataFillingOne.copy(rnTrailer = rnTrailer))
    }

    private fun updateDataFillingTwo(itemDetails: DataFillingTwo) {
        uiState.value = uiState.value.copy(dataFillingTwo = itemDetails)
    }

    fun updateDataFillingTwoRoute(route: String) {
        updateDataFillingTwo(uiState.value.dataFillingTwo.copy(route = route))
    }

    fun updateDataFillingTwoDateReturn(dateReturn: String) {
        updateDataFillingTwo(uiState.value.dataFillingTwo.copy(dateReturn = dateReturn))
    }

    fun updateDataFillingTwoDateDeparture(dateDeparture: String) {
        updateDataFillingTwo(uiState.value.dataFillingTwo.copy(dateDeparture = dateDeparture))
    }

    fun updateDataFillingTwoDateCrossingDeparture(dateCrossingDeparture: String) {
        updateDataFillingTwo(uiState.value.dataFillingTwo.copy
            (dateCrossingDeparture = dateCrossingDeparture))
    }

    fun updateDataFillingTwoDateCrossingReturn(dateCrossingReturn: String) {
        updateDataFillingTwo(uiState.value.dataFillingTwo.copy
            (dateCrossingReturn = dateCrossingReturn))
    }

    fun updateDataFillingTwoSpeedometerDeparture(speedometerDeparture: String) {
        updateDataFillingTwo(uiState.value.dataFillingTwo.copy
            (speedometerDeparture = speedometerDeparture))
    }

    fun updateDataFillingTwoSpeedometerReturn(speedometerReturn: String) {
        updateDataFillingTwo(uiState.value.dataFillingTwo.copy
            (speedometerReturn = speedometerReturn))
    }

    fun updateDataFillingTwoFuelled(fuelled: String) {
        updateDataFillingTwo(uiState.value.dataFillingTwo.copy(fuelled = fuelled))
    }

    private fun updateProgressReports(progressDetails: ProgressDetails) {
        uiStateProgressReports.value = uiStateProgressReports.value
            .copy(progressDetails = progressDetails)
    }

    fun updateProgressReportsCountry(country: String) {
        updateProgressReports(uiStateProgressReports.value.progressDetails.copy(country = country))
    }

    fun updateProgressReportsTownship(township: String) {
        updateProgressReports(uiStateProgressReports.value.progressDetails
            .copy(township = township))
    }

    fun updateProgressReportsDistance(distance: String) {
        updateProgressReports(uiStateProgressReports.value.progressDetails
            .copy(distance = distance))
    }

    fun updateProgressReportsCargoWeight(cargoWeight: String) {
        updateProgressReports(uiStateProgressReports.value.progressDetails
            .copy(cargoWeight = cargoWeight))
    }

    fun updateProgressReportsDate(date: String) {
        updateProgressReports(uiStateProgressReports.value.progressDetails.copy(date = date))
    }

    private fun updateTripExpenses(tripExpensesDetails: TripExpensesDetails) {
        uiStateTripExpenses.value = uiStateTripExpenses.value
            .copy(tripExpensesDetails = tripExpensesDetails)
    }

    fun updateTripExpensesDate(date: String) {
        updateTripExpenses(uiStateTripExpenses.value.tripExpensesDetails.copy(date = date))
    }

    fun updateTripExpensesDocumentNumber(documentNumber: String) {
        updateTripExpenses(uiStateTripExpenses.value.tripExpensesDetails
            .copy(documentNumber = documentNumber))
    }

    fun updateTripExpensesExpenseItem(expenseItem: String) {
        updateTripExpenses(uiStateTripExpenses.value.tripExpensesDetails
            .copy(expenseItem = expenseItem))
    }

    fun updateTripExpensesSum(sum: String) {
        updateTripExpenses(uiStateTripExpenses.value.tripExpensesDetails.copy(sum = sum))
    }

    fun updateTripExpensesCurrency(currency: String) {
        updateTripExpenses(uiStateTripExpenses.value.tripExpensesDetails.copy(currency = currency))
    }

    fun updatePreviewReportName(reportName: String) {
        uiState.value = uiState.value.copy(reportName = reportName)
    }
}