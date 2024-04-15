package com.example.reportsfordrivers.ui.layouts.firstentry

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Button
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.data.structure.Currency
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.firstentry.CountryDetailing
import com.example.reportsfordrivers.viewmodel.firstentry.FirstEntryViewModel
import java.util.Locale

const val TAG = "FirstEntryTwoScreen"

@Composable
fun FirstEntryTwoScreen(
    onMainMenu: () -> Unit = {},
    onFirstEntryOneScreen: () -> Unit = {},
    viewModel: FirstEntryViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp, bottom = 15.dp, start = 10.dp, end = 10.dp)

    ) {
        Text(
            text = stringResource(R.string.report_language),
            style = typography.titleLarge
        )

        Row(
            modifier = Modifier.selectableGroup()
        ) {
            RadioButtonFirstEntry(
                text = R.string.russian,
                selected = viewModel.languageUiState.value.languageRadioGroup,
                onClick = {
                    viewModel.selectedLanguage(true)
                },
                modifier = Modifier.weight(1f)
            )
            RadioButtonFirstEntry(
                text = R.string.english,
                selected = !viewModel.languageUiState.value.languageRadioGroup,
                onClick = {
                    viewModel.selectedLanguage(false)
                },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(
                onClick = { viewModel.openBottomSheetCurrency() },
            ) {
                Text(
                    text = stringResource(R.string.select_currency),
                    style = typography.titleLarge
                )
            }

            Text(
                text = viewModel.uiState.value.currency,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End,
                style = typography.titleLarge
            )
        }

        TextButton(
            onClick = {
                viewModel.openSelectedCountry()
            }
        ) {
            Text(
                text = stringResource(R.string.make_list_favorite_countries_cities),
                style = typography.titleLarge
            )
        }

        if (viewModel.openSelectedCountriesAndCities.value) {
            CountryAndCiti(
                isVisible = viewModel.openSelectedCountriesAndCities.value,
                viewModel = viewModel,
                state = viewModel.state,
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 10.dp)
            )
        } else {
            Column(
                modifier = Modifier.weight(1f)
            ) {}
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = {
                    onFirstEntryOneScreen()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.back_button)
                )
            }
            Button(
                onClick = {
                    viewModel.onFirstEntry()
                    onMainMenu()
                    viewModel.saveButton()
                },
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = stringResource(R.string.save)
                )
            }
        }
    }
    if (viewModel.openBottomSheetCurrency.value) {
        BottomSheetCurrency(
            isOpen = viewModel.openBottomSheetCurrency,
            listCurrency = viewModel.listCurrency.value,
            viewModel = viewModel
        )
    }
    if (viewModel.openBottomSheetAddCity.value) {
        BottomSheetAddCity(
            isOpen = viewModel.openBottomSheetAddCity,
            listCountry = viewModel.listCountriesUiState.value.listCountries,
            viewModel = viewModel,

        )
    }
}

@Composable
fun CountryAndCiti(
    isVisible: Boolean,
    viewModel: FirstEntryViewModel,
    state: MutableState<Int>,
    modifier: Modifier = Modifier
) {
    val searchTextCountry by viewModel.searchTextCountry.collectAsState()
    val countriesListCountry by viewModel.countriesListCountry.collectAsState()
    val searchTextTownship by viewModel.searchTextTownship.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TabRow(selectedTabIndex = state.value) {
            Tab(
                text = { Text(text = stringResource(R.string.countries)) },
                selected = state.value == 0,
                onClick = { state.value = 0 }
            )
            Tab(
                text = { Text(text = stringResource(R.string.cities)) },
                selected = state.value == 1,
                onClick = {
                    state.value = 1
                    viewModel.searchTownship(0)
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.value == 0) {
                SearchCountry(
                    modifier = Modifier.weight(1f),
                    countriesList = countriesListCountry,
                    viewModel = viewModel,
                    state = state,
                    searchTextCountry = searchTextCountry,
                )
            } else {
                SearchTownship(
                    modifier = Modifier.weight(1f),
                    viewModel = viewModel,
                    state = state,
                    searchTextTownship = searchTextTownship
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCountry(
    modifier: Modifier = Modifier,
    countriesList: List<CountryDetailing>,
    viewModel: FirstEntryViewModel,
    state: MutableState<Int>,
    searchTextCountry: String,
) {
    Column() {
        DockedSearchBar(
            query = searchTextCountry,
            onQueryChange = viewModel::onSearchTextChangeCountry,
            onSearch = viewModel::onSearchTextChangeCountry,
            active = false,
            onActiveChange = {
                viewModel.onToogleSearchCountry()
            },
            placeholder = {
                Text(
                    text = if (state.value == 0) {
                        stringResource(R.string.countries)
                    } else {
                        stringResource(R.string.cities)
                    }
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (state.value == 1) {
                    IconButton(
                        onClick = {
                            viewModel.openBottomSheetAddCity.value =
                                !viewModel.openBottomSheetAddCity.value
                        }
                    ) {
                        Icon(Icons.Outlined.Add, stringResource(R.string.add))
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {}

        LazyColumn(
            modifier = modifier
        ) {
            items(countriesList.size) { element ->
                val checked by remember {
                    mutableStateOf(viewModel.listCountriesUiState.value.listCountries[element].favorite == 1)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            viewModel.searchTownship(countriesList[element].id)
                            viewModel.state.value = 1
                            viewModel.selectedCountrySearch.value = countriesList[element].country
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = null
                        )
                    }
                    Text(
                        text = countriesList[element].country,
                        style = typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )
                    IconToggleButton(
                        checked = checked,
                        onCheckedChange = {
                            viewModel.updateItemCountry(
                                countriesList[element]
                            )
                        },
                    ) {
                        if (
                            viewModel.listCountriesUiState.value.listCountries[viewModel.idListCountry(
                                countriesList[element]
                            )].favorite == 0
                        ) {
                            Icon(Icons.TwoTone.Star, null)
                        } else {
                            Icon(Icons.Filled.Star, null)
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTownship(
    modifier: Modifier = Modifier,
    viewModel: FirstEntryViewModel,
    state: MutableState<Int>,
    searchTextTownship: String
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        DockedSearchBar(
            query = searchTextTownship,
            onQueryChange = viewModel::onSearchTextChangeTownship,
            onSearch = viewModel::onSearchTextChangeTownship,
            active = false,
            onActiveChange = {
                viewModel.onToogleSearchTownship()
            },
            placeholder = {
                Text(
                    text = if (state.value == 0) {
                        stringResource(R.string.countries)
                    } else {
                        stringResource(R.string.cities)
                    }
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (state.value == 1) {
                    IconButton(
                        onClick = {
                            viewModel.openBottomSheetAddCity.value =
                                !viewModel.openBottomSheetAddCity.value
                        }
                    ) {
                        Icon(Icons.Outlined.Add, stringResource(R.string.add))
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {}

        val townshipsList by viewModel.townshipsListTownship.collectAsState()
        if (viewModel.townshipsListTownship.value.isEmpty()) {
            if (viewModel.selectedCountrySearch.value != "") {
                Column {
                    RowSearchCountry(viewModel.selectedCountrySearch.value, viewModel)
                    EmptySearchTownship(viewModel.openBottomSheetAddCity)
                }
            } else {
                EmptySearchTownship(viewModel.openBottomSheetAddCity)
            }
        } else {
            if (viewModel.selectedCountrySearch.value != "") {
                RowSearchCountry(viewModel.selectedCountrySearch.value, viewModel)
            }

            LazyColumn(
                modifier = modifier
            ) {
                items(townshipsList.size) { element ->

                    val checked by remember {
                        mutableStateOf(viewModel.listTownshipsUiState.value.listTownships[element].favorite == 1)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = townshipsList[element].township,
                            style = typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )

                        IconToggleButton(
                            checked = checked,
                            onCheckedChange = {
                                viewModel.updateItemTownship(
                                    townshipsList[element]
                                )
                            },
                        ) {
                            if (viewModel.listTownshipsUiState.value.listTownships[viewModel.idListTownship(
                                    townshipsList[element]
                                )].favorite == 0
                            ) {
                                Icon(Icons.TwoTone.Star, null)
                            } else {
                                Icon(Icons.Filled.Star, null)
                            }
                        }
                    }
                }
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetCurrency(
    isOpen: MutableState<Boolean>,
    listCurrency: List<Currency>,
    viewModel: FirstEntryViewModel
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = { isOpen.value = false },
        sheetState = sheetState,
        modifier = Modifier.fillMaxHeight(0.75f)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = stringResource(R.string.select_currency),
                style = typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(listCurrency.size) { element ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                isOpen.value = false
                                viewModel.uiState.value = viewModel.uiState.value.copy(
                                    currency = listCurrency[element].shortNameCurrency
                                )
                            }
                    ) {
                        Text(
                            text = if (Locale.getDefault().language == "ru") {
                                listCurrency[element].fullNameCurrencyRus
                            } else {
                                listCurrency[element].fullNameCurrencyEng
                            },
                            modifier = Modifier.weight(1f),
                            style = typography.titleLarge
                        )
                        Text(
                            text = listCurrency[element].shortNameCurrency,
                            style = typography.titleLarge
                        )
                    }
                }
            }
            Button(
                onClick = { isOpen.value = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    style = typography.titleLarge
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetAddCity(
    isOpen: MutableState<Boolean>,
    listCountry: List<CountryDetailing>,
    viewModel: FirstEntryViewModel,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val searchTextAddCity by viewModel.searchTextAddCity.collectAsState()
    val countriesListAddCity by viewModel.countriesListAddCity.collectAsState()

    ModalBottomSheet(
        onDismissRequest = { isOpen.value = false },
        sheetState = sheetState,
        modifier = Modifier.fillMaxHeight(0.75f)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = stringResource(R.string.add_city),
                style = typography.headlineSmall
            )

            OutlinedTextField(
                value = viewModel.addCityUiState.value.nameCity,
                onValueChange = {
                    viewModel.updateAddCityNameCity(nameCity = it)
                },
                label = { Text(stringResource(R.string.name_city)) },
                singleLine = true,
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if (viewModel.addCityUiState.value.nameCity.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Outlined.Clear,
                            contentDescription = stringResource(R.string.clear),
                            modifier = Modifier
                                .clickable {
                                    viewModel.updateAddCityNameCity(nameCity = "")
                                },
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            DockedSearchBar(
                query = searchTextAddCity,
                onQueryChange = viewModel::onSearchTextChangeAddCity,
                onSearch = viewModel::onSearchTextChangeAddCity,
                active = false,
                onActiveChange = {
                    viewModel.onToogleSearchAddCity()
                },
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

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(countriesListAddCity.size) { element ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.updateAddCityCountry(countriesListAddCity[element])
                                Log.i(TAG, viewModel.addCityUiState.value.country.toString())
                            }
                    ) {
                        Text(
                            text = countriesListAddCity[element].country,
                            modifier = Modifier.weight(1f),
                            style = typography.titleLarge
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                isOpen.value = false
                viewModel.saveAddCity()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.validateAddCity()
        ) {
            Text(
                text = stringResource(R.string.save)
            )
        }
    }
}

@Composable
fun RowSearchCountry(name: String, viewModel: FirstEntryViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = name,
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = {
                viewModel.state.value = 0
                viewModel.selectedCountrySearch.value = ""
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Clear,
                contentDescription = null
            )
        }
    }
}

@Composable
fun EmptySearchTownship(isOpenAddCity: MutableState<Boolean>) {
    TextButton(
        onClick = {
            isOpenAddCity.value = !isOpenAddCity.value
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.not_list_add),
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FirstEntryTwoScreenPreview() {
    FirstEntryTwoScreen()
}
