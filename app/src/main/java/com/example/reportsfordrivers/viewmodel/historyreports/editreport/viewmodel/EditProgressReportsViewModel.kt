package com.example.reportsfordrivers.viewmodel.historyreports.editreport.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reportsfordrivers.data.dao.CountryDao
import com.example.reportsfordrivers.data.dao.TownshipDao
import com.example.reportsfordrivers.data.dao.editreport.EditProgressReportsDao
import com.example.reportsfordrivers.data.structure.Township
import com.example.reportsfordrivers.data.structure.editreport.EditProgressReports
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.interfaces.search.SearchCountry
import com.example.reportsfordrivers.interfaces.search.SearchTownship
import com.example.reportsfordrivers.viewmodel.firstentry.AddCity
import com.example.reportsfordrivers.viewmodel.firstentry.Countries
import com.example.reportsfordrivers.viewmodel.firstentry.CountryDetailing
import com.example.reportsfordrivers.viewmodel.firstentry.TownshipDetailing
import com.example.reportsfordrivers.viewmodel.firstentry.Townships
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditProgressReportsDetailingUiState
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditProgressReportsUiState
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
class EditProgressReportsViewModel @Inject constructor(
    private val fioPreferencesRepository: FioFirstEntryRepository
) : ViewModel(), SearchCountry, SearchTownship {

    @Inject
    lateinit var editProgressReportsDb: EditProgressReportsDao

    @Inject
    lateinit var townshipDb: TownshipDao

    @Inject
    lateinit var countryDb: CountryDao

    var uiStateEditProgressReports = mutableStateOf(EditProgressReportsUiState())
        private set

    var uiStateEditProgressReportsDetailing =
        mutableStateOf(EditProgressReportsDetailingUiState())
        private set

    var openBottomSheetTownshipEditProgressReports = mutableStateOf(false)
    var openBottomSheetCountryEditProgressReports = mutableStateOf(false)
    var openListSearchEditProgressReports = mutableIntStateOf(0)
    var openSearchBottomSheet = mutableIntStateOf(0)
    var openSearchCountryAndTownship = mutableIntStateOf(0)
    var openDialogDateEditProgressReports = mutableStateOf(false)
    var openDialogDeleteEditProgressReports = mutableStateOf(false)

    var openBottomSheetAddCityEditProgressReports = mutableStateOf(false)

    var firstOpenReportProgressReports = mutableStateOf(false)

    var selectedCountryIdInSearch = mutableIntStateOf(0)
    var selectedCountryNameInSearch = mutableStateOf("")

    fun startLoadEditProgressReports() = runBlocking {
        val editProgressReports = editProgressReportsDb.getAllItem().first()
        for(i in editProgressReports) {
            if(i.isAdd == 1) {
                uiStateEditProgressReports.value.editProgressReportsList.add(
                    EditProgressReportsDetailingUiState(
                        id = i.id,
                        country = i.country,
                        townshipOne = i.townshipOne,
                        townshipTwo = i.townshipTwo,
                        distance = i.distance,
                        cargoWeight = i.weight,
                        date = i.date,
                        isAdd = i.isAdd
                    )
                )
            } else {
                uiStateEditProgressReportsDetailing.value =
                    uiStateEditProgressReportsDetailing.value.copy(
                        id = i.id,
                        country = i.country,
                        townshipOne = i.townshipOne,
                        townshipTwo = i.townshipTwo,
                        distance = i.distance,
                        cargoWeight = i.weight,
                        date = i.date,
                        isAdd = i.isAdd
                    )
            }
        }
        fioPreferencesRepository.setEditSelectedPage(5)
//        loadCountries()
        firstOpenReportProgressReports.value = true
    }

    fun updateEditProgressReportsDate(date: String) {
        val parseDate = if (date.isNotEmpty()) parseDate(date) else date
        uiStateEditProgressReportsDetailing.value =
            uiStateEditProgressReportsDetailing.value.copy(
                date = parseDate
            )
        runBlocking {
            editProgressReportsDb.updateOneElementForIdDate(
                id = uiStateEditProgressReportsDetailing.value.id,
                date = uiStateEditProgressReportsDetailing.value.date
            )
        }
    }

    fun updateEditProgressReportsCountry(country: String) {
        uiStateEditProgressReportsDetailing.value =
            uiStateEditProgressReportsDetailing.value.copy(
                country = country
            )
        runBlocking {
            editProgressReportsDb.updateOneElementForIdCountry(
                id = uiStateEditProgressReportsDetailing.value.id,
                country = uiStateEditProgressReportsDetailing.value.country
            )
        }
    }

    fun updateEditProgressReportsTownshipOne(townshipOne: String, id: Int = 0) {
        uiStateEditProgressReportsDetailing.value =
            uiStateEditProgressReportsDetailing.value.copy(
                townshipOne = townshipOne
            )
        runBlocking {
            editProgressReportsDb.updateOneElementForIdTownshipOne(
                id = uiStateEditProgressReportsDetailing.value.id,
                townshipOne = uiStateEditProgressReportsDetailing.value.townshipOne
            )
        }
    }

    fun updateEditProgressReportsTownshipTwo(townshipTwo: String, id: Int = 0) {
        uiStateEditProgressReportsDetailing.value =
            uiStateEditProgressReportsDetailing.value.copy(
                townshipTwo = townshipTwo
            )
        runBlocking {
            editProgressReportsDb.updateOneElementForIdTownshipTwo(
                id = uiStateEditProgressReportsDetailing.value.id,
                townshipTwo = uiStateEditProgressReportsDetailing.value.townshipTwo
            )
        }
    }

    fun updateEditProgressReportsDistance(distance: String) {
        uiStateEditProgressReportsDetailing.value =
            uiStateEditProgressReportsDetailing.value.copy(
                distance = distance
            )
        runBlocking {
            editProgressReportsDb.updateOneElementForIdDistance(
                id = uiStateEditProgressReportsDetailing.value.id,
                distance = uiStateEditProgressReportsDetailing.value.distance
            )
        }
    }

    fun updateEditProgressReportsCargoWeight(cargoWeight: String) {
        uiStateEditProgressReportsDetailing.value =
            uiStateEditProgressReportsDetailing.value.copy(
                cargoWeight = cargoWeight
            )
        runBlocking {
            editProgressReportsDb.updateOneElementForIdWeight(
                id = uiStateEditProgressReportsDetailing.value.id,
                weight = uiStateEditProgressReportsDetailing.value.cargoWeight
            )
        }
    }

    fun isValidateAddEditProgressReports(): Boolean {
        return uiStateEditProgressReportsDetailing.value.date != "" &&
                uiStateEditProgressReportsDetailing.value.country != "" &&
                uiStateEditProgressReportsDetailing.value.townshipOne != "" &&
                uiStateEditProgressReportsDetailing.value.townshipTwo != "" &&
                uiStateEditProgressReportsDetailing.value.distance != "" &&
                uiStateEditProgressReportsDetailing.value.cargoWeight != ""
    }

    fun addListEditProgressReports() {
        uiStateEditProgressReportsDetailing.value =
            uiStateEditProgressReportsDetailing.value.copy(isAdd = 1)
        runBlocking {
            editProgressReportsDb.updateOneElementForIdIsAdd(
                id = uiStateEditProgressReportsDetailing.value.id,
                isAdd = 1
            )
        }
        uiStateEditProgressReports.value.editProgressReportsList.add(
            uiStateEditProgressReportsDetailing.value
        )
        val id = uiStateEditProgressReportsDetailing.value.id + 1
        uiStateEditProgressReportsDetailing.value = EditProgressReportsDetailingUiState()
        runBlocking {
            editProgressReportsDb.insert(
                EditProgressReports(
                    date = "",
                    country = "",
                    townshipOne = "",
                    townshipTwo = "",
                    distance = "",
                    weight = "",
                    isAdd = 0
                )
            )
        }
        uiStateEditProgressReportsDetailing.value = uiStateEditProgressReportsDetailing.value.copy(
            id = id
        )
    }

    fun deletePositionEditProgressReports(position: Int) = runBlocking {
        editProgressReportsDb.deleteOneElementForId(
            uiStateEditProgressReports.value.editProgressReportsList[position].id
        )
        uiStateEditProgressReports.value.editProgressReportsList.removeAt(position)
    }

    //SearchCountry EditProgressReports
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
        if (_isSearchingCountry.value) onSearchTextChangeCountry("")
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
            val a = if(isCheckedFavoriteCountry.value) {
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

    //Search township EditProgressReports
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
                if(countryId != 0) {
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
                    if(sortTownship.intValue == 0) {
                        townshipDb.getCountryIdFavoriteSortNameEng(countryId)
                    } else {
                        townshipDb.getCountryIdFavoriteSortRatingEng(countryId)
                    }
                } else {
                    if(sortTownship.intValue == 0) {
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
                    if(sortTownship.intValue == 0) {
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

    fun updateNameCityAddCityEditProgressReports(name: String) {
        uiStateAddCity.value = uiStateAddCity.value.copy(nameCity = name)
    }

    fun updateNameCountryAddCityEditProgressReports(element: CountryDetailing) {
        uiStateAddCity.value =
            uiStateAddCity.value.copy(nameCountry = element.country, country = element)
    }

    fun openAddCity(openBottomSheet: MutableState<Boolean>) {
        loadCountries()
        uiStateAddCity.value = uiStateAddCity.value.copy(
            nameCity = searchTextTownship.value,
            country = countriesListCountry.value[getPositionInCountry(
                selectedCountryIdInSearch.intValue
            )]
        )
        openBottomSheetAddCityEditProgressReports.value = true
        openBottomSheet.value = false
    }

    fun closeAddCity(openBottomSheet: MutableState<Boolean>) {
        openBottomSheet.value = true
        openBottomSheetAddCityEditProgressReports.value = false
    }

    fun validateAddCity(): Boolean {
        return try {
            uiStateAddCity.value.nameCity.isNotEmpty() &&
                    uiStateAddCity.value.country.country.isNotEmpty()
        } catch (e: NullPointerException) {
            false
        }
    }

    fun saveAddCityInBd(openBottomSheet: MutableState<Boolean>) = runBlocking {
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
        closeAddCity(openBottomSheet)
    }

    fun closeBottomSheetSearch(openBottomSheet: MutableState<Boolean>) {
        isCheckedFavoriteCountry.value = false
        isCheckedFavoriteTownship.value = false
        openListSearchEditProgressReports.intValue = 0
        sortCountry.intValue = 0
        sortTownship.intValue = 0
        selectedCountryIdInSearch.intValue = 0
        selectedCountryNameInSearch.value = ""
        openBottomSheet.value = false
    }

    override fun updateRatingCountry(id: Int) = runBlocking {
        val a = countryDb.getOneItem(id).first()
        countryDb.updateRatingForId(a.id, a.rating + 1)
    }

    override fun updateRatingTownship(id: Int) = runBlocking {
        val a = townshipDb.getOneItem(id).first()
        townshipDb.updateRatingForId(a.id, a.rating + 1)
    }

    @SuppressLint("SimpleDateFormat")
    private fun parseDate(date: String): String {
        val format = SimpleDateFormat("dd.MM.yyyy")
        return format.format(Date(date.toLong()))
    }

    private fun getPositionInCountry(id: Int): Int {
        for (i in 0..<countriesListCountry.value.size) {
            if(id == countriesListCountry.value[i].id) return i
        }
        return 0
    }
}