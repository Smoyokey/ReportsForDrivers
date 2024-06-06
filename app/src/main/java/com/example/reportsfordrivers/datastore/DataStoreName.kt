package com.example.reportsfordrivers.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreName {
    val IS_FIRST_ENTRY = booleanPreferencesKey(
        name = "is_first_entry"
    )

    val FIRST_NAME = stringPreferencesKey(
        name = "first_name"
    )

    val LAST_NAME = stringPreferencesKey(
        name = "last_name"
    )

    val PATRONYMIC = stringPreferencesKey(
        name = "patronymic"
    )

    val LANGUAGE_REPORT = intPreferencesKey(
        name = "languageReport"
    )

    val DEFAULT_CURRENCY = stringPreferencesKey(
        name = "defaultCurrency"
    )

    val THEME_APP = stringPreferencesKey(
        name = "themeApp"
    )

    val IS_VALIDATE_CREATE_REPORT_INFO = booleanPreferencesKey(
        name = "is_validate_create_report_info"
    )

    val IS_VALIDATE_CREATE_PERSONAL_INFO = booleanPreferencesKey(
        name = "is_validate_create_personal_info"
    )

    val IS_VALIDATE_CREATE_VEHICLE_TRAILER = booleanPreferencesKey(
        name = "is_validate_create_vehicle_trailer"
    )

    val IS_VALIDATE_CREATE_ROUTE = booleanPreferencesKey(
        name = "is_validate_create_route"
    )

    val IS_VALIDATE_CREATE_PROGRESS_REPORTS = booleanPreferencesKey(
        name = "is_validate_create_progress_reports"
    )

    val CREATE_SELECTED_PAGE = intPreferencesKey(
        name = "create_selected_page"
    )

    val START_CREATE_REPORT = booleanPreferencesKey(
        name = "start_create_report"
    )

    val EDIT_SELECTED_PAGE = intPreferencesKey(
        name = "edit_selected_page"
    )

    val EDIT_SELECTED_ID = intPreferencesKey(
        name = "edit_selected_id"
    )
}