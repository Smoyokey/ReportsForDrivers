package com.example.reportsfordrivers.ui.layouts.firstentry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.ui.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.RowVehicleAndTrailerElement
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.firstentry.FirstEntryViewModel
import com.example.reportsfordrivers.viewmodel.firstentry.IsSelectedVehicleAndTrailer

@Composable
fun FirstEntryOneScreen(
    viewModel: FirstEntryViewModel = hiltViewModel(),
    onMainMenu: () -> Unit = {},
    onFirstEntryTwo: () -> Unit = {}
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
            RadioButtonFirstEntry(
                text = R.string.vehicle,
                modifier = Modifier.weight(1f),
                selected = viewModel.vehicleUiState.value.isSelected.stateRadioGroup,
                onClick = {
                    viewModel.selectedPosition(
                        IsSelectedVehicleAndTrailer(true)
                    )
                })
            RadioButtonFirstEntry(
                text = R.string.trailer,
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
                RowVehicleAndTrailerElement(
                    objectVehicle = i,
                    isOpenDialog = viewModel.isOpenDialog,
                    onDelete = viewModel::deletePositionVehicle
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

@Preview(showBackground = true)
@Composable
fun FirstEntryScreenPreview() {
    FirstEntryOneScreen(onMainMenu = {})
}
