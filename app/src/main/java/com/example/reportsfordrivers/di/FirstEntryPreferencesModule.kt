package com.example.reportsfordrivers.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.reportsfordrivers.datastore.FirstEntryRepository
import com.example.reportsfordrivers.datastore.MyFirstEntryRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.firstEnterDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "com.example.reportsfordrivers.first_entry"
)

@Module
@InstallIn(SingletonComponent::class)
abstract class FirstEntryPreferencesModule {

    @Binds
    @Singleton
    abstract fun bindFirstEntryPreferencesRepository(
        firstEntryPreferencesRepository: MyFirstEntryRepo
    ): FirstEntryRepository

    companion object {

        @Provides
        @Singleton
        fun provideFirstEntryDataStorePreferences(
            @ApplicationContext applicationContext: Context
        ): DataStore<Preferences> {
            return applicationContext.firstEnterDataStore
        }
    }
}