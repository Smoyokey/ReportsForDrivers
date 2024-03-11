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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.ui.BottomBarCustom
import com.example.reportsfordrivers.ui.OutlinedTextFieldCustom
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportsViewModel

@Composable
fun CreateReportsDataPersonalInfoScreen(
    viewModel: CreateReportsViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    BackHandler {
        navController.navigate(
            ReportsForDriversSchema.ReportInfo.name,
            navOptions = NavOptions.Builder()
                .setPopUpTo(ReportsForDriversSchema.ReportInfo.name, true)
                .build()
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        viewModel.startFio()

        TabRowDataPersonalInfo(navController = navController, viewModel = viewModel)

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp, bottom = 16.dp, start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            OutlinedTextFieldCustom(
                label = R.string.last_name,
                value = viewModel.uiState.value.dataPersonalInfo.lastName,
                onValueChange = viewModel::updateDataPersonalInfoLastName,
                tag = Tags.TAG_TEST_DATA_PERSONAL_INFO_LAST_NAME,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )

            OutlinedTextFieldCustom(
                label = R.string.first_name,
                value = viewModel.uiState.value.dataPersonalInfo.firstName,
                onValueChange = viewModel::updateDataPersonalInfoFirstName,
                tag = Tags.TAG_TEST_DATA_PERSONAL_INFO_FIRST_NAME,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )

            OutlinedTextFieldCustom(
                label = R.string.patronymic,
                value = viewModel.uiState.value.dataPersonalInfo.patronymic,
                onValueChange = viewModel::updateDataPersonalInfoPatronymic,
                tag = Tags.TAG_TEST_DATA_PERSONAL_INFO_PATRONYMIC,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )
        }

        BottomBarCustom(
            onNext = { navController.navigate(ReportsForDriversSchema.VehicleInfo.name) },
            onBack = { navController.navigateUp() },
            enabled = viewModel.isValidateDataPersonalInfo()
        )
    }
}

@Composable
private fun TabRowDataPersonalInfo(
    navController: NavHostController,
    viewModel: CreateReportsViewModel
) {
    TabRow(selectedTabIndex = 1) {
        Tab(
            text = { Text("1") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.ReportInfo.name) }
        )
        Tab(
            text = { Text("2") },
            selected = false,
            onClick = { },
            enabled = false
        )
        Tab(
            text = { Text("3") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.VehicleInfo.name) },
            enabled = viewModel.isValidateDataPersonalInfo()
        )
        Tab(
            text = { Text("4") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.FillingDataTwo.name) },
            enabled = viewModel.isValidateDataPersonalInfo() &&
                    viewModel.isValidateDataVehicleInfo()
        )
        Tab(
            text = { Text("5") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.ProgressReport.name) },
            enabled = viewModel.isValidateDataPersonalInfo() &&
                    viewModel.isValidateDataVehicleInfo() &&
                    viewModel.isNextDataFillingTwoValidate()
        )
        Tab(
            text = { Text("6") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.TripExpenses.name) },
            enabled = viewModel.isValidateDataPersonalInfo() &&
                    viewModel.isValidateDataVehicleInfo() &&
                    viewModel.isNextDataFillingTwoValidate() &&
                    viewModel.isValidateNextProgressReports()
        )
    }
}