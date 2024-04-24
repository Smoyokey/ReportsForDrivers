package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.ui.AlertDialogAddVehicle
import com.example.reportsfordrivers.ui.BottomBarCustom
import com.example.reportsfordrivers.ui.OutlinedTextFieldCustom
import com.example.reportsfordrivers.viewmodel.createreports.CreateVehicleTrailerViewModel
import com.example.reportsfordrivers.viewmodel.firstentry.VehicleOrTrailer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateReportsDataVehicleInfoScreen(
    viewModel: CreateVehicleTrailerViewModel = hiltViewModel<CreateVehicleTrailerViewModel>(),
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
//        viewModel.startLoadDB()

        TabRowDataVehicleInfo(navController = navController, viewModel = viewModel)

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp, bottom = 16.dp, start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            ExposedDropdownMenuBox(
                expanded = viewModel.openMenuMakeVehicleCreateVehicleTrailer.value,
                onExpandedChange = {
                    viewModel.openMenuMakeVehicleCreateVehicleTrailer.value =
                        !viewModel.openMenuMakeVehicleCreateVehicleTrailer.value
                }
            ) {
                OutlinedTextField(
                    label = { Text(text = stringResource(R.string.make_vehicle)) },
                    value = viewModel.uiStateCreateVehicleTrailer.value.makeVehicle,
                    onValueChange = viewModel::updateDataCreateVehicleTrailerMakeVehicle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = viewModel.openMenuMakeVehicleCreateVehicleTrailer.value
                        )
                    }
                )
                ExposedDropdownMenu(
                    expanded = viewModel.openMenuMakeVehicleCreateVehicleTrailer.value,
                    onDismissRequest = {
                        viewModel.openMenuMakeVehicleCreateVehicleTrailer.value = false
                    })
                {
                    for(i in viewModel.uiStateListVehicle.value.listVehicle) {
                        DropdownMenuItem(
                            text = { Text(text = "${i.makeVehicle}: ${i.rnVehicle}") },
                            onClick = {
                                viewModel.updateDataCreateVehicleTrailerMakeVehicle(i.makeVehicle)
                                viewModel.updateDataCreateVehicleTrailerRnVehicle(i.rnVehicle)
                                viewModel.openMenuMakeVehicleCreateVehicleTrailer.value = false
                            }
                        )
                    }
                }
            }

            OutlinedTextFieldCustom(
                label = R.string.rn_vehicle,
                value = viewModel.uiStateCreateVehicleTrailer.value.rnVehicle,
                onValueChange = viewModel::updateDataCreateVehicleTrailerRnVehicle,
                tag = "",
                modifier = Modifier.fillMaxWidth()
            )

            TextButton(
                onClick = {
                    viewModel.openDialogCreateVehicleCreateVehicleTrailer.value = true
                },
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Text(text = stringResource(R.string.missing_from_list_create))
            }


            ExposedDropdownMenuBox(
                expanded = viewModel.openMenuMakeTrailerCreateVehicleTrailer.value,
                onExpandedChange = {
                    viewModel.openMenuMakeTrailerCreateVehicleTrailer.value =
                        !viewModel.openMenuMakeTrailerCreateVehicleTrailer.value
                }
            ) {
                OutlinedTextField(
                    label = { Text(text = stringResource(R.string.make_trailer)) },
                    value = viewModel.uiStateCreateVehicleTrailer.value.makeTrailer,
                    onValueChange = viewModel::updateDataCreateVehicleTrailerMakeTrailer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = viewModel.openMenuMakeTrailerCreateVehicleTrailer.value
                        )
                    }
                )
                ExposedDropdownMenu(
                    expanded = viewModel.openMenuMakeTrailerCreateVehicleTrailer.value,
                    onDismissRequest = {
                        viewModel.openMenuMakeTrailerCreateVehicleTrailer.value = false
                    })
                {
                    for(i in viewModel.uiStateListTrailer.value.listTrailer) {
                        DropdownMenuItem(
                            text = { Text(text = "${i.makeTrailer}: ${i.rnTrailer}") },
                            onClick = {
                                viewModel.updateDataCreateVehicleTrailerMakeTrailer(i.makeTrailer)
                                viewModel.updateDataCreateVehicleTrailerRnTrailer(i.rnTrailer)
                                viewModel.openMenuMakeTrailerCreateVehicleTrailer.value = false
                            }
                        )
                    }
                }
            }

            OutlinedTextFieldCustom(
                label = R.string.rn_trailer,
                value = viewModel.uiStateCreateVehicleTrailer.value.rnTrailer,
                onValueChange = viewModel::updateDataCreateVehicleTrailerRnTrailer,
                tag = "",
                modifier = Modifier.fillMaxWidth()
            )

            TextButton(
                onClick = {
                    viewModel.openDialogCreateTrailerCreateVehicleTrailer.value = true
                },
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Text(text = stringResource(R.string.missing_from_list_create))
            }
        }

        BottomBarCustom(
            onNext = { navController.navigate(ReportsForDriversSchema.FillingDataTwo.name) },
            onBack = { navController.navigateUp() },
            enabled = viewModel.isValidateDataCreateVehicleTrailer()
        )
    }

    if(viewModel.openDialogCreateVehicleCreateVehicleTrailer.value) {
        AlertDialogAddVehicle(
            isOpenDialog = viewModel.openDialogCreateVehicleCreateVehicleTrailer,
            title = R.string.test,
            headText = R.string.test,
            labelMake = R.string.make_vehicle,
            labelRn = R.string.rn_vehicle,
            saveInDB = viewModel::saveVehicleInDb,
            type = VehicleOrTrailer.VEHICLE.name
        )
    }
    if(viewModel.openDialogCreateTrailerCreateVehicleTrailer.value) {
        AlertDialogAddVehicle(
            isOpenDialog = viewModel.openDialogCreateTrailerCreateVehicleTrailer,
            title = R.string.test,
            headText = R.string.test,
            labelMake = R.string.make_trailer,
            labelRn = R.string.rn_vehicle,
            saveInDB = viewModel::saveTrailerInDb,
            type = VehicleOrTrailer.TRAILER.name
        )
    }
}

@Composable
private fun TabRowDataVehicleInfo(
    navController: NavHostController,
    viewModel: CreateVehicleTrailerViewModel
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
            enabled = viewModel.uiStateIsValidate.value.isValidateCreateVehicleTrailer
        )
        Tab(
            text = { Text("5") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.ProgressReport.name) },
            enabled = viewModel.uiStateIsValidate.value.isValidateCreateVehicleTrailer &&
                    viewModel.uiStateIsValidate.value.isValidateCreateRoute
        )
        Tab(
            text = { Text("6") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.TripExpenses.name) },
            enabled = viewModel.uiStateIsValidate.value.isValidateCreateVehicleTrailer &&
                    viewModel.uiStateIsValidate.value.isValidateCreateRoute &&
                    viewModel.uiStateIsValidate.value.isValidateCreateProgressReports
        )
    }

}