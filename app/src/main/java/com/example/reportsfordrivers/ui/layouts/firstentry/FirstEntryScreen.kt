package com.example.reportsfordrivers.ui.layouts.firstentry

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.viewmodel.AppViewModelProvider
import com.example.reportsfordrivers.viewmodel.firstentry.FioItemDetails
import com.example.reportsfordrivers.viewmodel.firstentry.FirstEntryViewModel
import com.example.reportsfordrivers.viewmodel.firstentry.IsSelectedVehicleAndTrailer
import com.example.reportsfordrivers.viewmodel.firstentry.MakeRnItemDetails
import com.example.reportsfordrivers.viewmodel.firstentry.UiState

@Composable
fun FirstEntryScreen(
    viewModel: FirstEntryViewModel = hiltViewModel(),
    onMainMenu: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(10.dp)
            .verticalScroll(state = scrollState)
    ) {
        Row {
            Text(
                text = stringResource(R.string.enter_information_yourself),
                fontSize = 20.sp,

                )
            OutlinedIconButton(
                onClick = {}
            ) {
                Icon(
                    Icons.Outlined.Info,
                    contentDescription = stringResource(R.string.info)
                )
            }
        }

        ItemInputFormFio(
            itemDetails = viewModel.uiState.value.fioItemDetails,
            onValueChange = viewModel::updateFio
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
                onClick = {viewModel.addElementVehicle()},
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

        for(i in 0..<viewModel.uiState.value.listVehicles.size) {
            val a = viewModel.uiState.value.listVehicles[i]
            TableMakeRn(type = a.vehicleOrTrailer.name, make = a.make, rn = a.rn)
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
fun TableMakeRn(type: String, make: String, rn: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = type,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = make,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = rn,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        IconButton(
            onClick = {}
        ) {
            Icon(
                painter = painterResource(R.drawable.close_24px),
                contentDescription = stringResource(R.string.delete)
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
            onClick = onClick
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
    isSelected: IsSelectedVehicleAndTrailer
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
            singleLine = true
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
            singleLine = true
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ItemInputFormFio( //Вроде бы работает
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
            singleLine = true
        )
        OutlinedTextField(
            value = itemDetails.firstName,
            onValueChange = { onValueChange(itemDetails.copy(firstName = it)) },
            label = { Text(stringResource(R.string.first_name)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = itemDetails.patronymic,
            onValueChange = { onValueChange(itemDetails.copy(patronymic = it)) },
            label = { Text(stringResource(R.string.patronymic)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
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
    TableMakeRn("Vehicle", "DAF", "1234IT 5")
}

