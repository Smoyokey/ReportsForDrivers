package com.example.reportsfordrivers.viewmodel.historyreports.editreport.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reportsfordrivers.data.dao.CountryDao
import com.example.reportsfordrivers.data.dao.TownshipDao
import com.example.reportsfordrivers.data.dao.editreport.EditExpensesTripDao
import com.example.reportsfordrivers.data.dao.editreport.EditPersonalInfoDao
import com.example.reportsfordrivers.data.dao.editreport.EditProgressReportsDao
import com.example.reportsfordrivers.data.dao.editreport.EditReportInfoDao
import com.example.reportsfordrivers.data.dao.editreport.EditRouteDao
import com.example.reportsfordrivers.data.dao.editreport.EditVehicleTrailerDao
import com.example.reportsfordrivers.data.structure.Township
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.interfaces.search.SearchCountry
import com.example.reportsfordrivers.interfaces.search.SearchTownship
import com.example.reportsfordrivers.viewmodel.firstentry.AddCity
import com.example.reportsfordrivers.viewmodel.firstentry.Countries
import com.example.reportsfordrivers.viewmodel.firstentry.CountryDetailing
import com.example.reportsfordrivers.viewmodel.firstentry.TownshipDetailing
import com.example.reportsfordrivers.viewmodel.firstentry.Townships
import com.example.reportsfordrivers.viewmodel.functions.EditNewReport
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditReportInfoUiState
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

private const val TAG = "EditReportInfoViewModel"

@HiltViewModel
class EditReportInfoViewModel @Inject constructor(
    private val fioPreferencesRepository: FioFirstEntryRepository
) : ViewModel(), SearchCountry, SearchTownship {

    @Inject
    lateinit var editReportInfoDb: EditReportInfoDao

    @Inject
    lateinit var editPersonalInfoDb: EditPersonalInfoDao

    @Inject
    lateinit var editVehicleTrailerDb: EditVehicleTrailerDao

    @Inject
    lateinit var editRouteDb: EditRouteDao

    @Inject
    lateinit var editProgressReportsDb: EditProgressReportsDao

    @Inject
    lateinit var editExpensesTripDb: EditExpensesTripDao

    @Inject
    lateinit var countryDb: CountryDao

    @Inject
    lateinit var townshipDb: TownshipDao

    var uiStateEditReportInfo = mutableStateOf(EditReportInfoUiState())

    var openDialogDateEditReportInfo = mutableStateOf(false)
    var openBottomSearchEditReportInfo = mutableStateOf(false)
    var openListSearchEditReportInfo = mutableIntStateOf(0) //0 - Country, 1 - Township

    var openBottomAddCityEditReportInfo = mutableStateOf(false)

    var firstOpenReport = mutableStateOf(false)

    var selectedCountryIdInSearch = mutableIntStateOf(0)
    var selectedCountryNameInSearch = mutableStateOf("")

    fun startLoadEditReportInfo() = runBlocking {
        val editReportInfo = editReportInfoDb.getAllItem().first()
        uiStateEditReportInfo.value = uiStateEditReportInfo.value.copy(
            id = editReportInfo[0].id,
            date = editReportInfo[0].date.ifEmpty { "" },
            waybill = editReportInfo[0].waybill.ifEmpty { "" },
            mainCity = editReportInfo[0].mainCity.ifEmpty { "" }
        )
        loadCountries()
    }

    fun startEditReport() = runBlocking {
        EditNewReport.deleteAllElements(
            editReportInfoDb,
            editPersonalInfoDb,
            editVehicleTrailerDb,
            editRouteDb,
            editProgressReportsDb,
            editExpensesTripDb
        )
        uiStateEditReportInfo.value = uiStateEditReportInfo.value.copy(
            date = "",
            mainCity = "",
            waybill = ""
        )
        EditNewReport.editNewReport(
            editReportInfoDb,
            editPersonalInfoDb,
            editVehicleTrailerDb,
            editRouteDb,
            editProgressReportsDb,
            editExpensesTripDb
        )
        fioPreferencesRepository.setEditSelectedPage(1)
//        fioPreferencesRepository.setS
    }

    fun updateDataEditReportInfoDate(date: String) {
        val parseDate = if (date.isNotEmpty()) parseDate(date) else date
        uiStateEditReportInfo.value = uiStateEditReportInfo.value.copy(date = parseDate)
        runBlocking {
            editReportInfoDb.updateOneElementForIdDate(
                id = uiStateEditReportInfo.value.id,
                date = uiStateEditReportInfo.value.date
            )
        }
    }

    fun updateDataEditReportInfoMainCity(mainCity: String, id: Int = 0) {
        uiStateEditReportInfo.value = uiStateEditReportInfo.value.copy(mainCity = mainCity)
        runBlocking {
            editReportInfoDb.updateOneElementForIdMainCity(
                id = uiStateEditReportInfo.value.id,
                mainCity = uiStateEditReportInfo.value.mainCity
            )
        }
    }

    fun updateDataEditReportInfoWaybill(waybill: String) {
        uiStateEditReportInfo.value = uiStateEditReportInfo.value.copy(waybill = waybill)
        runBlocking {
            editReportInfoDb.updateOneElementForIdWaybill(
                id = uiStateEditReportInfo.value.id,
                waybill = uiStateEditReportInfo.value.waybill
            )
        }
    }

    fun isValidateDateEditReportInfo(): Boolean {
        return uiStateEditReportInfo.value.date != "" &&
                uiStateEditReportInfo.value.mainCity != "" &&
                uiStateEditReportInfo.value.waybill != ""
    }

    override val uiStateCountry = mutableStateOf(Countries())
    override val isCheckedFavoriteCountry = mutableStateOf(false)
    override val sortCountry = mutableIntStateOf(0) // 0 - Алфавит, 1 - Популярность

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

    override fun loadCountries(): Unit = runBlocking {
        uiStateCountry.value.listCountries = SnapshotStateList()
        if(Locale.getDefault().language == "ru") {
            val a = if (isCheckedFavoriteCountry.value) {
                if(sortCountry.intValue == 0) {
                    countryDb.getFavoriteSortNameRus()
                } else {
                    countryDb.getFavoriteSortRatingRus()
                }
            } else {
                if(sortCountry.intValue == 0) {
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
            val a = if(isCheckedFavoriteCountry.value) {
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
                if(text.isBlank()) {
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
                    if(sortTownship.intValue == 0) {
                        townshipDb.getFavoriteSortNameRus()
                    } else {
                        townshipDb.getFavoriteSortRatingRus()
                    }
                }
            } else {
                if (countryId != 0) {
                    if(sortTownship.intValue == 0) {
                        townshipDb.getCountryIdTownshipSortNameRus(countryId)
                    } else {
                        townshipDb.getCountryIdTownshipSortRatingRus(countryId)
                    }
                } else {
                    if(sortTownship.intValue == 0) {
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
                if(countryId != 0) {
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
                    if(sortTownship.intValue == 0) {
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

    fun closeBottomSheetSearch() {
        isCheckedFavoriteCountry.value = false
        isCheckedFavoriteTownship.value = false
        openListSearchEditReportInfo.intValue = 0
        sortCountry.intValue = 0
        sortTownship.intValue = 0
        selectedCountryIdInSearch.intValue = 0
        selectedCountryNameInSearch.value = ""
        openBottomSearchEditReportInfo.value = false
    }

    var uiStateAddCity = mutableStateOf(AddCity())

    fun updateNameCityAddCityEditReportInfo(name: String) {
        uiStateAddCity.value = uiStateAddCity.value.copy(nameCity = name)
    }

    fun updateNameCountryAddCityEditReportInfo(element: CountryDetailing) {
        uiStateAddCity.value =
            uiStateAddCity.value.copy(nameCountry = element.country, country = element)
    }

    fun openAddCity() {
        loadCountries()
        uiStateAddCity.value = uiStateAddCity.value.copy(
            nameCity = searchTextTownship.value,
            country = countriesListCountry.value[getPositionInCountry(
                selectedCountryIdInSearch.intValue
            )]
        )
        openBottomAddCityEditReportInfo.value = true
        openBottomSearchEditReportInfo.value = false
    }

    fun closeAddCity() {
        openBottomSearchEditReportInfo.value = true
        openBottomAddCityEditReportInfo.value = false
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

    fun openListSearch() {
        openListSearchEditReportInfo.intValue = 0
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