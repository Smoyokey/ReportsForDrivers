package com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate

import androidx.compose.runtime.snapshots.SnapshotStateList

data class EditReports(
    val dataEditReportInfo: EditReportInfoUiState = EditReportInfoUiState(),
    val dataEditPersonalInfo: EditPersonalInfoUiState = EditPersonalInfoUiState(),
    val dataEditVehicleInfo: EditVehicleTrailerUiState = EditVehicleTrailerUiState(),
    val dataEditRoute: EditReportsRouteUiState = EditReportsRouteUiState(),
    val listDataEditProgressReports: SnapshotStateList<EditProgressReportsDetailingUiState> = SnapshotStateList(),
    val listDataEditExpensesTrip: SnapshotStateList<EditExpensesTripDetailingUiState> = SnapshotStateList(),
    val reportName: String = "",
    val editReportsId: EditReportsId = EditReportsId()
)

data class EditReportsRouteUiState(
    val route: String = "",
    val dateDeparture: String = "",
    val dateReturn: String = "",
    val dateCrossingDeparture: String = "",
    val dateCrossingReturn: String = "",
    val speedometerDeparture: String = "",
    val speedometerReturn: String = "",
    val fuelled: String = ""
)

data class EditReportsId(
    val mainInfoId: Int = 0,
    val reportNameId: Int = 0,
    val personalInfoId: Int = 0,
    val vehicleId: Int = 0,
    val trailerId: Int = 0,
    val routeId: Int = 0
)