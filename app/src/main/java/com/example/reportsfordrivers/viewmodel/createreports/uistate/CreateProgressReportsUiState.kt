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
    val date: String = ""
)

data class CountriesProgressReports(
    var listCountries: SnapshotStateList<CountryDetailingProgressReports> = SnapshotStateList()
)

data class CountryDetailingProgressReports(
    val id: Int = 0,
    val country: String = "",
    val shortCountry: String = "",
    val rating: Int = 0,
    val favorite: Int = 0
)

data class TownshipsProgressReports(
    var listTownships: SnapshotStateList<TownshipDetailingProgressReports> = SnapshotStateList()
)

data class TownshipDetailingProgressReports(
    val id: Int = 0,
    val township: String = "",
    val countryId: Int = 0,
    val rating: Int = 0,
    val favorite: Int = 0
)