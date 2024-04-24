package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.reportsfordrivers.ui.RowDateWithTextField
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportInfoViewModel

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
        TabRowReportInfoScreen(navController = navController, viewModel = viewModel)

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

            OutlinedTextFieldCustom(
                label = R.string.township,
                value = viewModel.uiStateCreateReportInfo.value.mainCity,
                onValueChange = viewModel::updateDataCreateReportInfoMainCity,
                tag = Tags.TAG_TEST_DATA_REPORT_INFO_MAIN_CITY,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = true
                )
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
}

@Composable
private fun TabRowReportInfoScreen(
    navController: NavHostController,
    viewModel: CreateReportInfoViewModel
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
                )
            },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.PersonalInfo.name) },
            enabled = viewModel.uiStateIsValidate.value.isValidateCreateReportInfo,
            modifier = Modifier
        )
        Tab(
            text = { Text("3") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.VehicleInfo.name) },
            enabled = viewModel.uiStateIsValidate.value.isValidateCreateReportInfo &&
                    viewModel.uiStateIsValidate.value.isValidateCreatePersonalInfo
        )
        Tab(
            text = { Text("4") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.FillingDataTwo.name) },
            enabled = viewModel.uiStateIsValidate.value.isValidateCreateReportInfo &&
                    viewModel.uiStateIsValidate.value.isValidateCreatePersonalInfo &&
                    viewModel.uiStateIsValidate.value.isValidateCreateVehicleTrailer
        )
        Tab(
            text = { Text("5") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.ProgressReport.name) },
            enabled = viewModel.uiStateIsValidate.value.isValidateCreateReportInfo &&
                    viewModel.uiStateIsValidate.value.isValidateCreatePersonalInfo &&
                    viewModel.uiStateIsValidate.value.isValidateCreateVehicleTrailer &&
                    viewModel.uiStateIsValidate.value.isValidateCreateRoute
        )
        Tab(
            text = { Text("6") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.TripExpenses.name) },
            enabled = viewModel.uiStateIsValidate.value.isValidateCreateReportInfo &&
                    viewModel.uiStateIsValidate.value.isValidateCreatePersonalInfo &&
                    viewModel.uiStateIsValidate.value.isValidateCreateVehicleTrailer &&
                    viewModel.uiStateIsValidate.value.isValidateCreateRoute &&
                    viewModel.uiStateIsValidate.value.isValidateCreateProgressReports
        )
    }
}