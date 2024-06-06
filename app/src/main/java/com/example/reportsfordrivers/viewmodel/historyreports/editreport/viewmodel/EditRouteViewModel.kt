package com.example.reportsfordrivers.viewmodel.historyreports.editreport.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reportsfordrivers.data.dao.CountryDao
import com.example.reportsfordrivers.data.dao.TownshipDao
import com.example.reportsfordrivers.data.dao.editreport.EditRouteDao
import com.example.reportsfordrivers.data.structure.Township
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.interfaces.search.SearchCountry
import com.example.reportsfordrivers.interfaces.search.SearchTownship
import com.example.reportsfordrivers.viewmodel.createreports.uistate.RouteElement
import com.example.reportsfordrivers.viewmodel.firstentry.AddCity
import com.example.reportsfordrivers.viewmodel.firstentry.Countries
import com.example.reportsfordrivers.viewmodel.firstentry.CountryDetailing
import com.example.reportsfordrivers.viewmodel.firstentry.TownshipDetailing
import com.example.reportsfordrivers.viewmodel.firstentry.Townships
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditRouteUiState
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

private const val TAG = "EditRouteViewModel"

@HiltViewModel
class EditRouteViewModel @Inject constructor(
    private val fioPreferencesRepository: FioFirstEntryRepository
) : ViewModel(), SearchCountry, SearchTownship {

    @Inject
    lateinit var editRouteDb: EditRouteDao

    @Inject
    lateinit var townshipDb: TownshipDao

    @Inject
    lateinit var countryDb: CountryDao

    var firstOpenEditRoute = mutableStateOf(false)

    var uiStateEditRoute = mutableStateOf(EditRouteUiState())

    var openDialogDateReturnEditRoute = mutableStateOf(false)
    var openDialogDateDepartureEditRoute = mutableStateOf(false)
    var openDialogDateCrossingDepartureEditRoute = mutableStateOf(false)
    var openDialogDateCrossingReturnEditRoute = mutableStateOf(false)

    var openBottomSheetCountryEditRoute = mutableStateOf(false)
    var openListSearchEditRoute = mutableIntStateOf(0) //0 - Country, 1 - Township

    var openBottomAddCityEditRoute = mutableStateOf(false)

    var selectedCountryIdInSearch = mutableIntStateOf(0)
    var selectedCountryNameInSearch = mutableStateOf("")

    var selectedRouteWrite = mutableIntStateOf(0)

    fun startLoadEditRoute() = runBlocking {
        val editRoute = editRouteDb.getAllItem().first()
        uiStateEditRoute.value = uiStateEditRoute.value.copy(
            id = editRoute[0].id,
            route = loadRouteToList(editRoute[0].route),
            dateDeparture = editRoute[0].dateDeparture.ifEmpty { "" },
            dateReturn = editRoute[0].dateReturn.ifEmpty { "" },
            dateCrossingDeparture = editRoute[0].dateBorderCrossingDeparture.ifEmpty { "" },
            dateCrossingReturn = editRoute[0].dateBorderCrossingReturn.ifEmpty { "" },
            speedometerDeparture = editRoute[0].speedometerReadingDeparture.ifEmpty { "" },
            speedometerReturn = editRoute[0].speedometerReadingReturn.ifEmpty { "" },
            fuelled = editRoute[0].fuelled.ifEmpty { "" }
        )
        uiStateEditRoute.value.route.addAll(editRoute[0].route.split(":").map {
            RouteElement(id = 0, text = it)
        })
        fioPreferencesRepository.setCreateSelectedPage(4)
        loadCountries()
        firstOpenEditRoute.value = true
    }

    fun updateDataEditRouteRoute(route: String, index: Int) {
        uiStateEditRoute.value.route[index] =
            uiStateEditRoute.value.route[index].copy(text = route)
        runBlocking {
            editRouteDb.updateOneElementForIdRoute(
                id = uiStateEditRoute.value.id,
                route = uiStateEditRoute.value.route.joinToString { it.text }
            )
        }
    }

    fun updateDataEditRouteDateReturn(dateReturn: String) {
        val parseDate = if (dateReturn.isNotEmpty()) parseDate(dateReturn) else dateReturn
        uiStateEditRoute.value = uiStateEditRoute.value.copy(dateReturn = parseDate)
        runBlocking {
            editRouteDb.updateOneElementForIdDateReturn(
                id = uiStateEditRoute.value.id,
                dateReturn = uiStateEditRoute.value.dateReturn
            )
        }
    }

    fun updateDataEditRouteDateDeparture(dateDeparture: String) {
        val parseDate = if (dateDeparture.isNotEmpty()) parseDate(dateDeparture) else dateDeparture
        uiStateEditRoute.value = uiStateEditRoute.value.copy(dateDeparture = parseDate)
        runBlocking {
            editRouteDb.updateOneElementForIdDateDeparture(
                id = uiStateEditRoute.value.id,
                dateDeparture = uiStateEditRoute.value.dateDeparture
            )
        }
    }

    fun updateDataEditRouteDateCrossingDeparture(dateCrossingDeparture: String) {
        val parseDate = if (dateCrossingDeparture.isNotEmpty())
            parseDate(dateCrossingDeparture)
        else
            dateCrossingDeparture
        uiStateEditRoute.value = uiStateEditRoute.value.copy(dateCrossingDeparture = parseDate)
        runBlocking {
            editRouteDb.updateOneElementForIdDateBorderCrossingDeparture(
                id = uiStateEditRoute.value.id,
                dateBorderCrossingDeparture = uiStateEditRoute.value.dateCrossingDeparture
            )
        }
    }

    fun updateDataEditRouteDateCrossingReturn(dateCrossingReturn: String) {
        val parseDate = if (dateCrossingReturn.isNotEmpty())
            parseDate(dateCrossingReturn)
        else
            dateCrossingReturn
        uiStateEditRoute.value = uiStateEditRoute.value.copy(dateCrossingReturn = parseDate)
        runBlocking {
            editRouteDb.updateOneElementForIdDateBorderCrossingReturn(
                id = uiStateEditRoute.value.id,
                dateBorderCrossingReturn = uiStateEditRoute.value.dateCrossingReturn
            )
        }
    }

    fun updateDataEditRouteSpeedometerDeparture(speedometerDeparture: String) {
        uiStateEditRoute.value =
            uiStateEditRoute.value.copy(speedometerDeparture = speedometerDeparture)
        runBlocking {
            editRouteDb.updateOneElementForIdSpeedometerReadingDeparture(
                id = uiStateEditRoute.value.id,
                speedometerReadingDeparture = uiStateEditRoute.value.speedometerDeparture
            )
        }
    }

    fun updateDataEditRouteSpeedometerReturn(speedometerReturn: String) {
        uiStateEditRoute.value =
            uiStateEditRoute.value.copy(speedometerReturn = speedometerReturn)
        runBlocking {
            editRouteDb.updateOneElementForIdSpeedometerReadingReturn(
                id = uiStateEditRoute.value.id,
                speedometerReadingReturn = uiStateEditRoute.value.speedometerReturn
            )
        }
    }

    fun updateDataEditRouteFuelled(fuelled: String) {
        uiStateEditRoute.value = uiStateEditRoute.value.copy(fuelled = fuelled)
        runBlocking {
            editRouteDb.updateOneElementForIdFuelled(
                id = uiStateEditRoute.value.id,
                fuelled = uiStateEditRoute.value.fuelled
            )
        }
    }

    fun isValidateDataEditRoute(): Boolean {
        return uiStateEditRoute.value.dateDeparture != "" &&
                uiStateEditRoute.value.dateReturn != "" &&
                uiStateEditRoute.value.dateCrossingDeparture != "" &&
                uiStateEditRoute.value.dateCrossingReturn != "" &&
                uiStateEditRoute.value.speedometerDeparture != "" &&
                uiStateEditRoute.value.speedometerReturn != "" &&
                uiStateEditRoute.value.fuelled != "" &&
                uiStateEditRoute.value.route.any { element ->
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
                if(text.isBlank()) {
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
        if(_isSearchingCountry.value) {
            onSearchTextChangeCountry("")
        }
    }

    override fun loadCountries() = runBlocking {
        uiStateCountry.value.listCountries = SnapshotStateList()

        if(Locale.getDefault().language == "ru") {
            val a = if (isCheckedFavoriteCountry.value) {
                if(sortCountry.intValue == 0) {
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
                if(sortCountry.intValue == 0) {
                    countryDb.getFavoriteSortNameEng()
                } else {
                    countryDb.getFavoriteSortRatingEng()
                }
            } else {
                if(sortCountry.intValue == 0) {
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
    override val searchTextTownship = _searchTextTownship.asStateFlow()
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
        if(_isSearchingTownship.value) {
            onSearchTextChangeTownship("")
        }
    }

    override fun loadTownships(countryId: Int) = runBlocking {
        uiStateTownship.value.listTownships = SnapshotStateList()
        if(Locale.getDefault().language == "ru") {
            val a = if(isCheckedFavoriteTownship.value) {
                if(countryId != 0) {
                    if(sortTownship.intValue == 0) {
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
            val a = if(isCheckedFavoriteTownship.value) {
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

    fun updateNameCityAddCityEditRoute(name: String) {
        uiStateAddCity.value = uiStateAddCity.value.copy(nameCity = name)
    }

    fun updateNameCountryAddCityEditRoute(element: CountryDetailing) {
        uiStateAddCity.value =
            uiStateAddCity.value.copy(nameCountry = element.country, country = element)
    }

    fun openAddCity() {
        loadCountries()
        uiStateAddCity.value = uiStateAddCity.value.copy(
            nameCity = searchTextCountry.value,
            country = countriesListCountry.value[getPositionInCountry(selectedCountryIdInSearch.intValue)]
        )
        openBottomAddCityEditRoute.value = true
        openBottomSheetCountryEditRoute.value = false
    }

    fun closeAddCity() {
        openBottomSheetCountryEditRoute.value = true
        openBottomAddCityEditRoute.value = false
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
        uiStateAddCity = mutableStateOf(AddCity())
        loadTownships(selectedCountryIdInSearch.intValue)
        closeAddCity()
    }

    fun closeBottomSheetSearch() {
        openBottomSheetCountryEditRoute.value = false
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
        return if (uiStateEditRoute.value.speedometerDeparture.isNotEmpty() &&
            uiStateEditRoute.value.speedometerReturn.isNotEmpty()
            ) {
            uiStateEditRoute.value.speedometerDeparture.toInt() >=
                    uiStateEditRoute.value.speedometerReturn.toInt()
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
        return if(route.isEmpty()) {
            SnapshotStateList()
        } else {
            val a = SnapshotStateList<RouteElement>()
            val b = route.split(", ")
            var id = 1
            a.addAll(b.map {
                RouteElement(id++, it)
            })
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