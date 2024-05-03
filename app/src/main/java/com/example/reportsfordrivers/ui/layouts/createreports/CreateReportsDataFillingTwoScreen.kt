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
import com.example.reportsfordrivers.ui.layouts.custom.BottomSheetAddCityCustom
import com.example.reportsfordrivers.ui.layouts.custom.ColumnSearchCountryCustom
import com.example.reportsfordrivers.ui.layouts.custom.ColumnSearchTownshipCustom
import com.example.reportsfordrivers.ui.layouts.custom.DatePickerDialogCustom
import com.example.reportsfordrivers.ui.layouts.custom.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.layouts.custom.OutlinedTextFieldCustomSearch
import com.example.reportsfordrivers.ui.layouts.custom.RowDateWithTextField
import com.example.reportsfordrivers.ui.layouts.custom.TabRowCustom
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
        BottomSheetAddCityCustom(
            searchText = viewModel.searchTextCountryAddCityCreateRoute,
            countriesList = viewModel.countriesListCountryAddCityCreateRoute,
            openBottom = viewModel.openBottomAddCityCreateRoute,
            modifier = Modifier.fillMaxHeight(0.75f),
            nameCity = viewModel.uiStateAddCity.value.nameCity,
            updateNameCity = viewModel::updateNameCityAddCityCreateRoute,
            onSearchTextChange = viewModel::onSearchTextChangeCountryAddCityCreateRoute,
            onToogleSearch = viewModel::onToogleSearchCountryAddCityCreateRoute,
            sortCountry = viewModel.sortCountryAddCityCreateRoute,
            loadCountries = viewModel::loadCountriesAddCityCreateRoute,
            isCheckedFavorite = viewModel.isCheckedFavoriteCountryAddCityCreateRoute,
            country = viewModel.uiStateAddCity.value.country,
            updateCountry = viewModel::updateNameCountryAddCityCreateRoute,
            closeAddCity = viewModel::closeAddCity,
            saveAddCityInBd = viewModel::saveAddCityInBd,
            validateAddCity = viewModel::validateAddCity
        )
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
                if (viewModel.openListSearchCreateRoute.intValue == 0) {
                    ColumnSearchCountryCustom(
                        searchText = viewModel.searchTextCountry,
                        countriesList = viewModel.countriesListCountry,
                        onSearchTextChangeCountry = viewModel::onSearchTextChangeCountry,
                        onToogleSearchCountry = viewModel::onToogleSearchCountry,
                        sortCountry = viewModel.sortCountry,
                        loadCountry = viewModel::loadCountries,
                        isCheckedFavoriteCountry = viewModel.isCheckedFavoriteCountry,
                        openListSearch = viewModel.openListSearchCreateRoute,
                        selectedCountryIdInSearch = viewModel.selectedCountryIdInSearch,
                        selectedCountryNameInSearch = viewModel.selectedCountryNameInSearch,
                        updateRating = viewModel::updateRatingCountry,
                        loadTownships = viewModel::loadTownships
                    )
                } else {
                    ColumnSearchTownshipCustom(
                        searchText = viewModel.searchTextTownship,
                        townshipsList = viewModel.townshipsListTownship,
                        onSearchTextChangeTownship = viewModel::onSearchTextChangeTownship,
                        onToogleSearchTownship = viewModel::onToogleSearchTownship,
                        openAddCity = viewModel::openAddCity,
                        modifier = Modifier,
                        sortTownship = viewModel.sortTownship,
                        loadTownships = viewModel::loadTownships,
                        selectedCountryIdInSearch = viewModel.selectedCountryIdInSearch,
                        isCheckedFavoriteTownship = viewModel.isCheckedFavoriteTownship,
                        listIsEmpty = false,
                        isOpenAddCity = viewModel::openAddCity,
                        updateData = viewModel::updateDataCreateRouteRoute,
                        updateRating = viewModel::updateRatingTownship,
                        closeBottom = viewModel::closeBottomSheetSearch
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


