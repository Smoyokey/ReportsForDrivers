package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
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
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportsViewModel

@Composable
fun CreateReportsDataReportInfoScreen(
    onNext: () -> Unit,
    onBack: () -> Unit,
    viewModel: CreateReportsViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {

    val source = remember { MutableInteractionSource() }
    if(source.collectIsPressedAsState().value) viewModel.openDialogDataReportInfoDate.value = true

    Column(modifier = Modifier.fillMaxSize()) {

        TabRow(selectedTabIndex = 0) {
            Tab(
                text = { Text("1")},
                selected = false,
                onClick = {},
                enabled = false
            )
            Tab(
                text = { Text("2")},
                selected = false,
                onClick = { navController.navigate(ReportsForDriversSchema.PersonalInfo.name) },
                enabled = viewModel.isValidateDataReportInfo()
            )
            Tab(
                text = { Text("3")},
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

        Column(modifier = Modifier.weight(1f)) {

            OutlinedTextFieldDatePicker(
                label = R.string.date,
                value = viewModel.uiState.value.dataReportInfo.date,
                interactionSource = source,
                onValueChange = viewModel::updateDataReportInfoDate,
                tag = Tags.TAG_TEST_DATA_REPORT_INFO_DATE,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextFieldCustom(
                label = R.string.township,
                value = viewModel.uiState.value.dataReportInfo.mainCity,
                onValueChange = viewModel::updateDataReportInfoMainCity,
                tag = Tags.TAG_TEST_DATA_REPORT_INFO_MAIN_CITY,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextFieldCustom(
                label = R.string.waybill,
                value = viewModel.uiState.value.dataReportInfo.waybill,
                onValueChange = viewModel::updateDataReportInfoWaybill,
                tag = Tags.TAG_TEST_DATA_REPORT_INFO_WAYBILL,
                modifier = Modifier.fillMaxWidth()
            )
        }

        BottomBarCustom(
            onNext = onNext, onBack = onBack
        )
    }

    DatePickerDialogCustom(
        openDialog = viewModel.openDialogDataReportInfoDate,
        onValueChange = viewModel::updateDataReportInfoDate
    )
}