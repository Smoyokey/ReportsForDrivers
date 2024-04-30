package com.example.reportsfordrivers.viewmodel.createreports.uistate

import androidx.compose.runtime.snapshots.SnapshotStateList

data class CreateVehicleTrailerUiState (
    val id: Int = 0,
    val makeVehicle: String = "",
    val rnVehicle: String = "",
    val makeTrailer: String = "",
    val rnTrailer: String = ""
)

data class CreateVehicleTrailerListVehicle(
    val listVehicle: SnapshotStateList<CreateVehicleTrailerDetailingListVehicle> = SnapshotStateList()
)

data class CreateVehicleTrailerDetailingListVehicle(
    val makeVehicle: String = "",
    val rnVehicle: String = ""
)

data class CreateVehicleTrailerListTrailer(
    val listTrailer: SnapshotStateList<CreateVehicleTrailerDetailingListTrailer> = SnapshotStateList()
)

data class CreateVehicleTrailerDetailingListTrailer(
    val makeTrailer: String = "",
    val rnTrailer: String = ""
)

data class CreateObjectVehicleOrTrailer(
    val type: String = "",
    val make: String = "",
    val rn: String = ""
)