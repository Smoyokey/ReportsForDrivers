package com.example.reportsfordrivers.interfaces.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.reportsfordrivers.data.dao.TownshipDao
import com.example.reportsfordrivers.viewmodel.firstentry.CountryDetailing
import com.example.reportsfordrivers.viewmodel.firstentry.TownshipDetailing
import com.example.reportsfordrivers.viewmodel.firstentry.Townships
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

interface SearchTownship {

    val uiStateTownship: MutableState<Townships>
    val isCheckedFavoriteTownship: MutableState<Boolean>
    val sortTownship: MutableState<Int>

    val _isSearchingTownship: MutableStateFlow<Boolean>
    val _searchTextTownship: MutableStateFlow<String>
    val searchTextTownship: StateFlow<String>

    var _townshipsListTownship: MutableStateFlow<SnapshotStateList<TownshipDetailing>>
    var townshipsListTownship: StateFlow<List<TownshipDetailing>>

    fun filterTownship(): StateFlow<List<TownshipDetailing>>

    fun onSearchTextChangeTownship(text: String)

    fun onToogleSearchTownship()

    fun loadTownships(countryId: Int)

    fun updateRatingTownship(id: Int)


}