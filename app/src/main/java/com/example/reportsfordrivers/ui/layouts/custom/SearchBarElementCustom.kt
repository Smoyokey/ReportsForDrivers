package com.example.reportsfordrivers.ui.layouts.custom

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.firstentry.TownshipDetailing
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnSearchTownship(
    searchText: StateFlow<String>,
    townshipsList: StateFlow<List<TownshipDetailing>>,
    onSearchTextChangeTownship: (String) -> Unit,
    onToogleSearchTownship: () -> Unit,
    openAddCity: () -> Unit,
    modifier: Modifier = Modifier,
    sortTownship: MutableStateFlow<Int>,
    loadTownships: (Int) -> Unit,
    selectedCountryIdInSearch: MutableStateFlow<Int>,
    isCheckedFavoriteTownship: MutableState<Boolean>,
    listIsEmpty: Boolean,
    isOpenAddCity: () -> Unit,
    updateData: (String) -> Unit,
    updateRating: (Int) -> Unit,
    closeBottom: () -> Unit
) {
    val searchTextTownship by searchText.collectAsState()
    val townshipsListTownship by townshipsList.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        DockedSearchBar(
            query = searchTextTownship,
            onQueryChange = onSearchTextChangeTownship,
            onSearch = onSearchTextChangeTownship,
            active = false,
            onActiveChange = { onToogleSearchTownship() },
            placeholder = { Text(text = stringResource(R.string.cities)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { openAddCity()}
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = stringResource(R.string.add)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {}

        RowSortingAndFavoriteTownship(
            sortTownship = sortTownship,
            loadTownships = loadTownships,
            selectedCountryIdInSearch = selectedCountryIdInSearch,
            modifier = Modifier.fillMaxWidth(),
            isCheckedFavoriteTownship = isCheckedFavoriteTownship
        )

        if(listIsEmpty) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                EmptySearchTownship(
                    isOpenAddCity = isOpenAddCity
                )
            }
        } else {
            LazyColumnTownship(
                listTownship = townshipsListTownship,
                updateData = updateData,
                updateRating = updateRating,
                closeBottom = closeBottom,
            )
        }
    }
}

@Composable
fun RowSortingAndFavoriteTownship(
    sortTownship: MutableStateFlow<Int>,
    loadTownships: (Int) -> Unit,
    selectedCountryIdInSearch: MutableStateFlow<Int>,
    modifier: Modifier = Modifier,
    isCheckedFavoriteTownship: MutableState<Boolean>
) {
    Row(
        modifier = modifier
    ) {
        RowSortingTownship(
            sortTownship = sortTownship,
            clickableMethod = {
                sortTownship.value = if(sortTownship.value == 0) 1 else 0
                loadTownships(selectedCountryIdInSearch.value)
            }
        )
        RowFavoriteTownship(
            modifier = Modifier.weight(1f),
            isCheckedFavoriteTownship = isCheckedFavoriteTownship,
            clickableMethod = {
                loadTownships(selectedCountryIdInSearch.value)
            }
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun RowSortingTownship(
    sortTownship: MutableStateFlow<Int>,
    clickableMethod: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable { clickableMethod() }
    ) {
        Icon(
            painter = painterResource(R.drawable.sort_24px),
            contentDescription = null
        )
        Text(
            text = if(sortTownship.value == 0) {
                stringResource(R.string.alphabetically)
            } else {
                stringResource(R.string.by_popularity)
            }
        )
    }
}

@Composable
fun RowFavoriteTownship(
    modifier: Modifier = Modifier,
    isCheckedFavoriteTownship: MutableState<Boolean>,
    clickableMethod: () -> Unit,
) {
    Row(
        modifier = modifier
            .toggleable(
                value = isCheckedFavoriteTownship.value,
                onValueChange = {
                    isCheckedFavoriteTownship.value = !isCheckedFavoriteTownship.value
                },
                role = Role.Checkbox
            )
    ) {
        Checkbox(
            checked = isCheckedFavoriteTownship.value,
            onCheckedChange = {
                isCheckedFavoriteTownship.value = it
                clickableMethod()
            }
        )
        Text(
            text = stringResource(R.string.favorite)
        )
    }
}

@Composable
fun LazyColumnTownship(
    listTownship: List<TownshipDetailing>,
    updateData: (String) -> Unit,
    updateRating: (Int) -> Unit,
    closeBottom: () -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(listTownship.size) { element ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        updateData(listTownship[element].township)
                        updateRating(listTownship[element].id)
                        closeBottom()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(listTownship[element].favorite == 1) {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = null
                    )
                }
                Text(
                    text = listTownship[element].township,
                    style = typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun EmptySearchTownship(
    isOpenAddCity: () -> Unit
) {
    TextButton(
        onClick = isOpenAddCity,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.not_list_add),
            textAlign = TextAlign.Center
        )
    }
}