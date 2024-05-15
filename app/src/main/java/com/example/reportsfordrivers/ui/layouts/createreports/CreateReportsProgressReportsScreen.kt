package com.example.reportsfordrivers.ui.layouts.createreports

import android.annotation.SuppressLint
import android.util.Log
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
import com.example.reportsfordrivers.ui.layouts.custom.AlertDialogDeleteElement
import com.example.reportsfordrivers.ui.layouts.custom.BottomBarCustom
import com.example.reportsfordrivers.ui.layouts.custom.DatePickerDialogCustom
import com.example.reportsfordrivers.ui.layouts.custom.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.layouts.custom.OutlinedTextFieldCustomSearch
import com.example.reportsfordrivers.ui.layouts.custom.RowDateWithTextField
import com.example.reportsfordrivers.ui.layouts.custom.RowProgressAndExpenses
import com.example.reportsfordrivers.ui.layouts.custom.TabRowCustom
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.createreports.CreateProgressReportsViewModel
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateProgressReportsDetailingUiState
import com.example.reportsfordrivers.viewmodel.firstentry.CountryDetailing

private const val TAG = "CreateReportsProgressReportsScreen"

@Composable
fun CreateReportsProgressReportsScreen(
    viewModel: CreateProgressReportsViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    BackHandler {
        navController.navigate(
            ReportsForDriversSchema.Route.name,
            navOptions = NavOptions.Builder()
                .setPopUpTo(ReportsForDriversSchema.Route.name, true)
                .build()
        )
    }

    val scrollState = rememberScrollState()

    val source = remember { MutableInteractionSource() }
    if (source.collectIsPressedAsState().value)
        viewModel.openDialogDateCreateProgressReports.value = true

    Column {
        TabRowCustom(
            index = 4,
            navController = navController,
            isEnabledFive = false,
            isEnabledSix = viewModel.uiStateIsValidate.value.isValidateCreateProgressReports
        )

        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 16.dp)
                .weight(1f)
                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {

            RowDateWithTextField(
                openDialog = viewModel.openDialogDateCreateProgressReports,
                date = viewModel.uiStateCreateProgressReportsDetailing.value.date,
                modifier = Modifier.weight(1f),
                text = R.string.date
            )

//            OutlinedTextFieldCustom(
//                label = R.string.country,
//                value = viewModel.uiStateCreateProgressReportsDetailing.value.country,
//                onValueChange = viewModel::updateProgressReportsCountry,
//                tag = Tags.TAG_TEST_PROGRESS_REPORTS_COUNTRY,
//                modifier = Modifier.fillMaxWidth(),
//                keyboardOptions = KeyboardOptions(
//                    capitalization = KeyboardCapitalization.Words,
//                    autoCorrect = true
//                )
//            )

            OutlinedTextField(
                label = { Text(text = stringResource(R.string.country)) },
                value = viewModel.uiStateCreateProgressReportsDetailing.value.country,
                onValueChange = viewModel::updateProgressReportsCountry,
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
                            viewModel.openBottomSheetCountryCreateProgressReports.value = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier
                    .clickable { viewModel.openBottomSheetCountryCreateProgressReports.value = true }
                    .fillMaxWidth()
            )

            OutlinedTextFieldCustomSearch(
                label = R.string.township_one,
                value = viewModel.uiStateCreateProgressReportsDetailing.value.townshipOne,
                onValueChange = { viewModel.updateProgressReportsTownshipOne(it) },
                tag = "",
                modifier = Modifier.fillMaxWidth(),
                isOpenSearch = viewModel.openBottomSheetTownshipCreateProgressReports,
                isOneAndTwoState = viewModel.openSearchBottomSheet,
                isOneAndTwo = 0
            )

            OutlinedTextFieldCustomSearch(
                label = R.string.township_two,
                value = viewModel.uiStateCreateProgressReportsDetailing.value.townshipTwo,
                onValueChange = { viewModel.updateProgressReportsTownshipTwo(it) },
                tag = "",
                modifier = Modifier.fillMaxWidth(),
                isOpenSearch = viewModel.openBottomSheetTownshipCreateProgressReports,
                isOneAndTwoState = viewModel.openSearchBottomSheet,
                isOneAndTwo = 1
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextFieldCustom(
                    label = R.string.distance,
                    value = viewModel.uiStateCreateProgressReportsDetailing.value.distance,
                    onValueChange = viewModel::updateProgressReportsDistance,
                    tag = Tags.TAG_TEST_PROGRESS_REPORTS_DISTANCE,
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                OutlinedTextFieldCustom(
                    label = R.string.cargo_weight,
                    value = viewModel.uiStateCreateProgressReportsDetailing.value.cargoWeight,
                    onValueChange = viewModel::updateProgressReportsCargoWeight,
                    tag = Tags.TAG_TEST_PROGRESS_REPORTS_CARGO_WEIGHT,
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        viewModel.addListProgressReports()
                    },
                    enabled = viewModel.isValidateAddProgressReports()
                ) {
                    Text(
                        text = stringResource(R.string.add)
                    )
                }
            }

            Column {
                for (i in 0..<viewModel.uiStateCreateProgressReports.value.createProgressReportsList.size) {
                    ColumnProgressReports(
                        viewModel.uiStateCreateProgressReports.value.createProgressReportsList[i],
                        viewModel::deletePositionProgressReports,
                        viewModel.openDialogDeleteCreateProgressReports,
                        i,
                        viewModel.uiStateCreateProgressReports.value.createProgressReportsList.size
                    )
                }
            }

            DatePickerDialogCustom(
                viewModel.openDialogDateCreateProgressReports,
                viewModel::updateProgressReportsDate
            )
        }

        BottomBarCustom(
            onNext = { navController.navigate(ReportsForDriversSchema.TripExpenses.name) },
            onBack = { navController.navigateUp() },
            enabled = viewModel.isValidateNextProgressReports()
        )
    }

    BottomSheetSearch(
        viewModel = viewModel
    )

    BottomSheetAddCity(
        viewModel = viewModel
    )
}

@Composable
private fun ColumnProgressReports(
    progressReportsDetailing: CreateProgressReportsDetailingUiState,
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
            if (size - 1 != position) {
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
                Icons.Outlined.Clear,
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
 * ПОИСКОВИК АААА
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetSearch(
    viewModel: CreateProgressReportsViewModel
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (viewModel.openBottomSheetTownshipCreateProgressReports.value ||
        viewModel.openBottomSheetCountryCreateProgressReports.value) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.closeBottomSheetSearch(viewModel.openBottomSheetTownshipCreateProgressReports)
            },
            sheetState = sheetState,
            modifier = Modifier.fillMaxHeight(0.75f)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (viewModel.openListSearchCreateProgressReports.intValue == 0) {
                    ColumnSearchCountry(viewModel = viewModel)
                } else {
                    ColumnSearchTownship(viewModel = viewModel)
                }

                Button(
                    onClick = {
                        viewModel.closeBottomSheetSearch(viewModel.openBottomSheetTownshipCreateProgressReports)
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
    viewModel: CreateProgressReportsViewModel,
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
                            if (viewModel.sortCountry.intValue == 0) 1 else 0
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

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(countriesListCountry.size) { element ->

                if (viewModel.openBottomSheetTownshipCreateProgressReports.value) {
                    RowTownship(
                        viewModel = viewModel,
                        element = element,
                        countriesListCountry = countriesListCountry
                    )
                } else if (viewModel.openBottomSheetCountryCreateProgressReports.value) {
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
    viewModel: CreateProgressReportsViewModel,
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
                IconButton(
                    onClick = {
                        viewModel.openAddCity(viewModel.openBottomSheetTownshipCreateProgressReports)
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
                            if (viewModel.sortTownship.intValue == 0) 1 else 0
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
                Text(text = stringResource(R.string.favorite))
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = viewModel.selectedCountryNameInSearch.value)
            IconButton(
                onClick = {
                    viewModel.openListSearchCreateProgressReports.intValue = 0
                    viewModel.loadCountries()
                }
            ) {
                Icon(imageVector = Icons.Outlined.Clear, contentDescription = null)
            }
        }

        if (viewModel.townshipsListTownship.value.isEmpty()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                EmptySearchTownshipReportsT(
                    isOpenAddCity = viewModel::openAddCity,
                    isOpen = viewModel.openBottomSheetTownshipCreateProgressReports
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
                                if (viewModel.openSearchBottomSheet.intValue == 0) {
                                    viewModel.updateProgressReportsTownshipOne(
                                        townshipsListTownship[element].township
                                    )
                                } else {
                                    viewModel.updateProgressReportsTownshipTwo(
                                        townshipsListTownship[element].township
                                    )
                                }
                                viewModel.updateRatingTownship(townshipsListTownship[element].id)
                                viewModel.closeBottomSheetSearch(
                                    viewModel.openBottomSheetTownshipCreateProgressReports
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
    viewModel: CreateProgressReportsViewModel
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (viewModel.openBottomSheetAddCityCreateProgressReports.value) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.closeAddCity(
                    if (viewModel.openSearchCountryAndTownship.intValue == 0) {
                        viewModel.openBottomSheetCountryCreateProgressReports
                    } else {
                        viewModel.openBottomSheetTownshipCreateProgressReports
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
                onValueChange = { viewModel.updateNameCityAddCityCreateProgressReports(name = it) },
                label = { Text(stringResource(R.string.name_city)) },
                singleLine = true,
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if (viewModel.uiStateAddCity.value.nameCity.isNotEmpty()) {
                        IconButton(onClick = {
                            viewModel.updateNameCityAddCityCreateProgressReports(
                                ""
                            )
                        }) {
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
                        if (viewModel.openSearchCountryAndTownship.intValue == 0) {
                            viewModel.openBottomSheetCountryCreateProgressReports
                        } else {
                            viewModel.openBottomSheetTownshipCreateProgressReports
                        }
                    )
                    viewModel.saveAddCityInBd(
                        if (viewModel.openSearchCountryAndTownship.intValue == 0) {
                            viewModel.openBottomSheetCountryCreateProgressReports
                        } else {
                            viewModel.openBottomSheetTownshipCreateProgressReports
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
    viewModel: CreateProgressReportsViewModel,
    modifier: Modifier = Modifier
) {
    val searchTextCountryAddCity by viewModel.searchTextCountry.collectAsState()
    val countriesListCountryAddCity by viewModel.countriesListCountry.collectAsState()

    Column() {
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
                            if (viewModel.sortCountry.intValue == 0) 1 else 0
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

        if (viewModel.uiStateAddCity.value.country.country.isNotEmpty()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = viewModel.uiStateAddCity.value.country.country)
                IconButton(
                    onClick = {
                        viewModel.updateNameCountryAddCityCreateProgressReports(CountryDetailing())
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
                            viewModel.updateNameCountryAddCityCreateProgressReports(
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
    viewModel: CreateProgressReportsViewModel,
    element: Int,
    countriesListCountry: List<CountryDetailing>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                viewModel.updateProgressReportsCountry(countriesListCountry[element].country)
                viewModel.updateRatingCountry(countriesListCountry[element].id)
                viewModel.closeBottomSheetSearch(viewModel.openBottomSheetCountryCreateProgressReports)
                Log.i(TAG, "Click")
            },
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
    }
}

@Composable
private fun RowTownship(
    viewModel: CreateProgressReportsViewModel,
    element: Int,
    countriesListCountry: List<CountryDetailing>
) {
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
                viewModel.openListSearchCreateProgressReports.intValue = 1
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