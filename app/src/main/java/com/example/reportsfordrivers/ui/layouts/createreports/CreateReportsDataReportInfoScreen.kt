package com.example.reportsfordrivers.ui.layouts.createreports

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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

@Composable
fun CreateReportsDataReportInfoScreen(
    viewModel: CreateReportsViewModel = hiltViewModel(),
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

    val source = remember { MutableInteractionSource() }
    if (source.collectIsPressedAsState().value) viewModel.openDialogDataReportInfoDate.value = true

    Column(modifier = Modifier.fillMaxSize()) {
        TabRowReportInfoScreen(navController = navController, viewModel = viewModel)

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp, start = 10.dp, top = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            RowDate(
                label = R.string.date_create_report,
                openDialog = viewModel.openDialogDataReportInfoDate,
                date = viewModel.uiState.value.dataReportInfo.date
            )

            OutlinedTextFieldCustom(
                label = R.string.township,
                value = viewModel.uiState.value.dataReportInfo.mainCity,
                onValueChange = viewModel::updateDataReportInfoMainCity,
                tag = Tags.TAG_TEST_DATA_REPORT_INFO_MAIN_CITY,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )

            OutlinedTextFieldCustom(
                label = R.string.waybill,
                value = viewModel.uiState.value.dataReportInfo.waybill,
                onValueChange = viewModel::updateDataReportInfoWaybill,
                tag = Tags.TAG_TEST_DATA_REPORT_INFO_WAYBILL,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        BottomBarCustom(
            onNext = { navController.navigate(ReportsForDriversSchema.PersonalInfo.name) },
            onBack = { navController.navigateUp() },
            enabled = viewModel.isValidateDataReportInfo()
        )
    }

    DatePickerDialogCustom(
        openDialog = viewModel.openDialogDataReportInfoDate,
        onValueChange = viewModel::updateDataReportInfoDate
    )
}

@Composable
private fun TabRowReportInfoScreen(
    navController: NavHostController,
    viewModel: CreateReportsViewModel
) {
    TabRow(selectedTabIndex = 0) {
        Tab(
            text = { Text("1") },
            selected = false,
            onClick = { },
            enabled = false
        )
        Tab(
            text = {
                Text(
                    text = "2",
//                    color = MaterialTheme.colorScheme.error
                )
            },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.PersonalInfo.name) },
            enabled = viewModel.isValidateDataReportInfo(),
//            modifier = Modifier.background(
//                if (viewModel.isValidateDataReportInfo()) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onPrimaryContainer
//            )
            modifier = Modifier
        )
        Tab(
            text = { Text("3") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.VehicleInfo.name) },
            enabled = viewModel.isValidateDataReportInfo() &&
                    viewModel.isValidateDataPersonalInfo()
        )
        Tab(
            text = { Text("4") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.FillingDataTwo.name) },
            enabled = viewModel.isValidateDataReportInfo() &&
                    viewModel.isValidateDataPersonalInfo() &&
                    viewModel.isValidateDataVehicleInfo()
        )
        Tab(
            text = { Text("5") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.ProgressReport.name) },
            enabled = viewModel.isValidateDataReportInfo() &&
                    viewModel.isValidateDataPersonalInfo() &&
                    viewModel.isValidateDataVehicleInfo() &&
                    viewModel.isNextDataFillingTwoValidate()
        )
        Tab(
            text = { Text("6") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.TripExpenses.name) },
            enabled = viewModel.isValidateDataReportInfo() &&
                    viewModel.isValidateDataPersonalInfo() &&
                    viewModel.isValidateDataVehicleInfo() &&
                    viewModel.isNextDataFillingTwoValidate() &&
                    viewModel.isValidateNextProgressReports()
        )
    }
}