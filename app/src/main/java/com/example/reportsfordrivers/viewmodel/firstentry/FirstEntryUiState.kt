package com.example.reportsfordrivers.viewmodel.firstentry

import androidx.compose.runtime.MutableState


data class VehicleObject(
    var isSelected: IsSelectedVehicleAndTrailer = IsSelectedVehicleAndTrailer(),
    var makeRnItemDetails: MakeRnItemDetails = MakeRnItemDetails(),
)

data class FirstEntryUiState(
    val listVehicles: MutableList<ObjectVehicle> = mutableListOf(),
    val fioItemDetails: FioItemDetails = FioItemDetails()
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

data class ObjectVehicle(
    val make: String = "",
    val rn: String = "",
    val vehicleOrTrailer: VehicleOrTrailer = VehicleOrTrailer.VEHICLE
)

data class UiState(
    val lastName: String = "",
    val firstName: String = ""
)

enum class VehicleOrTrailer(b: String) {
    VEHICLE("Vehicle"),
    TRAILER("Trailer")
}