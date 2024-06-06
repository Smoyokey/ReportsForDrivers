package com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate

data class EditReportInfoUiState(
    val id: Int = 0,
    val date: String = "",
    val waybill: String = "",
    val mainCity: String = "",
    val isStart: Boolean = false
)