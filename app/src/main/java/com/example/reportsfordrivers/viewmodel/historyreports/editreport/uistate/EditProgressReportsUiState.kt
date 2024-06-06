package com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate

import androidx.compose.runtime.snapshots.SnapshotStateList

data class EditProgressReportsUiState(
    val editProgressReportsList: SnapshotStateList<EditProgressReportsDetailingUiState> =
        SnapshotStateList()
)

data class EditProgressReportsDetailingUiState(
    val id: Int = 0,
    val country: String = "",
    val townshipOne: String = "",
    val townshipTwo: String = "",
    val distance: String = "",
    val cargoWeight: String = "",
    val date: String = "",
    val isAdd: Int = 0
)