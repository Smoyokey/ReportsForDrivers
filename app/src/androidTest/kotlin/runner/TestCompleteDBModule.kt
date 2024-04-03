package runner

import android.content.Context
import androidx.room.Room
import com.example.reportsfordrivers.data.AppDatabase
import com.example.reportsfordrivers.data.dao.CountryDao
import com.example.reportsfordrivers.data.dao.CurrencyDao
import com.example.reportsfordrivers.data.dao.TownshipDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestCompleteDBModule {

    @Provides
    @Singleton
    @Named("complete_db")
    fun provideCountryDao(database: AppDatabase): CountryDao {
        return database.countryDao()
    }

    @Provides
    @Singleton
    @Named("complete_db")
    fun provideTownshipDao(database: AppDatabase) : TownshipDao {
        return database.townshipDao()
    }

    @Provides
    @Singleton
    @Named("complete_db")
    fun provideCurrencyDao(database: AppDatabase) : CurrencyDao {
        return database.currencyDao()
    }

    @Provides
    @Named("complete_db")
    fun provideDatabase (@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "reports.db"
        ).allowMainThreadQueries()
            .createFromAsset("databases/reports.db")
            .build()
    }
}