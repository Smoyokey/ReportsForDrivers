package com.example.reportsfordrivers.viewmodel.firstentry

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.reportsfordrivers.data.structure.CountryRus
import com.example.reportsfordrivers.data.structure.TownshipRus
import com.example.reportsfordrivers.viewmodel.ObjectVehicle


data class VehicleObject(
    var isSelected: IsSelectedVehicleAndTrailer = IsSelectedVehicleAndTrailer(),
    var makeRnItemDetails: MakeRnItemDetails = MakeRnItemDetails(),
)

/**
 * Класс данных для сохранения в ДатаСтор и БД
 */
data class FirstEntryUiState(
    val listVehicles: SnapshotStateList<ObjectVehicle> = SnapshotStateList(),
    val fioItemDetails: FioItemDetails = FioItemDetails(),
    val selectedCountriesAndCities: SelectedCountriesAndCities = SelectedCountriesAndCities()
)

data class FioItemDetails (
    var lastName: String = "",
    val firstName: String = "",
    val patronymic: String = ""
)

data class IsSelectedVehicleAndTrailer(
    val stateRadioGroup: Boolean = true
)

data class MakeRnItemDetails (
    val make: String = "",
    val rn: String = ""
)

data class Countries(
    var listCountries: SnapshotStateList<CountryDetailing> = SnapshotStateList()
)

data class CountryDetailing(
    val id: Int = 0,
    val country: String = "",
    val shortCountry: String = "",
    val rating: Int = 0,
    var favorite: Int = 0
)

data class Townships(
    var listTownships: SnapshotStateList<TownshipDetailing> = SnapshotStateList()
)

data class TownshipDetailing(
    val id: Int = 0,
    val township: String = "",
    val countryId: Int = 0,
    val rating: Int = 0,
    val favorite: Int = 0

)

/**
 * Класс данных для добавления города в БД городов
 */
data class AddCity(
    val nameCity: String = "",
    val nameCountry: String = "",
    val country: CountryDetailing = CountryDetailing()
)


/**
 * Добавленные города в избранное
 */
data class SelectedCountriesAndCities(
    val listFavoriteCountry: MutableList<CountryDetailing> = mutableListOf(),
    val listFavoriteTownship: MutableList<TownshipDetailing> = mutableListOf()
)

data class SearchField(
    val country: String,
    val township: String,
    val countryAdd: String
)

enum class VehicleOrTrailer(title: String) {
    VEHICLE("Vehicle"),
    TRAILER("Trailer")
}