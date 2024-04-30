package com.example.reportsfordrivers.ui.layouts.setting

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.ui.layouts.custom.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.layouts.custom.RowVehicleAndTrailerElement
import com.example.reportsfordrivers.viewmodel.setting.DataVehiclesTrailersViewModel

@Composable
fun SettingDataVehiclesTrailersScreen(
    viewModel: DataVehiclesTrailersViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        Text(
            text = stringResource(R.string.data_vehicles_trailers),
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center
            )
        )

        Divider(
            modifier = Modifier.padding(10.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            RadioButtonSettingDataVehiclesTrailers(text = R.string.vehicle, modifier = Modifier.weight(1f))
            RadioButtonSettingDataVehiclesTrailers(text = R.string.trailer, modifier = Modifier.weight(1f))
        }

        OutlinedTextFieldCustom(
            label = R.string.make_vehicle,
            value = viewModel.objectVehicleUiState.value.make,
            onValueChange = viewModel::updateMake,
            tag = Tags.TAG_TEST_SETTING_DATA_VEHICLE_MAKE,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextFieldCustom(
            label = R.string.rn_trailer,
            value = viewModel.objectVehicleUiState.value.make,
            onValueChange = viewModel::updateRn,
            tag = Tags.TAG_TEST_SETTING_DATA_VEHICLE_RN,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                          Log.i("TAGs", viewModel.uiState.value.listVehicles.size.toString())
                },
            ) {
                Text(
                    text = stringResource(R.string.add)
                )
            }
        }

        Divider(
            modifier = Modifier.padding(10.dp)
        )

        for(i in viewModel.uiState.value.listVehicles) {
            RowVehicleAndTrailerElement(
                objectVehicle = i,
                isOpenDialog = viewModel.isOpenDialogSaveElement,
                onDelete = viewModel::deletePositionVehicle
            )
        }
    }
}

@Composable
fun RadioButtonSettingDataVehiclesTrailers(text: Int, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(top = 0.dp, bottom = 0.dp, end = 10.dp, start = 10.dp)
    ) {
        RadioButton(
            selected = false,
            onClick = {}
        )
        Text(
            text = stringResource(text),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Justify
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RadioButtonSettingDataVehiclesTrailersPreview() {
    RadioButtonSettingDataVehiclesTrailers(R.string.vehicle)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingDataVehiclesTrailersScreenPreview() {
    SettingDataVehiclesTrailersScreen()
}