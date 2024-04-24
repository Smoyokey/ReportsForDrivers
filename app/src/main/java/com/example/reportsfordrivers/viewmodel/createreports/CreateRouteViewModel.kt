package com.example.reportsfordrivers.viewmodel.createreports

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reportsfordrivers.data.dao.CountryDao
import com.example.reportsfordrivers.data.dao.TownshipDao
import com.example.reportsfordrivers.data.dao.createReport.CreateRouteDao
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CountriesRoute
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CountryDetailingRoute
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateRouteUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.RouteElement
import com.example.reportsfordrivers.viewmodel.createreports.uistate.SelectedNavigationUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.TownshipDetailingRoute
import com.example.reportsfordrivers.viewmodel.createreports.uistate.TownshipsRoute
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

@HiltViewModel
class CreateRouteViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var createRouteDb: CreateRouteDao

    @Inject
    lateinit var townshipDb: TownshipDao

    @Inject
    lateinit var countryDb: CountryDao

    var uiStateCreateRoute = mutableStateOf(CreateRouteUiState())
        private set
    var uiStateIsValidate = mutableStateOf(SelectedNavigationUiState())

    var openDialogDateReturnCreateRoute = mutableStateOf(false)
    var openDialogDateDepartureCreateRoute = mutableStateOf(false)
    var openDialogDateCrossingDepartureCreateRoute = mutableStateOf(false)
    var openDialogDateCrossingReturnCreateRoute = mutableStateOf(false)
    var openBottomSheetCountryCreateRoute = mutableStateOf(false)
    var openBottomSheetTownshipCreateRoute = mutableStateOf(false)

    fun startLoadCreateRoute() = runBlocking {
        val createRoute = createRouteDb.getAllItem().first()
        uiStateCreateRoute.value = uiStateCreateRoute.value.copy(
            route = SnapshotStateList(),
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
    }

    fun updateDataCreateRouteRoute(route: String, index: Int) {
        uiStateCreateRoute.value.route[index] = uiStateCreateRoute.value.route[index].copy(text = route)
    }

    fun updateDataCreateRouteDateReturn(dateReturn: String) {
        val parseDate = if(dateReturn.isNotEmpty()) parseDate(dateReturn) else dateReturn
        uiStateCreateRoute.value = uiStateCreateRoute.value.copy( dateReturn = parseDate )
    }

    fun updateDataCreateRouteDateDeparture(dateDeparture: String) {
        val parseDate = if (dateDeparture.isNotEmpty()) parseDate(dateDeparture) else dateDeparture
        uiStateCreateRoute.value = uiStateCreateRoute.value.copy( dateDeparture = parseDate )
    }

    fun updateDataCreateRouteDateCrossingDeparture(dateCrossingDeparture: String) {
        val parseDate = if (dateCrossingDeparture.isNotEmpty())
            parseDate(dateCrossingDeparture)
        else
            dateCrossingDeparture
        uiStateCreateRoute.value = uiStateCreateRoute.value.copy( dateCrossingDeparture = parseDate)
    }

    fun updateDataCreateRouteDateCrossingReturn(dateCrossingReturn: String) {
        val parseDate = if (dateCrossingReturn.isNotEmpty())
            parseDate(dateCrossingReturn)
        else
            dateCrossingReturn
        uiStateCreateRoute.value = uiStateCreateRoute.value.copy( dateCrossingReturn = parseDate )
    }

    fun updateDataCreateRouteSpeedometerDeparture(speedometerDeparture: String) {
        uiStateCreateRoute.value = uiStateCreateRoute.value.copy( speedometerDeparture = speedometerDeparture)
    }

    fun updateDataCreateRouteSpeedometerReturn(speedometerReturn: String) {
        uiStateCreateRoute.value = uiStateCreateRoute.value.copy( speedometerReturn = speedometerReturn )
    }

    fun updateDataCreateRouteFuelled(fuelled: String) {
        uiStateCreateRoute.value = uiStateCreateRoute.value.copy( fuelled = fuelled)
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

    val uiStateCountryCreateRoute = mutableStateOf(CountriesRoute())
    val isCheckedFavoriteCountryCreateRoute = mutableStateOf(false)
    val sortCountryCreateRoute = mutableIntStateOf(0) //0 - Алфавит, 1 - Популярность

    private val _isSearchingCountryCreateRoute = MutableStateFlow(false)
    private val _searchTextCountryCreateRoute = MutableStateFlow("")
    val searchTextCountryCreateRoute = _searchTextCountryCreateRoute.asStateFlow()
    private var _countriesListCountryCreateRoute = MutableStateFlow(uiStateCountryCreateRoute.value.listCountries)
    var countriesListCountryCreateRoute = countriesFilterCreateRoute()

    private fun countriesFilterCreateRoute(): StateFlow<List<CountryDetailingRoute>> {
        return searchTextCountryCreateRoute
            .combine(_countriesListCountryCreateRoute) { text, countries ->
                if(text.isBlank()) {
                    countries
                }
                countries.filter { country ->
                    country.country.uppercase().contains(text.trim().uppercase())
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(2000),
                initialValue = _countriesListCountryCreateRoute.value
            )
    }

    fun onSearchTextChangeCountryCreateRoute(text: String) {
        _searchTextCountryCreateRoute.value = text
    }

    fun onToogleSearchCountryCreateRoute() {
        _isSearchingCountryCreateRoute.value = !_isSearchingCountryCreateRoute.value
        if(_isSearchingCountryCreateRoute.value) {
            onSearchTextChangeCountryCreateRoute("")
        }
    }

    fun loadCountriesCreateRoute() = runBlocking {
        uiStateCountryCreateRoute.value.listCountries = SnapshotStateList()

        if(Locale.getDefault().language == "ru") {
            val a = if(isCheckedFavoriteCountryCreateRoute.value) {
                if(sortCountryCreateRoute.intValue == 0) {
                    countryDb.getFavoriteSortNameRus()
                } else {
                    countryDb.getFavoriteSortRatingRus()
                }
            } else {
                if(sortCountryCreateRoute.intValue == 0) {
                    countryDb.getSortNameRus()
                } else {
                    countryDb.getSortRatingRus()
                }
            }

            a.forEach {
                uiStateCountryCreateRoute.value.listCountries.add(
                    CountryDetailingRoute(
                        id = it.id,
                        country = it.fullNameCountryRus,
                        shortCountry = it.shortNameCountry,
                        rating = it.rating,
                        favorite = it.favorite
                    )
                )
            }
        } else {
            val a = if(isCheckedFavoriteCountryCreateRoute.value) {
                if(sortCountryCreateRoute.intValue == 0) {
                    countryDb.getFavoriteSortNameEng()
                } else {
                    countryDb.getFavoriteSortRatingEng()
                }
            } else {
                if(sortCountryCreateRoute.intValue == 0) {
                    countryDb.getSortNameEng()
                } else {
                    countryDb.getSortRatingEng()
                }
            }

            a.forEach {
                uiStateCountryCreateRoute.value.listCountries.add(
                    CountryDetailingRoute(
                        id = it.id,
                        country = it.fullNameCountryEng,
                        shortCountry = it.shortNameCountry,
                        rating = it.rating,
                        favorite = it.favorite
                    )
                )
            }
            _countriesListCountryCreateRoute = MutableStateFlow(uiStateCountryCreateRoute.value.listCountries)
            countriesListCountryCreateRoute = countriesFilterCreateRoute()
        }
    }

    val uiStateTownshipsCreateRoute = mutableStateOf(TownshipsRoute())
    val isCheckedFavoriteTownshipCreateRoute = mutableStateOf(false)
    val sortTownshipCreateRoute = mutableIntStateOf(0) //0 - Алфавит, 1 - Популярность

    private val _isSearchingTownshipCreateRoute = MutableStateFlow(false)
    private val _searchTextTownshipCreateRoute = MutableStateFlow("")
    val searchTextTownshipCreateRoute = _searchTextCountryCreateRoute.asStateFlow()
    private var _townshipsListTownshipCreateRoute = MutableStateFlow(uiStateTownshipsCreateRoute.value.listTownships)
    var townshipsListTownshipCreateRoute = townshipsFilterCreateRoute()

    private fun townshipsFilterCreateRoute(): StateFlow<List<TownshipDetailingRoute>> {
        return searchTextTownshipCreateRoute
            .combine(_townshipsListTownshipCreateRoute) { text, townships ->
                if(text.isBlank()) {
                    townships
                }
                townships.filter { township ->
                    township.township.uppercase().contains(text.trim().uppercase())
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(2000),
                initialValue = _townshipsListTownshipCreateRoute.value
            )
    }

    fun onSearchTextChangeTownshipCreateRoute(text: String) {
        _searchTextTownshipCreateRoute.value = text
    }

    fun onToogleSearchTownshipCreateRoute() {
        _isSearchingTownshipCreateRoute.value = !_isSearchingTownshipCreateRoute.value
        if(_isSearchingTownshipCreateRoute.value) {
            onSearchTextChangeTownshipCreateRoute("")
        }
    }

    fun loadTownshipsCreateRoute(countryId: Int) = runBlocking {
        uiStateTownshipsCreateRoute.value.listTownships = SnapshotStateList()
        if(Locale.getDefault().language == "ru") {
            val a = if(isCheckedFavoriteTownshipCreateRoute.value) {
                if(countryId != 0) {
                    if(sortTownshipCreateRoute.intValue == 0) {
                        townshipDb.getCountryIdFavoriteSortNameRus(countryId)
                    } else {
                        townshipDb.getCountryIdFavoriteSortRatingRus(countryId)
                    }
                } else {
                    if(sortTownshipCreateRoute.intValue == 0) {
                        townshipDb.getFavoriteSortNameRus()
                    } else {
                        townshipDb.getFavoriteSortRatingRus()
                    }
                }
            } else {
                if(countryId != 0) {
                    if(sortTownshipCreateRoute.intValue == 0) {
                        townshipDb.getCountryIdTownshipSortNameRus(countryId)
                    } else {
                        townshipDb.getCountryIdTownshipSortRatingRus(countryId)
                    }
                } else {
                    if(sortTownshipCreateRoute.intValue == 0) {
                        townshipDb.getSortNameRus()
                    } else {
                        townshipDb.getSortRatingRus()
                    }
                }
            }
            a.forEach {
                uiStateTownshipsCreateRoute.value.listTownships.add(
                    TownshipDetailingRoute(
                        id = it.id,
                        township = it.townshipRus,
                        countryId = it.countryId,
                        rating = it.rating,
                        favorite = it.favorite
                    )
                )
            }
        } else {
            val a = if(isCheckedFavoriteTownshipCreateRoute.value) {
                if(countryId != 0) {
                    if(sortTownshipCreateRoute.intValue == 0) {
                        townshipDb.getCountryIdFavoriteSortNameEng(countryId)
                    } else {
                        townshipDb.getCountryIdFavoriteSortRatingEng(countryId)
                    }
                } else {
                    if(sortTownshipCreateRoute.intValue == 0) {
                        townshipDb.getFavoriteSortNameEng()
                    } else {
                        townshipDb.getFavoriteSortRatingEng()
                    }
                }
            } else {
                if(countryId != 0) {
                    if(sortTownshipCreateRoute.intValue == 0) {
                        townshipDb.getCountryIdTownshipSortNameEng(countryId)
                    } else {
                        townshipDb.getCountryIdTownshipSortRatingEng(countryId)
                    }
                } else {
                    if(sortTownshipCreateRoute.intValue == 0) {
                        townshipDb.getSortNameEng()
                    } else {
                        townshipDb.getSortRatingEng()
                    }
                }
            }
            a.forEach {
                uiStateTownshipsCreateRoute.value.listTownships.add(
                    TownshipDetailingRoute(
                        id = it.id,
                        township = it.townshipEng,
                        countryId = it.countryId,
                        rating = it.rating,
                        favorite = it.favorite
                    )
                )
            }
        }

        _townshipsListTownshipCreateRoute = MutableStateFlow(uiStateTownshipsCreateRoute.value.listTownships)
        townshipsListTownshipCreateRoute = townshipsFilterCreateRoute()
    }

    @SuppressLint("SimpleDateFormat")
    private fun parseDate(date: String): String {
        val format = SimpleDateFormat("dd.MM.yyyy")
        return format.format(Date(date.toLong()))
    }
}