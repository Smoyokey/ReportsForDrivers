package com.example.reportsfordrivers.viewmodel.createreports.uistate

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.reportsfordrivers.viewmodel.ObjectVehicle

data class CreateReports(
    val dataReportInfo: DataReportInfo = DataReportInfo(),
    val dataPersonalInfo: DataPersonalInfo = DataPersonalInfo(),
    val dataVehicleInfo: DataVehicleInfo = DataVehicleInfo(),
    val dataFillingTwo: DataFillingTwo = DataFillingTwo(),
    val listProgress: SnapshotStateList<ProgressReports> = SnapshotStateList(),
    val listTripExpenses: SnapshotStateList<TripExpensesReports> = SnapshotStateList(),
    val reportName: String = ""
)

data class DataReportInfo (
    val date: String = "",
    val waybill: String = "",
    val mainCity: String = ""
)

data class DataPersonalInfo (
    val lastName: String = "",
    val firstName: String = "",
    val patronymic: String = ""
)

data class DataVehicleInfo (
    val makeVehicle: String = "",
    val rnVehicle: String = "",
    val makeTrailer: String = "",
    val rnTrailer: String = ""
)

data class DataFillingTwo(
    val route: SnapshotStateList<RouteElement> = SnapshotStateList(),
    val dateDeparture: String = "",
    val dateReturn: String = "",
    val dateCrossingDeparture: String = "",
    val dateCrossingReturn: String = "",
    val speedometerDeparture: String = "",
    val speedometerReturn: String = "",
    val fuelled: String = ""
)

data class ProgressReports(
    val progressDetails: ProgressDetails = ProgressDetails()
)

data class ProgressDetails(
    val country: String = "",
    val townshipOne: String = "",
    val townshipTwo: String = "",
    val distance: String = "",
    val cargoWeight: String = "",
    val date: String = ""
)

data class TripExpensesReports(
    val tripExpensesDetails: TripExpensesDetails = TripExpensesDetails()
)

data class TripExpensesDetails(
    val date: String = "",
    val documentNumber: String = "",
    val expenseItem: String = "",
    val sum: String = "",
    val currency: String = ""
)

data class ListVehicleAndTrailer(
    var listVehicle: MutableList<ObjectVehicle> = mutableListOf(),
    var listTrailer: MutableList<ObjectVehicle> = mutableListOf()
)

data class RouteElement(
    val id: Int,
    val text: String
)