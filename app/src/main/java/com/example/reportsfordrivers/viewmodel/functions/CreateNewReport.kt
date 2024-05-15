package com.example.reportsfordrivers.viewmodel.functions

import com.example.reportsfordrivers.data.dao.createReport.CreateExpensesTripDao
import com.example.reportsfordrivers.data.dao.createReport.CreatePersonalInfoDao
import com.example.reportsfordrivers.data.dao.createReport.CreateProgressReportsDao
import com.example.reportsfordrivers.data.dao.createReport.CreateReportInfoDao
import com.example.reportsfordrivers.data.dao.createReport.CreateRouteDao
import com.example.reportsfordrivers.data.dao.createReport.CreateVehicleTrailerDao
import com.example.reportsfordrivers.data.structure.createReport.CreateExpensesTrip
import com.example.reportsfordrivers.data.structure.createReport.CreatePersonalInfo
import com.example.reportsfordrivers.data.structure.createReport.CreateProgressReports
import com.example.reportsfordrivers.data.structure.createReport.CreateReportInfo
import com.example.reportsfordrivers.data.structure.createReport.CreateRoute
import com.example.reportsfordrivers.data.structure.createReport.CreateVehicleTrailer
import kotlinx.coroutines.runBlocking

object CreateNewReport {

    fun createNewReport(
        createReportInfoDb: CreateReportInfoDao,
        createPersonalInfoDb: CreatePersonalInfoDao,
        createVehicleTrailerDb: CreateVehicleTrailerDao,
        createRouteDb: CreateRouteDao,
        createProgressReportsDb: CreateProgressReportsDao,
        createExpensesTripDb: CreateExpensesTripDao
    ) {
        createNewReportCreateReportInfo(createReportInfoDb)
        createNewReportCreatePersonalInfo(createPersonalInfoDb)
        createNewReportCreateVehicleTrailer(createVehicleTrailerDb)
        createNewReportCreateRoute(createRouteDb)
        createNewReportCreateProgressReports(createProgressReportsDb)
        createNewReportCreateExpensesTrip(createExpensesTripDb)
    }

    private fun createNewReportCreateReportInfo(createReportInfoDb: CreateReportInfoDao) =
        runBlocking {
            createReportInfoDb.insert(
                CreateReportInfo(
                    date = "",
                    waybill = "",
                    mainCity = "",
                    reportName = "",
                    isStart = 1
                )
            )
        }

    private fun createNewReportCreatePersonalInfo(createPersonalInfoDb: CreatePersonalInfoDao) =
        runBlocking {
            createPersonalInfoDb.insert(
                CreatePersonalInfo(
                    lastName = "",
                    firstName = "",
                    patronymic = ""
                )
            )
        }

    private fun createNewReportCreateVehicleTrailer(createVehicleTrailerDb: CreateVehicleTrailerDao) =
        runBlocking {
            createVehicleTrailerDb.insert(
                CreateVehicleTrailer(
                    makeVehicle = "",
                    rnVehicle = "",
                    makeTrailer = "",
                    rnTrailer = ""
                )
            )
        }

    private fun createNewReportCreateRoute(createRouteDb: CreateRouteDao) = runBlocking {
        createRouteDb.insert(
            CreateRoute(
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

    private fun createNewReportCreateProgressReports(createProgressReportsDb: CreateProgressReportsDao) =
        runBlocking {
            createProgressReportsDb.insert(
                CreateProgressReports(
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

    private fun createNewReportCreateExpensesTrip(createExpensesTripDb: CreateExpensesTripDao) =
        runBlocking {
            createExpensesTripDb.insert(
                CreateExpensesTrip(
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
        createReportInfoDb: CreateReportInfoDao,
        createPersonalInfoDb: CreatePersonalInfoDao,
        createVehicleTrailerDb: CreateVehicleTrailerDao,
        createRouteDb: CreateRouteDao,
        createProgressReportsDb: CreateProgressReportsDao,
        createExpensesTripDb: CreateExpensesTripDao
    ) {
        deleteAllElementsCreateReportInfo(createReportInfoDb)
        deleteAllElementsCreatePersonalInfo(createPersonalInfoDb)
        deleteAllElementsCreateVehicleTrailer(createVehicleTrailerDb)
        deleteAllElementsCreateRoute(createRouteDb)
        deleteAllElementsCreateProgressReports(createProgressReportsDb)
        deleteAllElementsCreateExpensesTrip(createExpensesTripDb)
    }

    private fun deleteAllElementsCreateReportInfo(createReportInfoDb: CreateReportInfoDao) =
        runBlocking { createReportInfoDb.deleteAllElements() }

    private fun deleteAllElementsCreatePersonalInfo(createPersonalInfoDb: CreatePersonalInfoDao) =
        runBlocking { createPersonalInfoDb.deleteAllElements() }

    private fun deleteAllElementsCreateVehicleTrailer(createVehicleTrailerDb: CreateVehicleTrailerDao) =
        runBlocking { createVehicleTrailerDb.deleteAllElements() }

    private fun deleteAllElementsCreateRoute(createRouteDb: CreateRouteDao) =
        runBlocking { createRouteDb.deleteAllElements() }

    private fun deleteAllElementsCreateProgressReports(createProgressReportsDb: CreateProgressReportsDao) =
        runBlocking { createProgressReportsDb.deleteAllElements() }

    private fun deleteAllElementsCreateExpensesTrip(createExpensesTripDb: CreateExpensesTripDao) =
        runBlocking { createExpensesTripDb.deleteAllElements() }
}