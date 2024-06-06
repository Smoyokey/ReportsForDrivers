package com.example.reportsfordrivers.viewmodel.historyreports

import androidx.compose.runtime.snapshots.SnapshotStateList


data class HistoryReportsListUiState(
    val historyReportsList: SnapshotStateList<HistoryReportsListDetailingUiState> = SnapshotStateList()
)

data class HistoryReportsListDetailingUiState(
    val id: Int = 0,
    val nameReport: String,
    val dateCreate: String,
    val routeMainInfo: String
)