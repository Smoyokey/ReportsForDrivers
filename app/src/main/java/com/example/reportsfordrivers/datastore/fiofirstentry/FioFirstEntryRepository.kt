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

    suspend fun setIsValidateCreateReportInfo(isValidateCreateReportInfo: Boolean)

    suspend fun getIsValidateCreateReportInfo(): Result<Boolean>

    suspend fun setIsValidateCreatePersonalInfo(isValidateCreatePersonalInfo: Boolean)

    suspend fun getIsValidateCreatePersonalInfo(): Result<Boolean>

    suspend fun setIsValidateCreateVehicleTrailer(isValidateCreateVehicleTrailer: Boolean)

    suspend fun getIsValidateCreateVehicleTrailer(): Result<Boolean>

    suspend fun setIsValidateCreateRoute(isValidateCreateRoute: Boolean)

    suspend fun getIsValidateCreateRoute(): Result<Boolean>

    suspend fun setIsValidateCreateProgressReports(isValidateCreateProgressReports: Boolean)

    suspend fun getIsValidateCreateProgressReports(): Result<Boolean>

    suspend fun setCreateSelectedPage(createSelectedPage: Int)

    suspend fun getCreateSelectedPage(): Result<Int>

    suspend fun setStartCreateReport(startCreateReport: Boolean)

    suspend fun getStartCreateReport(): Result<Boolean>
}