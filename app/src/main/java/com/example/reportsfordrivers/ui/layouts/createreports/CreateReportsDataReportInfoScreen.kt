package com.example.reportsfordrivers.ui.layouts.createreports

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.ui.layouts.custom.BottomBarCustom
import com.example.reportsfordrivers.ui.layouts.custom.DatePickerDialogCustom
import com.example.reportsfordrivers.ui.layouts.custom.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.layouts.custom.RowDateWithTextField
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportInfoViewModel

private const val TAG = "CreateReportsDataReportInfoScreen"

@Composable
fun CreateReportsDataReportInfoScreen(
    viewModel: CreateReportInfoViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {

    BackHandler {
        navController.navigate(
            ReportsForDriversSchema.Start.name,
            navOptions = NavOptions.Builder()
                .setPopUpTo(ReportsForDriversSchema.Start.name, true)
                .build()
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TabRowReportInfoScreen(navController = navController, viewModel = viewModel)

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp, start = 10.dp, top = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            RowDateWithTextField(
                openDialog = viewModel.openDialogDateCreateReportInfo,
                date = viewModel.uiStateCreateReportInfo.value.date,
                modifier = Modifier.weight(1f),
                text = R.string.date_create_report
            )

            OutlinedTextField(
                value = viewModel.uiStateCreateReportInfo.value.mainCity,
                onValueChange = viewModel::updateDataCreateReportInfoMainCity,
                label = { Text(stringResource(R.string.township)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = typography.bodyLarge,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = true
                ),
                trailingIcon = {
                    if (viewModel.uiStateCreateReportInfo.value.mainCity.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                viewModel.updateDataCreateReportInfoMainCity("")
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = null
                            )
                        }
                    } else {
                        IconButton(
                            onClick = {
                                viewModel.openBottomSearchCreateReportInfo.value = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = null
                            )
                        }
                    }
                }
            )

            OutlinedTextFieldCustom(
                label = R.string.waybill,
                value = viewModel.uiStateCreateReportInfo.value.waybill,
                onValueChange = viewModel::updateDataCreateReportInfoWaybill,
                tag = Tags.TAG_TEST_DATA_REPORT_INFO_WAYBILL,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        BottomBarCustom(
            onNext = { navController.navigate(ReportsForDriversSchema.PersonalInfo.name) },
            onBack = { navController.navigateUp() },
            enabled = viewModel.isValidateDateCreateReportInfo()
        )
    }

    DatePickerDialogCustom(
        openDialog = viewModel.openDialogDateCreateReportInfo,
        onValueChange = viewModel::updateDataCreateReportInfoDate
    )

    BottomSheetSearch(
        viewModel = viewModel
    )

    if(viewModel.openBottomAddCityCreateReportInfo.value) {
        BottomSheetAddCity(viewModel = viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetSearch(
    viewModel: CreateReportInfoViewModel
) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (viewModel.openBottomSearchCreateReportInfo.value) {
        ModalBottomSheet(
            onDismissRequest = {
//                viewModel.openBottomSearchCreateReportInfo.value = false
                viewModel.closeBottomSheetSearch()
            },
            sheetState = sheetState,
            modifier = Modifier.fillMaxHeight(0.75f)
        ) {
            Column(
                modifier = Modifier.fillMaxHeight()
            ) {
                if (viewModel.openListSearchCreateReportInfo.intValue == 0) {
                    ColumnSearchCountry(
                        viewModel = viewModel,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    ColumnSearchTownship(
                        viewModel = viewModel,
                        modifier = Modifier.weight(1f)
                    )
                }
                Button(
                    onClick = {
                        viewModel.closeBottomSheetSearch()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnSearchCountry(
    viewModel: CreateReportInfoViewModel,
    modifier: Modifier = Modifier,
) {
    val searchTextCountry by viewModel.searchTextCountryCreateReportInfo.collectAsState()
    val countriesListCountry by viewModel.countriesListCountryCreateReportInfo.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        DockedSearchBar(
            query = searchTextCountry,
            onQueryChange = viewModel::onSearchTextChangeCountryCreateReportInfo,
            onSearch = viewModel::onSearchTextChangeCountryCreateReportInfo,
            active = false,
            onActiveChange = {
                viewModel.onToogleSearchCountryCreateReportInfo()
            },
            placeholder = {
                Text(text = stringResource(R.string.countries))
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
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.sortCountryCreateReportInfo.intValue =
                            if (viewModel.sortCountryCreateReportInfo.intValue == 0) 1 else 0
                        viewModel.loadCountriesCreateReportInfo()
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.sort_24px),
                    contentDescription = null
                )
                Text(
                    text = if (viewModel.sortCountryCreateReportInfo.intValue == 0) {
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
                        value = viewModel.isCheckedFavoriteCountryCreateReportInfo.value,
                        onValueChange = {
                            viewModel.isCheckedFavoriteCountryCreateReportInfo.value =
                                !viewModel.isCheckedFavoriteCountryCreateReportInfo.value
                        },
                        role = Role.Checkbox
                    )
            ) {
                Checkbox(
                    checked = viewModel.isCheckedFavoriteCountryCreateReportInfo.value,
                    onCheckedChange = {
                        viewModel.isCheckedFavoriteCountryCreateReportInfo.value = it
                        viewModel.loadCountriesCreateReportInfo()
                    }
                )
                Text(
                    text = stringResource(R.string.favorite)
                )
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(countriesListCountry.size) { element ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (countriesListCountry[element].favorite == 1) {
                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = null
                        )
                    }

                    Text(
                        text = countriesListCountry[element].country,
                        style = typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = {
                            viewModel.openListSearchCreateReportInfo.intValue = 1
                            viewModel.loadTownshipsCreateReportInfo(countriesListCountry[element].id)
                            viewModel.selectedCountryIdInSearch.intValue =
                                countriesListCountry[element].id
                            viewModel.selectedCountryNameInSearch.value =
                                countriesListCountry[element].country
                            viewModel.updateRatingCountry(countriesListCountry[element].id)
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
}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnSearchTownship(
    viewModel: CreateReportInfoViewModel,
    modifier: Modifier = Modifier
) {

    val searchTextTownship by viewModel.searchTextTownshipCreateReportInfo.collectAsState()
    val townshipsListTownship by viewModel.townshipsListTownshipCreateReportInfo.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        DockedSearchBar(
            query = searchTextTownship,
            onQueryChange = viewModel::onSearchTextChangeTownshipCreateReportInfo,
            onSearch = viewModel::onSearchTextChangeTownshipCreateReportInfo,
            active = false,
            onActiveChange = {
                viewModel.onToogleSearchTownshipCreateReportInfo()
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
                        viewModel.openAddCity()
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

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.sortTownshipCreateReportInfo.intValue =
                            if (viewModel.sortTownshipCreateReportInfo.intValue == 0) 1 else 0
                        viewModel.loadTownshipsCreateReportInfo(
                            viewModel.selectedCountryIdInSearch.intValue
                        )
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.sort_24px),
                    contentDescription = null
                )
                Text(
                    text = if (viewModel.sortTownshipCreateReportInfo.intValue == 0) {
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
                        value = viewModel.isCheckedFavoriteTownshipsCreateReportInfo.value,
                        onValueChange = {
                            viewModel.isCheckedFavoriteTownshipsCreateReportInfo.value =
                                !viewModel.isCheckedFavoriteTownshipsCreateReportInfo.value
                        },
                        role = Role.Checkbox
                    )
            ) {
                Checkbox(
                    checked = viewModel.isCheckedFavoriteTownshipsCreateReportInfo.value,
                    onCheckedChange = {
                        viewModel.isCheckedFavoriteTownshipsCreateReportInfo.value = it
                        viewModel.loadTownshipsCreateReportInfo(
                            viewModel.selectedCountryIdInSearch.intValue
                        )
                    }
                )
                Text(
                    text = stringResource(R.string.favorite)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = viewModel.selectedCountryNameInSearch.value
            )
            IconButton(
                onClick = {
                    viewModel.openListSearchCreateReportInfo.intValue = 0
                    viewModel.loadCountriesCreateReportInfo()
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = null
                )
            }
        }

        if(viewModel.townshipsListTownshipCreateReportInfo.value.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                EmptySearchTownship(isOpenAddCity = viewModel::openAddCity)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(townshipsListTownship.size) { element ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.updateDataCreateReportInfoMainCity(
                                    townshipsListTownship[element].township
                                )
                                viewModel.updateRatingTownship(townshipsListTownship[element].id)
                                viewModel.closeBottomSheetSearch()
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (townshipsListTownship[element].favorite == 1) {
                            Icon(
                                imageVector = Icons.Outlined.Star,
                                contentDescription = null,
                            )
                        }
                        Text(
                            text = townshipsListTownship[element].township,
                            style = typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetAddCity(
    viewModel: CreateReportInfoViewModel
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val searchTextAddCity by viewModel.searchTextCountryAddCityCreateReportInfo.collectAsState()
    val countriesListAddCity by viewModel.countriesListCountryAddCityCreateReportInfo.collectAsState()

    ModalBottomSheet(
        onDismissRequest = { viewModel.openBottomAddCityCreateReportInfo.value = false },
        sheetState = sheetState,
        modifier = Modifier.fillMaxHeight(0.75f)
    ) {
        Text(
            text = stringResource(R.string.add_city),
            style = typography.headlineSmall
        )

        OutlinedTextField(
            value = viewModel.uiStateAddCity.value.nameCity,
            onValueChange = {
                viewModel.updateNameCityAddCityCreateReportInfo(name = it)
            },
            label = { Text(stringResource(R.string.name_city)) },
            singleLine = true,
            textStyle = typography.bodyLarge,
            trailingIcon = {
                if (viewModel.uiStateAddCity.value.nameCity.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            viewModel.updateNameCityAddCityCreateReportInfo("")
                        }
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
            onQueryChange = viewModel::onSearchTextChangeCountryAddCityCreateReportInfo,
            onSearch = viewModel::onSearchTextChangeCountryAddCityCreateReportInfo,
            active = false,
            onActiveChange = {
                viewModel.onToogleSearchCountryAddCityCreateReportInfo()
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

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.sortCountryAddCityCreateReportInfo.intValue =
                            if (viewModel.sortCountryAddCityCreateReportInfo.intValue == 0) 1 else 0
                        viewModel.loadCountriesAddCityCreateRoute()
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.sort_24px),
                    contentDescription = null
                )
                Text(
                    text = if (viewModel.sortCountryAddCityCreateReportInfo.intValue == 0) {
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
                        value = viewModel.isCheckedFavoriteCountryAddCityCreateReportInfo.value,
                        onValueChange = {
                            viewModel.isCheckedFavoriteCountryAddCityCreateReportInfo.value =
                                !viewModel.isCheckedFavoriteCountryAddCityCreateReportInfo.value
                        },
                        role = Role.Checkbox
                    )
            ) {
                Checkbox(
                    checked = viewModel.isCheckedFavoriteCountryAddCityCreateReportInfo.value,
                    onCheckedChange = {
                        viewModel.isCheckedFavoriteCountryAddCityCreateReportInfo.value = it
                        viewModel.loadCountriesAddCityCreateRoute()
                    }
                )
                Text(
                    text = stringResource(R.string.favorite)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = viewModel.uiStateAddCity.value.country.country
            )
            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = stringResource(R.string.clear)
                )
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
                        .clickable {
                            viewModel.updateNameCountryAddCityCreateReportInfo(countriesListAddCity[element])
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

        Button(
            onClick = {
                viewModel.closeAddCity()
                viewModel.saveAddCityInBd()
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
private fun TabRowReportInfoScreen(
    navController: NavHostController,
    viewModel: CreateReportInfoViewModel
) {
    TabRow(selectedTabIndex = 0) {
        Tab(
            text = { Text("1") },
            selected = false,
            onClick = { },
            enabled = false
        )
        Tab(
            text = {
                Text(
                    text = "2",
                )
            },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.PersonalInfo.name) },
            enabled = viewModel.uiStateIsValidate.value.isValidateCreateReportInfo,
            modifier = Modifier
        )
        Tab(
            text = { Text("3") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.VehicleInfo.name) },
            enabled = viewModel.uiStateIsValidate.value.isValidateCreateReportInfo &&
                    viewModel.uiStateIsValidate.value.isValidateCreatePersonalInfo
        )
        Tab(
            text = { Text("4") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.FillingDataTwo.name) },
            enabled = viewModel.uiStateIsValidate.value.isValidateCreateReportInfo &&
                    viewModel.uiStateIsValidate.value.isValidateCreatePersonalInfo &&
                    viewModel.uiStateIsValidate.value.isValidateCreateVehicleTrailer
        )
        Tab(
            text = { Text("5") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.ProgressReport.name) },
            enabled = viewModel.uiStateIsValidate.value.isValidateCreateReportInfo &&
                    viewModel.uiStateIsValidate.value.isValidateCreatePersonalInfo &&
                    viewModel.uiStateIsValidate.value.isValidateCreateVehicleTrailer &&
                    viewModel.uiStateIsValidate.value.isValidateCreateRoute
        )
        Tab(
            text = { Text("6") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.TripExpenses.name) },
            enabled = viewModel.uiStateIsValidate.value.isValidateCreateReportInfo &&
                    viewModel.uiStateIsValidate.value.isValidateCreatePersonalInfo &&
                    viewModel.uiStateIsValidate.value.isValidateCreateVehicleTrailer &&
                    viewModel.uiStateIsValidate.value.isValidateCreateRoute &&
                    viewModel.uiStateIsValidate.value.isValidateCreateProgressReports
        )
    }
}

@Composable
private fun EmptySearchTownship(isOpenAddCity: () -> Unit) {
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