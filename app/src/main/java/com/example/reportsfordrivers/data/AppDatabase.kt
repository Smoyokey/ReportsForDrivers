package com.example.reportsfordrivers.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.reportsfordrivers.data.dao.PersonalInfoDao
import com.example.reportsfordrivers.data.dao.ProgressReportDao
import com.example.reportsfordrivers.data.dao.ReportNameDao
import com.example.reportsfordrivers.data.dao.RouteDao
import com.example.reportsfordrivers.data.dao.TrailerDao
import com.example.reportsfordrivers.data.dao.VehicleDao
import com.example.reportsfordrivers.data.structure.PersonalInfo
import com.example.reportsfordrivers.data.structure.ProgressReport
import com.example.reportsfordrivers.data.structure.ReportName
import com.example.reportsfordrivers.data.structure.Route
import com.example.reportsfordrivers.data.structure.Trailer
import com.example.reportsfordrivers.data.structure.Vehicle

@Database(
    entities = arrayOf(
        PersonalInfo::class,
        ProgressReport::class, ReportName::class, Route::class,
        Trailer::class, Vehicle::class
    ), version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personalInfoDao(): PersonalInfoDao
    abstract fun progressReportDao(): ProgressReportDao
    abstract fun reportNameDao(): ReportNameDao
    abstract fun routeDao(): RouteDao
    abstract fun trailerDao(): TrailerDao
    abstract fun vehicleDao(): VehicleDao
}