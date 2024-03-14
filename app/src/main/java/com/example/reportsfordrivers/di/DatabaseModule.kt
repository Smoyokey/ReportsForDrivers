package com.example.reportsfordrivers.di

import android.content.Context
import androidx.room.Room
import com.example.reportsfordrivers.data.AppDatabase
import com.example.reportsfordrivers.data.dao.MainInfoDao
import com.example.reportsfordrivers.data.dao.PersonalInfoDao
import com.example.reportsfordrivers.data.dao.ProgressReportDao
import com.example.reportsfordrivers.data.dao.ReportNameDao
import com.example.reportsfordrivers.data.dao.RouteDao
import com.example.reportsfordrivers.data.dao.TrailerDao
import com.example.reportsfordrivers.data.dao.TripExpensesDao
import com.example.reportsfordrivers.data.dao.VehicleAndTrailerSaveDataDao
import com.example.reportsfordrivers.data.dao.VehicleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideMainIndoDao(database: AppDatabase) : MainInfoDao {
        return database.mainInfoDao()
    }

    @Provides
    fun providePersonalInfoDao(database: AppDatabase) : PersonalInfoDao {
        return database.personalInfoDao()
    }

    @Provides
    fun provideProgressReportDao(database: AppDatabase) : ProgressReportDao {
        return database.progressReportDao()
    }

    @Provides
    fun provideReportNameDao(database: AppDatabase) : ReportNameDao {
        return database.reportNameDao()
    }

    @Provides
    fun provideRouteDao(database: AppDatabase) : RouteDao {
        return database.routeDao()
    }

    @Provides
    fun provideTrailerDao(database: AppDatabase) : TrailerDao {
        return database.trailerDao()
    }

    @Provides
    fun provideVehicleDao(database: AppDatabase) : VehicleDao {
        return database.vehicleDao()
    }

    @Provides
    fun provideVehicleAndTrailer(database: AppDatabase): VehicleAndTrailerSaveDataDao {
        return database.vehicleAndTrailerDao()
    }

    @Provides
    fun provideTripExpenses(database: AppDatabase): TripExpensesDao {
        return database.tripExpensesDao()
    }

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext appContext: Context) : AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "report.db"
        ).build()
    }
}