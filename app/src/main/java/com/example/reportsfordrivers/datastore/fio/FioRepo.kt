package com.example.reportsfordrivers.datastore.fio

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FioRepo @Inject constructor (
    private val dataStore: DataStore<Preferences>
): FioRepository {

    private companion object {
        val FIRST_NAME = stringPreferencesKey(
            name = "first_name"
        )
        val LAST_NAME = stringPreferencesKey(
            name = "last_name"
        )
        val PATRONYMIC = stringPreferencesKey(
            name = "patronymic"
        )
    }

    override suspend fun setFirstName(firstName: String) {
        dataStore.edit{ preferences ->
            preferences[FIRST_NAME] = firstName
        }
    }

    override suspend fun setLastName(lastName: String) {
        dataStore.edit{ preferences ->
            preferences[LAST_NAME] = lastName
        }
    }

    override suspend fun setPatronymic(patronymic: String) {
        dataStore.edit { preferences ->
            preferences[PATRONYMIC] = patronymic
        }
    }

    override suspend fun getFirstName() : Result<String> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch { exception ->

                }
                .map { preferences ->
                    preferences[FIRST_NAME]
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
                    preferences[LAST_NAME]
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
                    preferences[PATRONYMIC]
                }
            val value = flow.firstOrNull() ?: ""
            value
        }
    }
}