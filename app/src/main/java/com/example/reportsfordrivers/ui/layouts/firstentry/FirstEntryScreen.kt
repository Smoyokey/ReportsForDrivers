package com.example.reportsfordrivers.ui.layouts.firstentry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.ui.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.firstentry.FirstEntryViewModel
import com.example.reportsfordrivers.viewmodel.firstentry.IsSelectedVehicleAndTrailer
import com.example.reportsfordrivers.viewmodel.firstentry.ObjectVehicle

@Composable
fun FirstEntryScreen(
    viewModel: FirstEntryViewModel = hiltViewModel(),
    onMainMenu: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val modifierDivider = Modifier.padding(start = 16.dp, end = 16.dp)

    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(state = scrollState)
            .testTag(Tags.TAG_COLUMN_FIRST_ENTRY),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(R.string.driver),
            style = typography.titleLarge,
        )

        OutlinedTextFieldCustom(
            label = R.string.last_name,
            value = viewModel.uiState.value.fioItemDetails.lastName,
            onValueChange = viewModel::updateLastName,
            tag = Tags.TAG_TEST_FIRST_ENTRY_LAST_NAME,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextFieldCustom(
            label = R.string.first_name,
            value = viewModel.uiState.value.fioItemDetails.firstName,
            onValueChange = viewModel::updateFirstName,
            tag = Tags.TAG_TEST_FIRST_ENTRY_FIRST_NAME,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextFieldCustom(
            label = R.string.patronymic,
            value = viewModel.uiState.value.fioItemDetails.patronymic,
            onValueChange = viewModel::updatePatronymic,
            tag = Tags.TAG_TEST_FIRST_ENTRY_PATRONYMIC,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = stringResource(R.string.add_makes_registration_vehicles_trailers),
            style = typography.titleLarge,
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

        OutlinedTextFieldCustom(
            label = if (viewModel.vehicleUiState.value.isSelected.stateRadioGroup) R.string.make_vehicle
            else {
                R.string.make_trailer
            },
            value = viewModel.vehicleUiState.value.makeRnItemDetails.make,
            onValueChange = viewModel::updateMake,
            tag = Tags.TAG_TEST_FIRST_ENTRY_MAKE,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextFieldCustom(
            label = if (viewModel.vehicleUiState.value.isSelected.stateRadioGroup) R.string.rn_vehicle
            else R.string.rn_trailer,
            value = viewModel.vehicleUiState.value.makeRnItemDetails.rn,
            onValueChange = viewModel::updateRn,
            tag = Tags.TAG_TEST_FIRST_ENTRY_RN,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = { viewModel.addElementVehicle() },
                enabled = viewModel.validateAddVehicle()
            ) {
                Text(
                    text = stringResource(R.string.add),
                    style = typography.titleLarge
                )
            }
        }

        if(viewModel.uiState.value.listVehicles.size > 0) {
            Divider(
                modifier = modifierDivider
            )
        }

        Column {
            for(i in viewModel.uiState.value.listVehicles) {
                TableMakeRn(
                    objectVehicle = i,
                    onClick = viewModel::deletePositionVehicle,
                    isOpenDialog = viewModel.isOpenDialog
                )
            }
        }

        Divider(
            modifier = modifierDivider
        )

        Row(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally)
        ) {
            OutlinedButton(
                onClick = onMainMenu,
                modifier = Modifier.weight(1f),

            ) {
                Text(
                    text = stringResource(R.string.skip),
                    style = typography.titleLarge
                )
            }
            OutlinedButton(
                onClick = {
                    onMainMenu()
                    viewModel.saveButton()
                },
                enabled = viewModel.validateSave(),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.save),
                    style = typography.titleLarge
                )
            }
        }
    }
}

/**
 * AlertDialog - при удалении строки, возникает диалоговое меню подтверждения
 */
@Composable
fun AlertDialogDeleteElement(
    isOpenDialog: MutableState<Boolean>,
    onClick: (ObjectVehicle) -> Unit = {},
    objectVehicle: ObjectVehicle
) {
    if(isOpenDialog.value) {
        AlertDialog(
            text = {Text(text = stringResource(R.string.are_you_sure))},
            title = {Text(text = stringResource(R.string.deletion))}   ,
            onDismissRequest = { isOpenDialog.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        onClick(objectVehicle)
                        isOpenDialog.value = false
                    }
                ) {
                    Text(text = stringResource(R.string.yes))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { isOpenDialog.value = false }
                ) {
                    Text(text = stringResource(R.string.no))
                }
            }
        )
    }
}

@Composable
fun TableMakeRn(
    onClick: (ObjectVehicle) -> Unit = {},
    objectVehicle: ObjectVehicle = ObjectVehicle(),
    isOpenDialog: MutableState<Boolean> = mutableStateOf(false)
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = objectVehicle.vehicleOrTrailer.name,
            modifier = Modifier.weight(1f),
            style = typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = objectVehicle.make,
            modifier = Modifier.weight(1f),
            style = typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = objectVehicle.rn,
            modifier = Modifier.weight(1f),
            style = typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        IconButton(
            onClick = { isOpenDialog.value = true }
        ) {
            Icon(
                painter = painterResource(R.drawable.close_24px),
                contentDescription = stringResource(R.string.delete),
            )
        }
    }

    AlertDialogDeleteElement(
        isOpenDialog = isOpenDialog,
        onClick = onClick,
        objectVehicle = objectVehicle
    )
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

@Preview(showBackground = true)
@Composable
fun FirstEntryScreenPreview() {
    FirstEntryScreen(onMainMenu = {})
}

@Preview(showBackground = true)
@Composable
fun TableMakeRnPreview() {
    TableMakeRn()
}

