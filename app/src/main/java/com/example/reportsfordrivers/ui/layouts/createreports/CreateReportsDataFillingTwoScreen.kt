package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.ui.BottomBarCustom
import com.example.reportsfordrivers.ui.DatePickerDialogCustom
import com.example.reportsfordrivers.ui.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.RowDateWithTextField
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
                    OutlinedTextFieldCustom(
                        label = R.string.route,
                        value = viewModel.uiStateCreateRoute.value.route[i].text,
                        onValueChange = { viewModel.updateDataCreateRouteRoute(it, i) },
                        tag = "",
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(autoCorrect = true)
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
                text = R.string.date_departure
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            OutlinedTextFieldCustom(
                label = R.string.speedometer_reading_return,
                value = viewModel.uiStateCreateRoute.value.speedometerReturn,
                onValueChange = viewModel::updateDataCreateRouteSpeedometerReturn,
                tag = Tags.TAG_TEST_DATA_FILLING_TWO_SPEEDOMETER_RETURN,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
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

