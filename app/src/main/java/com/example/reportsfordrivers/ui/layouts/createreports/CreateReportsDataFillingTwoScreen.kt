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
import com.example.reportsfordrivers.viewmodel.createreports.DataFillingTwoViewModel
import com.example.reportsfordrivers.viewmodel.createreports.uistate.DataDetails
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun CreateReportsDataFillingTwoScreen(
    onProgressReport: () -> Unit,
    viewModel: DataFillingTwoViewModel = hiltViewModel()
) {
    Column() {
        //TOP APP BAR

        Text(
            text = stringResource(R.string.data_filling),
            style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight(400)
            ),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Divider(
            modifier = Modifier.padding(10.dp)
        )

        OutlinedTextField(
            value = viewModel.uiState.value.dataDetails.route,
            onValueChange = {
                viewModel.updateParamTextField(
                    viewModel.uiState.value.dataDetails.copy(
                        route = it
                    )
                )
            },
            label = { Text(text = stringResource(R.string.route)) },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (true) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.clear),
                        modifier = Modifier
                            .clickable {
                                viewModel.updateParamTextField(
                                    viewModel.uiState.value.dataDetails.copy(
                                        route = ""
                                    )
                                )
                            }
                            .testTag(Tags.TAG_TEST_DATA_FILLING_TWO_ROUTE)
                    )
                }
            }
        )

        LineDataFilling(R.string.date_departure)
        LineDataFilling(R.string.date_return)
        LineDataFilling(R.string.date_border_crossing_departure)
        LineDataFilling(R.string.date_border_crossing_return)

        OutlinedTextFieldDataFillingTwo(
            viewModel.uiState.value.dataDetails,
            viewModel::updateParamTextField
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {}

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = onProgressReport
            ) {
                Text(
                    text = stringResource(R.string.next)
                )
            }
        }
    }
}

@Composable
fun LineDataFilling(text: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(text),
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
}

@Composable
fun OutlinedTextFieldDataFillingTwo(
    dataDetails: DataDetails,
    onValueChange: (DataDetails) -> Unit
) {
    Column() {
        OutlinedTextField(
            value = dataDetails.speedometerDeparture,
            onValueChange = { onValueChange(dataDetails.copy(speedometerDeparture = it)) },
            label = { Text(text = stringResource(R.string.speedometer_reading_departure)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if (dataDetails.speedometerDeparture.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.clear),
                        modifier = Modifier
                            .clickable {
                                onValueChange(dataDetails.copy(speedometerDeparture = ""))
                            }
                            .testTag(Tags.TAG_TEST_DATA_FILLING_TWO_SPEEDOMETER_DEPARTURE)
                    )
                }
            }
        )
        OutlinedTextField(
            value = dataDetails.speedometerReturn,
            onValueChange = { onValueChange(dataDetails.copy(speedometerReturn = it)) },
            label = { Text(text = stringResource(R.string.speedometer_reading_return)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if (dataDetails.speedometerReturn.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.clear),
                        modifier = Modifier
                            .clickable {
                                onValueChange(dataDetails.copy(speedometerReturn = ""))
                            }
                            .testTag(Tags.TAG_TEST_DATA_FILLING_TWO_SPEEDOMETER_RETURN)
                    )
                }
            }
        )
        OutlinedTextField(
            value = dataDetails.fuelled,
            onValueChange = { onValueChange(dataDetails.copy(fuelled = it)) },
            label = { Text(text = stringResource(R.string.fuelled)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if (dataDetails.fuelled.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.clear),
                        modifier = Modifier
                            .clickable {
                                onValueChange(dataDetails.copy(fuelled = ""))
                            }
                            .testTag(Tags.TAG_TEST_DATA_FILLING_TWO_FUELLED)
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LineDataFillingPreview() {
    LineDataFilling(R.string.date_departure)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateReportsDataFillingTwoScreenPreview() {
    CreateReportsDataFillingTwoScreen(onProgressReport = {})
}
