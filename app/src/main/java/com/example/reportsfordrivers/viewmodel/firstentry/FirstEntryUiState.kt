package com.example.reportsfordrivers.viewmodel.firstentry

import androidx.compose.runtime.snapshots.SnapshotStateList


data class VehicleObject(
    var isSelected: IsSelectedVehicleAndTrailer = IsSelectedVehicleAndTrailer(),
    var makeRnItemDetails: MakeRnItemDetails = MakeRnItemDetails(),
)

data class FirstEntryUiState(
    val listVehicles: SnapshotStateList<ObjectVehicle> = SnapshotStateList(),
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

enum class VehicleOrTrailer(title: String) {
    VEHICLE("Vehicle"),
    TRAILER("Trailer")
}