package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.ui.BottomBarCustom
import com.example.reportsfordrivers.ui.DatePickerDialogCustom
import com.example.reportsfordrivers.ui.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.OutlinedTextFieldDatePicker
import com.example.reportsfordrivers.ui.RowDate
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportsViewModel
import com.example.reportsfordrivers.viewmodel.createreports.uistate.RouteElement

@Composable
fun CreateReportsDataFillingTwoScreen(
    viewModel: CreateReportsViewModel = hiltViewModel(),
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

        if(viewModel.uiState.value.dataFillingTwo.route.size == 0) {
            viewModel.uiState.value.dataFillingTwo.route.add(RouteElement(0, ""))
            viewModel.uiState.value.dataFillingTwo.route.add(RouteElement(1, ""))
        }

        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 16.dp)
                .verticalScroll(state = scrollState)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            /**
             * Для реализации нужен объект в котором будет его порядковый номер и значение
             * А еще нужен список этих объектов, что бы хранить полностью информацию
             */
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
//                OutlinedTextFieldCustom(
//                    label = R.string.route,
//                    value = viewModel.uiState.value.dataFillingTwo.route,
//                    onValueChange = viewModel::updateDataFillingTwoRoute,
//                    tag = Tags.TAG_TEST_DATA_FILLING_TWO_ROUTE,
//                    modifier = Modifier.weight(1f),
//                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
//                )
            }
            for(i in 0..<viewModel.uiState.value.dataFillingTwo.route.size)
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextFieldCustom(
                    label = R.string.route,
                    value = viewModel.uiState.value.dataFillingTwo.route[i].text,
                    onValueChange = { viewModel.updateDataFillingTwoRoute(it, i) },
                    tag = "",
                    modifier = Modifier.weight(1f)
                )
                if(i == viewModel.uiState.value.dataFillingTwo.route.size - 1) {
                    IconButton(
                        onClick = {
                            viewModel.uiState.value.dataFillingTwo.route.add(
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
                            viewModel.uiState.value.dataFillingTwo.route.removeAt(i)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Clear,
                            contentDescription = null
                        )
                    }
                }
            }

            Divider()

            RowDate(
                label = R.string.date_departure,
                openDialog = viewModel.openDialogDataFillingTwoDateDeparture,
                date = viewModel.uiState.value.dataFillingTwo.dateDeparture
            )

            RowDate(
                label = R.string.date_return,
                openDialog = viewModel.openDialogDataFillingTwoDateReturn,
                date = viewModel.uiState.value.dataFillingTwo.dateReturn
            )

            RowDate(
                label = R.string.date_border_crossing_departure,
                openDialog = viewModel.openDialogDataFillingTwoDateCrossingDeparture,
                date = viewModel.uiState.value.dataFillingTwo.dateCrossingDeparture
            )

            RowDate(
                label = R.string.date_border_crossing_return,
                openDialog = viewModel.openDialogDataFillingTwoDateCrossingReturn,
                date = viewModel.uiState.value.dataFillingTwo.dateCrossingReturn
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
            onBack = { navController.navigateUp() },
            enabled = viewModel.isNextDataFillingTwoValidate()
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

