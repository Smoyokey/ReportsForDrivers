package com.example.reportsfordrivers.di

import android.content.Context
import androidx.room.Room
import com.example.reportsfordrivers.data.AppDatabase
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
    fun provideCurrencyDao(database: AppDatabase): CurrencyDao {
        return database.currencyDao()
    }

    @Provides
    fun provideTownshipDao(database: AppDatabase): TownshipDao {
        return database.townshipDao()
    }

    @Provides
    fun provideCountryDao(database: AppDatabase): CountryDao {
        return database.countryDao()
    }

    @Provides
    fun provideMainIndoDao(database: AppDatabase): MainInfoDao {
        return database.mainInfoDao()
    }

    @Provides
    fun providePersonalInfoDao(database: AppDatabase): PersonalInfoDao {
        return database.personalInfoDao()
    }

    @Provides
    fun provideProgressReportDao(database: AppDatabase): ProgressReportDao {
        return database.progressReportDao()
    }

    @Provides
    fun provideReportNameDao(database: AppDatabase): ReportNameDao {
        return database.reportNameDao()
    }

    @Provides
    fun provideRouteDao(database: AppDatabase): RouteDao {
        return database.routeDao()
    }

    @Provides
    fun provideTrailerDao(database: AppDatabase): TrailerDao {
        return database.trailerDao()
    }

    @Provides
    fun provideVehicleDao(database: AppDatabase): VehicleDao {
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
    fun provideCreateExpensesTrip(database: AppDatabase): CreateExpensesTripDao {
        return database.createExpensesTripDao()
    }

    @Provides
    fun provideCreatePersonalInfo(database: AppDatabase): CreatePersonalInfoDao {
        return database.createPersonalInfoDao()
    }

    @Provides
    fun provideCreateProgressReports(database: AppDatabase): CreateProgressReportsDao {
        return database.createProgressReportsDao()
    }

    @Provides
    fun provideCreateReportInfo(database: AppDatabase): CreateReportInfoDao {
        return database.createReportInfoDao()
    }

    @Provides
    fun provideCreateRoute(database: AppDatabase): CreateRouteDao {
        return database.createRouteDao()
    }

    @Provides
    fun provideCreateVehicleTrailer(database: AppDatabase): CreateVehicleTrailerDao {
        return database.createVehicleTrailerDao()
    }

    @Provides
    fun provideEditExpensesTrip(database: AppDatabase): EditExpensesTripDao {
        return database.editExpensesTripDao()
    }

    @Provides
    fun provideEditPersonalInfo(database: AppDatabase): EditPersonalInfoDao {
        return database.editPersonalInfoDao()
    }

    @Provides
    fun provideEditProgressReports(database: AppDatabase): EditProgressReportsDao {
        return database.editProgressReportsDao()
    }

    @Provides
    fun provideEditReportInfo(database: AppDatabase): EditReportInfoDao {
        return database.editReportInfoDao()
    }

    @Provides
    fun provideEditRoute(database: AppDatabase): EditRouteDao {
        return database.editRouteDao()
    }

    @Provides
    fun provideEditVehicleTrailer(database: AppDatabase): EditVehicleTrailerDao {
        return database.editVehicleTrailerDao()
    }

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "report.db"
        ).createFromAsset("databases/reports.db")
            .build()
    }
}