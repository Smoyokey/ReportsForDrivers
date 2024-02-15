package com.example.reportsfordrivers.ui.layouts.createreports

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.viewmodel.createreports.DataFillingOneViewModel
import com.example.reportsfordrivers.viewmodel.createreports.uistate.FioDataFillingOne

@Composable
fun CreateReportsDataFillingOneScreen(
    onDataFillingTwo: () -> Unit,
    viewModel: DataFillingOneViewModel = hiltViewModel()
) {
    Column() {
        //Верхнее меню

        Text(
            text = stringResource(R.string.data_filling),
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight(400),
            ),
            textAlign = TextAlign.Center
        )

        Divider(
            modifier = Modifier.padding(10.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.date),
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {}
            ) {
                Text(
                    text = stringResource(R.string.date)
                )
            }
        }

        OutlinedTextFieldFio(
            viewModel.uiState.value.fioDataFillingOne,
            onValueChange = viewModel::updateParam
        )

        Divider(
            modifier = Modifier.padding(10.dp)
        )

        OutlinedTextFieldVehicle(
            fioItemDetails = viewModel.uiState.value.fioDataFillingOne,
            onValueChange = viewModel::updateParam
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = onDataFillingTwo
            ) {
                Text(
                    text = stringResource(R.string.next)
                )
            }
        }
    }
}

@Composable
fun OutlinedTextFieldFio(
    fioItemDetails: FioDataFillingOne,
    onValueChange: (FioDataFillingOne) -> Unit = {}
) {
    Column() {
        OutlinedTextField(
            value = fioItemDetails.lastName,
            onValueChange = { onValueChange(fioItemDetails.copy(lastName = it)) },
            label = { Text(stringResource(R.string.last_name)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if (fioItemDetails.lastName.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.clear),
                        modifier = Modifier
                            .clickable {
                                onValueChange(fioItemDetails.copy(lastName = ""))
                            }
                            .testTag(Tags.TAG_TEST_DATA_FILLING_ONE_LAST_NAME)
                    )
                }
            }
        )
        OutlinedTextField(
            value = fioItemDetails.firstName,
            onValueChange = { onValueChange(fioItemDetails.copy(firstName = it)) },
            label = { Text(stringResource(R.string.first_name)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if (fioItemDetails.firstName.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.clear),
                        modifier = Modifier
                            .clickable {
                                onValueChange(fioItemDetails.copy(firstName = ""))
                            }
                            .testTag(Tags.TAG_TEST_DATA_FILLING_ONE_FIRST_NAME)
                    )
                }
            }
        )
        OutlinedTextField(
            value = fioItemDetails.patronymic,
            onValueChange = { onValueChange(fioItemDetails.copy(patronymic = it)) },
            label = { Text(stringResource(R.string.patronymic)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if (fioItemDetails.patronymic.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.clear),
                        modifier = Modifier
                            .clickable {
                                onValueChange(fioItemDetails.copy(patronymic = ""))
                            }
                            .testTag(Tags.TAG_TEST_DATA_FILLING_ONE_PATRONYMIC)
                    )
                }
            }
        )
    }
}

@Composable
fun OutlinedTextFieldVehicle(
    fioItemDetails: FioDataFillingOne,
    onValueChange: (FioDataFillingOne) -> Unit
) {
    Column() {
        OutlinedTextField(
            value = fioItemDetails.makeVehicle,
            onValueChange = { onValueChange(fioItemDetails.copy(makeVehicle = it)) },
            label = { Text(stringResource(R.string.make_vehicle)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = fioItemDetails.rnVehicle,
            onValueChange = { onValueChange(fioItemDetails.copy(rnVehicle = it)) },
            label = { Text(stringResource(R.string.rn_vehicle)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = fioItemDetails.makeTrailer,
            onValueChange = { onValueChange(fioItemDetails.copy(makeTrailer = it))},
            label = { Text(stringResource(R.string.make_trailer)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = fioItemDetails.rnTrailer,
            onValueChange = { onValueChange(fioItemDetails.copy(rnTrailer = it)) },
            label = { Text(stringResource(R.string.rn_trailer)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateReportsDataFillingOneScreenPreview() {
    CreateReportsDataFillingOneScreen(onDataFillingTwo = {})
}