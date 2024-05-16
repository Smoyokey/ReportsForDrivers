package com.example.reportsfordrivers.ui.layouts.firstentry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.ui.layouts.custom.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.layouts.custom.RowVehicleAndTrailerElement
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.firstentry.FirstEntryViewModel
import com.example.reportsfordrivers.viewmodel.firstentry.IsSelectedVehicleAndTrailer

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FirstEntryOneScreen(
    viewModel: FirstEntryViewModel = hiltViewModel(),
    onFirstEntryTwo: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val modifierDivider = Modifier.padding(start = 16.dp, end = 16.dp)

    val (lastName, firstName, patronymic, make, rn) = remember { FocusRequester.createRefs() }
    val keyboardController = LocalSoftwareKeyboardController.current

    DisposableEffect(Unit) {
        lastName.requestFocus()
        onDispose { }
    }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .testTag(Tags.TAG_COLUMN_FIRST_ENTRY),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(state = scrollState)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {


            Text(
                text = stringResource(R.string.driver),
                style = typography.titleLarge,
            )

            OutlinedTextField(
                value = viewModel.uiState.value.fioItemDetails.lastName,
                label = { Text(stringResource(R.string.last_name)) },
                onValueChange = { viewModel.updateLastName(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(lastName),
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if (viewModel.uiState.value.fioItemDetails.lastName.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.updateLastName("") }
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
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(onNext = { firstName.requestFocus() })
            )

            OutlinedTextField(
                value = viewModel.uiState.value.fioItemDetails.firstName,
                label = { Text(stringResource(R.string.first_name)) },
                onValueChange = { viewModel.updateFirstName(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(firstName),
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if (viewModel.uiState.value.fioItemDetails.firstName.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.updateFirstName("") }
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
                keyboardActions = KeyboardActions(onNext = { patronymic.requestFocus() })
            )

            OutlinedTextField(
                value = viewModel.uiState.value.fioItemDetails.patronymic,
                label = { Text(stringResource(R.string.patronymic)) },
                onValueChange = { viewModel.updatePatronymic(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(patronymic),
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if (viewModel.uiState.value.fioItemDetails.patronymic.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.updatePatronymic("") }
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
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
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

            OutlinedTextField(
                value = viewModel.vehicleUiState.value.makeRnItemDetails.make,
                label = {
                    Text(
                        stringResource(
                            if (viewModel.vehicleUiState.value.isSelected.stateRadioGroup)
                                R.string.make_vehicle
                            else
                                R.string.make_trailer
                        )
                    )
                },
                onValueChange = { viewModel.updateMake(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(make),
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if (viewModel.vehicleUiState.value.makeRnItemDetails.make.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.updateMake("") }
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
                keyboardActions = KeyboardActions(onNext = { rn.requestFocus() })
            )

            OutlinedTextField(
                value = viewModel.vehicleUiState.value.makeRnItemDetails.rn,
                label = {
                    Text(
                        stringResource(
                            if (viewModel.vehicleUiState.value.isSelected.stateRadioGroup)
                                R.string.rn_vehicle
                            else
                                R.string.rn_trailer
                        )
                    )
                },
                onValueChange = { viewModel.updateRn(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(rn),
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if (viewModel.vehicleUiState.value.makeRnItemDetails.rn.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.updateRn("") }
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
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
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
                        text = stringResource(R.string.add),
                        style = typography.titleLarge
                    )
                }
            }

            if (viewModel.uiState.value.listVehicles.size > 0) {
                Divider(
                    modifier = modifierDivider
                )
            }

            Column {
                for (i in viewModel.uiState.value.listVehicles) {
                    RowVehicleAndTrailerElement(
                        objectVehicle = i,
                        isOpenDialog = viewModel.isOpenDialog,
                        onDelete = viewModel::deletePositionVehicle
                    )
                }
            }
        }

        Button(
            onClick = onFirstEntryTwo,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.next),
                style = typography.titleLarge
            )
        }
    }
}
