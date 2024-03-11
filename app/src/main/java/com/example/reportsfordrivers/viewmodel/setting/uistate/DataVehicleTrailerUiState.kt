package com.example.reportsfordrivers.viewmodel.setting.uistate

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.reportsfordrivers.viewmodel.ObjectVehicle

data class DataVehicleTrailerUiState(
    var listVehicles: SnapshotStateList<ObjectVehicle> = SnapshotStateList()
)

