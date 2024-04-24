package com.example.reportsfordrivers.viewmodel.setting.uistate

import androidx.compose.runtime.snapshots.SnapshotStateList


data class CountriesSetting(
    var listCountries: SnapshotStateList<CountryDetailingSetting> = SnapshotStateList()
)

data class CountryDetailingSetting(
    val id: Int = 0,
    val country: String = "",
    val shortCountry: String = "",
    val rating: Int = 0,
    val favorite: Int = 0
)

data class TownshipsSetting(
    var listTownships: SnapshotStateList<TownshipDetailingSetting> = SnapshotStateList()
)

data class TownshipDetailingSetting(
    val id: Int = 0,
    val township: String = "",
    val countryId: Int = 0,
    val rating: Int = 0,
    val favorite: Int = 0
)