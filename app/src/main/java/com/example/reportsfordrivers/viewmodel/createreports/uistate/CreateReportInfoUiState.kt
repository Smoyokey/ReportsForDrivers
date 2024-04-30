package com.example.reportsfordrivers.viewmodel.createreports.uistate

import androidx.compose.runtime.snapshots.SnapshotStateList

data class CreateReportInfoUiState (
    val id: Int = 0,
    val date: String = "",
    val waybill: String = "",
    val mainCity: String = "",
    val isStart: Boolean = false
)

data class CountriesCreateReportInfo(
    var listCountries: SnapshotStateList<CountryDetailingCreateReportInfo> = SnapshotStateList()
)

data class CountryDetailingCreateReportInfo(
    val id: Int = 0,
    val country: String = "",
    val shortCountry: String = "",
    val rating: Int = 0,
    val favorite: Int = 0
)

data class TownshipsCreateReportInfo(
    var listTownships: SnapshotStateList<TownshipDetailingCreateReportInfo> = SnapshotStateList()
)

data class TownshipDetailingCreateReportInfo(
    val id: Int = 0,
    val township: String = "",
    val countryId: Int = 0,
    val rating: Int = 0,
    val favorite: Int = 0
)

data class AddCityCreateReportInfo(
    val nameCity: String = "",
    val nameCountry: String = "",
    val country: CountryDetailingCreateReportInfo = CountryDetailingCreateReportInfo()
)