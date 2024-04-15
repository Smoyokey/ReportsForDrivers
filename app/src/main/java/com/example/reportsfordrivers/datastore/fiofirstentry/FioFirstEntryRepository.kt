package com.example.reportsfordrivers.datastore.fiofirstentry

interface FioFirstEntryRepository {

    suspend fun setFirstEntry(isFirstEntry: Boolean)

    suspend fun setFirstName(firstName: String)

    suspend fun setLastName(lastName: String)

    suspend fun setPatronymic(patronymic: String)

    suspend fun getFirstEntry(): Result<Boolean>

    suspend fun getFirstName(): Result<String>

    suspend fun getLastName(): Result<String>

    suspend fun getPatronymic(): Result<String>

    suspend fun setLanguageReport(languageReport: Int)

    suspend fun getLanguageReport(): Result<Int>

    suspend fun setDefaultCurrency(defaultCurrency: String)

    suspend fun getDefaultCurrency(): Result<String>
}