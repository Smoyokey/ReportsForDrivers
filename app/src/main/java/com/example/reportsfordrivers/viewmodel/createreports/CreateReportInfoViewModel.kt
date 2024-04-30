package com.example.reportsfordrivers.viewmodel.createreports

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reportsfordrivers.data.dao.CountryDao
import com.example.reportsfordrivers.data.dao.TownshipDao
import com.example.reportsfordrivers.data.dao.createReport.CreateExpensesTripDao
import com.example.reportsfordrivers.data.dao.createReport.CreatePersonalInfoDao
import com.example.reportsfordrivers.data.dao.createReport.CreateProgressReportsDao
import com.example.reportsfordrivers.data.dao.createReport.CreateReportInfoDao
import com.example.reportsfordrivers.data.dao.createReport.CreateRouteDao
import com.example.reportsfordrivers.data.dao.createReport.CreateVehicleTrailerDao
import com.example.reportsfordrivers.data.structure.Township
import com.example.reportsfordrivers.data.structure.createReport.CreateExpensesTrip
import com.example.reportsfordrivers.data.structure.createReport.CreatePersonalInfo
import com.example.reportsfordrivers.data.structure.createReport.CreateProgressReports
import com.example.reportsfordrivers.data.structure.createReport.CreateReportInfo
import com.example.reportsfordrivers.data.structure.createReport.CreateRoute
import com.example.reportsfordrivers.data.structure.createReport.CreateVehicleTrailer
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.viewmodel.createreports.uistate.AddCityCreateReportInfo
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CountriesCreateReportInfo
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CountryDetailingCreateReportInfo
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateReportInfoUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.SelectedNavigationUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.TownshipDetailingCreateReportInfo
import com.example.reportsfordrivers.viewmodel.createreports.uistate.TownshipsCreateReportInfo
import com.example.reportsfordrivers.viewmodel.firstentry.AddCity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

private const val TAG = "CrateReportInfoViewModel"

@HiltViewModel
class CreateReportInfoViewModel @Inject constructor(
    private val fioPreferencesRepository: FioFirstEntryRepository
) : ViewModel() {

    /**
     * Объект для запросов к БД с сохранившимся данными, для выгрузки и сохранения данных в БД.
     * Так же для первого входа создания отчета чистить все предыдущие данные и создавать новые объекты.
     */
    @Inject
    lateinit var createReportInfoDb: CreateReportInfoDao
    /**
     * Объект для обновления данных в БД при первом входе, пустые данные для последующего
     * заполнения
     */
    @Inject
    lateinit var createPersonalInfoDb: CreatePersonalInfoDao
    /**
     * Объект для обновления данных в БД при первом входе, пустые данные для последующего заполнения
     */
    @Inject
    lateinit var createVehicleTrailerDb: CreateVehicleTrailerDao
    /**
     * Объект для обновления данных в БД при первом входе, пустые данные для последующего заполнения
     */
    @Inject
    lateinit var createRouteDb: CreateRouteDao
    /**
     * Объект для обновления данных в БД при первом входе, пустые данные для последующего заполнения
     */
    @Inject
    lateinit var createProgressReportsDb: CreateProgressReportsDao
    /**
     * Объект для обновления данных в БД при первом входе, пустые данные для последующего заполнения
     */
    @Inject
    lateinit var createExpensesTripDb: CreateExpensesTripDao


    /**
     * Объект для запросов к БД *СТРАН*.
     */
    @Inject
    lateinit var countryDb: CountryDao
    /**
     * Объект для запросов к БД *ГОРОДА*
     */
    @Inject
    lateinit var townshipDb: TownshipDao

    /**
     * Объект состояния, который хранит состояние введенных данных, точнее хранит введенные данные:
     * @param CreateReportInfoUiState.id
     * @param CreateReportInfoUiState.date
     * @param CreateReportInfoUiState.waybill
     * @param CreateReportInfoUiState.mainCity
     * @param CreateReportInfoUiState.isStart - Показывает состояние начала создания отчёта
     */
    var uiStateCreateReportInfo = mutableStateOf(CreateReportInfoUiState())
    /**
     * Объект состояния, который хранит состояние перехода по вкладкам
     */
    var uiStateIsValidate = mutableStateOf(SelectedNavigationUiState())


    /**
     * Переменная, которая следит за открытием Диалогового окна для выбора даты
     * @param CreateReportInfoUiState.date
     * @author FALSE - Диалоговое меню закрыто
     */
    var openDialogDateCreateReportInfo = mutableStateOf(false)
    var openBottomSearchCreateReportInfo = mutableStateOf(false)
    var openListSearchCreateReportInfo = mutableIntStateOf(0) //0 - Country, 1 - Township

    var openBottomAddCityCreateReportInfo = mutableStateOf(false)

    var firstOpenReport = mutableStateOf(false)

    var selectedCountryIdInSearch = mutableIntStateOf(0)
    var selectedCountryNameInSearch = mutableStateOf("")

    fun startLoadCreateReportInfo() = runBlocking {
        val createReportInfo = createReportInfoDb.getAllItem().first()
        Log.i(TAG, "StartLoadCreateReportInfo")
        uiStateCreateReportInfo.value = uiStateCreateReportInfo.value.copy(
            id = createReportInfo[0].id,
            date = createReportInfo[0].date.ifEmpty { "" },
            waybill = createReportInfo[0].waybill.ifEmpty { "" },
            mainCity = createReportInfo[0].mainCity.ifEmpty { "" }
        )

        loadCountriesCreateReportInfo()
        Log.i(TAG, uiStateCountryCreateReportInfo.value.listCountries.size.toString())
    }

    fun startCreateReport() = runBlocking {
        deleteAllElements()
        uiStateCreateReportInfo.value = uiStateCreateReportInfo.value.copy(
            date = "",
            mainCity = "",
            waybill = ""
        )
        createNewReport()
        fioPreferencesRepository.setCreateSelectedPage(1)
        fioPreferencesRepository.setStartCreateReport(true)
    }

    fun updateDataCreateReportInfoDate(date: String) {
        val parseDate = if (date.isNotEmpty()) parseDate(date) else date
        uiStateCreateReportInfo.value = uiStateCreateReportInfo.value.copy( date = parseDate )
        runBlocking {
            createReportInfoDb.updateOneElementForIdDate(
                id = uiStateCreateReportInfo.value.id,
                date = uiStateCreateReportInfo.value.date
            )
        }
    }

    fun updateDataCreateReportInfoMainCity(mainCity: String) {
        uiStateCreateReportInfo.value = uiStateCreateReportInfo.value.copy( mainCity = mainCity )
        Log.i(TAG, uiStateCreateReportInfo.value.mainCity)
        runBlocking {
            createReportInfoDb.updateOneElementForIdMainCity(
                id = uiStateCreateReportInfo.value.id,
                mainCity = uiStateCreateReportInfo.value.mainCity
            )
        }
    }

    fun updateDataCreateReportInfoWaybill(waybill: String) {
        uiStateCreateReportInfo.value = uiStateCreateReportInfo.value.copy( waybill = waybill )
        runBlocking {
            createReportInfoDb.updateOneElementForIdWaybill(
                id = uiStateCreateReportInfo.value.id,
                waybill = uiStateCreateReportInfo.value.waybill
            )
        }
    }

    fun isValidateDateCreateReportInfo(): Boolean {
        return uiStateCreateReportInfo.value.date != "" &&
                uiStateCreateReportInfo.value.mainCity != "" &&
                uiStateCreateReportInfo.value.waybill != ""
    }

    val uiStateCountryCreateReportInfo = mutableStateOf(CountriesCreateReportInfo())
    val isCheckedFavoriteCountryCreateReportInfo = mutableStateOf(false)
    val sortCountryCreateReportInfo = mutableIntStateOf(0) //0 - Алфавит, 1 - Популярность

    private val _isSearchingCountryCreateReportInfo = MutableStateFlow(false)
    private val _searchTextCountryCreateReportInfo = MutableStateFlow("")
    val searchTextCountryCreateReportInfo = _searchTextCountryCreateReportInfo.asStateFlow()
    private var _countriesListCountryCreateReportInfo =
        MutableStateFlow(uiStateCountryCreateReportInfo.value.listCountries)
    var countriesListCountryCreateReportInfo = countriesFilterCreateReportInfo()

    private fun countriesFilterCreateReportInfo(): StateFlow<List<CountryDetailingCreateReportInfo>> {
        return searchTextCountryCreateReportInfo
            .combine(_countriesListCountryCreateReportInfo) { text, countries ->
                if(text.isBlank()) {
                    countries
                }
                countries.filter { country ->
                    country.country.uppercase().contains(text.trim().uppercase())
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(2000),
                initialValue = _countriesListCountryCreateReportInfo.value
            )
    }

    fun onSearchTextChangeCountryCreateReportInfo(text: String) {
        _searchTextCountryCreateReportInfo.value = text
    }

    fun onToogleSearchCountryCreateReportInfo() {
        _isSearchingCountryCreateReportInfo.value = !_isSearchingCountryCreateReportInfo.value
        if(_isSearchingCountryCreateReportInfo.value) {
            onSearchTextChangeCountryCreateReportInfo("")
        }
    }

    fun loadCountriesCreateReportInfo() = runBlocking {
        uiStateCountryCreateReportInfo.value.listCountries = SnapshotStateList()

        if(Locale.getDefault().language == "ru") {
            val a = if(isCheckedFavoriteCountryCreateReportInfo.value) {
                if(sortCountryCreateReportInfo.intValue == 0) {
                    countryDb.getFavoriteSortNameRus()
                } else {
                    countryDb.getFavoriteSortRatingRus()
                }
            } else {
                if(sortCountryCreateReportInfo.intValue == 0) {
                    countryDb.getSortNameRus()
                } else {
                    countryDb.getSortRatingRus()
                }
            }

            a.forEach {
                uiStateCountryCreateReportInfo.value.listCountries.add(
                    CountryDetailingCreateReportInfo(
                        id = it.id,
                        country = it.fullNameCountryRus,
                        shortCountry = it.shortNameCountry,
                        rating = it.rating,
                        favorite = it.favorite
                    )
                )
            }
        } else {
            val a = if(isCheckedFavoriteCountryCreateReportInfo.value) {
                if(sortCountryCreateReportInfo.intValue == 0) {
                    countryDb.getFavoriteSortNameEng()
                } else {
                    countryDb.getFavoriteSortRatingEng()
                }
            } else {
                if(sortCountryCreateReportInfo.intValue == 0) {
                    countryDb.getSortNameEng()
                } else {
                    countryDb.getSortRatingEng()
                }
            }

            a.forEach {
                uiStateCountryCreateReportInfo.value.listCountries.add(
                    CountryDetailingCreateReportInfo(
                        id = it.id,
                        country = it.fullNameCountryEng,
                        shortCountry = it.shortNameCountry,
                        rating = it.rating,
                        favorite = it.favorite
                    )
                )
            }
            _countriesListCountryCreateReportInfo = MutableStateFlow(uiStateCountryCreateReportInfo.value.listCountries)
            countriesListCountryCreateReportInfo = countriesFilterCreateReportInfo()
        }
    }

    val uiStateTownshipsCreateReportInfo = mutableStateOf(TownshipsCreateReportInfo())
    val isCheckedFavoriteTownshipsCreateReportInfo = mutableStateOf(false)
    val sortTownshipCreateReportInfo = mutableIntStateOf(0) //0 - Алфавит, 1 - Популярность

    private val _isSearchingTownshipCreateReportInfo = MutableStateFlow(false)
    private val _searchTextTownshipCreateReportInfo = MutableStateFlow("")
    val searchTextTownshipCreateReportInfo = _searchTextTownshipCreateReportInfo.asStateFlow()
    private var _townshipsListTownshipCreateReportInfo =
        MutableStateFlow(uiStateTownshipsCreateReportInfo.value.listTownships)
    var townshipsListTownshipCreateReportInfo = townshipsFilterCreateReportInfo()

    private fun townshipsFilterCreateReportInfo(): StateFlow<List<TownshipDetailingCreateReportInfo>> {
        return searchTextTownshipCreateReportInfo
            .combine(_townshipsListTownshipCreateReportInfo) { text, townships ->
                if(text.isBlank()) {
                    townships
                }
                townships.filter { township ->
                    township.township.uppercase().contains(text.trim().uppercase())
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(2000),
                initialValue = _townshipsListTownshipCreateReportInfo.value
            )
    }

    fun onSearchTextChangeTownshipCreateReportInfo(text: String) {
        _searchTextTownshipCreateReportInfo.value = text
    }

    fun onToogleSearchTownshipCreateReportInfo() {
        _isSearchingTownshipCreateReportInfo.value = !_isSearchingTownshipCreateReportInfo.value
        if(_isSearchingTownshipCreateReportInfo.value) {
            onSearchTextChangeTownshipCreateReportInfo("")
        }
    }

    fun loadTownshipsCreateReportInfo(countryId: Int) = runBlocking {
        uiStateTownshipsCreateReportInfo.value.listTownships = SnapshotStateList()

        if(Locale.getDefault().language == "ru") {
            val a = if(isCheckedFavoriteTownshipsCreateReportInfo.value) {
                if(countryId != 0) {
                    if(sortTownshipCreateReportInfo.intValue == 0) {
                        townshipDb.getCountryIdFavoriteSortNameRus(countryId)
                    } else {
                        townshipDb.getCountryIdFavoriteSortRatingRus(countryId)
                    }
                } else {
                    if(sortTownshipCreateReportInfo.intValue == 0) {
                        townshipDb.getFavoriteSortNameRus()
                    } else {
                        townshipDb.getFavoriteSortRatingRus()
                    }
                }
            } else {
                if(countryId != 0) {
                    if(sortTownshipCreateReportInfo.intValue == 0) {
                        townshipDb.getCountryIdTownshipSortNameRus(countryId)
                    } else {
                        townshipDb.getCountryIdTownshipSortRatingRus(countryId)
                    }
                } else {
                    if(sortTownshipCreateReportInfo.intValue == 0) {
                        townshipDb.getSortNameRus()
                    } else {
                        townshipDb.getSortRatingRus()
                    }
                }
            }

            a.forEach {
                uiStateTownshipsCreateReportInfo.value.listTownships.add(
                    TownshipDetailingCreateReportInfo(
                        id = it.id,
                        township = it.townshipRus,
                        countryId = it.countryId,
                        rating = it.rating,
                        favorite = it.favorite
                    )
                )
            }
        } else {
            val a = if(isCheckedFavoriteTownshipsCreateReportInfo.value) {
                if(countryId != 0) {
                    if(sortTownshipCreateReportInfo.intValue == 0) {
                        townshipDb.getCountryIdFavoriteSortNameEng(countryId)
                    } else {
                        townshipDb.getCountryIdFavoriteSortRatingEng(countryId)
                    }
                } else {
                    if(sortTownshipCreateReportInfo.intValue == 0) {
                        townshipDb.getFavoriteSortNameEng()
                    } else {
                        townshipDb.getFavoriteSortRatingEng()
                    }
                }
            } else {
                if(countryId != 0) {
                    if(sortTownshipCreateReportInfo.intValue == 0) {
                        townshipDb.getCountryIdTownshipSortNameEng(countryId)
                    } else {
                        townshipDb.getCountryIdTownshipSortRatingEng(countryId)
                    }
                } else {
                    if(sortTownshipCreateReportInfo.intValue == 0) {
                        townshipDb.getSortNameEng()
                    } else {
                        townshipDb.getSortRatingEng()
                    }
                }
            }

            a.forEach {
                uiStateTownshipsCreateReportInfo.value.listTownships.add(
                    TownshipDetailingCreateReportInfo(
                        id = it.id,
                        township = it.townshipEng,
                        countryId = it.countryId,
                        rating = it.rating,
                        favorite = it.favorite
                    )
                )
            }
        }

        _townshipsListTownshipCreateReportInfo = MutableStateFlow(uiStateTownshipsCreateReportInfo.value.listTownships)
        townshipsListTownshipCreateReportInfo = townshipsFilterCreateReportInfo()
    }

    fun updateRatingCountry(id: Int) = runBlocking {
        val a = countryDb.getOneItem(id).first()
        countryDb.updateRatingForId(a.id, a.rating + 1)
    }

    fun updateRatingTownship(id: Int) = runBlocking {
        val a = townshipDb.getOneItem(id).first()
        townshipDb.updateRatingForId(a.id, a.rating + 1)
    }

    fun closeBottomSheetSearch() {
        isCheckedFavoriteCountryCreateReportInfo.value = false
        isCheckedFavoriteTownshipsCreateReportInfo.value = false
        openListSearchCreateReportInfo.intValue = 0
        sortCountryCreateReportInfo.intValue = 0
        sortTownshipCreateReportInfo.intValue = 0
        selectedCountryIdInSearch.intValue = 0
        selectedCountryNameInSearch.value = ""
        openBottomSearchCreateReportInfo.value = false
    }

    var uiStateAddCity = mutableStateOf(AddCityCreateReportInfo())

    private val _isSearchingCountryAddCityCreateReportInfo = MutableStateFlow(false)
    private val _searchTextCountryAddCityCreateReportInfo = MutableStateFlow("")
    val searchTextCountryAddCityCreateReportInfo = _searchTextCountryAddCityCreateReportInfo.asStateFlow()
    private var _countriesListCountryAddCityCreateReportInfo =
        MutableStateFlow(uiStateCountryCreateReportInfo.value.listCountries)
    var countriesListCountryAddCityCreateReportInfo = countryAddCityCreateReportInfoFilter()

    val isCheckedFavoriteCountryAddCityCreateReportInfo = mutableStateOf(false)
    val sortCountryAddCityCreateReportInfo = mutableIntStateOf(0) //0 - Алфавит, 1 - Популярность

    private fun countryAddCityCreateReportInfoFilter(): StateFlow<List<CountryDetailingCreateReportInfo>> {
        return searchTextCountryAddCityCreateReportInfo
            .combine(_countriesListCountryAddCityCreateReportInfo) { text, countries ->
                if(text.isBlank()) {
                    countries
                }
                countries.filter { country ->
                    country.country.uppercase().contains(text.trim().uppercase())
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(2000),
                initialValue = _countriesListCountryAddCityCreateReportInfo.value
            )
    }

    fun onSearchTextChangeCountryAddCityCreateReportInfo(text: String) {
        _searchTextCountryAddCityCreateReportInfo.value = text
    }

    fun onToogleSearchCountryAddCityCreateReportInfo() {
        _isSearchingCountryAddCityCreateReportInfo.value = !_isSearchingCountryAddCityCreateReportInfo.value
        if(!_isSearchingCountryAddCityCreateReportInfo.value) {
            onSearchTextChangeCountryAddCityCreateReportInfo("")
        }
    }

    fun loadCountriesAddCityCreateRoute() = runBlocking {
        uiStateCountryCreateReportInfo.value.listCountries = SnapshotStateList()

        if(Locale.getDefault().language == "ru") {
            val a = if(isCheckedFavoriteCountryAddCityCreateReportInfo.value) {
                if(sortCountryAddCityCreateReportInfo.intValue == 0) {
                    countryDb.getFavoriteSortNameRus()
                } else {
                    countryDb.getFavoriteSortRatingRus()
                }
            } else {
                if(sortCountryAddCityCreateReportInfo.intValue == 0) {
                    countryDb.getSortNameRus()
                } else {
                    countryDb.getSortRatingRus()
                }
            }

            a.forEach {
                uiStateCountryCreateReportInfo.value.listCountries.add(
                    CountryDetailingCreateReportInfo(
                        id = it.id,
                        country = it.fullNameCountryRus,
                        shortCountry = it.shortNameCountry,
                        rating = it.rating,
                        favorite = it.favorite
                    )
                )
            }
        } else {
            val a = if(isCheckedFavoriteCountryAddCityCreateReportInfo.value) {
                if(sortCountryAddCityCreateReportInfo.intValue == 0) {
                    countryDb.getFavoriteSortNameEng()
                } else {
                    countryDb.getFavoriteSortRatingEng()
                }
            } else {
                if(sortCountryAddCityCreateReportInfo.intValue == 0) {
                    countryDb.getSortNameEng()
                } else {
                    countryDb.getSortRatingEng()
                }
            }

            a.forEach {
                uiStateCountryCreateReportInfo.value.listCountries.add(
                    CountryDetailingCreateReportInfo(
                        id = it.id,
                        country = it.fullNameCountryEng,
                        shortCountry = it.shortNameCountry,
                        rating = it.rating,
                        favorite = it.favorite
                    )
                )
            }
            _countriesListCountryAddCityCreateReportInfo =
                MutableStateFlow(uiStateCountryCreateReportInfo.value.listCountries)
            countriesListCountryAddCityCreateReportInfo = countryAddCityCreateReportInfoFilter()
        }
    }

    fun updateNameCityAddCityCreateReportInfo(name: String) {
        uiStateAddCity.value = uiStateAddCity.value.copy(nameCity = name)
    }

    fun updateNameCountryAddCityCreateReportInfo(element: CountryDetailingCreateReportInfo) {
        uiStateAddCity.value = uiStateAddCity.value.copy(nameCountry = element.country, country = element)
    }

    fun openAddCity() {
        loadCountriesAddCityCreateRoute()
        uiStateAddCity.value = uiStateAddCity.value.copy(
            nameCity = searchTextTownshipCreateReportInfo.value,
            country = countriesListCountryCreateReportInfo.value[getPositionInCountry(
                selectedCountryIdInSearch.intValue)]
        )
        openBottomAddCityCreateReportInfo.value = true
        openBottomSearchCreateReportInfo.value = false
    }

    fun closeAddCity() {
        openBottomSearchCreateReportInfo.value = true
        openBottomAddCityCreateReportInfo.value = false
    }

    fun validateAddCity(): Boolean {
        return try {
            uiStateAddCity.value.nameCity.isNotEmpty() &&
                    uiStateAddCity.value.country.country.isNotEmpty()
        } catch (e: NullPointerException) {
            false
        }

    }

    fun saveAddCityInBd() = runBlocking {
        townshipDb.insert(
            Township(
                townshipRus = uiStateAddCity.value.nameCity,
                townshipEng = uiStateAddCity.value.nameCity,
                countryId = uiStateAddCity.value.country.id,
                rating = 0,
                favorite = 0
            )
        )
        uiStateAddCity = mutableStateOf(AddCityCreateReportInfo())
        loadTownshipsCreateReportInfo(selectedCountryIdInSearch.intValue)
        closeAddCity()
    }

    @SuppressLint("SimpleDateFormat")
    private fun parseDate(date: String): String {
        val format = SimpleDateFormat("dd.MM.yyyy")
        return format.format(Date(date.toLong()))
    }

    private fun createNewReport() {
        createNewReportCreateReportInfo()
        createNewReportCreatePersonalInfo()
        createNewReportCreateVehicleTrailer()
        createNewReportCreateRoute()
        createNewReportCreateProgressReports()
        createNewReportCreateExpensesTrip()
    }

    private fun createNewReportCreateReportInfo() = runBlocking {
        createReportInfoDb.insert(
            CreateReportInfo(
                date = "",
                waybill = "",
                mainCity = "",
                reportName = "",
                isStart = 1
            )
        )
    }

    private fun createNewReportCreatePersonalInfo() = runBlocking {
        createPersonalInfoDb.insert(
            CreatePersonalInfo(
                lastName = "",
                firstName = "",
                patronymic = ""
            )
        )
    }

    private fun createNewReportCreateVehicleTrailer() = runBlocking {
        createVehicleTrailerDb.insert(
            CreateVehicleTrailer(
                makeVehicle = "",
                rnVehicle = "",
                makeTrailer = "",
                rnTrailer = ""
            )
        )
    }

    private fun createNewReportCreateRoute() = runBlocking {
        createRouteDb.insert(
            CreateRoute(
                route = "",
                dateDeparture = "",
                dateReturn = "",
                dateBorderCrossingDeparture = "",
                dateBorderCrossingReturn = "",
                speedometerReadingDeparture = "",
                speedometerReadingReturn = "",
                fuelled = ""
            )
        )
    }

    private fun createNewReportCreateProgressReports() = runBlocking {
        createProgressReportsDb.insert(
            CreateProgressReports(
                date = "",
                country = "",
                townshipOne = "",
                townshipTwo = "",
                distance = "",
                weight = ""
            )
        )
    }

    private fun createNewReportCreateExpensesTrip() = runBlocking {
        createExpensesTripDb.insert(
            CreateExpensesTrip(
                date = "",
                documentNumber = "",
                expenseItem = "",
                sum = "",
                currency = ""
            )
        )
    }

    private fun deleteAllElements() {
        deleteAllElementsCreateReportInfo()
        deleteAllElementsCreatePersonalInfo()
        deleteAllElementsCreateVehicleTrailer()
        deleteAllElementsCreateRoute()
        deleteAllElementsCreateProgressReports()
        deleteAllElementsCreateExpensesTrip()
    }

    private fun deleteAllElementsCreateReportInfo() = runBlocking {
        createReportInfoDb.deleteAllElements()
    }

    private fun deleteAllElementsCreatePersonalInfo() = runBlocking {
        createPersonalInfoDb.deleteAllElements()
    }

    private fun deleteAllElementsCreateVehicleTrailer() = runBlocking {
        createVehicleTrailerDb.deleteAllElements()
    }

    private fun deleteAllElementsCreateRoute() = runBlocking {
        createRouteDb.deleteAllElements()
    }

    private fun deleteAllElementsCreateProgressReports() = runBlocking {
        createProgressReportsDb.deleteAllElements()
    }

    private fun deleteAllElementsCreateExpensesTrip() = runBlocking {
        createExpensesTripDb.deleteAllElements()
    }

    private fun getPositionInCountry(id: Int): Int {
        for(i in 0..<countriesListCountryAddCityCreateReportInfo.value.size) {
            if(id == countriesListCountryAddCityCreateReportInfo.value[i].id) return i
        }
        return 0
    }
}