package com.example.reportsfordrivers.ui.layouts.createreports

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.ui.AlertDialogAddVehicle
import com.example.reportsfordrivers.ui.BottomBarCustom
import com.example.reportsfordrivers.ui.OutlinedTextFieldCustom
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportsViewModel
import com.example.reportsfordrivers.viewmodel.firstentry.VehicleOrTrailer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateReportsDataVehicleInfoScreen(
    viewModel: CreateReportsViewModel = hiltViewModel<CreateReportsViewModel>(),
    navController: NavHostController = rememberNavController()
) {
    BackHandler {
        navController.navigate(
            ReportsForDriversSchema.PersonalInfo.name,
            navOptions = NavOptions.Builder()
                .setPopUpTo(ReportsForDriversSchema.PersonalInfo.name, true)
                .build()
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        viewModel.startLoadDB()

        TabRowDataVehicleInfo(navController = navController, viewModel = viewModel)

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp, bottom = 16.dp, start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            ExposedDropdownMenuBox(
                expanded = viewModel.openMenuMakeVehicle.value,
                onExpandedChange = { viewModel.openMenuMakeVehicle.value = !viewModel.openMenuMakeVehicle.value }
            ) {
                OutlinedTextField(
                    label = { Text(text = stringResource(R.string.make_vehicle)) },
                    value = viewModel.uiState.value.dataVehicleInfo.makeVehicle,
                    onValueChange = viewModel::updateDataVehicleInfoMakeVehicle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.openMenuMakeVehicle.value)
                    }
                )
                ExposedDropdownMenu(expanded = viewModel.openMenuMakeVehicle.value,
                    onDismissRequest = { viewModel.openMenuMakeVehicle.value = false }) {
                    for(i in viewModel.uiStateListVehicle.value.listVehicle) {
                        DropdownMenuItem(
                            text = { Text(text = "${i.make}: ${i.rn}") },
                            onClick = {
                                viewModel.updateDataVehicleInfoMakeVehicle(i.make)
                                viewModel.updateDataVehicleInfoRnVehicle(i.rn)
                                viewModel.openMenuMakeVehicle.value = false
                            }
                        )
                    }
                }
            }

            OutlinedTextFieldCustom(
                label = R.string.rn_vehicle,
                value = viewModel.uiState.value.dataVehicleInfo.rnVehicle,
                onValueChange = viewModel::updateDataVehicleInfoRnVehicle,
                tag = "",
                modifier = Modifier.fillMaxWidth()
            )

            TextButton(
                onClick = {
                    viewModel.openDialogCreateVehicle.value = true
                },
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Text(text = stringResource(R.string.missing_from_list_create))
            }


            ExposedDropdownMenuBox(
                expanded = viewModel.openMenuMakeTrailer.value,
                onExpandedChange = {
                    viewModel.openMenuMakeTrailer.value = !viewModel.openMenuMakeTrailer.value
                }
            ) {
                OutlinedTextField(
                    label = { Text(text = stringResource(R.string.make_trailer)) },
                    value = viewModel.uiState.value.dataVehicleInfo.makeTrailer,
                    onValueChange = viewModel::updateDataVehicleInfoMakeTrailer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = viewModel.openMenuMakeTrailer.value
                        )
                    }
                )
                ExposedDropdownMenu(expanded = viewModel.openMenuMakeTrailer.value,
                    onDismissRequest = { viewModel.openMenuMakeTrailer.value = false }) {
                    for(i in viewModel.uiStateListVehicle.value.listTrailer) {
                        DropdownMenuItem(
                            text = { Text(text = "${i.make}: ${i.rn}") },
                            onClick = {
                                viewModel.updateDataVehicleInfoMakeTrailer(i.make)
                                viewModel.updateDataVehicleInfoRnTrailer(i.rn)
                                viewModel.openMenuMakeTrailer.value = false
                            }
                        )
                    }
                }
            }

            OutlinedTextFieldCustom(
                label = R.string.rn_trailer,
                value = viewModel.uiState.value.dataVehicleInfo.rnTrailer,
                onValueChange = viewModel::updateDataVehicleInfoRnTrailer,
                tag = "",
                modifier = Modifier.fillMaxWidth()
            )

            TextButton(
                onClick = {
                    viewModel.openDialogCreateVehicle.value = true
                },
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Text(text = stringResource(R.string.missing_from_list_create))
            }
        }

        BottomBarCustom(
            onNext = { navController.navigate(ReportsForDriversSchema.FillingDataTwo.name) },
            onBack = { navController.navigateUp() },
            enabled = viewModel.isValidateDataVehicleInfo()
        )
    }

    if(viewModel.openDialogCreateVehicle.value) {
        AlertDialogAddVehicle(
            isOpenDialog = viewModel.openDialogCreateVehicle,
            title = R.string.test,
            headText = R.string.test,
            labelMake = R.string.make_vehicle,
            labelRn = R.string.rn_vehicle,
            saveInDB = viewModel::saveObjectInDB,
            type = VehicleOrTrailer.VEHICLE.name
        )
    }
    if(viewModel.openDialogCreateTrailer.value) {
        AlertDialogAddVehicle(
            isOpenDialog = viewModel.openDialogCreateTrailer,
            title = R.string.test,
            headText = R.string.test,
            labelMake = R.string.make_trailer,
            labelRn = R.string.rn_vehicle,
            saveInDB = viewModel::saveObjectInDB,
            type = VehicleOrTrailer.TRAILER.name
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