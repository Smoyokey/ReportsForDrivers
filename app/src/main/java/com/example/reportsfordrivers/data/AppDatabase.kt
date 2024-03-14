package com.example.reportsfordrivers.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.reportsfordrivers.data.dao.MainInfoDao
import com.example.reportsfordrivers.data.dao.PersonalInfoDao
import com.example.reportsfordrivers.data.dao.ProgressReportDao
import com.example.reportsfordrivers.data.dao.ReportNameDao
import com.example.reportsfordrivers.data.dao.RouteDao
import com.example.reportsfordrivers.data.dao.TrailerDao
import com.example.reportsfordrivers.data.dao.TripExpensesDao
import com.example.reportsfordrivers.data.dao.VehicleAndTrailerSaveDataDao
import com.example.reportsfordrivers.data.dao.VehicleDao
import com.example.reportsfordrivers.data.structure.MainInfo
import com.example.reportsfordrivers.data.structure.PersonalInfo
import com.example.reportsfordrivers.data.structure.ProgressReport
import com.example.reportsfordrivers.data.structure.ReportName
import com.example.reportsfordrivers.data.structure.Route
import com.example.reportsfordrivers.data.structure.Trailer
import com.example.reportsfordrivers.data.structure.TripExpenses
import com.example.reportsfordrivers.data.structure.Vehicle
import com.example.reportsfordrivers.data.structure.VehicleAndTrailer

@Database(
    entities = arrayOf(
        MainInfo::class,
        PersonalInfo::class,
        ProgressReport::class, ReportName::class, Route::class,
        Trailer::class, Vehicle::class, VehicleAndTrailer::class,
        TripExpenses::class
    ), version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mainInfoDao(): MainInfoDao
    abstract fun personalInfoDao(): PersonalInfoDao
    abstract fun progressReportDao(): ProgressReportDao
    abstract fun reportNameDao(): ReportNameDao
    abstract fun routeDao(): RouteDao
    abstract fun trailerDao(): TrailerDao
    abstract fun vehicleDao(): VehicleDao
    abstract fun vehicleAndTrailerDao(): VehicleAndTrailerSaveDataDao
    abstract fun tripExpensesDao(): TripExpensesDao
}