package com.example.reportsfordrivers.viewmodel.createreports.uistate

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.reportsfordrivers.viewmodel.firstentry.CountryDetailing

data class CreateReportInfoUiState (
    val id: Int = 0,
    val date: String = "",
    val waybill: String = "",
    val mainCity: String = "",
    val isStart: Boolean = false
)
