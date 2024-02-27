package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.ui.BottomBarCustom
import com.example.reportsfordrivers.ui.DatePickerDialogCustom
import com.example.reportsfordrivers.ui.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.OutlinedTextFieldDatePicker
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportsViewModel

@Composable
fun CreateReportsDataFillingTwoScreen(
    viewModel: CreateReportsViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val scrollState = rememberScrollState()

    val sourceDateDeparture = remember { MutableInteractionSource() }
    val sourceDateReturn = remember { MutableInteractionSource() }
    val sourceDateCrossingDeparture = remember { MutableInteractionSource() }
    val sourceDateCrossingReturn = remember { MutableInteractionSource() }
    if (sourceDateDeparture.collectIsPressedAsState().value)
        viewModel.openDialogDataFillingTwoDateDeparture.value = true
    if (sourceDateReturn.collectIsPressedAsState().value)
        viewModel.openDialogDataFillingTwoDateReturn.value = true
    if (sourceDateCrossingDeparture.collectIsPressedAsState().value)
        viewModel.openDialogDataFillingTwoDateCrossingDeparture.value = true
    if (sourceDateCrossingReturn.collectIsPressedAsState().value)
        viewModel.openDialogDataFillingTwoDateCrossingReturn.value = true

    Column {
        TabRowDataFillingTwo(navController = navController, viewModel = viewModel)

        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 16.dp)
                .verticalScroll(state = scrollState)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            OutlinedTextFieldCustom(
                label = R.string.route,
                value = viewModel.uiState.value.dataFillingTwo.route,
                onValueChange = viewModel::updateDataFillingTwoRoute,
                tag = Tags.TAG_TEST_DATA_FILLING_TWO_ROUTE,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )

            OutlinedTextFieldDatePicker(
                label = R.string.date_departure,
                value = viewModel.uiState.value.dataFillingTwo.dateDeparture,
                interactionSource = sourceDateDeparture,
                onValueChange = viewModel::updateDataFillingTwoDateDeparture,
                tag = Tags.TAG_TEST_DATA_FILLING_TWO_DATE_DEPARTURE,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextFieldDatePicker(
                label = R.string.date_return,
                value = viewModel.uiState.value.dataFillingTwo.dateReturn,
                interactionSource = sourceDateReturn,
                onValueChange = viewModel::updateDataFillingTwoDateReturn,
                tag = Tags.TAG_TEST_DATA_FILLING_TWO_DATE_RETURN,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextFieldDatePicker(
                label = R.string.date_border_crossing_departure,
                value = viewModel.uiState.value.dataFillingTwo.dateCrossingDeparture,
                interactionSource = sourceDateCrossingDeparture,
                onValueChange = viewModel::updateDataFillingTwoDateCrossingDeparture,
                tag = Tags.TAG_TEST_DATA_FILLING_TWO_DATE_CROSSING_DEPARTURE,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextFieldDatePicker(
                label = R.string.date_border_crossing_return,
                value = viewModel.uiState.value.dataFillingTwo.dateCrossingReturn,
                interactionSource = sourceDateCrossingReturn,
                onValueChange = viewModel::updateDataFillingTwoDateCrossingReturn,
                tag = Tags.TAG_TEST_DATA_FILLING_TWO_DATE_CROSSING_RETURN,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextFieldCustom(
                label = R.string.speedometer_reading_departure,
                value = viewModel.uiState.value.dataFillingTwo.speedometerDeparture,
                onValueChange = viewModel::updateDataFillingTwoSpeedometerDeparture,
                tag = Tags.TAG_TEST_DATA_FILLING_TWO_SPEEDOMETER_DEPARTURE,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            OutlinedTextFieldCustom(
                label = R.string.speedometer_reading_return,
                value = viewModel.uiState.value.dataFillingTwo.speedometerReturn,
                onValueChange = viewModel::updateDataFillingTwoSpeedometerReturn,
                tag = Tags.TAG_TEST_DATA_FILLING_TWO_SPEEDOMETER_RETURN,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            OutlinedTextFieldCustom(
                label = R.string.fuelled,
                value = viewModel.uiState.value.dataFillingTwo.fuelled,
                onValueChange = viewModel::updateDataFillingTwoFuelled,
                tag = Tags.TAG_TEST_DATA_FILLING_TWO_FUELLED,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
        }

        BottomBarCustom(
            onNext = { navController.navigate(ReportsForDriversSchema.ProgressReport.name) },
            onBack = { navController.navigateUp() }
        )
    }
    DatePickerDialogCustom(
        viewModel.openDialogDataFillingTwoDateDeparture,
        viewModel::updateDataFillingTwoDateDeparture
    )
    DatePickerDialogCustom(
        viewModel.openDialogDataFillingTwoDateReturn,
        viewModel::updateDataFillingTwoDateReturn
    )
    DatePickerDialogCustom(
        viewModel.openDialogDataFillingTwoDateCrossingDeparture,
        viewModel::updateDataFillingTwoDateCrossingDeparture
    )
    DatePickerDialogCustom(
        viewModel.openDialogDataFillingTwoDateCrossingReturn,
        viewModel::updateDataFillingTwoDateCrossingReturn
    )
}

@Composable
private fun TabRowDataFillingTwo(
    navController: NavHostController,
    viewModel: CreateReportsViewModel
) {
    TabRow(selectedTabIndex = 3) {
        Tab(
            text = { Text("1") },
            selected = false,
            onClick = {
                navController.navigate(ReportsForDriversSchema.ReportInfo.name) {
                    popUpTo(ReportsForDriversSchema.Start.name)
                }
            }
        )
        Tab(
            text = { Text("2") },
            selected = false,
            onClick = {
                navController.navigate(ReportsForDriversSchema.PersonalInfo.name) {
                    popUpTo(ReportsForDriversSchema.ReportInfo.name)
                }
            }
        )
        Tab(
            text = { Text("3") },
            selected = false,
            onClick = {
                navController.navigate(ReportsForDriversSchema.VehicleInfo.name) {
                    popUpTo(ReportsForDriversSchema.PersonalInfo.name)
                }
            }
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
            enabled = viewModel.isNextDataFillingTwoValidate()
        )
        Tab(
            text = { Text("6") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.TripExpenses.name) },
            enabled = viewModel.isNextDataFillingTwoValidate() &&
                    viewModel.isValidateNextProgressReports()
        )
    }
}

