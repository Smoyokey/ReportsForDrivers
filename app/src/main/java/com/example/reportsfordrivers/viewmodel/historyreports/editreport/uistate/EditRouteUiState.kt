package com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.reportsfordrivers.viewmodel.createreports.uistate.RouteElement

data class EditRouteUiState(
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