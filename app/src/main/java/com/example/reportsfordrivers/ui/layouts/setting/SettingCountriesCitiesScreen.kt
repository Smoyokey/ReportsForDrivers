package com.example.reportsfordrivers.ui.layouts.setting

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.setting.CountriesAndCitiesViewModel

private const val TAG = "SettingCountriesCitiesScreen"

@Composable
fun SettingCountriesCitiesScreen(
    viewModel: CountriesAndCitiesViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TabRow(selectedTabIndex = viewModel.stateTab.intValue) {

            Tab(
                text = { Text(text = stringResource(R.string.countries)) },
                selected = viewModel.stateTab.intValue == 0,
                onClick = { viewModel.stateTab.intValue = 0 }
            )
            Tab(
                text = { Text(text = stringResource(R.string.cities)) },
                selected = viewModel.stateTab.intValue == 1,
                onClick = { viewModel.stateTab.intValue = 1 }
            )
        }

        if (viewModel.stateTab.intValue == 0) {
            SearchCountrySetting(viewModel = viewModel)
        } else {
            SearchTownshipSetting(viewModel = viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCountrySetting(
    modifier: Modifier = Modifier,
    viewModel: CountriesAndCitiesViewModel,

    ) {
    val searchTextCountry by viewModel.searchTextCountrySetting.collectAsState()


    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        DockedSearchBar(
            query = searchTextCountry,
            onQueryChange = viewModel::onSearchTextChangeCountrySetting,
            onSearch = viewModel::onSearchTextChangeCountrySetting,
            active = false,
            onActiveChange = {
                viewModel.onToogleSearchCountrySetting()
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.countries)
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

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(R.drawable.sort_24px),
                    contentDescription = null
                )
                Text(
                    text = if (viewModel.sortCountry.intValue == 0) {
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
                        value = viewModel.isCheckedFavoriteCountry.value,
                        onValueChange = {
                            viewModel.isCheckedFavoriteCountry.value =
                                !viewModel.isCheckedFavoriteCountry.value
                        },
                        role = Role.Checkbox
                    )
            ) {
                Checkbox(
                    checked = viewModel.isCheckedFavoriteCountry.value,
                    onCheckedChange = null
                )
                Text(
                    text = stringResource(R.string.favorite)
                )
            }
        }

        val countriesListCountry by viewModel.countriesListCountrySetting.collectAsState()
        Log.i(TAG, countriesListCountry.joinToString("::"))
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(countriesListCountry.size) { element ->
                val checked by remember {
                    mutableStateOf(viewModel.uiStateCountry.value.listCountries[element].favorite == 1)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            viewModel.openSelectedTownship(countriesListCountry[element].id)
                            viewModel.stateTab.intValue = 1
                            viewModel.selectedCountrySearch.value =
                                countriesListCountry[element].country
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = null
                        )
                    }
                    Text(
                        text = countriesListCountry[element].country,
                        style = typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )
                    IconToggleButton(
                        checked = checked,
                        onCheckedChange = {
                            viewModel.updateItemCountrySetting(countriesListCountry[element])
                        }
                    ) {
                        if (
                            viewModel.uiStateCountry.value.listCountries[viewModel.idListCountrySetting(
                                countriesListCountry[element]
                            )].favorite == 0
                        ) {
                            Icon(
                                imageVector = Icons.TwoTone.Star, contentDescription = null
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.Star, contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTownshipSetting(
    viewModel: CountriesAndCitiesViewModel
) {

    val searchTextTownship by viewModel.searchTextTownshipSetting.collectAsState()
    val townshipsListTownship by viewModel.townshipsListTownshipSetting.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        DockedSearchBar(
            query = searchTextTownship,
            onQueryChange = viewModel::onSearchTextChangeTownshipSetting,
            onSearch = viewModel::onSearchTextChangeTownshipSetting,
            active = false,
            onActiveChange = {
                viewModel.onToogleSearchTownshipSetting()
            },
            placeholder = {
                Text(text = stringResource(R.string.cities))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = stringResource(R.string.add)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {}

        if (viewModel.townshipsListTownshipSetting.value.isEmpty()) {
            if (viewModel.selectedCountrySearch.value != "") {
                Column {
                    RowSearchCountrySetting(viewModel.selectedCountrySearch.value, viewModel)
                    EmptySearchTownshipSetting(mutableStateOf(false))
                }
            } else {
                EmptySearchTownshipSetting(mutableStateOf(false))
            }
        } else {
            if (viewModel.selectedCountrySearch.value != "") {
                RowSearchCountrySetting(viewModel.selectedCountrySearch.value, viewModel)
            }

            LazyColumn() {
                items(townshipsListTownship.size) { element ->

                    val checked by remember {
                        mutableStateOf(viewModel.uiStateTownship.value.listTownships[element].favorite == 1)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = townshipsListTownship[element].township,
                            style = typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )

                        IconToggleButton(
                            checked = checked,
                            onCheckedChange = {
                                viewModel.updateItemTownshipSetting(townshipsListTownship[element])
                            }
                        ) {
                            if (viewModel.uiStateTownship.value.listTownships[viewModel.idListTownshipSetting(
                                    townshipsListTownship[element]
                                )].favorite == 0
                            ) {
                                Icon(imageVector = Icons.TwoTone.Star, contentDescription = null)
                            } else {
                                Icon(imageVector = Icons.Filled.Star, contentDescription = null)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RowSearchCountrySetting(name: String, viewModel: CountriesAndCitiesViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = name,
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = {
                viewModel.stateTab.intValue = 0
                viewModel.selectedCountrySearch.value = ""
            }
        ) {
            Icon(imageVector = Icons.Outlined.Clear, contentDescription = null)
        }
    }
}

@Composable
fun EmptySearchTownshipSetting(isOpenAddCity: MutableState<Boolean>) {
    TextButton(
        onClick = {},
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.not_list_add),
            textAlign = TextAlign.Center
        )
    }
}