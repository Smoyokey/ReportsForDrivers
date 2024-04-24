package com.example.reportsfordrivers.viewmodel.createreports

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reportsfordrivers.data.dao.CountryDao
import com.example.reportsfordrivers.data.dao.TownshipDao
import com.example.reportsfordrivers.data.dao.createReport.CreateProgressReportsDao
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CountriesProgressReports
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CountryDetailingProgressReports
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateProgressReportsDetailingUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateProgressReportsUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.SelectedNavigationUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.TownshipDetailingProgressReports
import com.example.reportsfordrivers.viewmodel.createreports.uistate.TownshipsProgressReports
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
class CreateProgressReportsViewModel @Inject constructor(
    private val fioPreferencesRepository: FioFirstEntryRepository
) : ViewModel() {

    @Inject
    lateinit var createProgressReportsDb: CreateProgressReportsDao

    @Inject
    lateinit var townshipDb: TownshipDao

    @Inject
    lateinit var countryDb: CountryDao

    var uiStateCreateProgressReports = mutableStateOf(CreateProgressReportsUiState())
        private set

    var uiStateCreateProgressReportsDetailing =
        mutableStateOf(CreateProgressReportsDetailingUiState())
        private set

    var openBottomSheetCountryCreateProgressReports = mutableStateOf(false)
    var openBottomSheetTownshipCreateProgressReports = mutableStateOf(false)
    var openDialogDateCreateProgressReports = mutableStateOf(false)
    var openDialogDeleteCreateProgressReports = mutableStateOf(false)

    fun startLoadCreateProgressReports() = runBlocking {
        val createProgressReports = createProgressReportsDb.getAllItem().first()
        for(i in createProgressReports) {
            uiStateCreateProgressReports.value.createProgressReportsList.add(
                CreateProgressReportsDetailingUiState(
                    country = i.country,
                    townshipOne = i.townshipOne,
                    townshipTwo = i.townshipTwo,
                    distance = i.distance,
                    cargoWeight = i.weight,
                    date = i.date
                )
            )
        }
    }

    fun updateProgressReportsDate(date: String) {
        val parseDate = if (date.isNotEmpty()) parseDate(date) else date
        uiStateCreateProgressReportsDetailing.value =
            uiStateCreateProgressReportsDetailing.value.copy(
                date = parseDate
            )
    }

    fun updateProgressReportsCountry(country: String) {
        uiStateCreateProgressReportsDetailing.value =
            uiStateCreateProgressReportsDetailing.value.copy(
                country = country
            )
    }

    fun updateProgressReportsTownshipOne(townshipOne: String) {
        uiStateCreateProgressReportsDetailing.value =
            uiStateCreateProgressReportsDetailing.value.copy(
                townshipOne = townshipOne
            )
    }

    fun updateProgressReportsTownshipTwo(townshipTwo: String) {
        uiStateCreateProgressReportsDetailing.value =
            uiStateCreateProgressReportsDetailing.value.copy(
                townshipTwo = townshipTwo
            )
    }

    fun updateProgressReportsDistance(distance: String) {
        uiStateCreateProgressReportsDetailing.value =
            uiStateCreateProgressReportsDetailing.value.copy(
                distance = distance
            )
    }

    fun updateProgressReportsCargoWeight(cargoWeight: String) {
        uiStateCreateProgressReportsDetailing.value =
            uiStateCreateProgressReportsDetailing.value.copy(
                cargoWeight = cargoWeight
            )
    }

    fun isValidateAddProgressReports(): Boolean {
        return uiStateCreateProgressReportsDetailing.value.date != "" &&
                uiStateCreateProgressReportsDetailing.value.country != "" &&
                uiStateCreateProgressReportsDetailing.value.townshipOne != "" &&
                uiStateCreateProgressReportsDetailing.value.townshipTwo != "" &&
                uiStateCreateProgressReportsDetailing.value.distance != "" &&
                uiStateCreateProgressReportsDetailing.value.cargoWeight != ""
    }

    fun addListProgressReports() {
        uiStateCreateProgressReports.value.createProgressReportsList.add(
            uiStateCreateProgressReportsDetailing.value
        )
        uiStateCreateProgressReportsDetailing.value = CreateProgressReportsDetailingUiState()
    }

    fun isValidateNextProgressReports(): Boolean {
        return uiStateCreateProgressReports.value.createProgressReportsList.size > 0
    }

    fun deletePositionProgressReports(position: Int) {
        uiStateCreateProgressReports.value.createProgressReportsList.removeAt(position)
    }

    //SearchCountry ProgressReports
    val uiStateCountryCreateProgressReports = mutableStateOf(CountriesProgressReports())
    val isCheckedFavoriteCountryCreateProgressReports = mutableStateOf(false)
    val sortCountryCreateProgressReports = mutableIntStateOf(0) //0 - Алфавит, 1 - Популярность

    private val _isSearchingCountryCreateProgressReports = MutableStateFlow(false)
    private val _searchTextCountryCreateProgressReports = MutableStateFlow("")
    val searchTextCountryCreateProgressReports = _searchTextCountryCreateProgressReports.asStateFlow()
    private var _countriesListCountryCreateProgressReports = MutableStateFlow(
        uiStateCountryCreateProgressReports.value.listCountries
    )
    var countriesListCountryCreateProgressReports = countriesFilterCreateProgressReports()

    private fun countriesFilterCreateProgressReports(): StateFlow<List<CountryDetailingProgressReports>> {
        return searchTextCountryCreateProgressReports
            .combine(_countriesListCountryCreateProgressReports) { text, countries ->
                if(text.isBlank()) {
                    countries
                }
                countries.filter { country ->
                    country.country.uppercase().contains(text.trim().uppercase())
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(2000),
                initialValue = _countriesListCountryCreateProgressReports.value
            )
    }

    fun onSearchTextChangeCountryCreateProgressReports(text: String) {
        _searchTextCountryCreateProgressReports.value = text
    }

    fun onToogleSearchCountryCreateProgressReports() {
        _isSearchingCountryCreateProgressReports.value = !_isSearchingCountryCreateProgressReports.value
        if(_isSearchingCountryCreateProgressReports.value) {
            onSearchTextChangeCountryCreateProgressReports("")
        }
    }

    fun loadCountriesCreateProgressReports() = runBlocking {
        uiStateCountryCreateProgressReports.value.listCountries = SnapshotStateList()

        if(Locale.getDefault().language == "ru") {
            val a = if(isCheckedFavoriteCountryCreateProgressReports.value) {
                if(sortCountryCreateProgressReports.intValue == 0) {
                    countryDb.getFavoriteSortNameRus()
                } else {
                    countryDb.getFavoriteSortRatingRus()
                }
            } else {
                if(sortCountryCreateProgressReports.intValue == 0) {
                    countryDb.getSortNameRus()
                } else {
                    countryDb.getSortRatingRus()
                }
            }

            a.forEach {
                uiStateCountryCreateProgressReports.value.listCountries.add(
                    CountryDetailingProgressReports(
                        id = it.id,
                        country = it.fullNameCountryRus,
                        shortCountry = it.shortNameCountry,
                        rating = it.rating,
                        favorite = it.favorite
                    )
                )
            }
        } else {
            val a = if(isCheckedFavoriteCountryCreateProgressReports.value) {
                if(sortCountryCreateProgressReports.intValue == 0) {
                    countryDb.getFavoriteSortNameEng()
                } else {
                    countryDb.getFavoriteSortRatingEng()
                }
            } else {
                if(sortCountryCreateProgressReports.intValue == 0) {
                    countryDb.getSortNameEng()
                } else {
                    countryDb.getSortRatingEng()
                }
            }

            a.forEach {
                uiStateCountryCreateProgressReports.value.listCountries.add(
                    CountryDetailingProgressReports(
                        id = it.id,
                        country = it.fullNameCountryEng,
                        shortCountry = it.shortNameCountry,
                        rating = it.rating,
                        favorite = it.favorite
                    )
                )
            }
            _countriesListCountryCreateProgressReports = MutableStateFlow(
                uiStateCountryCreateProgressReports.value.listCountries
            )
            countriesListCountryCreateProgressReports = countriesFilterCreateProgressReports()
        }
    }

    //Search Township ProgressReports
    val uiStateTownshipsCreateProgressReports = mutableStateOf(TownshipsProgressReports())
    val isCheckedFavoriteTownshipCreateProgressReports = mutableStateOf(false)
    val sortTownshipCreateProgressReports = mutableIntStateOf(0) //0 - Алфавит, 1 - Популярность

    private val _isSearchingTownshipCreateProgressReports = MutableStateFlow(false)
    private val _searchTextTownshipCreateProgressReports = MutableStateFlow("")
    val searchTextTownshipCreateProgressReports = _searchTextCountryCreateProgressReports.asStateFlow()
    private var _townshipsListTownshipCreateProgressReports = MutableStateFlow(
        uiStateTownshipsCreateProgressReports.value.listTownships
    )
    var townshipsListTownshipCreateProgressReports = townshipsFilterCreateProgressReports()

    private fun townshipsFilterCreateProgressReports(): StateFlow<List<TownshipDetailingProgressReports>> {
        return searchTextTownshipCreateProgressReports
            .combine(_townshipsListTownshipCreateProgressReports) { text, townships ->
                if(text.isBlank()) {
                    townships
                }
                townships.filter { township ->
                    township.township.uppercase().contains(text.trim().uppercase())
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(2000),
                initialValue = _townshipsListTownshipCreateProgressReports.value
            )
    }

    fun onSearchTextChangeTownshipCreateProgressReports(text: String) {
        _searchTextTownshipCreateProgressReports.value = text
    }

    fun onToogleSearchTownshipCreateProgressReports() {
        _isSearchingTownshipCreateProgressReports.value = !_isSearchingTownshipCreateProgressReports.value
        if(_isSearchingTownshipCreateProgressReports.value) {
            onSearchTextChangeTownshipCreateProgressReports("")
        }
    }

    fun loadTownshipsCreateProgressReports(countryId: Int) = runBlocking {
        uiStateTownshipsCreateProgressReports.value.listTownships = SnapshotStateList()

        if(Locale.getDefault().language == "ru") {
            val a = if(isCheckedFavoriteTownshipCreateProgressReports.value) {
                if(countryId != 0) {
                    if(sortTownshipCreateProgressReports.intValue == 0) {
                        townshipDb.getCountryIdFavoriteSortNameRus(countryId)
                    } else {
                        townshipDb.getCountryIdFavoriteSortRatingRus(countryId)
                    }
                } else {
                    if(sortTownshipCreateProgressReports.intValue == 0) {
                        townshipDb.getFavoriteSortNameRus()
                    } else {
                        townshipDb.getFavoriteSortRatingRus()
                    }
                }
            } else {
                if(countryId != 0) {
                    if(sortTownshipCreateProgressReports.intValue == 0) {
                        townshipDb.getCountryIdTownshipSortNameRus(countryId)
                    } else {
                        townshipDb.getCountryIdTownshipSortRatingRus(countryId)
                    }
                } else {
                    if(sortTownshipCreateProgressReports.intValue == 0) {
                        townshipDb.getSortNameRus()
                    } else {
                        townshipDb.getSortRatingRus()
                    }
                }
            }

            a.forEach {
                uiStateTownshipsCreateProgressReports.value.listTownships.add(
                    TownshipDetailingProgressReports(
                        id = it.id,
                        township = it.townshipRus,
                        countryId = it.countryId,
                        rating = it.rating,
                        favorite = it.favorite
                    )
                )
            }
        } else {
            val a = if(isCheckedFavoriteTownshipCreateProgressReports.value) {
                if(countryId != 0) {
                    if(sortTownshipCreateProgressReports.intValue == 0) {
                        townshipDb.getCountryIdFavoriteSortNameEng(countryId)
                    } else {
                        townshipDb.getCountryIdFavoriteSortRatingEng(countryId)
                    }
                } else {
                    if(sortTownshipCreateProgressReports.intValue == 0) {
                        townshipDb.getFavoriteSortNameEng()
                    } else {
                        townshipDb.getFavoriteSortRatingEng()
                    }
                }
            } else {
                if(countryId != 0) {
                    if(sortTownshipCreateProgressReports.intValue == 0) {
                        townshipDb.getCountryIdTownshipSortNameEng(countryId)
                    } else {
                        townshipDb.getCountryIdTownshipSortRatingEng(countryId)
                    }
                } else {
                    if(sortTownshipCreateProgressReports.intValue == 0) {
                        townshipDb.getSortNameEng()
                    } else {
                        townshipDb.getSortRatingEng()
                    }
                }
            }

            a.forEach {
                uiStateTownshipsCreateProgressReports.value.listTownships.add(
                    TownshipDetailingProgressReports(
                        id = it.id,
                        township = it.townshipEng,
                        countryId = it.countryId,
                        rating = it.rating,
                        favorite = it.favorite
                    )
                )
            }
        }
        _townshipsListTownshipCreateProgressReports = MutableStateFlow(
            uiStateTownshipsCreateProgressReports.value.listTownships
        )
        townshipsListTownshipCreateProgressReports = townshipsFilterCreateProgressReports()
    }

    @SuppressLint("SimpleDateFormat")
    private fun parseDate(date: String): String {
        val format = SimpleDateFormat("dd.MM.yyyy")
        return format.format(Date(date.toLong()))
    }
}