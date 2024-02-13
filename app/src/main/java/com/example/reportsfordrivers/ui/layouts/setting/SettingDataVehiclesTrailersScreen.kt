package com.example.reportsfordrivers.ui.layouts.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags

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

        Divider(
            modifier = Modifier.padding(10.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            RadioButtonSettingDataVehiclesTrailers(text = R.string.vehicle, modifier = Modifier.weight(1f))
            RadioButtonSettingDataVehiclesTrailers(text = R.string.trailer, modifier = Modifier.weight(1f))
        }

        OutlinedTextFieldSettingDataVehiclesTrailers()

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

        Divider(
            modifier = Modifier.padding(10.dp)
        )

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
fun OutlinedTextFieldSettingDataVehiclesTrailers() {
    Column() {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(text = stringResource(R.string.make_vehicle)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if(true) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.clear),
                        modifier = Modifier
                            .clickable {
//                                onValueChane(itemDetails.copy(tt = ""))
                            }
                            .testTag(Tags.TAG_TEST_SETTING_DATA_VEHICLE_MAKE)
                    )
                }
            }
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(text = stringResource(R.string.rn_vehicle)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if(true) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.clear),
                        modifier = Modifier
                            .clickable {
//                                onValueChane(itemDetails.copy(tt = ""))
                            }
                            .testTag(Tags.TAG_TEST_SETTING_DATA_VEHICLE_RN)
                    )
                }
            }
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