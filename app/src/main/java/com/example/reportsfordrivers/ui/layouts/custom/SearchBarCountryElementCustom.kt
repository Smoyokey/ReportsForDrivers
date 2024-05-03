package com.example.reportsfordrivers.ui.layouts.custom

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
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.firstentry.CountryDetailing
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnSearchCountryCustom(
    modifier: Modifier = Modifier,
    searchText: StateFlow<String>,
    countriesList: StateFlow<List<CountryDetailing>>,
    onSearchTextChangeCountry: (String) -> Unit,
    onToogleSearchCountry: () -> Unit,
    sortCountry: MutableState<Int>,
    loadCountry: () -> Unit,
    isCheckedFavoriteCountry: MutableState<Boolean>,
    openListSearch: MutableState<Int>,
    selectedCountryIdInSearch: MutableState<Int>,
    selectedCountryNameInSearch: MutableState<String>,
    updateRating: (Int) -> Unit,
    loadTownships: (Int) -> Unit
    ) {
    val searchTextCountry by searchText.collectAsState()
    val countriesListCountry by countriesList.collectAsState()

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

        RowSortingAndFavoriteCountry(
            sortCountry = sortCountry,
            loadCountry = loadCountry,
            isCheckedFavoriteCountry = isCheckedFavoriteCountry
        )

        LazyColumnCountry(
            modifier = Modifier.weight(1f),
            countriesList = countriesListCountry,
            openListSearch = openListSearch,
            selectedCountryIdInSearch = selectedCountryIdInSearch,
            selectedCountryNameInSearch = selectedCountryNameInSearch,
            updateRating = updateRating,
            loadTownships = loadTownships
        )
    }
}

@Composable
fun RowSortingAndFavoriteCountry(
    sortCountry: MutableState<Int>,
    loadCountry: () -> Unit,
    isCheckedFavoriteCountry: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        RowSortingCountry(
            sortCountry = sortCountry,
            clickableMethodCountry = {
                sortCountry.value = if(sortCountry.value == 0) 1 else 0
                loadCountry()
            }
        )
        RowFavoriteCountry(
            modifier = Modifier.weight(1f),
            isCheckedFavoriteCountry = isCheckedFavoriteCountry,
            clickableMethod = {
                loadCountry
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
            text = if(sortCountry.value == 0) {
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
        Text( text = stringResource(R.string.favorite) )
    }
}

@Composable
fun LazyColumnCountry(
    modifier: Modifier = Modifier,
    countriesList: List<CountryDetailing>,
    openListSearch: MutableState<Int>,
    selectedCountryIdInSearch: MutableState<Int>,
    selectedCountryNameInSearch: MutableState<String>,
    updateRating: (Int) -> Unit,
    loadTownships: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(countriesList.size) { element ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(countriesList[element].favorite == 1) {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = null
                    )
                }

                Text(
                    text = countriesList[element].country,
                    style = typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = {
                        openListSearch.value = 1
                        loadTownships(countriesList[element].id)
                        selectedCountryIdInSearch.value = countriesList[element].id
                        selectedCountryNameInSearch.value = countriesList[element].country
                        updateRating(countriesList[element].id)
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