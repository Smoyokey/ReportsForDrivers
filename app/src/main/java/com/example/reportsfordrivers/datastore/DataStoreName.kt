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
}