package com.example.reportsfordrivers.datastore.firstentry

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MyFirstEntryRepo @Inject constructor(
    private val dataStore: DataStore<Preferences>
): FirstEntryRepository {

    private companion object {
        val IS_FIRST_ENTRY = booleanPreferencesKey(
            name = "is_first_entry"
        )
    }

    override suspend fun setFirstEntry(isFirstEntry: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_FIRST_ENTRY] = isFirstEntry
        }
    }

    override suspend fun getFirstEntry() : Result<Boolean> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch {exception ->

                }
                .map { preferences ->
                     preferences[IS_FIRST_ENTRY]
                }
            val value = flow.firstOrNull() ?: true
            value
        }
    }
}