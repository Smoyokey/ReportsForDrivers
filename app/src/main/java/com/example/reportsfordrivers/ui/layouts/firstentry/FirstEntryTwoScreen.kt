package com.example.reportsfordrivers.ui.layouts.firstentry

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import kotlinx.coroutines.flow.collect
import java.util.Locale

const val TAG = "FirstEntryTwoScreen"

@Composable
fun FirstEntryTwoScreen(
    onMainMenu: () -> Unit = {},
    onFirstEntryOneScreen: () -> Unit = {},
    viewModel: FirstEntryViewModel = hiltViewModel()
) {



    Column {
        Text(
            text = stringResource(R.string.report_language)
        )

        Row(
            modifier = Modifier.selectableGroup()
        ) {
            RadioButtonFirstEntry(
                text = R.string.russian,
                selected = true,
                onClick = {},
                modifier = Modifier.weight(1f)
            )
            RadioButtonFirstEntry(
                text = R.string.english,
                selected = false,
                onClick = {},
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
                    text = stringResource(R.string.select_currency)
                )
            }

            Text(
                text = "",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }

        TextButton(
            onClick = {
                viewModel.openSelectedCountry()
            }
        ) {
            Text(
                text = stringResource(R.string.make_list_favorite_countries_cities)
            )
        }

        if (viewModel.openSelectedCountriesAndCities.value) {
            CountryAndCiti(
                isVisible = viewModel.openSelectedCountriesAndCities.value,
                viewModel = viewModel,
                state = viewModel.state,
                modifier = Modifier.weight(1f)
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
            listCurrency = viewModel.listCurrency.value
        )
    }
    if (viewModel.openBottomSheetAddCity.value) {
        BottomSheetAddCity(
            isOpen = viewModel.openBottomSheetAddCity,
            listCountry = viewModel.listCountriesUiState.value.listCountries,
            viewModel = viewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryAndCiti(
    isVisible: Boolean,
    viewModel: FirstEntryViewModel,
    state: MutableState<Int>,
    modifier: Modifier = Modifier
) {
    val searchText by viewModel.searchText.collectAsState()
    val countriesList by viewModel.countriesList.collectAsState()

    Column(
        modifier = modifier
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
                    viewModel.openSelectedTownship()
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            SearchBar(
                query = searchText,
                onQueryChange = viewModel::onSearchTextChange,
                onSearch = viewModel::onSearchTextChange,
                active = true,
                onActiveChange = { viewModel.onToogleSearch() },
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
            ) {

                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    if (state.value == 0) {
                        items(countriesList.size) { element ->
                            val checked by remember {
                                mutableStateOf(viewModel.listCountriesUiState.value.listCountries[element].favorite == 1)
                            }
                            Log.i(TAG, countriesList.joinToString("::"))
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                IconButton(
                                    onClick = {}
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
                                        viewModel.listCountriesUiState.value.
                                            listCountries[viewModel.idList(countriesList[element])].
                                                favorite == 0
                                    ) {
                                        Icon(Icons.TwoTone.Star, null)
                                    } else {
                                        Icon(Icons.Filled.Star, null)
                                    }
                                }
                            }
                        }
                    } else {
                        items(viewModel.listTownshipsUiState.value.listTownships.size) { element ->
                            val checked by remember {
                                mutableStateOf(viewModel.listTownshipsUiState.value.listTownships[element].favorite == 1)
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = viewModel.listTownshipsUiState.value.listTownships[element].township,
                                    style = typography.bodyLarge,
                                    modifier = Modifier.weight(1f)
                                )
                                IconToggleButton(
                                    checked = checked,
                                    onCheckedChange = {
                                        viewModel.updateItemTownship(
                                            element,
                                            viewModel.listTownshipsUiState.value.listTownships[element]
                                        )
                                    },
                                ) {
                                    if (viewModel.listTownshipsUiState.value.listTownships[countriesList[element].id].favorite == 0) {
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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetAddCity(
    isOpen: MutableState<Boolean>,
    listCountry: List<CountryDetailing>,
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
                .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = stringResource(R.string.add_city),
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

            OutlinedTextField(
                value = viewModel.addCityUiState.value.nameCountry,
                onValueChange = {
                    viewModel.updateAddCityNameCountry(nameCountry = it)
                },
                label = { Text(stringResource(R.string.name_country)) },
                singleLine = true,
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if (viewModel.addCityUiState.value.nameCountry.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Outlined.Clear,
                            contentDescription = stringResource(R.string.clear),
                            modifier = Modifier
                                .clickable {
                                    viewModel.updateAddCityNameCountry(nameCountry = "")
                                }
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            LazyColumn {
                items(listCountry.size) { element ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.updateAddCityCountry(listCountry[element])
                                Log.i(TAG, viewModel.addCityUiState.value.country.toString())
                            }
                    ) {
                        Text(
                            text = listCountry[element].country,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = listCountry[element].shortCountry,
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
fun LazyColumnSearchCountry(
    modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
//        if(state.value == 0) {
//
//        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetCurrency(
    isOpen: MutableState<Boolean>,
    listCurrency: List<Currency>
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
        ) {
            Text(
                text = stringResource(R.string.select_currency),
                style = typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(listCurrency.size) { element ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = if(Locale.getDefault().language == "ru") {
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


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FirstEntryTwoScreenPreview() {
    FirstEntryTwoScreen()
}
