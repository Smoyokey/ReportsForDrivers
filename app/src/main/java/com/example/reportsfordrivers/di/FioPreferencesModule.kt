package com.example.reportsfordrivers.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.reportsfordrivers.datastore.fio.FioRepo
import com.example.reportsfordrivers.datastore.fio.FioRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


val Context.fioDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "com.example.reportsfordrivers.fio"
)

@Module
@InstallIn(SingletonComponent::class)
abstract class FioPreferencesModule {

    @Binds
    @Singleton
    abstract fun bindFioPreferencesRepository(
        fioPreferencesRepository: FioRepo
    ): FioRepository

    companion object {

        @Provides
        @Singleton
        fun provideFioDataStorePreferences(
            @ApplicationContext applicationContext: Context
        ): DataStore<Preferences> {
            return applicationContext.fioDataStore
        }
    }
}