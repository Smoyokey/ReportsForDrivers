package com.example.reportsfordrivers.ui.layouts.firstentry

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reportsfordrivers.R

@Composable
fun FirstEntryScreen() {
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        Row {
            Text(
                text = stringResource(R.string.enter_information_yourself),
                fontSize = 20.sp
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

        OutlinedTextFieldDef(R.string.last_name)
        OutlinedTextFieldDef(R.string.first_name)
        OutlinedTextFieldDef(R.string.patronymic)

        /**
         * Тут добавить разделитель
         */

        Text(
            text = stringResource(R.string.add_makes_registration_vehicles_trailers),
            fontSize = 20.sp
        )

        Row(
        ) {
            RadioButtonDef(R.string.vehicle, modifier = Modifier.weight(1f))
            RadioButtonDef(R.string.trailer, modifier = Modifier.weight(1f))
        }

        OutlinedTextFieldDef(R.string.make_vehicle_trailer)
        OutlinedTextFieldDef(R.string.rn_vehicle_trailer)

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {},
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
        TableMakeRn(type = "vehicle", make = "DAF", rn = "1234IT 5")
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
fun RadioButtonDef(text: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = false,
            onClick = {}
        )
        Text(
            text = stringResource(text)
        )
    }
}

@Composable
fun OutlinedTextFieldDef(label: Int) {
    OutlinedTextField(
        value = "",
        onValueChange = { },
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
fun FirstEntryScreenPreview() {
    FirstEntryScreen()
}

@Preview(showBackground = true)
@Composable
fun TableMakeRnPreview() {
    TableMakeRn("Vehicle", "DAF", "1234IT 5")
}

