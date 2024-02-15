package com.example.reportsfordrivers.viewmodel.createreports.uistate

data class DataFillingOneUiState (
    val fioDataFillingOne: FioDataFillingOne = FioDataFillingOne(),
)

data class FioDataFillingOne(
    val lastName: String = "",
    val firstName: String = "",
    val patronymic: String ="",
    val makeVehicle: String = "",
    val rnVehicle: String = "",
    val makeTrailer: String = "",
    val rnTrailer: String = ""
)