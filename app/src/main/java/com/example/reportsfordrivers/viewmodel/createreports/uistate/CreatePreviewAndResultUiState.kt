package com.example.reportsfordrivers.viewmodel.createreports.uistate

import androidx.compose.runtime.snapshots.SnapshotStateList

data class CreateReports(
    val dataCreateReportInfo: CreateReportInfoUiState = CreateReportInfoUiState(),
    val dataCreatePersonalInfo: CreatePersonalInfoUiState = CreatePersonalInfoUiState(),
    val dataCreateVehicleInfo: CreateVehicleTrailerUiState = CreateVehicleTrailerUiState(),
    val dataCreateRoute: CreateReportsRouteUiState = CreateReportsRouteUiState(),
    val listDataCreateProgressReports: SnapshotStateList<CreateProgressReportsDetailingUiState> = SnapshotStateList(),
    val listDataCreateExpensesTrip: SnapshotStateList<CreateExpensesTripDetailingUiState> = SnapshotStateList(),
    val reportName: String = ""
)

data class CreateReportsRouteUiState(
    val route: String = "",
    val dateDeparture: String = "",
    val dateReturn: String = "",
    val dateCrossingDeparture: String = "",
    val dateCrossingReturn: String = "",
    val speedometerDeparture: String = "",
    val speedometerReturn: String = "",
    val fuelled: String = ""
)

data class SelectedNavigationUiState(
    val isValidateCreateReportInfo: Boolean = false,
    val isValidateCreatePersonalInfo: Boolean = false,
    val isValidateCreateVehicleTrailer: Boolean = false,
    val isValidateCreateRoute: Boolean = false,
    val isValidateCreateProgressReports: Boolean = false,
)
