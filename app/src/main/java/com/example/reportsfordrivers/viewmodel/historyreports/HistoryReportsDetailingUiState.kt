package com.example.reportsfordrivers.viewmodel.historyreports

import androidx.compose.runtime.snapshots.SnapshotStateList

data class HistoryReportsDetailingUiState(
    val waybill: String = "",
    val mainCity: String = "",
    val date: String = "",
    val nameReport: String = "",
    var historyPersonalInfoUiState: HistoryPersonalInfoUiState = HistoryPersonalInfoUiState(),
    val historyVehicleUiState: HistoryVehicleUiState = HistoryVehicleUiState(),
    val historyTrailerUiState: HistoryTrailerUiState = HistoryTrailerUiState(),
    val historyRouteUiState: HistoryRouteUiState = HistoryRouteUiState(),
    val historyProgressReportsUiState: SnapshotStateList<HistoryProgressReportDetailingUiState> =
        SnapshotStateList(),
    val historyExpensesTripUiState: SnapshotStateList<HistoryExpensesTripDetailingUiState> =
        SnapshotStateList()
)

data class HistoryPersonalInfoUiState(
    val lastName: String = "",
    val firstName: String = "",
    val patronymic: String = ""
)

data class HistoryVehicleUiState(
    val makeVehicle: String = "",
    val rnVehicle: String = ""
)

data class HistoryTrailerUiState(
    val makeTrailer: String = "",
    val rnTrailer: String = ""
)

data class HistoryRouteUiState(
    val route: String = "",
    val dateDeparture: String = "",
    val dateReturn: String = "",
    val dateCrossingDeparture: String = "",
    val dateCrossingReturn: String = "",
    val speedometerDeparture: String = "",
    val speedometerReturn: String = "",
    val fuelled: String = ""
)

data class HistoryProgressReportDetailingUiState(
    val date: String,
    val country: String,
    val townshipOne: String,
    val townshipTwo: String,
    val distance: String,
    val weight: String
)

data class HistoryExpensesTripDetailingUiState(
    val date: String,
    val documentNumber: String,
    val expenseItem: String,
    val sum: String,
    val currency: String
)