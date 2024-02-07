package com.example.reportsfordrivers.ui.layouts.firstentry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.viewmodel.AppViewModelProvider
import com.example.reportsfordrivers.viewmodel.firstentry.FirstEntryViewModel

@Composable
fun FirstEntryScreen(
    viewModel: FirstEntryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onMainMenu: () -> Unit
) {
    Column(
        modifier = Modifier.padding(10.dp)
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

        OutlinedTextFieldDef(R.string.last_name, viewModel)
        OutlinedTextFieldDef(R.string.first_name, viewModel)
        OutlinedTextFieldDef(R.string.patronymic, viewModel)

        Divider(
            modifier = Modifier.padding(10.dp)
        )

        Text(
            text = stringResource(R.string.add_makes_registration_vehicles_trailers),
            fontSize = 20.sp
        )

        Row(
        ) {
            RadioButtonDef(R.string.vehicle, modifier = Modifier.weight(1f))
            RadioButtonDef(R.string.trailer, modifier = Modifier.weight(1f))
        }

        OutlinedTextFieldDef(R.string.make_vehicle_trailer, viewModel)
        OutlinedTextFieldDef(R.string.rn_vehicle_trailer, viewModel)

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {},
                enabled = false
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
fun OutlinedTextFieldDef(label: Int, viewModel: FirstEntryViewModel) {
    OutlinedTextField(
        value = "",
        onValueChange = { viewModel.setLastName(it) },
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
    FirstEntryScreen(onMainMenu = {})
}

@Preview(showBackground = true)
@Composable
fun TableMakeRnPreview() {
    TableMakeRn("Vehicle", "DAF", "1234IT 5")
}

