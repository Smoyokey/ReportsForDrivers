package com.example.reportsfordrivers.ui.layouts.createreports

import android.annotation.SuppressLint
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
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.ui.layouts.custom.BottomBarCustom
import com.example.reportsfordrivers.ui.layouts.custom.DatePickerDialogCustom
import com.example.reportsfordrivers.ui.layouts.custom.OutlinedTextFieldCustomSearch
import com.example.reportsfordrivers.ui.layouts.custom.RowDateWithTextField
import com.example.reportsfordrivers.ui.layouts.custom.TabRowCustom
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.createreports.CreateRouteViewModel
import com.example.reportsfordrivers.viewmodel.createreports.uistate.RouteElement
import com.example.reportsfordrivers.viewmodel.firstentry.CountryDetailing

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateReportsDataFillingTwoScreen(
    viewModel: CreateRouteViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {

    val (dateDeparture, dateReturn, dateCrossingDeparture, dateCrossingReturn,
        speedometerDeparture, speedometerReturn, fuelled) = remember { FocusRequester.createRefs() }
    val keyboardController = LocalSoftwareKeyboardController.current

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
        TabRowCustom(
            index = 3,
            navController = navController,
            isEnabledFive = viewModel.uiStateIsValidate.value.isValidateCreateRoute,
            isEnabledSix = viewModel.uiStateIsValidate.value.isValidateCreateProgressReports
        )

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

            OutlinedTextField(
                value = viewModel.uiStateCreateRoute.value.speedometerDeparture,
                label = { Text(stringResource(R.string.speedometer_reading_departure)) },
                onValueChange = { viewModel.updateDataCreateRouteSpeedometerDeparture(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(speedometerDeparture),
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if(viewModel.uiStateCreateRoute.value.speedometerDeparture.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.updateDataCreateRouteSpeedometerDeparture("")}
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = stringResource(R.string.clear)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(onNext = {speedometerReturn.requestFocus()}),
                isError = viewModel.validateSpeedometer()
            )

            OutlinedTextField(
                value = viewModel.uiStateCreateRoute.value.speedometerReturn,
                label = { Text(stringResource(R.string.speedometer_reading_return)) },
                onValueChange = { viewModel.updateDataCreateRouteSpeedometerReturn(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(speedometerReturn),
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if(viewModel.uiStateCreateRoute.value.speedometerReturn.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.updateDataCreateRouteSpeedometerReturn("") }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = stringResource(R.string.clear)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {fuelled.requestFocus()}),
                isError = viewModel.validateSpeedometer()
            )

            OutlinedTextField(
                value = viewModel.uiStateCreateRoute.value.fuelled,
                label = { Text(stringResource(R.string.fuelled)) },
                onValueChange = { viewModel.updateDataCreateRouteFuelled(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(fuelled),
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if(viewModel.uiStateCreateRoute.value.fuelled.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.updateDataCreateRouteFuelled("") }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = stringResource(R.string.clear)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()})
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

    BottomSheetAddCity(
        viewModel = viewModel
    )
}




/**
 * ПОИСКОВИК аааа
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetSearchRoute(
    viewModel: CreateRouteViewModel
) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if(viewModel.openBottomSheetCountryCreateRoute.value) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.closeBottomSheetSearch() },
            sheetState = sheetState,
            modifier = Modifier.fillMaxHeight(0.75f)
        ) {
            Column( modifier = Modifier.fillMaxHeight() ) {
                if(viewModel.openListSearchCreateRoute.intValue == 0) {
                    ColumnSearchCountry(viewModel = viewModel)
                } else {
                    ColumnSearchTownship(viewModel = viewModel)
                }
                Button(
                    onClick = { viewModel.closeBottomSheetSearch() },
                    modifier = Modifier.fillMaxWidth()
                ) { Text(text = stringResource(R.string.cancel)) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ColumnSearchCountry(
    viewModel: CreateRouteViewModel,
    modifier: Modifier = Modifier
) {
    val searchTextCountry by viewModel.searchTextCountry.collectAsState()
    val countriesListCountry by viewModel.countriesListCountry.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        DockedSearchBar(
            query = searchTextCountry,
            onQueryChange = viewModel::onSearchTextChangeCountry,
            onSearch = viewModel::onSearchTextChangeCountry,
            active = false,
            onActiveChange = { viewModel.onToogleSearchCountry() },
            placeholder = { Text(text = stringResource(R.string.countries)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {}

        Row( modifier = Modifier.fillMaxWidth() ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.sortCountry.intValue = if(viewModel.sortCountry.intValue == 0) 1 else 0
                        viewModel.loadCountries()
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.sort_24px),
                    contentDescription = null
                )
                Text(
                    text = if(viewModel.sortCountry.intValue == 0) {
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
                            viewModel.isCheckedFavoriteCountry.value = it
                            viewModel.loadCountries()
                        },
                        role = Role.Checkbox
                    )
            ) {
                Checkbox(
                    checked = viewModel.isCheckedFavoriteCountry.value,
                    onCheckedChange = {
                        viewModel.isCheckedFavoriteCountry.value = it
                        viewModel.loadCountries()
                    }
                )
                Text( text = stringResource(R.string.favorite) )
            }
        }

        LazyColumn( modifier = Modifier.weight(1f) ) {
            items(countriesListCountry.size) { element ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if(countriesListCountry[element].favorite == 1) {
                        Icon(imageVector = Icons.Outlined.Star, contentDescription = null)
                    }

                    Text(
                        text = countriesListCountry[element].country,
                        style = typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = {
                            viewModel.openListSearchCreateRoute.intValue = 1
                            viewModel.loadTownships(countriesListCountry[element].id)
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
private fun ColumnSearchTownship(
    viewModel: CreateRouteViewModel,
    modifier: Modifier = Modifier
) {
    val searchTextTownship by viewModel.searchTextTownship.collectAsState()
    val townshipsListTownship by viewModel.townshipsListTownship.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        DockedSearchBar(
            query = searchTextTownship,
            onQueryChange = viewModel::onSearchTextChangeTownship,
            onSearch = viewModel::onSearchTextChangeTownship,
            active = false,
            onActiveChange = { viewModel.onToogleSearchTownship() },
            placeholder = { Text(text = stringResource(R.string.cities)) },
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
            },
            trailingIcon = {
                IconButton( onClick = { viewModel.openAddCity() } ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = stringResource(R.string.add)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {}

        Row( modifier = Modifier.fillMaxWidth() ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.sortTownship.intValue =
                            if(viewModel.sortTownship.intValue == 0) 1 else 0
                        viewModel.loadTownships(viewModel.selectedCountryIdInSearch.intValue)
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.sort_24px),
                    contentDescription = null
                )
                Text(
                    text = if(viewModel.sortTownship.intValue == 0) {
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
                        value = viewModel.isCheckedFavoriteTownship.value,
                        onValueChange = {
                            viewModel.isCheckedFavoriteTownship.value =
                                !viewModel.isCheckedFavoriteTownship.value
                        },
                        role = Role.Checkbox
                    )
            ) {
                Checkbox(
                    checked = viewModel.isCheckedFavoriteTownship.value,
                    onCheckedChange = {
                        viewModel.isCheckedFavoriteTownship.value = it
                        viewModel.loadTownships(viewModel.selectedCountryIdInSearch.intValue)
                    }
                )
                Text(
                    text = stringResource(R.string.favorite)
                )
            }
        }

        Row( modifier = Modifier.fillMaxWidth() ) {
            Text(
                text = viewModel.selectedCountryNameInSearch.value
            )
            IconButton(
                onClick = {
                    viewModel.openListSearchCreateRoute.intValue = 0
                    viewModel.loadCountries()
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = null
                )
            }
        }

        if(viewModel.townshipsListTownship.value.isEmpty()) {
            Column( modifier = Modifier.fillMaxWidth() ) {
                EmptySearchTownshipReports(isOpenAddCity = viewModel::openAddCity)
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
                                viewModel.updateDataCreateRouteRoute(
                                    townshipsListTownship[element].township,
                                    viewModel.selectedRouteWrite.intValue
                                )
                                viewModel.updateRatingTownship(townshipsListTownship[element].id)
                                viewModel.closeBottomSheetSearch()
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if(townshipsListTownship[element].favorite == 1) {
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetAddCity(
    viewModel: CreateRouteViewModel
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if(viewModel.openBottomAddCityCreateRoute.value) {
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
                onValueChange = { viewModel.updateNameCityAddCityCreateRoute(name = it) },
                label = { Text(stringResource(R.string.name_city)) },
                singleLine = true,
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if(viewModel.uiStateAddCity.value.nameCity.isNotEmpty()) {
                        IconButton( onClick = { viewModel.updateNameCityAddCityCreateRoute("")}) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = stringResource(R.string.clear)
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            ColumnSearchCountryAddCity(
                viewModel = viewModel,
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {
                    viewModel.closeAddCity()
                    viewModel.saveAddCityInBd()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.validateAddCity()
            ) {
                Text( text = stringResource(R.string.save) )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ColumnSearchCountryAddCity(
    viewModel: CreateRouteViewModel,
    modifier: Modifier = Modifier
) {
    val searchTextCountryAddCity by viewModel.searchTextCountry.collectAsState()
    val countriesListCountryAddCity by viewModel.countriesListCountry.collectAsState()

    Column(modifier = modifier) {
        DockedSearchBar(
            query = searchTextCountryAddCity,
            onQueryChange = viewModel::onSearchTextChangeCountry,
            onSearch = viewModel::onSearchTextChangeCountry,
            active = false,
            onActiveChange = { viewModel.onToogleSearchCountry() },
            placeholder = {
                Text(
                    text = stringResource(R.string.country),
                    style = typography.bodyLarge
                )
            },
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
            },
            modifier = Modifier.fillMaxWidth()
        ) {}

        Row( modifier = Modifier.fillMaxWidth() ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.sortCountry.intValue = if(viewModel.sortCountry.intValue == 0) 1 else 0
                        viewModel.loadCountries()
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.sort_24px),
                    contentDescription = null
                )
                Text(
                    text = if(viewModel.sortCountry.intValue == 0) {
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
                    onCheckedChange = {
                        viewModel.isCheckedFavoriteCountry.value = it
                        viewModel.loadCountries()
                    }
                )
                Text( text = stringResource(R.string.favorite) )
            }
        }

        if(viewModel.uiStateAddCity.value.country.country.isNotEmpty()) {
            Row( modifier = Modifier.fillMaxWidth() ) {
                Text( text = viewModel.uiStateAddCity.value.country.country )
                IconButton(
                    onClick = {
                        viewModel.updateNameCountryAddCityCreateRoute(CountryDetailing())
                    }
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
            items(countriesListCountryAddCity.size) { element ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.updateNameCountryAddCityCreateRoute(countriesListCountryAddCity[element])
                        }
                ) {
                    Text(
                        text = countriesListCountryAddCity[element].country,
                        modifier = Modifier.weight(1f),
                        style = typography.titleLarge
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptySearchTownshipReports(isOpenAddCity: () -> Unit) {
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


