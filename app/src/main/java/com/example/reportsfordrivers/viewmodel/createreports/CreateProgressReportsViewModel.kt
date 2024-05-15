package com.example.reportsfordrivers.viewmodel.createreports

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reportsfordrivers.data.dao.CountryDao
import com.example.reportsfordrivers.data.dao.TownshipDao
import com.example.reportsfordrivers.data.dao.createReport.CreateProgressReportsDao
import com.example.reportsfordrivers.data.structure.Township
import com.example.reportsfordrivers.data.structure.createReport.CreateProgressReports
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.interfaces.search.SearchCountry
import com.example.reportsfordrivers.interfaces.search.SearchTownship
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateProgressReportsDetailingUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateProgressReportsUiState
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

@HiltViewModel
class CreateProgressReportsViewModel @Inject constructor(
    private val fioPreferencesRepository: FioFirstEntryRepository
) : ViewModel(), SearchCountry, SearchTownship {

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

    var uiStateIsValidate = mutableStateOf(SelectedNavigationUiState())

    var openBottomSheetTownshipCreateProgressReports = mutableStateOf(false)
    var openBottomSheetCountryCreateProgressReports = mutableStateOf(false)
    var openListSearchCreateProgressReports = mutableIntStateOf(0)
    var openSearchBottomSheet = mutableIntStateOf(0) //TownshipOne and TownshipTwo
    var openSearchCountryAndTownship = mutableIntStateOf(0)
    var openDialogDateCreateProgressReports = mutableStateOf(false)
    var openDialogDeleteCreateProgressReports = mutableStateOf(false)

    var openBottomSheetAddCityCreateProgressReports = mutableStateOf(false)

    var firstOpenReportProgressReports = mutableStateOf(false)

    var selectedCountryIdInSearch = mutableIntStateOf(0)
    var selectedCountryNameInSearch = mutableStateOf("")

    fun startLoadCreateProgressReports() = runBlocking {
        val createProgressReports = createProgressReportsDb.getAllItem().first()
        for (i in createProgressReports) {
            if (i.isAdd == 1) {
                uiStateCreateProgressReports.value.createProgressReportsList.add(
                    CreateProgressReportsDetailingUiState(
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
                uiStateCreateProgressReportsDetailing.value =
                    uiStateCreateProgressReportsDetailing.value.copy(
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
        fioPreferencesRepository.setCreateSelectedPage(5)
        loadCountries()
        firstOpenReportProgressReports.value = true
    }

    fun updateProgressReportsDate(date: String) {
        val parseDate = if (date.isNotEmpty()) parseDate(date) else date
        uiStateCreateProgressReportsDetailing.value =
            uiStateCreateProgressReportsDetailing.value.copy(
                date = parseDate
            )
        runBlocking {
            createProgressReportsDb.updateOneElementForIdDate(
                id = uiStateCreateProgressReportsDetailing.value.id,
                date = uiStateCreateProgressReportsDetailing.value.date
            )
        }
    }

    fun updateProgressReportsCountry(country: String) {
        uiStateCreateProgressReportsDetailing.value =
            uiStateCreateProgressReportsDetailing.value.copy(
                country = country
            )
        runBlocking {
            createProgressReportsDb.updateOneElementForIdCountry(
                id = uiStateCreateProgressReportsDetailing.value.id,
                country = uiStateCreateProgressReportsDetailing.value.country
            )
        }
    }

    fun updateProgressReportsTownshipOne(townshipOne: String, id: Int = 0) {
        uiStateCreateProgressReportsDetailing.value =
            uiStateCreateProgressReportsDetailing.value.copy(
                townshipOne = townshipOne
            )
        runBlocking {
            createProgressReportsDb.updateOneElementForIdTownshipOne(
                id = uiStateCreateProgressReportsDetailing.value.id,
                townshipOne = uiStateCreateProgressReportsDetailing.value.townshipOne
            )
        }
    }

    fun updateProgressReportsTownshipTwo(townshipTwo: String, id: Int = 0) {
        uiStateCreateProgressReportsDetailing.value =
            uiStateCreateProgressReportsDetailing.value.copy(
                townshipTwo = townshipTwo
            )
        runBlocking {
            createProgressReportsDb.updateOneElementForIdTownshipTwo(
                id = uiStateCreateProgressReportsDetailing.value.id,
                townshipTwo = uiStateCreateProgressReportsDetailing.value.townshipTwo
            )
        }
    }

    fun updateProgressReportsDistance(distance: String) {
        uiStateCreateProgressReportsDetailing.value =
            uiStateCreateProgressReportsDetailing.value.copy(
                distance = distance
            )
        runBlocking {
            createProgressReportsDb.updateOneElementForIdDistance(
                id = uiStateCreateProgressReportsDetailing.value.id,
                distance = uiStateCreateProgressReportsDetailing.value.distance
            )
        }
    }

    fun updateProgressReportsCargoWeight(cargoWeight: String) {
        uiStateCreateProgressReportsDetailing.value =
            uiStateCreateProgressReportsDetailing.value.copy(
                cargoWeight = cargoWeight
            )
        runBlocking {
            createProgressReportsDb.updateOneElementForIdWeight(
                id = uiStateCreateProgressReportsDetailing.value.id,
                weight = uiStateCreateProgressReportsDetailing.value.cargoWeight
            )
        }
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
        uiStateCreateProgressReportsDetailing.value =
            uiStateCreateProgressReportsDetailing.value.copy(isAdd = 1)
        runBlocking {
            createProgressReportsDb.updateOneElementForIdIsAdd(
                id = uiStateCreateProgressReportsDetailing.value.id,
                isAdd = 1
            )
        }
        uiStateCreateProgressReports.value.createProgressReportsList.add(
            uiStateCreateProgressReportsDetailing.value
        )
        val id = uiStateCreateProgressReportsDetailing.value.id + 1
        uiStateCreateProgressReportsDetailing.value = CreateProgressReportsDetailingUiState()
        runBlocking {
            createProgressReportsDb.insert(
                CreateProgressReports(
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
        uiStateCreateProgressReportsDetailing.value = uiStateCreateProgressReportsDetailing.value.copy(
            id = id
        )
    }

    fun isValidateNextProgressReports(): Boolean {
        return uiStateCreateProgressReports.value.createProgressReportsList.size > 0
    }

    fun deletePositionProgressReports(position: Int) = runBlocking {
        createProgressReportsDb.deleteOneElementForId(
            uiStateCreateProgressReports.value.createProgressReportsList[position].id
        )
        uiStateCreateProgressReports.value.createProgressReportsList.removeAt(position)
    }

    //SearchCountry ProgressReports
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
        if (_isSearchingCountry.value) onSearchTextChangeCountry("")
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
            _countriesListCountry = MutableStateFlow(uiStateCountry.value.listCountries)
            countriesListCountry = filterCountry()
        }
    }

    //Search Township ProgressReports
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

    fun updateNameCityAddCityCreateProgressReports(name: String) {
        uiStateAddCity.value = uiStateAddCity.value.copy(nameCity = name)
    }

    fun updateNameCountryAddCityCreateProgressReports(element: CountryDetailing) {
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
        openBottomSheetAddCityCreateProgressReports.value = true
        openBottomSheet.value = false
    }

    fun closeAddCity(openBottomSheet: MutableState<Boolean>) {
        openBottomSheet.value = true
        openBottomSheetAddCityCreateProgressReports.value = false
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
        openListSearchCreateProgressReports.intValue = 0
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
            if (id == countriesListCountry.value[i].id) return i
        }
        return 0
    }
}