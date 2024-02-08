package com.example.reportsfordrivers.datastore.fiofirstentry

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.reportsfordrivers.datastore.DataStoreName
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FioFirstEntryRepo @Inject constructor(
    private val dataStore: DataStore<Preferences>
): FioFirstEntryRepository {

    override suspend fun setFirstEntry(isFirstEntry: Boolean) {
        dataStore.edit { preferences ->
            preferences[DataStoreName.IS_FIRST_ENTRY] = isFirstEntry
        }
    }

    override suspend fun setFirstName(firstName: String) {
        dataStore.edit { preferences ->
            preferences[DataStoreName.FIRST_NAME] = firstName
        }
    }

    override suspend fun setLastName(lastName: String) {
        dataStore.edit { preferences ->
            preferences[DataStoreName.LAST_NAME] = lastName
        }
    }

    override suspend fun setPatronymic(patronymic: String) {
        dataStore.edit { preferences ->
            preferences[DataStoreName.PATRONYMIC] = patronymic
        }
    }

    override suspend fun getFirstEntry(): Result<Boolean> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch { exception ->

                }
                .map { preferences ->
                    preferences[DataStoreName.IS_FIRST_ENTRY]
                }
            val value = flow.firstOrNull() ?: true
            value
        }
    }

    override suspend fun getFirstName(): Result<String> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch { exception ->

                }
                .map { preferences ->
                    preferences[DataStoreName.FIRST_NAME]
                }
            val value = flow.firstOrNull() ?: ""
            value
        }
    }

    override suspend fun getLastName(): Result<String> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch { exception ->

                }
                .map { preferences ->
                    preferences[DataStoreName.LAST_NAME]
                }
            val value = flow.firstOrNull() ?: ""
            value
        }
    }

    override suspend fun getPatronymic(): Result<String> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch { exception ->

                }
                .map { preferences ->
                    preferences[DataStoreName.PATRONYMIC]
                }
            val value = flow.firstOrNull() ?: ""
            value
        }
    }
}