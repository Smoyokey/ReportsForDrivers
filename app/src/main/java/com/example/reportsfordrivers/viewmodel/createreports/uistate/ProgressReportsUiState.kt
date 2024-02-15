package com.example.reportsfordrivers.viewmodel.createreports.uistate

data class ProgressReportsUiState(
    val progressDetails: ProgressDetails = ProgressDetails(),
    val listProgress: MutableList<ProgressDetails> = mutableListOf()
)

data class ProgressDetails(
    val country: String = "",
    val township: String = "",
    val distance: String = "",
    val cargoWeight: String = ""
)