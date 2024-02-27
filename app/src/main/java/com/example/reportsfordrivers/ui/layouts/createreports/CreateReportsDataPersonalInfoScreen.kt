package com.example.reportsfordrivers.ui.layouts.createreports

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.ui.BottomBarCustom
import com.example.reportsfordrivers.ui.OutlinedTextFieldCustom
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportsViewModel

@Composable
fun CreateReportsDataPersonalInfoScreen(
    onNext: () -> Unit,
    onBack: () -> Unit,
    viewModel: CreateReportsViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    Column(modifier = Modifier.fillMaxSize()) {
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
            onNext = onNext, onBack = onBack
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
            onClick = {
                navController.navigate(ReportsForDriversSchema.ReportInfo.name) {
                    popUpTo(ReportsForDriversSchema.Start.name)
                }
            }
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