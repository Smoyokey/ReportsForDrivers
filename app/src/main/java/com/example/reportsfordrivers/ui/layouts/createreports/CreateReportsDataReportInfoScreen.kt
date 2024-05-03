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
import com.example.reportsfordrivers.ui.layouts.custom.BottomSheetAddCityCustom
import com.example.reportsfordrivers.ui.layouts.custom.ColumnSearchCountryCustom
import com.example.reportsfordrivers.ui.layouts.custom.ColumnSearchTownshipCustom
import com.example.reportsfordrivers.ui.layouts.custom.DatePickerDialogCustom
import com.example.reportsfordrivers.ui.layouts.custom.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.layouts.custom.RowDateWithTextField
import com.example.reportsfordrivers.ui.layouts.custom.TabRowCustom
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
        TabRowCustom(
            index = 0,
            navController = navController,
            isEnabledOne = false,
            isEnabledTwo = viewModel.uiStateIsValidate.value.isValidateCreateReportInfo,
            isEnabledThree = viewModel.uiStateIsValidate.value.isValidateCreatePersonalInfo,
            isEnabledFour = viewModel.uiStateIsValidate.value.isValidateCreateVehicleTrailer,
            isEnabledFive = viewModel.uiStateIsValidate.value.isValidateCreateRoute,
            isEnabledSix = viewModel.uiStateIsValidate.value.isValidateCreateProgressReports
        )

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
        BottomSheetAddCityCustom(
            searchText = viewModel.searchTextCountryAddCityCreateReportInfo,
            countriesList = viewModel.countriesListCountryAddCityCreateReportInfo,
            openBottom = viewModel.openBottomAddCityCreateReportInfo,
            modifier = Modifier.fillMaxHeight(0.75f),
            nameCity = viewModel.uiStateAddCity.value.nameCity,
            updateNameCity = viewModel::updateNameCityAddCityCreateReportInfo,
            onSearchTextChange = viewModel::onSearchTextChangeCountryAddCityCreateReportInfo,
            onToogleSearch = viewModel::onToogleSearchCountryAddCityCreateReportInfo,
            sortCountry = viewModel.sortCountryAddCityCreateReportInfo,
            loadCountries = viewModel::loadCountriesAddCityCreateRoute,
            isCheckedFavorite = viewModel.isCheckedFavoriteCountryAddCityCreateReportInfo,
            country = viewModel.uiStateAddCity.value.country,
            updateCountry = viewModel::updateNameCountryAddCityCreateReportInfo,
            closeAddCity = viewModel::closeAddCity,
            saveAddCityInBd = viewModel::saveAddCityInBd,
            validateAddCity = viewModel::validateAddCity
        )
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
                viewModel.closeBottomSheetSearch()
            },
            sheetState = sheetState,
            modifier = Modifier.fillMaxHeight(0.75f)
        ) {
            Column(
                modifier = Modifier.fillMaxHeight()
            ) {
                if (viewModel.openListSearchCreateReportInfo.intValue == 0) {
                    ColumnSearchCountryCustom(
                        searchText = viewModel.searchTextCountry,
                        countriesList = viewModel.countriesListCountry,
                        onSearchTextChangeCountry = viewModel::onSearchTextChangeCountry,
                        onToogleSearchCountry = viewModel::onToogleSearchCountry,
                        sortCountry = viewModel.sortCountry,
                        loadCountry = viewModel::loadCountries,
                        isCheckedFavoriteCountry = viewModel.isCheckedFavoriteCountry,
                        openListSearch = viewModel.openListSearchCreateReportInfo,
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
                        updateData = viewModel::updateDataCreateReportInfoMainCity,
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