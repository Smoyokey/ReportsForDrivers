package com.example.reportsfordrivers.ui.layouts.hirstoryreports.editreport

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.material3.Divider
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
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.example.reportsfordrivers.ui.layouts.custom.AlertDialogDeleteElement
import com.example.reportsfordrivers.ui.layouts.custom.BottomBarCustom
import com.example.reportsfordrivers.ui.layouts.custom.DatePickerDialogCustom
import com.example.reportsfordrivers.ui.layouts.custom.RowDateWithTextField
import com.example.reportsfordrivers.ui.layouts.custom.RowProgressAndExpenses
import com.example.reportsfordrivers.ui.layouts.custom.TabRowCustom
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.firstentry.CountryDetailing
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditProgressReportsDetailingUiState
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.viewmodel.EditProgressReportsViewModel

private const val TAG = "EditReportProgressReportsScreen"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditReportProgressReportsScreen(
    viewModel: EditProgressReportsViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {

    val (date, country, townshipOne, townshipTwo, distance, weight) =
        remember { FocusRequester.createRefs() }
    val keyboardController = LocalSoftwareKeyboardController.current

    BackHandler {
        navController.navigate(
            ReportsForDriversSchema.EditRoute.name,
            navOptions = NavOptions.Builder()
                .setPopUpTo(ReportsForDriversSchema.EditRoute.name, true)
                .build()
        )
    }

    val scrollState = rememberScrollState()

    val source = remember { MutableInteractionSource() }
    if(source.collectIsPressedAsState().value)
        viewModel.openDialogDateEditProgressReports.value = true

    Column {
        TabRowCustom(
            index = 4,
            navController = navController,
            isEnabledFive = false
        )

        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 16.dp)
                .weight(1f)
                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            RowDateWithTextField(
                openDialog = viewModel.openDialogDateEditProgressReports,
                date = viewModel.uiStateEditProgressReportsDetailing.value.date,
                modifier = Modifier.weight(1f),
                text = R.string.date
            )

            OutlinedTextField(
                label = { Text(text = stringResource(R.string.country)) },
                value = viewModel.uiStateEditProgressReportsDetailing.value.country,
                onValueChange = viewModel::updateEditProgressReportsCountry,
                singleLine = true,
                textStyle = typography.bodyLarge,
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
                    IconButton(
                        onClick = {
                            viewModel.openBottomSheetCountryEditProgressReports.value = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier
                    .clickable {
                        viewModel.openBottomSheetCountryEditProgressReports.value = true
                    }
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.uiStateEditProgressReportsDetailing.value.townshipOne,
                label = { Text(stringResource(R.string.township_one)) },
                onValueChange = { viewModel.updateEditProgressReportsTownshipOne(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(townshipOne),
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if(viewModel.uiStateEditProgressReportsDetailing.value.townshipOne.isEmpty()) {
                        IconButton(
                            onClick = {
                                viewModel.openBottomSheetTownshipEditProgressReports.value = true
                                viewModel.openSearchBottomSheet.intValue = 0
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = null
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { viewModel.updateEditProgressReportsTownshipOne("") }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = stringResource(R.string.clear)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {townshipTwo.requestFocus()})
            )

            OutlinedTextField(
                value = viewModel.uiStateEditProgressReportsDetailing.value.townshipTwo,
                label = { Text(stringResource(R.string.township_two)) },
                onValueChange = { viewModel.updateEditProgressReportsTownshipTwo(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(townshipTwo),
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if(viewModel.uiStateEditProgressReportsDetailing.value.townshipTwo.isEmpty()) {
                        IconButton(
                            onClick = {
                                viewModel.run {
                                    openBottomSheetTownshipEditProgressReports.value = true
                                    openSearchBottomSheet.intValue = 1
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = null
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { viewModel.updateEditProgressReportsTownshipTwo("") }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = stringResource(R.string.clear)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {distance.requestFocus()})
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = viewModel.uiStateEditProgressReportsDetailing.value.distance,
                    label = { Text(stringResource(R.string.distance)) },
                    onValueChange = { viewModel.updateEditProgressReportsDistance(it) },
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(distance),
                    textStyle = typography.bodyLarge,
                    trailingIcon = {
                        if(viewModel.uiStateEditProgressReportsDetailing.value.distance.isNotEmpty()) {
                            IconButton(
                                onClick = { viewModel.updateEditProgressReportsDistance("") }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Clear,
                                    contentDescription = stringResource(R.string.clear)
                                )
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Decimal
                    ),
                    keyboardActions = KeyboardActions(onNext = {weight.requestFocus()})
                )

                OutlinedTextField(
                    value = viewModel.uiStateEditProgressReportsDetailing.value.cargoWeight,
                    label = { Text(stringResource(R.string.cargo_weight)) },
                    onValueChange = { viewModel.updateEditProgressReportsCargoWeight(it) },
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(weight),
                    textStyle = typography.bodyLarge,
                    trailingIcon = {
                        if(viewModel.uiStateEditProgressReportsDetailing.value.cargoWeight.isNotEmpty()) {
                            IconButton(
                                onClick = { viewModel.updateEditProgressReportsCargoWeight("") }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Clear,
                                    contentDescription = stringResource(R.string.clear)
                                )
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Decimal
                    ),
                    keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()})
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        viewModel.addListEditProgressReports()
                    },
                    enabled = viewModel.isValidateAddEditProgressReports()
                ) {
                    Text(
                        text = stringResource(R.string.add)
                    )
                }
            }

            Column {
                for (i in 0..<viewModel.uiStateEditProgressReports.value.editProgressReportsList.size) {
                    ColumnProgressReports(
                        viewModel.uiStateEditProgressReports.value.editProgressReportsList[i],
                        viewModel::deletePositionEditProgressReports,
                        viewModel.openDialogDeleteEditProgressReports,
                        i,
                        viewModel.uiStateEditProgressReports.value.editProgressReportsList.size
                    )
                }
            }

            DatePickerDialogCustom(
                viewModel.openDialogDateEditProgressReports,
                viewModel::updateEditProgressReportsDate
            )
        }

        BottomBarCustom(
            onNext = { navController.navigate(ReportsForDriversSchema.EditTripExpenses.name) },
            onBack = { navController.navigateUp() },
        )
    }

    BottomSheetSearch(
        viewModel = viewModel,
        townshipTwo = townshipTwo,
        distance = distance
    )

    BottomSheetAddCity(
        viewModel = viewModel
    )
}

@Composable
private fun ColumnProgressReports(
    progressReportsDetailing: EditProgressReportsDetailingUiState,
    delete: (Int) -> Unit,
    isOpen: MutableState<Boolean>,
    position: Int,
    size: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            RowProgressAndExpenses(
                title = R.string.date,
                text = progressReportsDetailing.date
            )
            RowProgressAndExpenses(
                title = R.string.country,
                text = progressReportsDetailing.country
            )
            RowProgressAndExpenses(
                title = R.string.township,
                text = "${progressReportsDetailing.townshipOne} " +
                        "- ${progressReportsDetailing.townshipTwo}"
            )
            RowProgressAndExpenses(
                title = R.string.distance,
                text = progressReportsDetailing.distance
            )
            RowProgressAndExpenses(
                title = R.string.cargo_weight,
                text = progressReportsDetailing.cargoWeight
            )
            if(size - 1 != position) {
                Divider(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 10.dp,
                        bottom = 10.dp
                    )
                )
            }
        }
        IconButton(
            onClick = {
                isOpen.value = true
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Clear,
                contentDescription = stringResource(R.string.delete)
            )
        }
    }
    AlertDialogDeleteElement(
        isOpen = isOpen,
        delete = delete,
        position = position
    )
}

/**
 * ПОИСКОВИК ААААААА
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetSearch(
    viewModel: EditProgressReportsViewModel,
    townshipTwo: FocusRequester,
    distance: FocusRequester
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if(viewModel.openBottomSheetTownshipEditProgressReports.value ||
        viewModel.openBottomSheetCountryEditProgressReports.value) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.closeBottomSheetSearch(viewModel.openBottomSheetTownshipEditProgressReports)
            },
            sheetState = sheetState,
            modifier = Modifier.fillMaxHeight(0.75f)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                if(viewModel.openListSearchEditProgressReports.intValue == 0) {
                    ColumnSearchCountry(viewModel = viewModel)
                } else {
                    ColumnSearchTownship(
                        viewModel = viewModel,
                        townshipTwo = townshipTwo,
                        distance = distance
                    )
                }

                Button(
                    onClick = {
                        viewModel.closeBottomSheetSearch(viewModel.openBottomSheetTownshipEditProgressReports)
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
private fun ColumnSearchCountry(
    viewModel: EditProgressReportsViewModel,
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
                        viewModel.sortCountry.intValue =
                            if(viewModel.sortCountry.intValue == 0) 1 else 0
                        viewModel.loadCountries()
                    }
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

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(countriesListCountry.size) { element ->

                if(viewModel.openBottomSheetTownshipEditProgressReports.value) {
                    RowTownship(
                        viewModel = viewModel,
                        element = element,
                        countriesListCountry = countriesListCountry
                    )
                } else if (viewModel.openBottomSheetCountryEditProgressReports.value) {
                    RowCountry(
                        viewModel = viewModel,
                        element = element,
                        countriesListCountry = countriesListCountry
                    )
                }
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ColumnSearchTownship(
    viewModel: EditProgressReportsViewModel,
    modifier: Modifier = Modifier,
    townshipTwo: FocusRequester,
    distance: FocusRequester
) {
    val searchTextTownship by viewModel.searchTextTownship.collectAsState()
    val townshipsListTownship by viewModel.townshipsListTownship.collectAsState()

    Column(
        modifier = Modifier,
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
                IconButton(
                    onClick = {
                        viewModel.openAddCity(viewModel.openBottomSheetTownshipEditProgressReports)
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

        Row(modifier = Modifier.fillMaxWidth()) {
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
                Text(text = stringResource(R.string.favorite))
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = viewModel.selectedCountryNameInSearch.value)
            IconButton(
                onClick = {
                    viewModel.openListSearchEditProgressReports.intValue = 0
                    viewModel.loadCountries()
                }
            ) {
                Icon(imageVector = Icons.Outlined.Clear, contentDescription = null)
            }
        }

        if(viewModel.townshipsListTownship.value.isEmpty()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                EmptySearchTownshipReportsT(
                    isOpenAddCity = viewModel::openAddCity,
                    isOpen = viewModel.openBottomSheetTownshipEditProgressReports
                )
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
                                if(viewModel.openSearchBottomSheet.intValue == 0) {
                                    viewModel.updateEditProgressReportsTownshipOne(
                                        townshipsListTownship[element].township
                                    )
                                    townshipTwo.requestFocus()
                                } else {
                                    viewModel.updateEditProgressReportsTownshipTwo(
                                        townshipsListTownship[element].township
                                    )
                                    distance.requestFocus()
                                }
                                viewModel.updateRatingTownship(townshipsListTownship[element].id)
                                viewModel.closeBottomSheetSearch(
                                    viewModel.openBottomSheetTownshipEditProgressReports
                                )
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
    viewModel: EditProgressReportsViewModel
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if(viewModel.openBottomSheetAddCityEditProgressReports.value) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.closeAddCity(
                    if(viewModel.openSearchCountryAndTownship.intValue == 0) {
                        viewModel.openBottomSheetCountryEditProgressReports
                    } else {
                        viewModel.openBottomSheetTownshipEditProgressReports
                    }
                )
            },
            sheetState = sheetState,
            modifier = Modifier.fillMaxHeight(0.75f)
        ) {
            Text(
                text = stringResource(R.string.add_city),
                style = typography.headlineSmall
            )

            OutlinedTextField(
                value = viewModel.uiStateAddCity.value.nameCity,
                onValueChange = { viewModel.updateNameCityAddCityEditProgressReports(name = it) },
                label = { Text(stringResource(R.string.name_city)) },
                singleLine = true,
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if(viewModel.uiStateAddCity.value.nameCity.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                viewModel.updateNameCityAddCityEditProgressReports("")
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

            ColumnSearchCountryAddCity(viewModel = viewModel)

            Button(
                onClick = {
                    viewModel.closeAddCity(
                        if(viewModel.openSearchCountryAndTownship.intValue == 0) {
                            viewModel.openBottomSheetCountryEditProgressReports
                        } else {
                            viewModel.openBottomSheetTownshipEditProgressReports
                        }
                    )
                    viewModel.saveAddCityInBd(
                        if(viewModel.openSearchCountryAndTownship.intValue == 0) {
                            viewModel.openBottomSheetCountryEditProgressReports
                        } else {
                            viewModel.openBottomSheetTownshipEditProgressReports
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.validateAddCity()
            ) {
                Text(text = stringResource(R.string.save))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ColumnSearchCountryAddCity(
    viewModel: EditProgressReportsViewModel,
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

        Row(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.sortCountry.intValue =
                            if(viewModel.sortCountry.intValue == 0) 1 else 0
                        viewModel.loadCountries()
                    }
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
                    onCheckedChange = {
                        viewModel.isCheckedFavoriteCountry.value = it
                        viewModel.loadCountries()
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
                        viewModel.updateNameCountryAddCityEditProgressReports(CountryDetailing())
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
                            viewModel.updateNameCountryAddCityEditProgressReports(
                                countriesListCountryAddCity[element]
                            )
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
private fun EmptySearchTownshipReportsT(
    isOpenAddCity: (MutableState<Boolean>) -> Unit,
    isOpen: MutableState<Boolean>
) {
    TextButton(
        onClick = { isOpenAddCity(isOpen) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.not_list_add),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun RowCountry(
    viewModel: EditProgressReportsViewModel,
    element: Int,
    countriesListCountry: List<CountryDetailing>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                viewModel.run {
                    updateEditProgressReportsCountry(countriesListCountry[element].country)
                    updateRatingCountry(countriesListCountry[element].id)
                    closeBottomSheetSearch(openBottomSheetCountryEditProgressReports)
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(countriesListCountry[element].favorite == 1) {
            Icon(imageVector = Icons.Outlined.Clear, contentDescription = null)
        }

        Text(
            text = countriesListCountry[element].country,
            style = typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun RowTownship(
    viewModel: EditProgressReportsViewModel,
    element: Int,
    countriesListCountry: List<CountryDetailing>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(countriesListCountry[element].favorite == 1) {
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
                    openListSearchEditProgressReports.intValue = 1
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

