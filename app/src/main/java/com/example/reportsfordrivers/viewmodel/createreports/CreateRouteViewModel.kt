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
import com.example.reportsfordrivers.data.dao.createReport.CreateRouteDao
import com.example.reportsfordrivers.data.structure.Township
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.interfaces.search.SearchCountry
import com.example.reportsfordrivers.interfaces.search.SearchTownship
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateRouteUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.RouteElement
import com.example.reportsfordrivers.viewmodel.createreports.uistate.SelectedNavigationUiState
import com.example.reportsfordrivers.viewmodel.firstentry.AddCity
import com.example.reportsfordrivers.viewmodel.firstentry.Countries
import com.example.reportsfordrivers.viewmodel.firstentry.CountryDetailing
import com.example.reportsfordrivers.viewmodel.firstentry.TownshipDetailing
import com.example.reportsfordrivers.viewmodel.firstentry.Townships
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

private const val TAG = "CreateRouteViewModel"

@HiltViewModel
class CreateRouteViewModel @Inject constructor(
    private val fioPreferencesRepository: FioFirstEntryRepository
) : ViewModel(), SearchCountry, SearchTownship {

    @Inject
    lateinit var createRouteDb: CreateRouteDao

    @Inject
    lateinit var townshipDb: TownshipDao

    @Inject
    lateinit var countryDb: CountryDao

    var firstOpenCreateRoute = mutableStateOf(false)

    var uiStateCreateRoute = mutableStateOf(CreateRouteUiState())
        private set
    var uiStateIsValidate = mutableStateOf(SelectedNavigationUiState())

    var openDialogDateReturnCreateRoute = mutableStateOf(false)
    var openDialogDateDepartureCreateRoute = mutableStateOf(false)
    var openDialogDateCrossingDepartureCreateRoute = mutableStateOf(false)
    var openDialogDateCrossingReturnCreateRoute = mutableStateOf(false)

    var openBottomSheetCountryCreateRoute = mutableStateOf(false)
    var openListSearchCreateRoute = mutableIntStateOf(0) //0 - Country, 1 - Township

    var openBottomAddCityCreateRoute = mutableStateOf(false)

    var selectedCountryIdInSearch = mutableIntStateOf(0)
    var selectedCountryNameInSearch = mutableStateOf("")

    var selectedRouteWrite = mutableIntStateOf(0)

    fun startLoadCreateRoute() = runBlocking {
        val createRoute = createRouteDb.getAllItem().first()
        uiStateCreateRoute.value = uiStateCreateRoute.value.copy(
            id = createRoute[0].id,
            route = loadRouteToList(createRoute[0].route), //Добавление ввода
            dateDeparture = createRoute[0].dateDeparture.ifEmpty { "" },
            dateReturn = createRoute[0].dateReturn.ifEmpty { "" },
            dateCrossingDeparture = createRoute[0].dateBorderCrossingDeparture.ifEmpty { "" },
            dateCrossingReturn = createRoute[0].dateBorderCrossingReturn.ifEmpty { "" },
            speedometerDeparture = createRoute[0].speedometerReadingDeparture.ifEmpty { "" },
            speedometerReturn = createRoute[0].speedometerReadingReturn.ifEmpty { "" },
            fuelled = createRoute[0].fuelled.ifEmpty { "" }
        )
        uiStateCreateRoute.value.route.addAll(createRoute[0].route.split(":").map {
            RouteElement(id = 0, text = it)
        })
        fioPreferencesRepository.setCreateSelectedPage(4)
        loadCountries()
        firstOpenCreateRoute.value = true
    }

    fun updateDataCreateRouteRoute(route: String, index: Int) {
        uiStateCreateRoute.value.route[index] =
            uiStateCreateRoute.value.route[index].copy(text = route)
        Log.i(TAG, uiStateCreateRoute.value.route.joinToString { it.text })
        runBlocking {
            createRouteDb.updateOneElementForIdRoute(
                id = uiStateCreateRoute.value.id,
                route = uiStateCreateRoute.value.route.joinToString { it.text }
            )
        }
    }

    fun updateDataCreateRouteDateReturn(dateReturn: String) {
        val parseDate = if (dateReturn.isNotEmpty()) parseDate(dateReturn) else dateReturn
        uiStateCreateRoute.value = uiStateCreateRoute.value.copy(dateReturn = parseDate)
        runBlocking {
            createRouteDb.updateOneElementForIdDateReturn(
                id = uiStateCreateRoute.value.id,
                dateReturn = uiStateCreateRoute.value.dateReturn
            )
        }
    }

    fun updateDataCreateRouteDateDeparture(dateDeparture: String) {
        val parseDate = if (dateDeparture.isNotEmpty()) parseDate(dateDeparture) else dateDeparture
        uiStateCreateRoute.value = uiStateCreateRoute.value.copy(dateDeparture = parseDate)
        runBlocking {
            createRouteDb.updateOneElementForIdDateDeparture(
                id = uiStateCreateRoute.value.id,
                dateDeparture = uiStateCreateRoute.value.dateDeparture
            )
        }
    }

    fun updateDataCreateRouteDateCrossingDeparture(dateCrossingDeparture: String) {
        val parseDate = if (dateCrossingDeparture.isNotEmpty())
            parseDate(dateCrossingDeparture)
        else
            dateCrossingDeparture
        uiStateCreateRoute.value = uiStateCreateRoute.value.copy(dateCrossingDeparture = parseDate)
        runBlocking {
            createRouteDb.updateOneElementForIdDateBorderCrossingDeparture(
                id = uiStateCreateRoute.value.id,
                dateBorderCrossingDeparture = uiStateCreateRoute.value.dateCrossingDeparture
            )
        }
    }

    fun updateDataCreateRouteDateCrossingReturn(dateCrossingReturn: String) {
        val parseDate = if (dateCrossingReturn.isNotEmpty())
            parseDate(dateCrossingReturn)
        else
            dateCrossingReturn
        uiStateCreateRoute.value = uiStateCreateRoute.value.copy(dateCrossingReturn = parseDate)
        runBlocking {
            createRouteDb.updateOneElementForIdDateBorderCrossingReturn(
                id = uiStateCreateRoute.value.id,
                dateBorderCrossingReturn = uiStateCreateRoute.value.dateCrossingReturn
            )
        }
    }

    fun updateDataCreateRouteSpeedometerDeparture(speedometerDeparture: String) {
        uiStateCreateRoute.value =
            uiStateCreateRoute.value.copy(speedometerDeparture = speedometerDeparture)
        runBlocking {
            createRouteDb.updateOneElementForIdSpeedometerReadingDeparture(
                id = uiStateCreateRoute.value.id,
                speedometerReadingDeparture = uiStateCreateRoute.value.speedometerDeparture
            )
        }
    }

    fun updateDataCreateRouteSpeedometerReturn(speedometerReturn: String) {
        uiStateCreateRoute.value =
            uiStateCreateRoute.value.copy(speedometerReturn = speedometerReturn)
        runBlocking {
            createRouteDb.updateOneElementForIdSpeedometerReadingReturn(
                id = uiStateCreateRoute.value.id,
                speedometerReadingReturn = uiStateCreateRoute.value.speedometerReturn
            )
        }
    }

    fun updateDataCreateRouteFuelled(fuelled: String) {
        uiStateCreateRoute.value = uiStateCreateRoute.value.copy(fuelled = fuelled)
        runBlocking {
            createRouteDb.updateOneElementForIdFuelled(
                id = uiStateCreateRoute.value.id,
                fuelled = uiStateCreateRoute.value.fuelled
            )
        }
    }

    fun isValidateDataCreateRoute(): Boolean {
        return uiStateCreateRoute.value.dateDeparture != "" &&
                uiStateCreateRoute.value.dateReturn != "" &&
                uiStateCreateRoute.value.dateCrossingDeparture != "" &&
                uiStateCreateRoute.value.dateCrossingReturn != "" &&
                uiStateCreateRoute.value.speedometerDeparture != "" &&
                uiStateCreateRoute.value.speedometerReturn != "" &&
                uiStateCreateRoute.value.fuelled != "" &&
                uiStateCreateRoute.value.route.any { element ->
                    element.text.isNotEmpty()
                }
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

    override fun loadCountries() = runBlocking {
        uiStateCountry.value.listCountries = SnapshotStateList()

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
        }
        _countriesListCountry = MutableStateFlow(uiStateCountry.value.listCountries)
        countriesListCountry = filterCountry()
    }

    override val uiStateTownship = mutableStateOf(Townships())
    override val isCheckedFavoriteTownship = mutableStateOf(false)
    override val sortTownship = mutableIntStateOf(0) //0 - Алфавит, 1 - Популярность

    override val _isSearchingTownship = MutableStateFlow(false)
    override val _searchTextTownship = MutableStateFlow("")
    override val searchTextTownship = _searchTextCountry.asStateFlow()
    override var _townshipsListTownship = MutableStateFlow(uiStateTownship.value.listTownships)
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

    var uiStateAddCity = mutableStateOf(AddCity())

    fun updateNameCityAddCityCreateRoute(name: String) {
        uiStateAddCity.value = uiStateAddCity.value.copy(nameCity = name)
    }

    fun updateNameCountryAddCityCreateRoute(element: CountryDetailing) {
        uiStateAddCity.value =
            uiStateAddCity.value.copy(nameCountry = element.country, country = element)
    }

    fun openAddCity(openBottom: MutableState<Boolean> = openBottomSheetCountryCreateRoute) {
        loadCountries()
        uiStateAddCity.value = uiStateAddCity.value.copy(
            nameCity = searchTextCountry.value,
            country = countriesListCountry.value[getPositionInCountry(selectedCountryIdInSearch.intValue)]
        )
        openBottomAddCityCreateRoute.value = true
        openBottomSheetCountryCreateRoute.value = false
    }

    fun closeAddCity(openBottom: MutableState<Boolean> = openBottomSheetCountryCreateRoute) {
        openBottomSheetCountryCreateRoute.value = true
        openBottomAddCityCreateRoute.value = false
    }

    fun validateAddCity(): Boolean {
        return try {
            uiStateAddCity.value.nameCity.isNotEmpty() &&
                    uiStateAddCity.value.country.country.isNotEmpty()
        } catch (e: NullPointerException) {
            false
        }
    }

    fun saveAddCityInBd(openBottom: MutableState<Boolean> = openBottomSheetCountryCreateRoute) =
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

    fun closeBottomSheetSearch(openBottom: MutableState<Boolean> = openBottomSheetCountryCreateRoute) {
        openBottomSheetCountryCreateRoute.value = false
    }

    override fun updateRatingCountry(id: Int) = runBlocking {
        val a = countryDb.getOneItem(id).first()
        countryDb.updateRatingForId(a.id, a.rating + 1)
    }

    override fun updateRatingTownship(id: Int) = runBlocking {
        val a = townshipDb.getOneItem(id).first()
        townshipDb.updateRatingForId(a.id, a.rating + 1)
    }

    fun validateSpeedometer(): Boolean {
        return if (uiStateCreateRoute.value.speedometerDeparture.isNotEmpty() &&
            uiStateCreateRoute.value.speedometerReturn.isNotEmpty()
        ) {
            uiStateCreateRoute.value.speedometerDeparture.toInt() >=
                    uiStateCreateRoute.value.speedometerReturn.toInt()
        } else {
            false
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun parseDate(date: String): String {
        val format = SimpleDateFormat("dd.MM.yyyy")
        return format.format(Date(date.toLong()))
    }

    private fun loadRouteToList(route: String): SnapshotStateList<RouteElement> {
        return if (route.isEmpty()) {
            SnapshotStateList()
        } else {
            val a = SnapshotStateList<RouteElement>()
            val b = route.split(", ")
            var id = 1
            a.addAll(b.map {
                RouteElement(id++, it)
            })
            Log.i(TAG, a.joinToString(" "))
            a
        }
    }

    private fun getPositionInCountry(id: Int): Int {
        for (i in 0..<countriesListCountry.value.size) {
            if (id == countriesListCountry.value[i].id) return i
        }
        return 0
    }
}