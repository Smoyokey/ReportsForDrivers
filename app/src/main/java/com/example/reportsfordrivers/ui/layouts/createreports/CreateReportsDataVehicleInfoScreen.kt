package com.example.reportsfordrivers.ui.layouts.createreports

import android.util.Log
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import com.example.reportsfordrivers.ui.layouts.custom.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.layouts.custom.TabRowCustom
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.createreports.CreateVehicleTrailerViewModel
import com.example.reportsfordrivers.viewmodel.firstentry.VehicleOrTrailer

private const val TAG = "CreateReportsDataVehicleInfoScreen"

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CreateReportsDataVehicleInfoScreen(
    viewModel: CreateVehicleTrailerViewModel = hiltViewModel<CreateVehicleTrailerViewModel>(),
    navController: NavHostController = rememberNavController()
) {

    val (makeVehicle, rnVehicle, makeTrailer, rnTrailer) = remember { FocusRequester.createRefs() }
    val keyboardController = LocalSoftwareKeyboardController.current

    BackHandler {
        navController.navigate(
            ReportsForDriversSchema.PersonalInfo.name,
            navOptions = NavOptions.Builder()
                .setPopUpTo(ReportsForDriversSchema.PersonalInfo.name, true)
                .build()
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {

        TabRowCustom(
            index = 2,
            navController = navController,
            isEnabledThree = false,
            isEnabledFour = viewModel.uiStateIsValidate.value.isValidateCreateVehicleTrailer,
            isEnabledFive = viewModel.uiStateIsValidate.value.isValidateCreateRoute,
            isEnabledSix = viewModel.uiStateIsValidate.value.isValidateCreateProgressReports
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp, bottom = 16.dp, start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            if(viewModel.uiStateListVehicle.value.listVehicle.isNotEmpty()) {
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
                            if(viewModel.uiStateCreateVehicleTrailer.value.makeVehicle.isNotEmpty()) {
                                IconButton(
                                    onClick = {viewModel.updateDataCreateVehicleTrailerMakeVehicle("")}
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Clear,
                                        contentDescription = stringResource(R.string.clear)
                                    )
                                }
                            } else if(viewModel.uiStateListVehicle.value.listVehicle.isNotEmpty()) {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = viewModel.openMenuMakeVehicleCreateVehicleTrailer.value
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
                                    makeTrailer.requestFocus()
                                }
                            )
                        }
                    }
                }
            } else { //Если нет ниодного добавленного транспорта
                OutlinedTextField(
                    value = viewModel.uiStateCreateVehicleTrailer.value.makeVehicle,
                    label = { Text(stringResource(R.string.make_vehicle)) },
                    onValueChange = { viewModel.updateDataCreateVehicleTrailerMakeVehicle(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(makeVehicle),
                    textStyle = typography.bodyLarge,
                    trailingIcon = {
                        if(viewModel.uiStateCreateVehicleTrailer.value.makeVehicle.isNotEmpty()) {
                            IconButton(
                                onClick = { viewModel.updateDataCreateVehicleTrailerMakeVehicle("") }
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
                value = viewModel.uiStateCreateVehicleTrailer.value.rnVehicle,
                label = { Text(stringResource(R.string.rn_vehicle)) },
                onValueChange = { viewModel.updateDataCreateVehicleTrailerRnVehicle(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(rnVehicle),
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if(viewModel.uiStateCreateVehicleTrailer.value.rnVehicle.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.updateDataCreateVehicleTrailerRnVehicle("") }
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
                    viewModel.openDialogCreateVehicleCreateVehicleTrailer.value = true
                },
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Text(text = stringResource(R.string.missing_from_list_create))
            }


            if(viewModel.uiStateListTrailer.value.listTrailer.isNotEmpty()) {
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
                            .focusRequester(makeTrailer)
                            .menuAnchor(),
                        trailingIcon = {
                            if(viewModel.uiStateCreateVehicleTrailer.value.makeTrailer.isEmpty()) {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = viewModel.openMenuMakeTrailerCreateVehicleTrailer.value
                                )
                            } else {
                                IconButton(
                                    onClick = {
                                        viewModel.updateDataCreateVehicleTrailerMakeTrailer("")
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
                                    keyboardController?.hide()
                                }
                            )
                        }
                    }
                }
            } else {
                OutlinedTextField(
                    value = viewModel.uiStateCreateVehicleTrailer.value.makeTrailer,
                    label = { Text(stringResource(R.string.make_trailer)) },
                    onValueChange = { viewModel.updateDataCreateVehicleTrailerMakeTrailer("") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(makeTrailer),
                    textStyle = typography.bodyLarge,
                    trailingIcon = {
                        if(viewModel.uiStateCreateVehicleTrailer.value.makeTrailer.isNotEmpty()) {
                            IconButton(
                                onClick = { viewModel.updateDataCreateVehicleTrailerMakeTrailer("")}
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
                value = viewModel.uiStateCreateVehicleTrailer.value.rnTrailer,
                label = { Text(stringResource(R.string.rn_trailer)) },
                onValueChange = { viewModel.updateDataCreateVehicleTrailerRnTrailer(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(rnTrailer),
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if(viewModel.uiStateCreateVehicleTrailer.value.rnTrailer.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.updateDataCreateVehicleTrailerRnTrailer("")}
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
                    viewModel.openDialogCreateTrailerCreateVehicleTrailer.value = true
                },
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Text(text = stringResource(R.string.missing_from_list_create))
            }
        }

        BottomBarCustom(
            onNext = { navController.navigate(ReportsForDriversSchema.Route.name) },
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