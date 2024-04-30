package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import com.example.reportsfordrivers.ui.layouts.custom.OutlinedTextFieldCustomSearch
import com.example.reportsfordrivers.ui.layouts.custom.RowDateWithTextField
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.createreports.CreateRouteViewModel
import com.example.reportsfordrivers.viewmodel.createreports.uistate.RouteElement

@Composable
fun CreateReportsDataFillingTwoScreen(
    viewModel: CreateRouteViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    BackHandler {
        navController.navigate(
            ReportsForDriversSchema.VehicleInfo.name,
            navOptions = NavOptions.Builder()
                .setPopUpTo(ReportsForDriversSchema.VehicleInfo.name, true)
                .build()
        )
    }

    val scrollState = rememberScrollState()

    Column {
        TabRowDataFillingTwo(navController = navController, viewModel = viewModel)

        if(viewModel.uiStateCreateRoute.value.route.size == 0) {
            viewModel.uiStateCreateRoute.value.route.add(RouteElement(0, ""))
            viewModel.uiStateCreateRoute.value.route.add(RouteElement(1, ""))
        }

        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 16.dp)
                .verticalScroll(state = scrollState)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            for(i in 0..<viewModel.uiStateCreateRoute.value.route.size) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextFieldCustomSearch(
                        label = R.string.route,
                        value = viewModel.uiStateCreateRoute.value.route[i].text,
                        onValueChange = { viewModel.updateDataCreateRouteRoute(it, i) },
                        tag = "",
                        modifier = Modifier.weight(1f),
                        isOpenSearch = viewModel.openBottomSheetCountryCreateRoute
                    )
                    if(i == viewModel.uiStateCreateRoute.value.route.size - 1) {
                        IconButton(
                            onClick = {
                                viewModel.uiStateCreateRoute.value.route.add(
                                    RouteElement(i + 1, "")
                                )
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Add,
                                contentDescription = null
                            )
                        }
                    } else if(i != 0){
                        IconButton(
                            onClick = {
                                viewModel.uiStateCreateRoute.value.route.removeAt(i)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = null
                            )
                        }
                    }
                }
            }

            RowDateWithTextField(
                openDialog = viewModel.openDialogDateDepartureCreateRoute,
                date = viewModel.uiStateCreateRoute.value.dateDeparture,
                modifier = Modifier.weight(1f),
                text = R.string.date_departure,
            )

            RowDateWithTextField(
                openDialog = viewModel.openDialogDateReturnCreateRoute,
                date = viewModel.uiStateCreateRoute.value.dateReturn,
                modifier = Modifier.weight(1f),
                text = R.string.date_return
            )

            RowDateWithTextField(
                openDialog = viewModel.openDialogDateCrossingDepartureCreateRoute,
                date = viewModel.uiStateCreateRoute.value.dateCrossingDeparture,
                modifier = Modifier.weight(1f),
                text = R.string.date_border_crossing_departure
            )

            RowDateWithTextField(
                openDialog = viewModel.openDialogDateCrossingReturnCreateRoute,
                date = viewModel.uiStateCreateRoute.value.dateCrossingReturn,
                modifier = Modifier.weight(1f),
                text = R.string.date_border_crossing_return
            )

            OutlinedTextFieldCustom(
                label = R.string.speedometer_reading_departure,
                value = viewModel.uiStateCreateRoute.value.speedometerDeparture,
                onValueChange = viewModel::updateDataCreateRouteSpeedometerDeparture,
                tag = Tags.TAG_TEST_DATA_FILLING_TWO_SPEEDOMETER_DEPARTURE,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                ),
                isError = viewModel.validateSpeedometer()

            )

            OutlinedTextFieldCustom(
                label = R.string.speedometer_reading_return,
                value = viewModel.uiStateCreateRoute.value.speedometerReturn,
                onValueChange = viewModel::updateDataCreateRouteSpeedometerReturn,
                tag = Tags.TAG_TEST_DATA_FILLING_TWO_SPEEDOMETER_RETURN,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                ),
                isError = viewModel.validateSpeedometer()
            )

            OutlinedTextFieldCustom(
                label = R.string.fuelled,
                value = viewModel.uiStateCreateRoute.value.fuelled,
                onValueChange = viewModel::updateDataCreateRouteFuelled,
                tag = Tags.TAG_TEST_DATA_FILLING_TWO_FUELLED,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
        }

        BottomBarCustom(
            onNext = { navController.navigate(ReportsForDriversSchema.ProgressReport.name) },
            onBack = { navController.navigateUp() },
            enabled = viewModel.isValidateDataCreateRoute()
        )
    }
    DatePickerDialogCustom(
        viewModel.openDialogDateDepartureCreateRoute,
        viewModel::updateDataCreateRouteDateDeparture
    )
    DatePickerDialogCustom(
        viewModel.openDialogDateReturnCreateRoute,
        viewModel::updateDataCreateRouteDateReturn
    )
    DatePickerDialogCustom(
        viewModel.openDialogDateCrossingDepartureCreateRoute,
        viewModel::updateDataCreateRouteDateCrossingDeparture
    )
    DatePickerDialogCustom(
        viewModel.openDialogDateCrossingReturnCreateRoute,
        viewModel::updateDataCreateRouteDateCrossingReturn
    )
    BottomSheetSearchRoute(
        viewModel = viewModel
    )

    if(viewModel.openBottomAddCityCreateRoute.value) {
        BottomSheetAddCity(viewModel = viewModel)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetSearchRoute(
    viewModel: CreateRouteViewModel
) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (viewModel.openBottomSheetCountryCreateRoute.value) {
        ModalBottomSheet(
            onDismissRequest = {
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
    viewModel: CreateRouteViewModel,
    modifier: Modifier = Modifier
) {
    val searchTextCountry by viewModel.searchTextCountryCreateRoute.collectAsState()
    val countriesListCountry by viewModel.countriesListCountryCreateRoute.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        DockedSearchBar(
            query = searchTextCountry,
            onQueryChange = viewModel::onSearchTextChangeCountryCreateRoute,
            onSearch = viewModel::onSearchTextChangeCountryCreateRoute,
            active = false,
            onActiveChange = {
                viewModel.onToogleSearchCountryCreateRoute()
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
                        viewModel.sortCountryCreateRoute.intValue =
                            if(viewModel.sortCountryCreateRoute.intValue == 0) 1 else 0
                        viewModel.loadCountriesCreateRoute()
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.sort_24px),
                    contentDescription = null
                )
                Text(
                    text = if (viewModel.sortCountryCreateRoute.intValue == 0) {
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
                        value = viewModel.isCheckedFavoriteCountryCreateRoute.value,
                        onValueChange = {
                            viewModel.isCheckedFavoriteCountryCreateRoute.value =
                                !viewModel.isCheckedFavoriteCountryCreateRoute.value
                        },
                        role = Role.Checkbox
                    )
            ) {
                Checkbox(
                    checked = viewModel.isCheckedFavoriteCountryCreateRoute.value,
                    onCheckedChange = {
                        viewModel.isCheckedFavoriteCountryCreateRoute.value = it
                        viewModel.loadCountriesCreateRoute()
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
                            viewModel.openListSearchCreateReportInfo.intValue =1
                            viewModel.loadTownshipsCreateRoute(countriesListCountry[element].id)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnSearchTownship(
    viewModel: CreateRouteViewModel,
    modifier: Modifier = Modifier
) {

    val searchTextTownship by viewModel.searchTextTownshipCreateRoute.collectAsState()
    val townshipsListTownship by viewModel.townshipsListTownshipCreateRoute.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        DockedSearchBar(
            query = searchTextTownship,
            onQueryChange = viewModel::onSearchTextChangeTownshipCreateRoute,
            onSearch = viewModel::onSearchTextChangeTownshipCreateRoute,
            active = false,
            onActiveChange = {
                viewModel.onToogleSearchTownshipCreateRoute()
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
                    onClick = { viewModel.openAddCity() }
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
                        viewModel.sortTownshipCreateRoute.intValue =
                            if (viewModel.sortTownshipCreateRoute.intValue == 0) 1 else 0
                        viewModel.loadTownshipsCreateRoute(
                            viewModel.selectedCountryIdInSearch.intValue
                        )
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.sort_24px),
                    contentDescription = null
                )
                Text(
                    text = if (viewModel.sortTownshipCreateRoute.intValue == 0) {
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
                        value = viewModel.isCheckedFavoriteTownshipCreateRoute.value,
                        onValueChange = {
                            viewModel.isCheckedFavoriteTownshipCreateRoute.value =
                                !viewModel.isCheckedFavoriteTownshipCreateRoute.value
                        },
                        role = Role.Checkbox
                    )
            ) {
                Checkbox(
                    checked = viewModel.isCheckedFavoriteTownshipCreateRoute.value,
                    onCheckedChange = {
                        viewModel.isCheckedFavoriteTownshipCreateRoute.value = it
                        viewModel.loadTownshipsCreateRoute(
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
                    viewModel.loadCountriesCreateRoute()
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = null
                )
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(townshipsListTownship.size) { element ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.updateDataCreateRouteRoute(
                                townshipsListTownship[element].township,
                                viewModel.selectedRouteWrite.intValue
                            )
                            viewModel.updateRatingTownship(townshipsListTownship[element].id)
                            viewModel.closeBottomSheetSearch()
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (townshipsListTownship[element].favorite == 1) {
                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = null
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetAddCity(
    viewModel: CreateRouteViewModel
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val searchTextAddCity by viewModel.searchTextCountryAddCityCreateRoute.collectAsState()
    val countriesListAddCity by viewModel.countriesListCountryAddCityCreateRoute.collectAsState()

    ModalBottomSheet(
        onDismissRequest = { viewModel.openBottomAddCityCreateRoute.value = false },
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
                viewModel.updateNameCityAddCityCreateRoute(name = it)
            },
            label = { Text(stringResource(R.string.name_city)) },
            singleLine = true,
            textStyle = typography.bodyLarge,
            trailingIcon = {
                if(viewModel.uiStateAddCity.value.nameCity.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            viewModel.updateNameCityAddCityCreateRoute("")
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
            onQueryChange = viewModel::onSearchTextChangeCountryAddCityCreateRoute,
            onSearch = viewModel::onSearchTextChangeCountryAddCityCreateRoute,
            active = false,
            onActiveChange = {
                viewModel.onToogleSearchCountryAddCityCreateRoute()
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
                        viewModel.sortCountryAddCityCreateRoute.intValue =
                            if(viewModel.sortCountryAddCityCreateRoute.intValue == 0) 1 else 0
                        viewModel.loadCountriesAddCityCreateRoute()
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.sort_24px),
                    contentDescription = null
                )
                Text(
                    text = if(viewModel.sortCountryAddCityCreateRoute.intValue == 0) {
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
                        value = viewModel.isCheckedFavoriteCountryAddCityCreateRoute.value,
                        onValueChange = {
                            viewModel.isCheckedFavoriteCountryAddCityCreateRoute.value =
                                !viewModel.isCheckedFavoriteCountryAddCityCreateRoute.value
                        },
                        role = Role.Checkbox
                    )
            ) {
                Checkbox(
                    checked = viewModel.isCheckedFavoriteCountryAddCityCreateRoute.value,
                    onCheckedChange = {
                        viewModel.isCheckedFavoriteCountryAddCityCreateRoute.value = it
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
                onClick = {}
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
                            viewModel.updateNameCountryAddCityCreateRoute(countriesListAddCity[element])
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
private fun TabRowDataFillingTwo(
    navController: NavHostController,
    viewModel: CreateRouteViewModel
) {
    TabRow(selectedTabIndex = 3) {
        Tab(
            text = { Text("1") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.ReportInfo.name) }
        )
        Tab(
            text = { Text("2") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.PersonalInfo.name) }
        )
        Tab(
            text = { Text("3") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.VehicleInfo.name) }
        )
        Tab(
            text = { Text("4") },
            selected = false,
            onClick = { },
            enabled = false
        )
        Tab(
            text = { Text("5") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.ProgressReport.name) },
            enabled = viewModel.uiStateIsValidate.value.isValidateCreateRoute
        )
        Tab(
            text = { Text("6") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.TripExpenses.name) },
            enabled = viewModel.uiStateIsValidate.value.isValidateCreateRoute &&
                    viewModel.uiStateIsValidate.value.isValidateCreateProgressReports
        )
    }
}


