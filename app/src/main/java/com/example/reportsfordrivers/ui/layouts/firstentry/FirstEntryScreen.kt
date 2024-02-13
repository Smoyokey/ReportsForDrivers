package com.example.reportsfordrivers.ui.layouts.firstentry

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.viewmodel.firstentry.FioItemDetails
import com.example.reportsfordrivers.viewmodel.firstentry.FirstEntryViewModel
import com.example.reportsfordrivers.viewmodel.firstentry.IsSelectedVehicleAndTrailer
import com.example.reportsfordrivers.viewmodel.firstentry.MakeRnItemDetails
import com.example.reportsfordrivers.viewmodel.firstentry.ObjectVehicle
import com.example.reportsfordrivers.viewmodel.firstentry.VehicleOrTrailer


@Composable
fun FirstEntryScreen(
    viewModel: FirstEntryViewModel = hiltViewModel(),
    onMainMenu: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    val openDialog = remember { mutableStateOf(false) } //Временно

    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(state = scrollState)
            .testTag(Tags.TAG_COLUMN_FIRST_ENTRY)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.enter_information_yourself),
                fontSize = 20.sp,
                modifier = Modifier.weight(1f)
                )
            OutlinedIconButton(
                onClick = {
                       openDialog.value = true
                }
            ) {
                Icon(
                    Icons.Outlined.Info,
                    contentDescription = stringResource(R.string.info)
                )
            }
            OpenDialogHelp()
        }

        ItemInputFormFio(
            itemDetails = viewModel.uiState.value.fioItemDetails,
            onValueChange = viewModel::updateFio,
        )

        Divider(
            modifier = Modifier.padding(10.dp)
        )

        Text(
            text = stringResource(R.string.add_makes_registration_vehicles_trailers),
            fontSize = 20.sp
        )

        Row(
            modifier = Modifier.selectableGroup()
        ) {
            RadioButtonDef(
                R.string.vehicle,
                modifier = Modifier.weight(1f),
                selected = viewModel.vehicleUiState.value.isSelected.stateRadioGroup,
                onClick = {
                    viewModel.selectedPosition(
                        IsSelectedVehicleAndTrailer(true)
                    )
                })
            RadioButtonDef(
                R.string.trailer,
                modifier = Modifier.weight(1f),
                selected = !viewModel.vehicleUiState.value.isSelected.stateRadioGroup,
                onClick = {
                    viewModel.selectedPosition(
                        IsSelectedVehicleAndTrailer(false)
                    )
                })
        }

        ItemInputFromMakeAndRn(
            makeRnItemDetails = viewModel.vehicleUiState.value.makeRnItemDetails,
            onValueChange = viewModel::updateMakeRn,
            isSelected = viewModel.vehicleUiState.value.isSelected
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { viewModel.addElementVehicle() },
                enabled = viewModel.validateAddVehicle()
            ) {
                Text(
                    text = stringResource(R.string.add)
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = stringResource(R.string.type),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.make),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.rn),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }

        for (i in 0..<viewModel.uiState.value.listVehicles.size) {
            val a = viewModel.uiState.value.listVehicles[i]
            TableMakeRn(
                vehicleObject = a,
                listObject = viewModel.uiState.value.listVehicles,
                onClick = viewModel::deletePositionVehicle
            )
        }


        Divider(
            modifier = Modifier.padding(10.dp)
        )

        Row() {
            Text(
                text = stringResource(R.string.save_entered_data),
                modifier = Modifier.weight(1f)
            )
        }

        Divider(
            modifier = Modifier.padding(10.dp)
        )

        Row() {
            Button(
                onClick = onMainMenu,
                Modifier.weight(1f)
            ) {
                Text(text = stringResource(R.string.skip))
            }
            Button(
                onClick = onMainMenu,
                enabled = false, //StateUI
                modifier = Modifier.weight(1f)
            ) {
                Text(text = stringResource(R.string.save))
            }
        }
    }
}

/**
 * Переделать придется метод под UIState
 */
@Composable
fun TableMakeRn(
    vehicleObject: ObjectVehicle,
    listObject: MutableList<ObjectVehicle> = mutableListOf(),
    onClick: (MutableList<ObjectVehicle>) -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = vehicleObject.vehicleOrTrailer.name,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = vehicleObject.make,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = vehicleObject.rn,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        IconButton(
            onClick = {}
        ) {
            Icon(
                painter = painterResource(R.drawable.close_24px),
                contentDescription = stringResource(R.string.delete),
                modifier = Modifier.clickable {
                    listObject.remove(vehicleObject)
                    onClick(listObject)
                }
            )
        }
    }
}

@Composable
fun RadioButtonDef( //Вроде бы работает
    text: Int, modifier: Modifier = Modifier, selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            modifier = Modifier.semantics { contentDescription = text.toString() }
        )
        Text(
            text = stringResource(text),
        )
    }
}

@Composable
fun ItemInputFromMakeAndRn(
    makeRnItemDetails: MakeRnItemDetails,
    modifier: Modifier = Modifier,
    onValueChange: (MakeRnItemDetails) -> Unit = {},
    isSelected: IsSelectedVehicleAndTrailer,
) {
    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = makeRnItemDetails.make,
            onValueChange = { onValueChange(makeRnItemDetails.copy(make = it)) },
            label = {
                Text(
                    text = if (isSelected.stateRadioGroup) stringResource(R.string.make_vehicle)
                    else stringResource(R.string.make_trailer)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if (makeRnItemDetails.make.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.clear),
                        modifier = Modifier
                            .clickable {
                                onValueChange(makeRnItemDetails.copy(make = ""))
                            }
                            .testTag(Tags.TAG_TEST_FIRST_ENTRY_MAKE)
                    )
                }
            }
        )
        OutlinedTextField(
            value = makeRnItemDetails.rn,
            onValueChange = { onValueChange(makeRnItemDetails.copy(rn = it)) },
            label = {
                Text(
                    text = if (isSelected.stateRadioGroup) stringResource(R.string.rn_vehicle)
                    else stringResource(R.string.rn_trailer)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if (makeRnItemDetails.rn.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.clear),
                        modifier = Modifier
                            .clickable {
                                onValueChange(makeRnItemDetails.copy(rn = ""))
                            }
                            .testTag(Tags.TAG_TEST_FIRST_ENTRY_RN)
                    )
                }
            }
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ItemInputFormFio(
    //Вроде бы работает
    itemDetails: FioItemDetails,
    onValueChange: (FioItemDetails) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = itemDetails.lastName,
            onValueChange = { onValueChange(itemDetails.copy(lastName = it)) },
            label = { Text(stringResource(R.string.last_name)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if (itemDetails.lastName.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.clear),
                        modifier = Modifier
                            .clickable {
                                onValueChange(itemDetails.copy(lastName = ""))
                            }
                            .testTag(Tags.TAG_TEST_FIRST_ENTRY_LAST_NAME)
                    )
                }
            }
        )
        OutlinedTextField(
            value = itemDetails.firstName,
            onValueChange = { onValueChange(itemDetails.copy(firstName = it)) },
            label = { Text(stringResource(R.string.first_name)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if (itemDetails.firstName.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.clear),
                        modifier = Modifier
                            .clickable {
                                onValueChange(itemDetails.copy(firstName = ""))
                            }
                            .testTag(Tags.TAG_TEST_FIRST_ENTRY_FIRST_NAME)
                    )
                }
            }
        )
        OutlinedTextField(
            value = itemDetails.patronymic,
            onValueChange = { onValueChange(itemDetails.copy(patronymic = it)) },
            label = { Text(stringResource(R.string.patronymic)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if (itemDetails.patronymic.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.clear),
                        modifier = Modifier
                            .clickable {
                                onValueChange(itemDetails.copy(patronymic = ""))
                            }
                            .testTag(Tags.TAG_TEST_FIRST_ENTRY_PATRONYMIC)
                    )
                }
            }
        )
    }
}

@Composable
fun OpenDialogHelp(openDialog: MutableState<Boolean> = mutableStateOf(true)) {
    if(openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = stringResource(R.string.test))},
            text = { Text(text = stringResource(R.string.test))},
            confirmButton = {},
            dismissButton = {}
        )
    }
}

@Preview
@Composable
fun OpenDialogHelpPreview() {
    OpenDialogHelp()
}


@Preview(showBackground = true)
@Composable
fun FirstEntryScreenPreview() {
    FirstEntryScreen(onMainMenu = {})
}

@Preview(showBackground = true)
@Composable
fun TableMakeRnPreview() {
    TableMakeRn(
        ObjectVehicle(
            make = "DAF",
            rn = "1234II-1",
            vehicleOrTrailer = VehicleOrTrailer.VEHICLE
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun ItemInputFormFioPreview() {
    ItemInputFormFio(FioItemDetails(), {})
}

