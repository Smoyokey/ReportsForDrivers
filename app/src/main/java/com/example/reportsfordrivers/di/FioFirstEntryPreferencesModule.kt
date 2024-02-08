package com.example.reportsfordrivers.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepo
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

val Context.fioFirstEntryDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "com.example.reportfordrivers.fio_first_entry"
)

@Module
@InstallIn(SingletonComponent::class)
abstract class FioFirstEntryPreferencesModule {

    @Binds
    @Singleton
    abstract fun bindFioFirstEntryPreferencesRepository(
        fioFirstEntryPreferencesRepository: FioFirstEntryRepo
    ): FioFirstEntryRepository

    companion object {

        @Provides
        @Singleton
        fun provideFioFirstEntryDataStorePreferences(
            @ApplicationContext applicationContext: Context
        ): DataStore<Preferences> {
            return applicationContext.fioFirstEntryDataStore
        }
    }
}