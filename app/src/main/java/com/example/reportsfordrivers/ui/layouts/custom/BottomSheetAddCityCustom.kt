package com.example.reportsfordrivers.ui.layouts.custom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
fun BottomSheetAddCityCustom(
    searchText: StateFlow<String>,
    countriesList: StateFlow<List<CountryDetailing>>,
    openBottom: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    nameCity: String,
    updateNameCity: (String) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onToogleSearch: () -> Unit,
    sortCountry: MutableState<Int>,
    loadCountries: () -> Unit,
    isCheckedFavorite: MutableState<Boolean>,
    country: CountryDetailing,
    updateCountry: (CountryDetailing) -> Unit,
    closeAddCity: (MutableState<Boolean>) -> Unit,
    saveAddCityInBd: (MutableState<Boolean>) -> Unit,
    validateAddCity: () -> Boolean
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val searchTextAddCity by searchText.collectAsState()
    val countriesListAddCity by countriesList.collectAsState()

    ModalBottomSheet(
        onDismissRequest = { openBottom.value = false },
        sheetState = sheetState,
        modifier = Modifier.fillMaxHeight(0.75f)
    ) {

        Column() {
            Text(
                text = stringResource(R.string.add_city),
                style = typography.headlineSmall
            )

            OutlinedTextField(
                value = nameCity,
                onValueChange = { updateNameCity(it) },
                label = { Text(stringResource(R.string.name_city)) },
                singleLine = true,
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if (nameCity.isNotEmpty()) {
                        IconButton(
                            onClick = { updateNameCity("") }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = stringResource(R.string.clear)
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            DockedSearchBar(
                query = searchTextAddCity,
                onQueryChange = { onSearchTextChange(it) },
                onSearch = { onSearchTextChange(it) },
                active = false,
                onActiveChange = { onToogleSearch() },
                placeholder = {
                    Text(
                        text = stringResource(R.string.country),
                        style = typography.bodyLarge
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {}

            Row(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            sortCountry.value = if (sortCountry.value == 0) 1 else 0
                            loadCountries()
                        }
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
                            value = isCheckedFavorite.value,
                            onValueChange = { isCheckedFavorite.value = !isCheckedFavorite.value },
                            role = Role.Checkbox,
                        )
                ) {
                    Checkbox(
                        checked = isCheckedFavorite.value,
                        onCheckedChange = {
                            isCheckedFavorite.value = it
                            loadCountries()
                        }
                    )
                    Text(
                        text = stringResource(R.string.favorite)
                    )
                }
            }

            if (country.country.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = country.country
                    )
                    IconButton(
                        onClick = { updateCountry(CountryDetailing()) }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Clear,
                            contentDescription = stringResource(R.string.clear)
                        )
                    }
                }
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(countriesListAddCity.size) { element ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { updateCountry(countriesListAddCity[element]) }
                    ) {
                        Text(
                            text = countriesListAddCity[element].country,
                            modifier = Modifier.weight(1f),
                            style = typography.titleLarge
                        )
                    }
                }
            }

            Button(
                onClick = {
                    closeAddCity(openBottom)
                    saveAddCityInBd(openBottom)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = validateAddCity()
            ) {
                Text(
                    text = stringResource(R.string.save)
                )
            }
        }
    }
}