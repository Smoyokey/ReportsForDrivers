package com.example.reportsfordrivers.datastore.fiofirstentry

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
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
                .catch {}
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
                .catch {}
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
                .catch {}
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
                .catch {}
                .map { preferences ->
                    preferences[DataStoreName.PATRONYMIC]
                }
            val value = flow.firstOrNull() ?: ""
            value
        }
    }

    override suspend fun setLanguageReport(languageReport: Int) {
        dataStore.edit { preferences ->
            preferences[DataStoreName.LANGUAGE_REPORT] = languageReport
        }
    }

    override suspend fun getLanguageReport(): Result<Int> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch {}
                .map { preferences ->
                    preferences[DataStoreName.LANGUAGE_REPORT]
                }
            val value = flow.firstOrNull() ?: 0
            value
        }
    }

    override suspend fun setDefaultCurrency(defaultCurrency: String) {
        dataStore.edit { preferences ->
            preferences[DataStoreName.DEFAULT_CURRENCY] = defaultCurrency
        }
    }

    override suspend fun getDefaultCurrency(): Result<String> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch {}
                .map { preferences ->
                    preferences[DataStoreName.DEFAULT_CURRENCY]
                }
            val value = flow.firstOrNull() ?: ""
            value
        }
    }

    override suspend fun setThemeApp(themeApp: String) {
        dataStore.edit { preferences ->
            preferences[DataStoreName.THEME_APP] = themeApp
        }
    }

    override suspend fun getThemeApp(): Result<String> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch {}
                .map { preferences ->
                    preferences[DataStoreName.THEME_APP]
                }
            val value = flow.firstOrNull() ?: ""
            value
        }
    }

    override suspend fun setIsValidateCreateReportInfo(isValidateCreateReportInfo: Boolean) {
        dataStore.edit { preferences ->
            preferences[DataStoreName.IS_VALIDATE_CREATE_REPORT_INFO] = isValidateCreateReportInfo
        }
    }

    override suspend fun getIsValidateCreateReportInfo(): Result<Boolean> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch {}
                .map { preferences ->
                    preferences[DataStoreName.IS_VALIDATE_CREATE_REPORT_INFO]
                }
            val value = flow.firstOrNull() ?: false
            value
        }
    }

    override suspend fun setIsValidateCreatePersonalInfo(isValidateCreatePersonalInfo: Boolean) {
        dataStore.edit { preferences ->
            preferences[DataStoreName.IS_VALIDATE_CREATE_PERSONAL_INFO] = isValidateCreatePersonalInfo
        }
    }

    override suspend fun getIsValidateCreatePersonalInfo(): Result<Boolean> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch {}
                .map { preferences ->
                    preferences[DataStoreName.IS_VALIDATE_CREATE_PERSONAL_INFO]
                }
            val value = flow.firstOrNull() ?: false
            value
        }
    }

    override suspend fun setIsValidateCreateVehicleTrailer(isValidateCreateVehicleTrailer: Boolean) {
        dataStore.edit { preferences ->
            preferences[DataStoreName.IS_VALIDATE_CREATE_VEHICLE_TRAILER] = isValidateCreateVehicleTrailer
        }
    }

    override suspend fun getIsValidateCreateVehicleTrailer(): Result<Boolean> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch {}
                .map { preferences ->
                    preferences[DataStoreName.IS_VALIDATE_CREATE_VEHICLE_TRAILER]
                }
            val value = flow.firstOrNull() ?: false
            value
        }
    }

    override suspend fun setIsValidateCreateRoute(isValidateCreateRoute: Boolean) {
        dataStore.edit { preferences ->
            preferences[DataStoreName.IS_VALIDATE_CREATE_ROUTE] = isValidateCreateRoute
        }
    }

    override suspend fun getIsValidateCreateRoute(): Result<Boolean> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch {}
                .map { preferences ->
                    preferences[DataStoreName.IS_VALIDATE_CREATE_ROUTE]
                }
            val value = flow.firstOrNull() ?: false
            value
        }
    }

    override suspend fun setIsValidateCreateProgressReports(isValidateCreateProgressReports: Boolean) {
        dataStore.edit { preferences ->
            preferences[DataStoreName.IS_VALIDATE_CREATE_PROGRESS_REPORTS] = isValidateCreateProgressReports
        }
    }

    override suspend fun getIsValidateCreateProgressReports(): Result<Boolean> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch {}
                .map { preferences ->
                    preferences[DataStoreName.IS_VALIDATE_CREATE_PROGRESS_REPORTS]
                }
            val value = flow.firstOrNull() ?: false
            value
        }
    }

    override suspend fun setCreateSelectedPage(createSelectedPage: Int) {
        dataStore.edit { preferences ->
            preferences[DataStoreName.CREATE_SELECTED_PAGE] = createSelectedPage
        }
    }

    override suspend fun getCreateSelectedPage(): Result<Int> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch {}
                .map { preferences ->
                    preferences[DataStoreName.CREATE_SELECTED_PAGE]
                }
            val value = flow.firstOrNull() ?: 0
            value
        }
    }

    override suspend fun setStartCreateReport(startCreateReport: Boolean) {
        dataStore.edit { preferences ->
            preferences[DataStoreName.START_CREATE_REPORT] = startCreateReport
        }
    }

    override suspend fun getStartCreateReport(): Result<Boolean> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch {}
                .map { preferences ->
                    preferences[DataStoreName.START_CREATE_REPORT]
                }
            val value = flow.firstOrNull() ?: false
            value
        }
    }

    override suspend fun setEditSelectedPage(editSelectedPage: Int) {
        dataStore.edit { preferences ->
            preferences[DataStoreName.EDIT_SELECTED_PAGE] = editSelectedPage
        }
    }

    override suspend fun getEditSelectedPage(): Result<Int> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch {}
                .map { preferences ->
                    preferences[DataStoreName.EDIT_SELECTED_PAGE]
                }
            val value = flow.firstOrNull() ?: 0
            value
        }
    }

    override suspend fun setEditSelectedId(editSelectedId: Int) {
        dataStore.edit { preferences ->
            preferences[DataStoreName.EDIT_SELECTED_ID] = editSelectedId
        }
    }

    override suspend fun getEditSelectedId(): Result<Int> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch {}
                .map { preferences ->
                    preferences[DataStoreName.EDIT_SELECTED_ID]
                }
            val value = flow.firstOrNull() ?: 0
            value
        }
    }
}