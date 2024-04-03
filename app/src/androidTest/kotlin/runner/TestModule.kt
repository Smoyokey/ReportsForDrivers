package runner

import android.content.Context
import androidx.room.Room
import com.example.reportsfordrivers.data.AppDatabase
import com.example.reportsfordrivers.data.dao.CountryDao
import com.example.reportsfordrivers.data.dao.CurrencyDao
import com.example.reportsfordrivers.data.dao.PersonalInfoDao
import com.example.reportsfordrivers.data.dao.ProgressReportDao
import com.example.reportsfordrivers.data.dao.ReportNameDao
import com.example.reportsfordrivers.data.dao.RouteDao
import com.example.reportsfordrivers.data.dao.TownshipDao
import com.example.reportsfordrivers.data.dao.TrailerDao
import com.example.reportsfordrivers.data.dao.TripExpensesDao
import com.example.reportsfordrivers.data.dao.VehicleAndTrailerSaveDataDao
import com.example.reportsfordrivers.data.dao.VehicleDao
import com.example.reportsfordrivers.di.DatabaseModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestModule {
    @Provides
    @Singleton
    @Named("test_db")
    fun providePersonalInfoDao(database: AppDatabase) : PersonalInfoDao {
        return database.personalInfoDao()
    }

    @Provides
    @Singleton
    @Named("test_db")
    fun provideProgressReportDao(database: AppDatabase) : ProgressReportDao {
        return database.progressReportDao()
    }

    @Provides
    @Singleton
    @Named("test_db")
    fun provideReportNameDao(database: AppDatabase) : ReportNameDao {
        return database.reportNameDao()
    }

    @Provides
    @Singleton
    @Named("test_db")
    fun provideReportDao(database: AppDatabase) : RouteDao {
        return database.routeDao()
    }

    @Provides
    @Singleton
    @Named("test_db")
    fun provideTrailerDao(database: AppDatabase) : TrailerDao {
        return database.trailerDao()
    }

    @Provides
    @Singleton
    @Named("test_db")
    fun provideVehicleDao(database: AppDatabase) : VehicleDao {
        return database.vehicleDao()
    }

    @Provides
    @Singleton
    @Named("test_db")
    fun provideVehicleAndTrailerDao(database: AppDatabase) : VehicleAndTrailerSaveDataDao {
        return database.vehicleAndTrailerDao()
    }

    @Provides
    @Singleton
    @Named("test_db")
    fun providerTripExpensesDao(database: AppDatabase) : TripExpensesDao {
        return database.tripExpensesDao()
    }

    @Provides
    @Named("test_db")
    fun provideCountryDao(database: AppDatabase) : CountryDao {
        return database.countryDao()
    }

    @Provides
    @Named("test_db")
    fun provideTownshipDao(database: AppDatabase) : TownshipDao {
        return database.townshipDao()
    }

    @Provides
    @Named("test_db")
    fun provideCurrencyDao(database: AppDatabase) : CurrencyDao {
        return database.currencyDao()
    }

    @Provides
    @Named("test_db")
    fun provideDatabase (@ApplicationContext context: Context): AppDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java,
        )
            .allowMainThreadQueries()
//            .createFromAsset("databases/reports.db")
            .build()
    }
//        Room.inMemoryDatabaseBuilder(
//            context,
//            AppDatabase::class.java
//        ).allowMainThreadQueries()
////            .createFromAsset("databases/reports.db")
//            .build()

}