package runner

import android.content.Context
import androidx.room.Room
import com.example.reportsfordrivers.data.AppDatabase
import com.example.reportsfordrivers.data.dao.PersonalInfoDao
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
    @Named("test_db")
    fun providePersonalInfo (@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
}