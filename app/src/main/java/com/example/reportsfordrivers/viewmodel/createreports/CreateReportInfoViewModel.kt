package com.example.reportsfordrivers.viewmodel.createreports

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
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
import com.example.reportsfordrivers.interfaces.search.SearchCountry
import com.example.reportsfordrivers.interfaces.search.SearchTownship
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateReportInfoUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.SelectedNavigationUiState
import com.example.reportsfordrivers.viewmodel.firstentry.AddCity
import com.example.reportsfordrivers.viewmodel.firstentry.Countries
import com.example.reportsfordrivers.viewmodel.firstentry.CountryDetailing
import com.example.reportsfordrivers.viewmodel.firstentry.TownshipDetailing
import com.example.reportsfordrivers.viewmodel.firstentry.Townships
import com.example.reportsfordrivers.viewmodel.functions.CreateNewReport
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
) : ViewModel(), SearchCountry, SearchTownship {

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
        loadCountries()
    }

    fun startCreateReport() = runBlocking {
        CreateNewReport.deleteAllElements(
            createReportInfoDb,
            createPersonalInfoDb,
            createVehicleTrailerDb,
            createRouteDb,
            createProgressReportsDb,
            createExpensesTripDb
        )
        uiStateCreateReportInfo.value = uiStateCreateReportInfo.value.copy(
            date = "",
            mainCity = "",
            waybill = ""
        )
        CreateNewReport.createNewReport(
            createReportInfoDb,
            createPersonalInfoDb,
            createVehicleTrailerDb,
            createRouteDb,
            createProgressReportsDb,
            createExpensesTripDb
        )
        fioPreferencesRepository.setCreateSelectedPage(1)
        fioPreferencesRepository.setStartCreateReport(true)
    }

    fun updateDataCreateReportInfoDate(date: String) {
        val parseDate = if (date.isNotEmpty()) parseDate(date) else date
        uiStateCreateReportInfo.value = uiStateCreateReportInfo.value.copy(date = parseDate)
        runBlocking {
            createReportInfoDb.updateOneElementForIdDate(
                id = uiStateCreateReportInfo.value.id,
                date = uiStateCreateReportInfo.value.date
            )
        }
    }

    fun updateDataCreateReportInfoMainCity(mainCity: String, id: Int = 0) {
        uiStateCreateReportInfo.value = uiStateCreateReportInfo.value.copy(mainCity = mainCity)
        Log.i(TAG, uiStateCreateReportInfo.value.mainCity)
        runBlocking {
            createReportInfoDb.updateOneElementForIdMainCity(
                id = uiStateCreateReportInfo.value.id,
                mainCity = uiStateCreateReportInfo.value.mainCity
            )
        }
    }

    fun updateDataCreateReportInfoWaybill(waybill: String) {
        uiStateCreateReportInfo.value = uiStateCreateReportInfo.value.copy(waybill = waybill)
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

    override val uiStateCountry = mutableStateOf(Countries())
    override val isCheckedFavoriteCountry = mutableStateOf(false)
    override val sortCountry = mutableIntStateOf(0) //0 - Алфавит, 1 - Популярность

    override val _isSearchingCountry = MutableStateFlow(false)
    override val _searchTextCountry = MutableStateFlow("")
    override val searchTextCountry = _searchTextCountry.asStateFlow()
    override var _countriesListCountry = MutableStateFlow(uiStateCountry.value.listCountries)
    override var countriesListCountry = filterCountry()

    override fun filterCountry(): StateFlow<List<CountryDetailing>> {
        return searchTextCountry
            .combine(_countriesListCountry) { text, countries ->
                if (text.isBlank()) {
                    countries
                }
                countries.filter { country ->
                    country.country.uppercase().contains(text.trim().uppercase())
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(2000),
                initialValue = _countriesListCountry.value
            )
    }

    override fun onSearchTextChangeCountry(text: String) {
        _searchTextCountry.value = text
    }

    override fun onToogleSearchCountry() {
        _isSearchingCountry.value = !_isSearchingCountry.value
        if (_isSearchingCountry.value) {
            onSearchTextChangeCountry("")
        }
    }

    override fun loadCountries(): Unit = runBlocking {
        uiStateCountry.value.listCountries = SnapshotStateList()
        Log.i(TAG, sortCountry.intValue.toString() + " sortCountry")
        if (Locale.getDefault().language == "ru") {
            val a = if (isCheckedFavoriteCountry.value) {
                if (sortCountry.intValue == 0) {
                    countryDb.getFavoriteSortNameRus()
                } else {
                    countryDb.getFavoriteSortRatingRus()
                }
            } else {
                if (sortCountry.intValue == 0) {
                    countryDb.getSortNameRus()
                } else {
                    countryDb.getSortRatingRus()
                }
            }

            a.forEach {
                uiStateCountry.value.listCountries.add(
                    CountryDetailing(
                        id = it.id,
                        country = it.fullNameCountryRus,
                        shortCountry = it.shortNameCountry,
                        rating = it.rating,
                        favorite = it.favorite
                    )
                )
            }
        } else {
            val a = if (isCheckedFavoriteCountry.value) {
                if (sortCountry.intValue == 0) {
                    countryDb.getFavoriteSortNameEng()
                } else {
                    countryDb.getFavoriteSortRatingEng()
                }
            } else {
                if (sortCountry.intValue == 0) {
                    countryDb.getSortNameEng()
                } else {
                    countryDb.getSortRatingEng()
                }
            }

            a.forEach {
                uiStateCountry.value.listCountries.add(
                    CountryDetailing(
                        id = it.id,
                        country = it.fullNameCountryEng,
                        shortCountry = it.shortNameCountry,
                        rating = it.rating,
                        favorite = it.favorite
                    )
                )
            }
            _countriesListCountry = MutableStateFlow(uiStateCountry.value.listCountries)
            Log.i(TAG, _countriesListCountry.value.joinToString("**"))

            countriesListCountry = filterCountry()

            Log.i(TAG, countriesListCountry.value.joinToString("::"))
        }
    }

    override val uiStateTownship = mutableStateOf(Townships())
    override val isCheckedFavoriteTownship = mutableStateOf(false)
    override val sortTownship = mutableIntStateOf(0) //0 - Алфавит, 1 - Популярность

    override val _isSearchingTownship = MutableStateFlow(false)
    override val _searchTextTownship = MutableStateFlow("")
    override val searchTextTownship = _searchTextTownship.asStateFlow()
    override var _townshipsListTownship =
        MutableStateFlow(uiStateTownship.value.listTownships)
    override var townshipsListTownship = filterTownship()

    override fun filterTownship(): StateFlow<List<TownshipDetailing>> {
        return searchTextTownship
            .combine(_townshipsListTownship) { text, townships ->
                if (text.isBlank()) {
                    townships
                }
                townships.filter { township ->
                    township.township.uppercase().contains(text.trim().uppercase())
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(2000),
                initialValue = _townshipsListTownship.value
            )
    }

    override fun onSearchTextChangeTownship(text: String) {
        _searchTextTownship.value = text
    }

    override fun onToogleSearchTownship() {
        _isSearchingTownship.value = !_isSearchingTownship.value
        if (_isSearchingTownship.value) {
            onSearchTextChangeTownship("")
        }
    }

    override fun loadTownships(countryId: Int) = runBlocking {
        uiStateTownship.value.listTownships = SnapshotStateList()

        if (Locale.getDefault().language == "ru") {
            val a = if (isCheckedFavoriteTownship.value) {
                if (countryId != 0) {
                    if (sortTownship.intValue == 0) {
                        townshipDb.getCountryIdFavoriteSortNameRus(countryId)
                    } else {
                        townshipDb.getCountryIdFavoriteSortRatingRus(countryId)
                    }
                } else {
                    if (sortTownship.intValue == 0) {
                        townshipDb.getFavoriteSortNameRus()
                    } else {
                        townshipDb.getFavoriteSortRatingRus()
                    }
                }
            } else {
                if (countryId != 0) {
                    if (sortTownship.intValue == 0) {
                        townshipDb.getCountryIdTownshipSortNameRus(countryId)
                    } else {
                        townshipDb.getCountryIdTownshipSortRatingRus(countryId)
                    }
                } else {
                    if (sortTownship.intValue == 0) {
                        townshipDb.getSortNameRus()
                    } else {
                        townshipDb.getSortRatingRus()
                    }
                }
            }

            a.forEach {
                uiStateTownship.value.listTownships.add(
                    TownshipDetailing(
                        id = it.id,
                        township = it.townshipRus,
                        countryId = it.countryId,
                        rating = it.rating,
                        favorite = it.favorite
                    )
                )
            }
        } else {
            val a = if (isCheckedFavoriteTownship.value) {
                if (countryId != 0) {
                    if (sortTownship.intValue == 0) {
                        townshipDb.getCountryIdFavoriteSortNameEng(countryId)
                    } else {
                        townshipDb.getCountryIdFavoriteSortRatingEng(countryId)
                    }
                } else {
                    if (sortTownship.intValue == 0) {
                        townshipDb.getFavoriteSortNameEng()
                    } else {
                        townshipDb.getFavoriteSortRatingEng()
                    }
                }
            } else {
                if (countryId != 0) {
                    if (sortTownship.intValue == 0) {
                        townshipDb.getCountryIdTownshipSortNameEng(countryId)
                    } else {
                        townshipDb.getCountryIdTownshipSortRatingEng(countryId)
                    }
                } else {
                    if (sortTownship.intValue == 0) {
                        townshipDb.getSortNameEng()
                    } else {
                        townshipDb.getSortRatingEng()
                    }
                }
            }

            a.forEach {
                uiStateTownship.value.listTownships.add(
                    TownshipDetailing(
                        id = it.id,
                        township = it.townshipEng,
                        countryId = it.countryId,
                        rating = it.rating,
                        favorite = it.favorite
                    )
                )
            }
        }

        _townshipsListTownship = MutableStateFlow(uiStateTownship.value.listTownships)
        townshipsListTownship = filterTownship()
    }

    override fun updateRatingCountry(id: Int) = runBlocking {
        val a = countryDb.getOneItem(id).first()
        countryDb.updateRatingForId(a.id, a.rating + 1)
    }

    override fun updateRatingTownship(id: Int) = runBlocking {
        val a = townshipDb.getOneItem(id).first()
        townshipDb.updateRatingForId(a.id, a.rating + 1)
    }

    fun closeBottomSheetSearch(openBottom: MutableState<Boolean> = openBottomSearchCreateReportInfo) {
        isCheckedFavoriteCountry.value = false
        isCheckedFavoriteTownship.value = false
        openListSearchCreateReportInfo.intValue = 0
        sortCountry.intValue = 0
        sortTownship.intValue = 0
        selectedCountryIdInSearch.intValue = 0
        selectedCountryNameInSearch.value = ""
        openBottomSearchCreateReportInfo.value = false
    }

    var uiStateAddCity = mutableStateOf(AddCity())

    fun updateNameCityAddCityCreateReportInfo(name: String) {
        uiStateAddCity.value = uiStateAddCity.value.copy(nameCity = name)
    }

    fun updateNameCountryAddCityCreateReportInfo(element: CountryDetailing) {
        uiStateAddCity.value =
            uiStateAddCity.value.copy(nameCountry = element.country, country = element)
    }

    fun openAddCity(openBottom: MutableState<Boolean> = openBottomSearchCreateReportInfo) {
        loadCountries()
        uiStateAddCity.value = uiStateAddCity.value.copy(
            nameCity = searchTextTownship.value,
            country = countriesListCountry.value[getPositionInCountry(
                selectedCountryIdInSearch.intValue
            )]
        )
        openBottomAddCityCreateReportInfo.value = true
        openBottomSearchCreateReportInfo.value = false
    }

    fun closeAddCity(openBottom: MutableState<Boolean> = openBottomSearchCreateReportInfo) {
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

    fun saveAddCityInBd(openBottom: MutableState<Boolean> = openBottomSearchCreateReportInfo) =
        runBlocking {
            townshipDb.insert(
                Township(
                    townshipRus = uiStateAddCity.value.nameCity,
                    townshipEng = uiStateAddCity.value.nameCity,
                    countryId = uiStateAddCity.value.country.id,
                    rating = 0,
                    favorite = 0
                )
            )
            uiStateAddCity = mutableStateOf(AddCity())
            loadTownships(selectedCountryIdInSearch.intValue)
            closeAddCity()
        }

    fun openListSearch() {
        openListSearchCreateReportInfo.intValue = 0
        selectedCountryIdInSearch.intValue = 0
        selectedCountryNameInSearch.value = ""
    }

    @SuppressLint("SimpleDateFormat")
    private fun parseDate(date: String): String {
        val format = SimpleDateFormat("dd.MM.yyyy")
        return format.format(Date(date.toLong()))
    }

    private fun getPositionInCountry(id: Int): Int {
        for (i in 0..<countriesListCountry.value.size) {
            if (id == countriesListCountry.value[i].id) return i
        }
        return 0
    }
}