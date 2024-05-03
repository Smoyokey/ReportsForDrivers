package com.example.reportsfordrivers.interfaces.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.reportsfordrivers.viewmodel.firstentry.Countries
import com.example.reportsfordrivers.viewmodel.firstentry.CountryDetailing
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface SearchCountry {

    val uiStateCountry: MutableState<Countries>
    val isCheckedFavoriteCountry: MutableState<Boolean>
    val sortCountry: MutableState<Int>
    val _isSearchingCountry: MutableStateFlow<Boolean>
    val _searchTextCountry: MutableStateFlow<String>
    val searchTextCountry: StateFlow<String>
    var _countriesListCountry: MutableStateFlow<SnapshotStateList<CountryDetailing>>
    var countriesListCountry: StateFlow<List<CountryDetailing>>

    fun filterCountry(): StateFlow<List<CountryDetailing>>

    fun onSearchTextChangeCountry(text: String)

    fun onToogleSearchCountry()

    fun loadCountries()

    fun updateRatingCountry(id: Int)
}