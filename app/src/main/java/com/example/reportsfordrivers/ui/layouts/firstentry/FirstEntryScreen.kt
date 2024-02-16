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
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.ui.OutlinedTextFieldCustom
import com.example.reportsfordrivers.viewmodel.firstentry.FirstEntryViewModel
import com.example.reportsfordrivers.viewmodel.firstentry.IsSelectedVehicleAndTrailer
import com.example.reportsfordrivers.viewmodel.firstentry.ObjectVehicle
import com.example.reportsfordrivers.viewmodel.firstentry.VehicleOrTrailer


@Composable
fun FirstEntryScreen(
    viewModel: FirstEntryViewModel = hiltViewModel(),
    onMainMenu: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(state = scrollState)
            .testTag(Tags.TAG_COLUMN_FIRST_ENTRY)
    ) {
        Text(
            text = stringResource(R.string.enter_information_yourself),
            fontSize = 20.sp,
            modifier = Modifier.weight(1f)
        )

        OutlinedTextFieldCustom(
            label = R.string.last_name,
            value = viewModel.uiState.value.fioItemDetails.lastName,
            onValueChange = viewModel::updateLastName,
            tag = Tags.TAG_TEST_FIRST_ENTRY_LAST_NAME,
            modifier = Modifier
        )

        OutlinedTextFieldCustom(
            label = R.string.first_name,
            value = viewModel.uiState.value.fioItemDetails.firstName,
            onValueChange = viewModel::updateFirstName,
            tag = Tags.TAG_TEST_FIRST_ENTRY_FIRST_NAME,
            modifier = Modifier
        )

        OutlinedTextFieldCustom(
            label = R.string.patronymic,
            value = viewModel.uiState.value.fioItemDetails.patronymic,
            onValueChange = viewModel::updatePatronymic,
            tag = Tags.TAG_TEST_FIRST_ENTRY_PATRONYMIC,
            modifier = Modifier
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

        OutlinedTextFieldCustom(
            label = if (viewModel.vehicleUiState.value.isSelected.stateRadioGroup) R.string.make_vehicle
            else {R.string.make_trailer},
            value = viewModel.vehicleUiState.value.makeRnItemDetails.make,
            onValueChange = viewModel::updateMake,
            tag = Tags.TAG_TEST_FIRST_ENTRY_MAKE,
            modifier = Modifier
        )

        OutlinedTextFieldCustom(
            label = if (viewModel.vehicleUiState.value.isSelected.stateRadioGroup) R.string.rn_vehicle
            else R.string.rn_trailer,
            value = viewModel.vehicleUiState.value.makeRnItemDetails.make,
            onValueChange = viewModel::updateRn,
            tag = Tags.TAG_TEST_FIRST_ENTRY_RN,
            modifier = Modifier
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

