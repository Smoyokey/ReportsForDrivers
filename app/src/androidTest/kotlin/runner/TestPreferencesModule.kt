package runner

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.reportsfordrivers.datastore.DataStoreName
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepo
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.di.FioFirstEntryPreferencesModule
import com.example.reportsfordrivers.di.fioFirstEntryDataStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//abstract class TestPreferencesModule {
//
//    @Binds
//    @Singleton
//    abstract fun bindFioFirstEntryPreferencesRepository(
//        fioFirstEntryPreferencesRepository: FioFirstEntryRepository
//    ): FioFirstEntryRepository
//}
