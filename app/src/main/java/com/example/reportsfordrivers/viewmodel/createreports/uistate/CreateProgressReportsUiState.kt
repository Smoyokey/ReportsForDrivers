package com.example.reportsfordrivers.viewmodel.createreports.uistate

import androidx.compose.runtime.snapshots.SnapshotStateList

data class CreateProgressReportsUiState (
    val createProgressReportsList: SnapshotStateList<CreateProgressReportsDetailingUiState> =
        SnapshotStateList()
)

data class CreateProgressReportsDetailingUiState(
    val id: Int = 0,
    val country: String = "",
    val townshipOne: String = "",
    val townshipTwo: String = "",
    val distance: String = "",
    val cargoWeight: String = "",
    val date: String = "",
    val isAdd: Int = 0
)