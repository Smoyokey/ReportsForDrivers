package com.example.reportsfordrivers.viewmodel.createreports.uistate

data class CreateReports(
    val dataFillingOne: DataFillingOne = DataFillingOne(),
    val dataFillingTwo: DataFillingTwo = DataFillingTwo(),
    val listProgress: MutableList<ProgressReports> = mutableListOf(),
    val listTripExpenses: MutableList<TripExpensesReports> = mutableListOf(),
    val reportName: String = ""
)

data class DataFillingOne(
    val date: String = "",
    val lastName: String = "",
    val firstName: String = "",
    val patronymic: String = "",
    val makeVehicle: String = "",
    val rnVehicle: String = "",
    val makeTrailer: String = "",
    val rnTrailer: String = ""
)

data class DataFillingTwo(
    val route: String = "",
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
    val township: String = "",
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