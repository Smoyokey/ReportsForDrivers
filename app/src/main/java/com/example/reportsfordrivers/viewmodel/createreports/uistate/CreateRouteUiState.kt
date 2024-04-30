package com.example.reportsfordrivers.viewmodel.createreports.uistate

import androidx.compose.runtime.snapshots.SnapshotStateList

data class CreateRouteUiState (
    val id: Int = 0,
    val route: SnapshotStateList<RouteElement> = SnapshotStateList(),
    val dateDeparture: String = "",
    val dateReturn: String = "",
    val dateCrossingDeparture: String = "",
    val dateCrossingReturn: String = "",
    val speedometerDeparture: String = "",
    val speedometerReturn: String = "",
    val fuelled: String = ""
)

data class RouteElement (
    val id: Int,
    val text: String
)

data class CountriesRoute(
    var listCountries: SnapshotStateList<CountryDetailingRoute> = SnapshotStateList()
)

data class CountryDetailingRoute(
    val id: Int = 0,
    val country: String = "",
    val shortCountry: String = "",
    val rating: Int = 0,
    val favorite: Int = 0
)

data class TownshipsRoute(
    var listTownships: SnapshotStateList<TownshipDetailingRoute> = SnapshotStateList()
)

data class TownshipDetailingRoute(
    val id: Int = 0,
    val township: String = "",
    val countryId: Int = 0,
    val rating: Int = 0,
    val favorite: Int = 0
)

data class AddCityCreateRoute(
    val nameCity: String = "",
    val nameCountry: String = "",
    val country: CountryDetailingRoute = CountryDetailingRoute()
)