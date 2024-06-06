package com.example.reportsfordrivers.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.reportsfordrivers.data.dao.CountryDao
import com.example.reportsfordrivers.data.dao.CurrencyDao
import com.example.reportsfordrivers.data.dao.MainInfoDao
import com.example.reportsfordrivers.data.dao.PersonalInfoDao
import com.example.reportsfordrivers.data.dao.ProgressReportDao
import com.example.reportsfordrivers.data.dao.ReportNameDao
import com.example.reportsfordrivers.data.dao.RouteDao
import com.example.reportsfordrivers.data.dao.TownshipDao
import com.example.reportsfordrivers.data.dao.TrailerDao
import com.example.reportsfordrivers.data.dao.TripExpensesDao
import com.example.reportsfordrivers.data.dao.VehicleAndTrailerSaveDataDao
import com.example.reportsfordrivers.data.dao.VehicleDao
import com.example.reportsfordrivers.data.dao.createreport.CreateExpensesTripDao
import com.example.reportsfordrivers.data.dao.createreport.CreatePersonalInfoDao
import com.example.reportsfordrivers.data.dao.createreport.CreateProgressReportsDao
import com.example.reportsfordrivers.data.dao.createreport.CreateReportInfoDao
import com.example.reportsfordrivers.data.dao.createreport.CreateRouteDao
import com.example.reportsfordrivers.data.dao.createreport.CreateVehicleTrailerDao
import com.example.reportsfordrivers.data.dao.editreport.EditExpensesTripDao
import com.example.reportsfordrivers.data.dao.editreport.EditPersonalInfoDao
import com.example.reportsfordrivers.data.dao.editreport.EditProgressReportsDao
import com.example.reportsfordrivers.data.dao.editreport.EditReportInfoDao
import com.example.reportsfordrivers.data.dao.editreport.EditRouteDao
import com.example.reportsfordrivers.data.dao.editreport.EditVehicleTrailerDao
import com.example.reportsfordrivers.data.structure.Country
import com.example.reportsfordrivers.data.structure.Currency
import com.example.reportsfordrivers.data.structure.MainInfo
import com.example.reportsfordrivers.data.structure.PersonalInfo
import com.example.reportsfordrivers.data.structure.ProgressReport
import com.example.reportsfordrivers.data.structure.ReportName
import com.example.reportsfordrivers.data.structure.Route
import com.example.reportsfordrivers.data.structure.Township
import com.example.reportsfordrivers.data.structure.Trailer
import com.example.reportsfordrivers.data.structure.TripExpenses
import com.example.reportsfordrivers.data.structure.Vehicle
import com.example.reportsfordrivers.data.structure.VehicleAndTrailer
import com.example.reportsfordrivers.data.structure.createreport.CreateExpensesTrip
import com.example.reportsfordrivers.data.structure.createreport.CreatePersonalInfo
import com.example.reportsfordrivers.data.structure.createreport.CreateProgressReports
import com.example.reportsfordrivers.data.structure.createreport.CreateReportInfo
import com.example.reportsfordrivers.data.structure.createreport.CreateRoute
import com.example.reportsfordrivers.data.structure.createreport.CreateVehicleTrailer
import com.example.reportsfordrivers.data.structure.editreport.EditExpensesTrip
import com.example.reportsfordrivers.data.structure.editreport.EditPersonalInfo
import com.example.reportsfordrivers.data.structure.editreport.EditProgressReports
import com.example.reportsfordrivers.data.structure.editreport.EditReportInfo
import com.example.reportsfordrivers.data.structure.editreport.EditRoute
import com.example.reportsfordrivers.data.structure.editreport.EditVehicleTrailer

@Database(
    entities = arrayOf(
        MainInfo::class,
        PersonalInfo::class,
        ProgressReport::class, ReportName::class, Route::class,
        Trailer::class, Vehicle::class, VehicleAndTrailer::class,
        TripExpenses::class, Currency::class, Country::class, Township::class,
        CreateExpensesTrip::class, CreatePersonalInfo::class, CreateProgressReports::class,
        CreateReportInfo::class, CreateRoute::class, CreateVehicleTrailer::class,
        EditExpensesTrip::class, EditPersonalInfo::class, EditProgressReports::class,
        EditReportInfo::class, EditRoute::class, EditVehicleTrailer::class
    ), version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun townshipDao(): TownshipDao
    abstract fun countryDao(): CountryDao
    abstract fun mainInfoDao(): MainInfoDao
    abstract fun personalInfoDao(): PersonalInfoDao
    abstract fun progressReportDao(): ProgressReportDao
    abstract fun reportNameDao(): ReportNameDao
    abstract fun routeDao(): RouteDao
    abstract fun trailerDao(): TrailerDao
    abstract fun vehicleDao(): VehicleDao
    abstract fun vehicleAndTrailerDao(): VehicleAndTrailerSaveDataDao
    abstract fun tripExpensesDao(): TripExpensesDao

    //CreateReport
    abstract fun createExpensesTripDao(): CreateExpensesTripDao
    abstract fun createPersonalInfoDao(): CreatePersonalInfoDao
    abstract fun createProgressReportsDao(): CreateProgressReportsDao
    abstract fun createReportInfoDao(): CreateReportInfoDao
    abstract fun createRouteDao(): CreateRouteDao
    abstract fun createVehicleTrailerDao(): CreateVehicleTrailerDao

    //EditReport
    abstract fun editExpensesTripDao(): EditExpensesTripDao
    abstract fun editPersonalInfoDao(): EditPersonalInfoDao
    abstract fun editProgressReportsDao(): EditProgressReportsDao
    abstract fun editReportInfoDao(): EditReportInfoDao
    abstract fun editRouteDao(): EditRouteDao
    abstract fun editVehicleTrailerDao(): EditVehicleTrailerDao
}