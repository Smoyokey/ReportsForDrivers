package com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate

import androidx.compose.runtime.snapshots.SnapshotStateList

data class EditVehicleTrailerUiState(
    val id: Int = 0,
    val makeVehicle: String = "",
    val rnVehicle: String = "",
    val makeTrailer: String = "",
    val rnTrailer: String = ""
)

data class EditVehicleTrailerListVehicle(
    var listVehicle: SnapshotStateList<EditVehicleTrailerDetailingListVehicle> = SnapshotStateList()
)

data class EditVehicleTrailerDetailingListVehicle(
    val makeVehicle: String = "",
    val rnVehicle: String = ""
)

data class EditVehicleTrailerListTrailer(
    var listTrailer: SnapshotStateList<EditVehicleTrailerDetailingListTrailer> = SnapshotStateList()
)

data class EditVehicleTrailerDetailingListTrailer(
    val makeTrailer: String = "",
    val rnTrailer: String = ""
)

data class EditObjectVehicleOrTrailer(
    val type: String = "",
    val make: String = "",
    val rn: String = ""
)