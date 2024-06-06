package com.example.reportsfordrivers.viewmodel.functions

import com.example.reportsfordrivers.data.dao.editreport.EditExpensesTripDao
import com.example.reportsfordrivers.data.dao.editreport.EditPersonalInfoDao
import com.example.reportsfordrivers.data.dao.editreport.EditProgressReportsDao
import com.example.reportsfordrivers.data.dao.editreport.EditReportInfoDao
import com.example.reportsfordrivers.data.dao.editreport.EditRouteDao
import com.example.reportsfordrivers.data.dao.editreport.EditVehicleTrailerDao
import com.example.reportsfordrivers.data.structure.editreport.EditExpensesTrip
import com.example.reportsfordrivers.data.structure.editreport.EditPersonalInfo
import com.example.reportsfordrivers.data.structure.editreport.EditProgressReports
import com.example.reportsfordrivers.data.structure.editreport.EditReportInfo
import com.example.reportsfordrivers.data.structure.editreport.EditRoute
import com.example.reportsfordrivers.data.structure.editreport.EditVehicleTrailer
import kotlinx.coroutines.runBlocking

object EditNewReport {

    fun editNewReport(
        editReportInfoDb: EditReportInfoDao,
        editPersonalInfoDb: EditPersonalInfoDao,
        editVehicleTrailerDb: EditVehicleTrailerDao,
        editRouteDb: EditRouteDao,
        editProgressReportsDb: EditProgressReportsDao,
        editExpensesTripDb: EditExpensesTripDao
    ) {
        editNewReportEditReportInfo(editReportInfoDb)
        editNewReportEditPersonalInfo(editPersonalInfoDb)
        editNewReportEditVehicleTrailer(editVehicleTrailerDb)
        editNewReportEditRoute(editRouteDb)
        editNewReportEditProgressReports(editProgressReportsDb)
        editNewReportEditExpensesTrip(editExpensesTripDb)
    }

    private fun editNewReportEditReportInfo(editReportInfoDb: EditReportInfoDao) =
        runBlocking {
            editReportInfoDb.insert(
                EditReportInfo(
                    date = "",
                    waybill = "",
                    mainCity = "",
                    reportName = "",
                    isStart = 1
                )
            )
        }

    private fun editNewReportEditPersonalInfo(editPersonalInfoDb: EditPersonalInfoDao) =
        runBlocking {
            editPersonalInfoDb.insert(
                EditPersonalInfo(
                    lastName = "",
                    firstName = "",
                    patronymic = ""
                )
            )
        }

    private fun editNewReportEditVehicleTrailer(editVehicleTrailerDb: EditVehicleTrailerDao) =
        runBlocking {
            editVehicleTrailerDb.insert(
                EditVehicleTrailer(
                    makeVehicle = "",
                    rnVehicle = "",
                    makeTrailer = "",
                    rnTrailer = ""
                )
            )
        }

    private fun editNewReportEditRoute(editRouteDb: EditRouteDao) = runBlocking {
        editRouteDb.insert(
            EditRoute(
                route = "",
                dateDeparture = "",
                dateReturn = "",
                dateBorderCrossingDeparture = "",
                dateBorderCrossingReturn = "",
                speedometerReadingDeparture = "",
                speedometerReadingReturn = "",
                fuelled = ""
            )
        )
    }

    private fun editNewReportEditProgressReports(editProgressReportsDb: EditProgressReportsDao) =
        runBlocking {
            editProgressReportsDb.insert(
                EditProgressReports(
                    date = "",
                    country = "",
                    townshipOne = "",
                    townshipTwo = "",
                    distance = "",
                    weight = "",
                    isAdd = 0
                )
            )
        }

    private fun editNewReportEditExpensesTrip(editExpensesTripDb: EditExpensesTripDao) =
        runBlocking {
            editExpensesTripDb.insert(
                EditExpensesTrip(
                    date = "",
                    documentNumber = "",
                    expenseItem = "",
                    sum = "",
                    currency = "",
                    isAdd = 0
                )
            )
        }

    fun deleteAllElements(
        editReportInfoDb: EditReportInfoDao,
        editPersonalInfoDb: EditPersonalInfoDao,
        editVehicleTrailerDb: EditVehicleTrailerDao,
        editRouteDb: EditRouteDao,
        editProgressReportsDb: EditProgressReportsDao,
        editExpensesTripDb: EditExpensesTripDao
    ) {
        deleteAllElementsEditReportInfo(editReportInfoDb)
        deleteAllElementsEditPersonalInfo(editPersonalInfoDb)
        deleteAllElementsEditVehicleTrailer(editVehicleTrailerDb)
        deleteAllElementsEditRoute(editRouteDb)
        deleteAllElementsEditProgressReports(editProgressReportsDb)
        deleteAllElementsEditExpensesTrip(editExpensesTripDb)
    }

    private fun deleteAllElementsEditReportInfo(editReportInfoDb: EditReportInfoDao) =
        runBlocking { editReportInfoDb.deleteAllElements() }

    private fun deleteAllElementsEditPersonalInfo(editPersonalInfoDb: EditPersonalInfoDao) =
        runBlocking { editPersonalInfoDb.deleteAllElements() }

    private fun deleteAllElementsEditVehicleTrailer(editVehicleTrailerDb: EditVehicleTrailerDao) =
        runBlocking { editVehicleTrailerDb.deleteAllElements() }

    private fun deleteAllElementsEditRoute(editRouteDb: EditRouteDao) =
        runBlocking { editRouteDb.deleteAllElements() }

    private fun deleteAllElementsEditProgressReports(editProgressReportsDb: EditProgressReportsDao) =
        runBlocking { editProgressReportsDb.deleteAllElements() }

    private fun deleteAllElementsEditExpensesTrip(editExpensesTripDb: EditExpensesTripDao) =
        runBlocking { editExpensesTripDb.deleteAllElements() }
}