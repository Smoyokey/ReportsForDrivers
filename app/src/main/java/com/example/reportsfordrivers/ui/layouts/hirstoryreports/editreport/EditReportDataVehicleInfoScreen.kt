package com.example.reportsfordrivers.ui.layouts.hirstoryreports.editreport

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.ui.layouts.custom.AlertDialogAddVehicle
import com.example.reportsfordrivers.ui.layouts.custom.BottomBarCustom
import com.example.reportsfordrivers.ui.layouts.custom.TabRowCustom
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.firstentry.VehicleOrTrailer
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.viewmodel.EditVehicleTrailerViewModel

private const val TAG = "EditReportDataVehicleInfoScreen"

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun EditReportDataVehicleInfoScreen(
    viewModel: EditVehicleTrailerViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val (makeVehicle, rnVehicle, makeTrailer, rnTrailer) = remember { FocusRequester.createRefs() }
    val keyboardController = LocalSoftwareKeyboardController.current

    BackHandler {
        navController.navigate(
            ReportsForDriversSchema.EditPersonalInfo.name,
            navOptions = NavOptions.Builder()
                .setPopUpTo(ReportsForDriversSchema.EditPersonalInfo.name, true)
                .build()
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {

        TabRowCustom(
            index = 2,
            navController = navController,
            isEnabledThree = false
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp, bottom = 16.dp, start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            if (viewModel.isHaveVehicleInList.value) {
                ExposedDropdownMenuBox(
                    expanded = viewModel.openMenuMakeVehicleEditVehicleTrailer.value,
                    onExpandedChange = {
                        viewModel.openMenuMakeVehicleEditVehicleTrailer.value =
                            !viewModel.openMenuMakeVehicleEditVehicleTrailer.value
                    }
                ) {
                    OutlinedTextField(
                        label = { Text(text = stringResource(R.string.make_vehicle)) },
                        value = viewModel.uiStateEditVehicleTrailer.value.makeVehicle,
                        onValueChange = viewModel::updateDataEditVehicleTrailerMakeVehicle,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        trailingIcon = {
                            if(viewModel.uiStateEditVehicleTrailer.value.makeVehicle.isNotEmpty()) {
                                IconButton(
                                    onClick = { viewModel.updateDataEditVehicleTrailerMakeVehicle("") }
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Clear,
                                        contentDescription = stringResource(R.string.clear)
                                    )
                                }
                            } else if (viewModel.uiStateListVehicle.value.listVehicle.isNotEmpty()) {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = viewModel.openMenuMakeVehicleEditVehicleTrailer.value
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = {rnVehicle.requestFocus()})
                    )
                    ExposedDropdownMenu(
                        expanded = viewModel.openMenuMakeVehicleEditVehicleTrailer.value,
                        onDismissRequest = {
                            viewModel.openMenuMakeVehicleEditVehicleTrailer.value = false
                        }
                    ) {
                        for(i in viewModel.uiStateListVehicle.value.listVehicle) {
                            DropdownMenuItem(
                                text = { Text(text = "${i.makeVehicle}: ${i.rnVehicle}") },
                                onClick = {
                                    viewModel.run {
                                        updateDataEditVehicleTrailerMakeVehicle(i.makeVehicle)
                                        updateDataEditVehicleTrailerRnVehicle(i.rnVehicle)
                                        openMenuMakeVehicleEditVehicleTrailer.value = false
                                        makeTrailer.requestFocus()
                                    }
                                }
                            )
                        }
                    }
                }
            } else {
                OutlinedTextField(
                    value = viewModel.uiStateEditVehicleTrailer.value.makeVehicle,
                    label = { Text(stringResource(R.string.make_vehicle)) },
                    onValueChange = { viewModel.updateDataEditVehicleTrailerMakeVehicle(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(makeVehicle),
                    textStyle = typography.bodyLarge,
                    trailingIcon = {
                        if (viewModel.uiStateEditVehicleTrailer.value.makeVehicle.isNotEmpty()) {
                            IconButton(
                                onClick = { viewModel.updateDataEditVehicleTrailerMakeVehicle("") }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Clear,
                                    contentDescription = stringResource(R.string.clear)
                                )
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {rnVehicle.requestFocus()})
                )
            }

            OutlinedTextField(
                value = viewModel.uiStateEditVehicleTrailer.value.rnVehicle,
                label = { Text(stringResource(R.string.rn_vehicle)) },
                onValueChange = { viewModel.updateDataEditVehicleTrailerRnVehicle(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(rnVehicle),
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if (viewModel.uiStateEditVehicleTrailer.value.rnVehicle.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.updateDataEditVehicleTrailerRnVehicle("") }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = stringResource(R.string.clear)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {makeTrailer.requestFocus()})
            )

            TextButton(
                onClick = {
                    viewModel.openDialogCreateVehicleEditVehicleTrailer.value = true
                },
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Text(text = stringResource(R.string.missing_from_list_create))
            }

            if(viewModel.isHaveTrailerInList.value) {
                ExposedDropdownMenuBox(
                    expanded = viewModel.openMenuMakeTrailerEditVehicleTrailer.value,
                    onExpandedChange = {
                        viewModel.openMenuMakeTrailerEditVehicleTrailer.value =
                            !viewModel.openMenuMakeTrailerEditVehicleTrailer.value
                    }
                ) {
                    OutlinedTextField(
                        label = { Text(text = stringResource(R.string.make_trailer)) },
                        value = viewModel.uiStateEditVehicleTrailer.value.makeTrailer,
                        onValueChange = viewModel::updateDataEditVehicleTrailerMakeTrailer,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(makeTrailer)
                            .menuAnchor(),
                        trailingIcon = {
                            if(viewModel.uiStateEditVehicleTrailer.value.makeTrailer.isEmpty()) {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = viewModel.openMenuMakeTrailerEditVehicleTrailer.value
                                )
                            } else {
                                IconButton(
                                    onClick = {
                                        viewModel.updateDataEditVehicleTrailerMakeTrailer("")
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Clear,
                                        contentDescription = null
                                    )
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = {rnTrailer.requestFocus()})
                    )
                    ExposedDropdownMenu(
                        expanded = viewModel.openMenuMakeVehicleEditVehicleTrailer.value,
                        onDismissRequest = {
                            viewModel.openMenuMakeTrailerEditVehicleTrailer.value = false
                        }
                    ) {
                        for(i in viewModel.uiStateListTrailer.value.listTrailer) {
                            DropdownMenuItem(
                                text = { Text(text = "${i.makeTrailer}: ${i.rnTrailer}") },
                                onClick = {
                                    viewModel.run {
                                        updateDataEditVehicleTrailerMakeTrailer(i.makeTrailer)
                                        updateDataEditVehicleTrailerRnTrailer(i.rnTrailer)
                                        openMenuMakeTrailerEditVehicleTrailer.value = false
                                        keyboardController?.hide()
                                    }
                                }
                            )
                        }
                    }
                }
            } else {
                OutlinedTextField(
                    value = viewModel.uiStateEditVehicleTrailer.value.makeTrailer,
                    label = { Text(stringResource(R.string.make_trailer)) },
                    onValueChange = { viewModel.updateDataEditVehicleTrailerMakeTrailer("") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(makeTrailer),
                    textStyle = typography.bodyLarge,
                    trailingIcon = {
                        if (viewModel.uiStateEditVehicleTrailer.value.makeTrailer.isNotEmpty()) {
                            IconButton(
                                onClick = { viewModel.updateDataEditVehicleTrailerMakeTrailer("")}
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Clear,
                                    contentDescription = stringResource(R.string.clear)
                                )
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {rnTrailer.requestFocus()})
                )
            }

            OutlinedTextField(
                value = viewModel.uiStateEditVehicleTrailer.value.rnTrailer,
                label = { Text(stringResource(R.string.rn_trailer)) },
                onValueChange = { viewModel.updateDataEditVehicleTrailerRnTrailer(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(rnTrailer),
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if(viewModel.uiStateEditVehicleTrailer.value.rnTrailer.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.updateDataEditVehicleTrailerRnTrailer("") }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = stringResource(R.string.clear)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()})
            )

            TextButton(
                onClick = {
                    viewModel.openDialogCreateTrailerEditVehicleTrailer.value = true
                },
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Text(text = stringResource(R.string.missing_from_list_create))
            }
        }

        BottomBarCustom(
            onNext = { navController.navigate(ReportsForDriversSchema.EditRoute.name) },
            onBack = { navController.navigateUp() },
            enabled = viewModel.isValidateDataEditVehicleTrailer()
        )
    }

    if(viewModel.openDialogCreateVehicleEditVehicleTrailer.value) {
        AlertDialogAddVehicle(
            isOpenDialog = viewModel.openDialogCreateVehicleEditVehicleTrailer,
            title = R.string.add,
            headText = R.string.enter_make_number_vehicle,
            labelMake = R.string.make_vehicle,
            labelRn = R.string.rn_vehicle,
            saveInDB = viewModel::saveVehicleInDb,
            type = VehicleOrTrailer.VEHICLE.name
        )
    }
    if(viewModel.openDialogCreateTrailerEditVehicleTrailer.value) {
        AlertDialogAddVehicle(
            isOpenDialog = viewModel.openDialogCreateTrailerEditVehicleTrailer,
            title = R.string.add,
            headText = R.string.enter_make_number_trailer,
            labelMake = R.string.make_trailer,
            labelRn = R.string.rn_trailer,
            saveInDB = viewModel::saveTrailerInDb,
            type = VehicleOrTrailer.TRAILER.name
        )
    }
}