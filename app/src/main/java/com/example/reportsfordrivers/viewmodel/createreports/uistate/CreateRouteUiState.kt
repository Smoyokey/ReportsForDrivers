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