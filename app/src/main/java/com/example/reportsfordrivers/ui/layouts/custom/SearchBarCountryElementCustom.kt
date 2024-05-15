package com.example.reportsfordrivers.ui.layouts.custom

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportInfoViewModel
import com.example.reportsfordrivers.viewmodel.firstentry.CountryDetailing
import kotlinx.coroutines.flow.StateFlow

private const val TAG = "ColumnSearchCountryCustom"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnSearchCountryCustom(
    modifier: Modifier = Modifier,
    searchText: StateFlow<String>,
    countriesListCountry: StateFlow<List<CountryDetailing>>,
    onSearchTextChangeCountry: (String) -> Unit,
    onToogleSearchCountry: () -> Unit,
    sortCountry: MutableState<Int>,
    loadCountry: () -> Unit,
    isCheckedFavoriteCountry: MutableState<Boolean>,
    openListSearch: MutableState<Int>,
    selectedCountryIdInSearch: MutableState<Int>,
    selectedCountryNameInSearch: MutableState<String>,
    updateRating: (Int) -> Unit,
    loadTownships: (Int) -> Unit,
    viewModelCreateReport: CreateReportInfoViewModel = hiltViewModel()
) {

    val searchTextCountry by searchText.collectAsState()
//    Log.i(TAG, countriesListCountry.joinToString("::"))


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        DockedSearchBar(
            query = searchTextCountry,
            onQueryChange = { onSearchTextChangeCountry(it) },
            onSearch = { onSearchTextChangeCountry(it) },
            active = false,
            onActiveChange = { onToogleSearchCountry() },
            placeholder = { Text(text = stringResource(R.string.countries)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {}

//        RowSortingAndFavoriteCountry(
//            sortCountry = sortCountry,
//            loadCountry = loadCountry,
//            isCheckedFavoriteCountry = isCheckedFavoriteCountry,
//        )

        LazyColumnCountry(
            modifier = Modifier.weight(1f),
            countriesList = countriesListCountry,
            openListSearch = openListSearch,
            selectedCountryIdInSearch = selectedCountryIdInSearch,
            selectedCountryNameInSearch = selectedCountryNameInSearch,
            updateRating = updateRating,
            loadTownships = loadTownships,
            viewModelCreateReport = viewModelCreateReport,
            sortCountry = sortCountry,
            clickableMethodCountry = {
                sortCountry.value = if (sortCountry.value == 0) 1 else 0
                loadCountry()
            },
            isCheckedFavoriteCountry = isCheckedFavoriteCountry,
            clickableMethod = { loadCountry() }
        )
    }
}

@Composable
fun RowSortingAndFavoriteCountry(
    sortCountry: MutableState<Int>,
    loadCountry: () -> Unit,
    isCheckedFavoriteCountry: MutableState<Boolean>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
    ) {
        RowSortingCountry(
            sortCountry = sortCountry,
            clickableMethodCountry = {
                sortCountry.value = if (sortCountry.value == 0) 1 else 0
                loadCountry()
            }
        )
        RowFavoriteCountry(
            modifier = Modifier.weight(1f),
            isCheckedFavoriteCountry = isCheckedFavoriteCountry,
            clickableMethod = {
                loadCountry()
            }
        )
    }
}

@Composable
fun RowSortingCountry(
    sortCountry: MutableState<Int>,
    clickableMethodCountry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable { clickableMethodCountry() }
    ) {
        Icon(
            painter = painterResource(R.drawable.sort_24px),
            contentDescription = null
        )
        Text(
            text = if (sortCountry.value == 0) {
                stringResource(R.string.alphabetically)
            } else {
                stringResource(R.string.by_popularity)
            }
        )
    }
}

@Composable
fun RowFavoriteCountry(
    modifier: Modifier = Modifier,
    isCheckedFavoriteCountry: MutableState<Boolean>,
    clickableMethod: () -> Unit,
) {
    Row(
        modifier = modifier
            .toggleable(
                value = isCheckedFavoriteCountry.value,
                onValueChange = {
                    isCheckedFavoriteCountry.value = !isCheckedFavoriteCountry.value
                },
                role = Role.Checkbox
            )
    ) {
        Checkbox(
            checked = isCheckedFavoriteCountry.value,
            onCheckedChange = {
                isCheckedFavoriteCountry.value = it
                clickableMethod()
            }
        )
        Text(text = stringResource(R.string.favorite))
    }
}

@Composable
fun LazyColumnCountry(
    modifier: Modifier = Modifier,
    countriesList: StateFlow<List<CountryDetailing>>,
    openListSearch: MutableState<Int>,
    selectedCountryIdInSearch: MutableState<Int>,
    selectedCountryNameInSearch: MutableState<String>,
    updateRating: (Int) -> Unit,
    loadTownships: (Int) -> Unit,
    viewModelCreateReport: CreateReportInfoViewModel,
    sortCountry: MutableState<Int>,
    clickableMethodCountry: () -> Unit,
    isCheckedFavoriteCountry: MutableState<Boolean>,
    clickableMethod: () -> Unit
) {
    val countriesListT by countriesList.collectAsState()
    Column {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable { clickableMethodCountry() }
            ) {
                Icon(
                    painter = painterResource(R.drawable.sort_24px),
                    contentDescription = null
                )
                Text(
                    text = if (sortCountry.value == 0) {
                        stringResource(R.string.alphabetically)
                    } else {
                        stringResource(R.string.by_popularity)
                    }
                )
            }
            Row(
                modifier = Modifier
                    .weight(1f)
                    .toggleable(
                        value = isCheckedFavoriteCountry.value,
                        onValueChange = {
                            isCheckedFavoriteCountry.value = !isCheckedFavoriteCountry.value
                        },
                        role = Role.Checkbox
                    )
            ) {
                Checkbox(
                    checked = isCheckedFavoriteCountry.value,
                    onCheckedChange = {
                        isCheckedFavoriteCountry.value = it
                        clickableMethod()
                    }
                )
                Text(text = stringResource(R.string.favorite))
            }
        }
    }

    LazyColumn(
        modifier = modifier
    ) {
        items(countriesListT.size) { element ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (countriesListT[element].favorite == 1) {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = null
                    )
                }

                Text(
                    text = countriesListT[element].country,
                    style = typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = {
                        openListSearch.value = 1
                        loadTownships(countriesListT[element].id)
                        selectedCountryIdInSearch.value = countriesListT[element].id
                        selectedCountryNameInSearch.value = countriesListT[element].country
                        updateRating(countriesListT[element].id)
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                        contentDescription = null
                    )
                }
            }
        }
    }
}