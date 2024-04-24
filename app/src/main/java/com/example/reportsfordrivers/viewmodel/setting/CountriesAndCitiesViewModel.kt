package com.example.reportsfordrivers.viewmodel.setting

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reportsfordrivers.data.dao.CountryDao
import com.example.reportsfordrivers.data.dao.TownshipDao
import com.example.reportsfordrivers.viewmodel.setting.uistate.CountriesSetting
import com.example.reportsfordrivers.viewmodel.setting.uistate.CountryDetailingSetting
import com.example.reportsfordrivers.viewmodel.setting.uistate.TownshipDetailingSetting
import com.example.reportsfordrivers.viewmodel.setting.uistate.TownshipsSetting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import java.util.Locale
import javax.inject.Inject

private const val TAG = "CountriesAndCitiesViewModel"

@HiltViewModel
class CountriesAndCitiesViewModel @Inject constructor() : ViewModel() {

    val stateTab = mutableIntStateOf(0)

    val uiStateCountry = mutableStateOf(CountriesSetting())
    val uiStateTownship = mutableStateOf(TownshipsSetting())

    val selectedCountrySearch = mutableStateOf("")

    val isCheckedFavoriteCountry = mutableStateOf(false)
    val isCheckedFavoriteTownship = mutableStateOf(false)
    val sortCountry = mutableIntStateOf(0) //0 - Алфавит, 1 - Популярность
    val sortTownship = mutableIntStateOf(0) //0 - Алфавит, 1 - Популярность

    @Inject
    lateinit var countryDb: CountryDao
    @Inject
    lateinit var townshipDb: TownshipDao

    //Поиск страны
    private val _isSearchingCountrySetting = MutableStateFlow(false)
    private val _searchTextCountrySetting = MutableStateFlow("")
    val searchTextCountrySetting = _searchTextCountrySetting.asStateFlow()
    private var _countriesListCountrySetting = MutableStateFlow(uiStateCountry.value.listCountries)
    var countriesListCountrySetting = countriesSettingFilter()

    private fun countriesSettingFilter(): StateFlow<List<CountryDetailingSetting>> {
        return searchTextCountrySetting
            .combine(_countriesListCountrySetting) { text, countries ->
                if(text.isBlank()) {
                    countries
                }
                countries.filter { country ->
                    country.country.uppercase().contains(text.trim().uppercase())
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(2000),
                initialValue = _countriesListCountrySetting.value
            )
    }

    fun onSearchTextChangeCountrySetting(text: String) {
        _searchTextCountrySetting.value = text
    }

    fun onToogleSearchCountrySetting() {
        _isSearchingCountrySetting.value = !_isSearchingCountrySetting.value
        if(_isSearchingCountrySetting.value) {
            onSearchTextChangeCountrySetting("")
        }
    }

    fun openSelectedCountry() = runBlocking {
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
                    CountryDetailingSetting(
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
            Log.i(TAG, a.joinToString("::"))
            a.forEach {
                uiStateCountry.value.listCountries.add(
                    CountryDetailingSetting(
                        id = it.id,
                        country = it.fullNameCountryEng,
                        shortCountry = it.shortNameCountry,
                        rating = it.rating,
                        favorite = it.favorite
                    )
                )
            }
        }
        _countriesListCountrySetting = MutableStateFlow(uiStateCountry.value.listCountries)
        countriesListCountrySetting = countriesSettingFilter()
    }

    fun updateItemCountrySetting(element: CountryDetailingSetting) = runBlocking {
        val index = idListCountrySetting(element)
        uiStateCountry.value.listCountries[index] = uiStateCountry.value.listCountries[index].copy(
            favorite = if (uiStateCountry.value.listCountries[index].favorite == 0) 1 else 0
        )
        countryDb.updateFavorite(
            uiStateCountry.value.listCountries[index].favorite,
            uiStateCountry.value.listCountries[index].id
        )
    }

    fun idListCountrySetting(countryDetailing: CountryDetailingSetting): Int {
        var a = -1
        for (i in uiStateCountry.value.listCountries.indices) {
            if(countryDetailing.id == uiStateCountry.value.listCountries[i].id) a = 1
        }
        return a
    }


    //Поиск города
    private val _isSearchingTownshipSetting = MutableStateFlow(false)
    val isSearchingTownshipSetting = _isSearchingTownshipSetting.asStateFlow()
    private val _searchTextTownshipSetting = MutableStateFlow("")
    val searchTextTownshipSetting = _searchTextTownshipSetting.asStateFlow()
    var _townshipsListTownshipSetting = MutableStateFlow(uiStateTownship.value.listTownships)
    var townshipsListTownshipSetting = townshipsFilterSetting()

    private fun townshipsFilterSetting(): StateFlow<List<TownshipDetailingSetting>> {
        return searchTextTownshipSetting
            .combine(_townshipsListTownshipSetting) { text, townships ->
                if(text.isBlank()) {
                    townships
                }
                townships.filter { township ->
                    township.township.uppercase().contains(text.trim().uppercase())
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(2000),
                initialValue = _townshipsListTownshipSetting.value
            )
    }

    fun onSearchTextChangeTownshipSetting(text: String) {
        _searchTextTownshipSetting.value = text
    }

    fun onToogleSearchTownshipSetting() {
        _isSearchingTownshipSetting.value = !_isSearchingTownshipSetting.value
        if(!_isSearchingTownshipSetting.value) {
            onSearchTextChangeTownshipSetting("")
        }
    }

    fun openSelectedTownship(countryId: Int) = runBlocking {
        uiStateTownship.value.listTownships = SnapshotStateList()
        if (Locale.getDefault().language == "ru") {
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
                    TownshipDetailingSetting(
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
                if(countryId != 0) {
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
                    TownshipDetailingSetting(
                        id = it.id,
                        township = it.townshipEng,
                        countryId = it.countryId,
                        rating = it.rating,
                        favorite = it.favorite
                    )
                )
            }
        }

        _townshipsListTownshipSetting = MutableStateFlow(uiStateTownship.value.listTownships)
        townshipsListTownshipSetting = townshipsFilterSetting()
    }

    fun updateItemTownshipSetting(element: TownshipDetailingSetting) = runBlocking {
        val index = idListTownshipSetting(element)
        uiStateTownship.value.listTownships[index] = uiStateTownship.value.listTownships[index].copy(
            favorite = if(uiStateTownship.value.listTownships[index].favorite == 0) 1 else 0
        )
        townshipDb.updateFavorite(
            uiStateTownship.value.listTownships[index].favorite,
            uiStateTownship.value.listTownships[index].id
        )
    }

    /**
     * Получение ID города, для добавления в избранное
     */
    fun idListTownshipSetting(townshipDetailingSetting: TownshipDetailingSetting): Int {
        var a = -1
        for(i in uiStateTownship.value.listTownships.indices) {
            if(townshipDetailingSetting.id == uiStateTownship.value.listTownships[i].id) {
                a = i
            }
        }
        return a
    }
}