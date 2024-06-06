package com.example.reportsfordrivers.ui.layouts.hirstoryreports.editreport

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.input.KeyboardCapitalization
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
import com.example.reportsfordrivers.ui.layouts.custom.TabRowCustom
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.firstentry.CountryDetailing
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.viewmodel.EditReportInfoViewModel
import kotlinx.coroutines.launch
import java.util.Date

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditReportsDataReportInfoScreen(
    viewModel: EditReportInfoViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {

    val (date, township, waybill) = remember { FocusRequester.createRefs() }
    val keyboardController = LocalSoftwareKeyboardController.current

    BackHandler {
        navController.navigate(
            ReportsForDriversSchema.Start.name,
            navOptions = NavOptions.Builder()
                .setPopUpTo(ReportsForDriversSchema.SelectElementHistory.name, true)
                .build()
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TabRowCustom(
            index = 0,
            navController = navController,
            isEnabledOne = false
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp, start = 10.dp, top = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.date_create_report),
                    modifier = Modifier.weight(1f),
                    style = typography.bodyLarge
                )
                OutlinedTextField(
                    label = { Text(text = stringResource(R.string.date)) },
                    value = viewModel.uiStateEditReportInfo.value.date,
                    onValueChange = {},
                    modifier = Modifier.clickable {
                        viewModel.openDialogDateEditReportInfo.value = true
                    }
                        .focusRequester(date),
                    readOnly = true,
                    enabled = false,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Sharp.DateRange,
                            contentDescription = stringResource(R.string.calendar),
                            modifier = Modifier.clickable {
                                viewModel.openDialogDateEditReportInfo.value = true
                            }
                        )
                    }
                )
            }

            OutlinedTextField(
                value = viewModel.uiStateEditReportInfo.value.mainCity,
                onValueChange = viewModel::updateDataEditReportInfoMainCity,
                label = { Text(stringResource(R.string.township)) },
                modifier = Modifier.fillMaxWidth()
                    .focusRequester(township),
                textStyle = typography.bodyLarge,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = true,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {waybill.requestFocus()}),
                trailingIcon = {
                    if(viewModel.uiStateEditReportInfo.value.mainCity.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                viewModel.updateDataEditReportInfoMainCity("")
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
                                viewModel.openBottomSearchEditReportInfo.value = true
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

            OutlinedTextField(
                value = viewModel.uiStateEditReportInfo.value.waybill,
                label = { Text(stringResource(R.string.waybill)) },
                onValueChange = { viewModel.updateDataEditReportInfoWaybill(it) },
                modifier = Modifier.fillMaxWidth()
                    .focusRequester(waybill),
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if(viewModel.uiStateEditReportInfo.value.waybill.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.updateDataEditReportInfoWaybill("") }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = stringResource(R.string.clear)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide()})
            )
        }

        BottomBarCustom(
            onNext = { navController.navigate(ReportsForDriversSchema.PersonalInfo.name) },
            onBack = { navController.navigateUp() },
        )
    }

    DatePickerDialogCreate(
        viewModel = viewModel,
        township = township
    )

    BottomSheetSearch(
        viewModel = viewModel,
        waybill = waybill
    )

    BottomSheetAddCity(
        viewModel = viewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerDialogCreate(
    viewModel: EditReportInfoViewModel,
    township: FocusRequester
) {
    val snackScope = rememberCoroutineScope()
    val datePickerState = rememberDatePickerState()
    if(viewModel.openDialogDateEditReportInfo.value) {
        DatePickerDialog(
            onDismissRequest = { viewModel.openDialogDateEditReportInfo.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.openDialogDateEditReportInfo.value = false
                        if(datePickerState.selectedDateMillis != null) {
                            snackScope.launch {
                                viewModel.updateDataEditReportInfoDate(datePickerState.selectedDateMillis.toString())
                            }
                        } else {
                            viewModel.updateDataEditReportInfoDate(Date().time.toString())
                        }
                        township.requestFocus()
                    }
                ) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { viewModel.openDialogDateEditReportInfo.value = false }
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }
}


/**
 * ПОИСКОВИК !!!!!!!!!!!!!!!!!!!!!!!!!
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetSearch(
    viewModel: EditReportInfoViewModel,
    waybill: FocusRequester
) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (viewModel.openBottomSearchEditReportInfo.value) {
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
                if(viewModel.openListSearchEditReportInfo.intValue == 0) {
                    ColumnSearchCountry(
                        viewModel = viewModel,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    ColumnSearchTownship(
                        viewModel = viewModel,
                        modifier = Modifier.weight(1f),
                        waybill = waybill
                    )
                }

                Button(
                    onClick = { viewModel.closeBottomSheetSearch() },
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
private fun ColumnSearchCountry(
    viewModel: EditReportInfoViewModel,
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

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
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
                            viewModel.isCheckedFavoriteCountry.value = !viewModel.isCheckedFavoriteCountry.value
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

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(countriesListCountry.size) { element ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (countriesListCountry[element].favorite == 1) {
                        Icon(imageVector = Icons.Outlined.Clear, contentDescription = null)
                    }

                    Text(
                        text = countriesListCountry[element].country,
                        style = typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = {
                            viewModel.run {
                                openListSearchEditReportInfo.intValue = 1
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
    viewModel: EditReportInfoViewModel,
    modifier: Modifier = Modifier,
    waybill: FocusRequester
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
                Icon (imageVector = Icons.Outlined.Search, contentDescription = null)
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

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.sortTownship.intValue = if(viewModel.sortTownship.intValue == 0) 1 else 0
                        viewModel.loadTownships(viewModel.selectedCountryIdInSearch.intValue)
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.sort_24px),
                    contentDescription = null
                )
                Text(
                    text = if (viewModel.sortTownship.intValue == 0) {
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
                Text( text = stringResource(R.string.favorite))
            }
        }

        Row( modifier = Modifier.fillMaxWidth() ) {
            Text( text = viewModel.selectedCountryNameInSearch.value )
            IconButton(
                onClick = {
                    viewModel.openListSearchEditReportInfo.intValue = 0
                    viewModel.loadCountries()
                }
            ) {
                Icon(imageVector = Icons.Outlined.Clear, contentDescription = null)
            }
        }

        if(viewModel.townshipsListTownship.value.isEmpty()) {
            Column( modifier = Modifier.fillMaxWidth()) {
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
                                    updateDataEditReportInfoMainCity(
                                        townshipsListTownship[element].township
                                    )
                                    updateRatingTownship(townshipsListTownship[element].id)
                                    closeBottomSheetSearch()
                                    waybill.requestFocus()
                                }
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetAddCity(
    viewModel: EditReportInfoViewModel
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (viewModel.openBottomAddCityEditReportInfo.value) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.closeAddCity() },
            sheetState = sheetState,
            modifier = Modifier.fillMaxHeight(0.75f)
        ) {
            Text(
                text = stringResource(R.string.add_city),
                style = typography.headlineSmall
            )

            OutlinedTextField(
                value = viewModel.uiStateAddCity.value.nameCity,
                onValueChange = { viewModel.updateNameCityAddCityEditReportInfo(name = it) },
                label = { Text(stringResource(R.string.name_city)) },
                singleLine = true,
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if (viewModel.uiStateAddCity.value.nameCity.isNotEmpty()) {
                        IconButton( onClick = { viewModel.updateNameCityAddCityEditReportInfo("" ) } ) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = stringResource(R.string.clear)
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            ColumnSearchCountryAddCity(viewModel = viewModel)

            Button(
                onClick = {
                    viewModel.closeAddCity()
                    viewModel.saveAddCityInBd()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.validateAddCity()
            ) {
                Text ( text = stringResource(R.string.save))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ColumnSearchCountryAddCity(
    viewModel: EditReportInfoViewModel,
    modifier: Modifier = Modifier
) {
    val searchTextCountryAddCity by viewModel.searchTextCountry.collectAsState()
    val countriesListCountryAddCity by viewModel.countriesListCountry.collectAsState()

    Column {
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
                Text( text = stringResource(R.string.favorite) )
            }
        }

        if(viewModel.uiStateAddCity.value.country.country.isNotEmpty()) {
            Row( modifier = Modifier.fillMaxWidth() ) {
                Text( text = viewModel.uiStateAddCity.value.country.country )
                IconButton(
                    onClick = {
                        viewModel.updateNameCountryAddCityEditReportInfo(CountryDetailing())
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
                            viewModel.updateNameCountryAddCityEditReportInfo(countriesListCountryAddCity[element])
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