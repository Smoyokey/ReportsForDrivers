package com.example.reportsfordrivers.viewmodel.createreports.uistate

data class DataFillingTwoUiState(
    val dataDetails: DataDetails = DataDetails()
)

data class DataDetails(
    val route: String = "",
    val dateDeparture: String = "",
    val dateReturn: String = "",
    val dateCrossingDeparture: String = "",
    val dataCrossingReturn: String = "",
    val speedometerDeparture: String = "",
    val speedometerReturn: String = "",
    val fuelled: String = ""
    )
