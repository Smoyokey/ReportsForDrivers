package com.example.reportsfordrivers.ui.layouts.hirstoryreports.editreport

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
import com.example.reportsfordrivers.viewmodel.createreports.uistate.RouteElement
import com.example.reportsfordrivers.viewmodel.firstentry.CountryDetailing
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.viewmodel.EditRouteViewModel

private const val TAG = "EditReportDataRouteScreen"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditReportDataRouteScreen(
    viewModel: EditRouteViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {

    val (dateDeparture, dateReturn, dateCrossingDeparture, dateCrossingReturn,
        speedometerDeparture, speedometerReturn, fuelled) = remember { FocusRequester.createRefs() }
    val keyboardController = LocalSoftwareKeyboardController.current

    BackHandler {
        navController.navigate(
            ReportsForDriversSchema.EditVehicleInfo.name,
            navOptions = NavOptions.Builder()
                .setPopUpTo(ReportsForDriversSchema.EditVehicleInfo.name, true)
                .build()
        )
    }

    val scrollState = rememberScrollState()

    Column {
        TabRowCustom(
            index = 3,
            navController = navController,
            isEnabledFour = false
        )

        if (viewModel.uiStateEditRoute.value.route.size == 0) {
            viewModel.uiStateEditRoute.value.route.add(RouteElement(0, ""))
            viewModel.uiStateEditRoute.value.route.add(RouteElement(1, ""))
        }

        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 16.dp)
                .verticalScroll(state = scrollState)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            for(i in 0..<viewModel.uiStateEditRoute.value.route.size) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextFieldCustomSearch(
                        label = R.string.route,
                        value = viewModel.uiStateEditRoute.value.route[i].text,
                        onValueChange = { viewModel.updateDataEditRouteRoute(it, i) },
                        tag = "",
                        modifier = Modifier.weight(1f),
                        isOpenSearch = viewModel.openBottomSheetCountryEditRoute
                    )
                    if(i == viewModel.uiStateEditRoute.value.route.size - 1) {
                        IconButton(
                            onClick = {
                                viewModel.uiStateEditRoute.value.route.add(
                                    RouteElement(i + 1, "")
                                )
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Add,
                                contentDescription = null
                            )
                        }
                    } else if (i != 0) {
                        IconButton(
                            onClick = {
                                viewModel.uiStateEditRoute.value.route.removeAt(i)
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
                openDialog = viewModel.openDialogDateDepartureEditRoute,
                date = viewModel.uiStateEditRoute.value.dateDeparture,
                modifier = Modifier.weight(1f),
                text = R.string.date_departure
            )

            RowDateWithTextField(
                openDialog = viewModel.openDialogDateReturnEditRoute,
                date = viewModel.uiStateEditRoute.value.dateReturn,
                modifier = Modifier.weight(1f),
                text = R.string.date_return
            )

            RowDateWithTextField(
                openDialog = viewModel.openDialogDateCrossingDepartureEditRoute,
                date = viewModel.uiStateEditRoute.value.dateCrossingDeparture,
                modifier = Modifier.weight(1f),
                text = R.string.date_border_crossing_departure
            )

            RowDateWithTextField(
                openDialog = viewModel.openDialogDateCrossingReturnEditRoute,
                date = viewModel.uiStateEditRoute.value.dateCrossingReturn,
                modifier = Modifier.weight(1f),
                text = R.string.date_border_crossing_return
            )

            OutlinedTextField(
                value = viewModel.uiStateEditRoute.value.speedometerDeparture,
                label = { Text(stringResource(R.string.speedometer_reading_departure)) },
                onValueChange = { viewModel.updateDataEditRouteSpeedometerDeparture(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(speedometerDeparture),
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if (viewModel.uiStateEditRoute.value.speedometerDeparture.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.updateDataEditRouteSpeedometerDeparture("") }
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
                value = viewModel.uiStateEditRoute.value.speedometerReturn,
                label = { Text(stringResource(R.string.speedometer_reading_return)) },
                onValueChange = { viewModel.updateDataEditRouteSpeedometerReturn(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(speedometerReturn),
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if(viewModel.uiStateEditRoute.value.speedometerReturn.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.updateDataEditRouteSpeedometerReturn("") }
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
                value = viewModel.uiStateEditRoute.value.fuelled,
                label = { Text(stringResource(R.string.fuelled)) },
                onValueChange = { viewModel.updateDataEditRouteFuelled(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(fuelled),
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if(viewModel.uiStateEditRoute.value.fuelled.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.updateDataEditRouteFuelled("") }
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
            onNext = { navController.navigate(ReportsForDriversSchema.EditProgressReport.name) },
            onBack = { navController.navigateUp() },
            enabled = viewModel.isValidateDataEditRoute()
        )
    }

    DatePickerDialogCustom(
        viewModel.openDialogDateDepartureEditRoute,
        viewModel::updateDataEditRouteDateDeparture
    )
    DatePickerDialogCustom(
        viewModel.openDialogDateReturnEditRoute,
        viewModel::updateDataEditRouteDateReturn
    )
    DatePickerDialogCustom(
        viewModel.openDialogDateCrossingDepartureEditRoute,
        viewModel::updateDataEditRouteDateCrossingDeparture
    )
    DatePickerDialogCustom(
        viewModel.openDialogDateCrossingReturnEditRoute,
        viewModel::updateDataEditRouteDateCrossingReturn
    )
    BottomSheetSearchRoute(
        viewModel = viewModel
    )
    BottomSheetAddCity(
        viewModel = viewModel
    )
}


/**
 * ПОИСКОВИК АААААААА ------------------------------
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetSearchRoute(
    viewModel: EditRouteViewModel
) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (viewModel.openBottomSheetCountryEditRoute.value) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.closeBottomSheetSearch() },
            sheetState = sheetState,
            modifier = Modifier.fillMaxHeight(0.75f)
        ) {
            Column(modifier = Modifier.fillMaxHeight()) {
                if(viewModel.openListSearchEditRoute.intValue == 0) {
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
    viewModel: EditRouteViewModel,
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
                        viewModel.sortCountry.intValue = if (viewModel.sortCountry.intValue == 0) 1 else 0
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
                            viewModel.run {
                                isCheckedFavoriteCountry.value = it
                                loadCountries()
                            }
                        },
                        role = Role.Checkbox
                    )
            ) {
                Checkbox(
                    checked = viewModel.isCheckedFavoriteCountry.value,
                    onCheckedChange = {
                        viewModel.run {
                            isCheckedFavoriteCountry.value = it
                            loadCountries()
                        }
                    }
                )
                Text(text = stringResource(R.string.favorite))
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
                            viewModel.run {
                                openListSearchEditRoute.intValue = 1
                                loadTownships(countriesListCountry[element].id)
                                selectedCountryIdInSearch.intValue = countriesListCountry[element].id
                                selectedCountryNameInSearch.value = countriesListCountry[element].country
                                updateRatingCountry(countriesListCountry[element].id)
                            }
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
    viewModel: EditRouteViewModel,
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
                IconButton( onClick = {viewModel.openAddCity()}) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = stringResource(R.string.add)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {}

        Row(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.run {
                            sortTownship.intValue = if(viewModel.sortTownship.intValue == 0) 1 else 0
                            loadTownships(selectedCountryIdInSearch.intValue)
                        }
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
                        viewModel.run {
                            isCheckedFavoriteTownship.value = it
                            loadTownships(viewModel.selectedCountryIdInSearch.intValue)
                        }
                    }
                )
                Text(
                    text = stringResource(R.string.favorite)
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = viewModel.selectedCountryNameInSearch.value
            )
            IconButton(
                onClick = {
                    viewModel.run {
                        openListSearchEditRoute.intValue = 0
                        loadCountries()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = null
                )
            }
        }

        if(viewModel.townshipsListTownship.value.isEmpty()) {
            Column(modifier = Modifier.fillMaxWidth()) {
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
                                viewModel.run {
                                    updateDataEditRouteRoute(
                                        townshipsListTownship[element].township,
                                        selectedRouteWrite.intValue
                                    )
                                    updateRatingTownship(townshipsListTownship[element].id)
                                    closeBottomSheetSearch()
                                }
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
    viewModel: EditRouteViewModel
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if(viewModel.openBottomAddCityEditRoute.value) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.openBottomAddCityEditRoute.value = false },
            sheetState = sheetState,
            modifier = Modifier.fillMaxHeight(0.75f)
        ) {
            Text(
                text = stringResource(R.string.add_city),
                style = typography.headlineSmall
            )

            OutlinedTextField(
                value = viewModel.uiStateAddCity.value.nameCity,
                onValueChange = { viewModel.updateNameCityAddCityEditRoute(name = it) },
                label = { Text(stringResource(R.string.name_city)) },
                singleLine = true,
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if(viewModel.uiStateAddCity.value.nameCity.isNotEmpty()) {
                        IconButton(onClick = { viewModel.updateNameCityAddCityEditRoute("")}) {
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
                    viewModel.run {
                        closeAddCity()
                        saveAddCityInBd()
                    }
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
    viewModel: EditRouteViewModel,
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

        Row(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.run {
                            sortCountry.intValue = if(sortCountry.intValue == 0) 1 else 0
                            loadCountries()
                        }
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
                        viewModel.run {
                            isCheckedFavoriteCountry.value = it
                            loadCountries()
                        }
                    }
                )
                Text(text = stringResource(R.string.favorite))
            }
        }

        if(viewModel.uiStateAddCity.value.country.country.isNotEmpty()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = viewModel.uiStateAddCity.value.country.country)
                IconButton(
                    onClick = {
                        viewModel.updateNameCountryAddCityEditRoute(CountryDetailing())
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
                            viewModel.updateNameCountryAddCityEditRoute(countriesListCountryAddCity[element])
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

