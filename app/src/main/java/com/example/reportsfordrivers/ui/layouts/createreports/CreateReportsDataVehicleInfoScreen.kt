package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
fun CreateReportsDataVehicleInfoScreen(
    viewModel: CreateReportsViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
//    navController.popBackStack(ReportsForDriversSchema.PersonalInfo.name, false)

    BackHandler{
        navController.navigate(
            ReportsForDriversSchema.PersonalInfo.name,
            navOptions = NavOptions.Builder()
                .setPopUpTo(ReportsForDriversSchema.PersonalInfo.name, true)
                .build()
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TabRowDataVehicleInfo(navController = navController, viewModel = viewModel)

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp, bottom = 16.dp, start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {


            OutlinedTextFieldCustom(
                label = R.string.make_vehicle,
                value = viewModel.uiState.value.dataVehicleInfo.makeVehicle,
                onValueChange = viewModel::updateDataVehicleInfoMakeVehicle,
                tag = Tags.TAG_TEST_DATA_VEHICLE_INFO_MAKE_VEHICLE,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextFieldCustom(
                label = R.string.rn_vehicle,
                value = viewModel.uiState.value.dataVehicleInfo.rnVehicle,
                onValueChange = viewModel::updateDataVehicleInfoRnVehicle,
                tag = Tags.TAG_TEST_DATA_VEHICLE_INFO_RN_VEHICLE,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextFieldCustom(
                label = R.string.make_trailer,
                value = viewModel.uiState.value.dataVehicleInfo.makeTrailer,
                onValueChange = viewModel::updateDataVehicleInfoMakeTrailer,
                tag = Tags.TAG_TEST_DATA_VEHICLE_INFO_MAKE_TRAILER,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextFieldCustom(
                label = R.string.rn_trailer,
                value = viewModel.uiState.value.dataVehicleInfo.rnTrailer,
                onValueChange = viewModel::updateDataVehicleInfoRnTrailer,
                tag = Tags.TAG_TEST_DATA_VEHICLE_INFO_RN_TRAILER,
                modifier = Modifier.fillMaxWidth()
            )
        }

        BottomBarCustom(
            onNext = { navController.navigate(ReportsForDriversSchema.FillingDataTwo.name) },
            onBack = { navController.navigateUp() }
        )
    }
}

@Composable
private fun TabRowDataVehicleInfo(
    navController: NavHostController,
    viewModel: CreateReportsViewModel
) {
    TabRow(selectedTabIndex = 2) {
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
            onClick = { }
        )
        Tab(
            text = { Text("4") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.FillingDataTwo.name) },
            enabled = viewModel.isValidateDataVehicleInfo()
        )
        Tab(
            text = { Text("5") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.ProgressReport.name) },
            enabled = viewModel.isValidateDataVehicleInfo() &&
                    viewModel.isNextDataFillingTwoValidate()
        )
        Tab(
            text = { Text("6") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.TripExpenses.name) },
            enabled = viewModel.isValidateDataVehicleInfo() &&
                    viewModel.isNextDataFillingTwoValidate() &&
                    viewModel.isValidateNextProgressReports()
        )
    }

}