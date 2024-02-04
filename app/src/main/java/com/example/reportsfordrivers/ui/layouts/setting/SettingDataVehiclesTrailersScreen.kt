package com.example.reportsfordrivers.ui.layouts.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import com.example.reportsfordrivers.R

@Composable
fun SettingDataVehiclesTrailersScreen() {
    Column() {
        Text(
            text = stringResource(R.string.data_vehicles_trailers),
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center
            )
        )

        /**
         * Бахаем разделитель тута
         */

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            RadioButtonSettingDataVehiclesTrailers(text = R.string.vehicle, modifier = Modifier.weight(1f))
            RadioButtonSettingDataVehiclesTrailers(text = R.string.trailer, modifier = Modifier.weight(1f))
        }
        OutlinedTextFieldSettingDataVehiclesTrailers(R.string.make_vehicle_trailer)
        OutlinedTextFieldSettingDataVehiclesTrailers(R.string.registration_number_vehicle_trailer)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {},
            ) {
                Text(
                    text = stringResource(R.string.add)
                )
            }
        }
        /**
         * Разделитель тута
         */

        //Дальше идет таблица
        //Надо подумать как красиво реализовать
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

@Composable
fun OutlinedTextFieldSettingDataVehiclesTrailers(label: Int) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        label = {
            Text(
                text = stringResource(label)
            )
        },
        trailingIcon = {
            Icon(Icons.Outlined.Clear, stringResource(R.string.clear))
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun OutlinedTextFieldSettingDataVehiclesTrailersPreview() {
    OutlinedTextFieldSettingDataVehiclesTrailers(R.string.make_vehicle_trailer)
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