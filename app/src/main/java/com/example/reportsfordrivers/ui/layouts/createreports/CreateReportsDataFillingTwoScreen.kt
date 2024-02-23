package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.reportsfordrivers.ui.DatePickerDialogCustom
import com.example.reportsfordrivers.ui.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportsViewModel
import com.example.reportsfordrivers.viewmodel.createreports.uistate.DataFillingTwo
import kotlinx.coroutines.launch

@Composable
fun CreateReportsDataFillingTwoScreen(
    onProgressReport: () -> Unit,
    viewModel: CreateReportsViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    Column() {
        TabRow(selectedTabIndex = 2) {
            viewModel.tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = 2 == index,
                    onClick = { },
                    enabled = false
                )
            }
        }

        Column(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 16.dp)
                .verticalScroll(state = scrollState)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            OutlinedTextFieldCustom(
                label = R.string.route,
                value = viewModel.uiState.value.dataFillingTwo.route,
                onValueChange = viewModel::updateDataFillingTwoRoute,
                tag = Tags.TAG_TEST_DATA_FILLING_TWO_ROUTE,
                modifier = Modifier.fillMaxWidth()
            )

            LineDataFilling(
                text = R.string.date_departure,
                openDialog = viewModel.openDialogDataFillingTwoDateDeparture,
                dateDetails = viewModel.uiState.value.dataFillingTwo.dateDeparture,
                tag = Tags.TAG_TEST_DATE_DEPARTURE
            )
            LineDataFilling(
                text = R.string.date_return,
                openDialog = viewModel.openDialogDataFillingTwoDateReturn,
                dateDetails = viewModel.uiState.value.dataFillingTwo.dateReturn,
                tag = Tags.TAG_TEST_DATE_RETURN
            )
            LineDataFilling(
                text = R.string.date_border_crossing_departure,
                openDialog = viewModel.openDialogDataFillingTwoDateCrossingDeparture,
                dateDetails = viewModel.uiState.value.dataFillingTwo.dateCrossingDeparture,
                tag = Tags.TAG_TEST_DATE_CROSSING_DEPARTURE
            )
            LineDataFilling(
                text = R.string.date_border_crossing_return,
                openDialog = viewModel.openDialogDataFillingTwoDateCrossingReturn,
                dateDetails = viewModel.uiState.value.dataFillingTwo.dateCrossingReturn,
                tag = Tags.TAG_TEST_DATE_CROSSING_RETURN
            )

            OutlinedTextFieldCustom(
                label = R.string.speedometer_reading_departure,
                value = viewModel.uiState.value.dataFillingTwo.speedometerDeparture,
                onValueChange = viewModel::updateDataFillingTwoSpeedometerDeparture,
                tag = Tags.TAG_TEST_DATA_FILLING_TWO_SPEEDOMETER_DEPARTURE,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextFieldCustom(
                label = R.string.speedometer_reading_return,
                value = viewModel.uiState.value.dataFillingTwo.speedometerReturn,
                onValueChange = viewModel::updateDataFillingTwoSpeedometerReturn,
                tag = Tags.TAG_TEST_DATA_FILLING_TWO_SPEEDOMETER_RETURN,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextFieldCustom(
                label = R.string.fuelled,
                value = viewModel.uiState.value.dataFillingTwo.fuelled,
                onValueChange = viewModel::updateDataFillingTwoFuelled,
                tag = Tags.TAG_TEST_DATA_FILLING_TWO_FUELLED,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column() {
            Divider(modifier = Modifier.padding(start = 16.dp, end = 16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(end = 10.dp, bottom = 10.dp, top = 5.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onProgressReport,
                ) {
                    Text(
                        text = stringResource(R.string.next),
                        style = typography.titleLarge
                    )
                }
            }
        }
    }
    DatePickerDialogCustom(
        viewModel.openDialogDataFillingTwoDateDeparture,
        viewModel::updateDataFillingTwoDateDeparture
    )
    DatePickerDialogCustom(
        viewModel.openDialogDataFillingTwoDateReturn,
        viewModel::updateDataFillingTwoDateReturn
    )
    DatePickerDialogCustom(
        viewModel.openDialogDataFillingTwoDateCrossingDeparture,
        viewModel::updateDataFillingTwoDateCrossingDeparture
    )
    DatePickerDialogCustom(
        viewModel.openDialogDataFillingTwoDateCrossingReturn,
        viewModel::updateDataFillingTwoDateCrossingReturn
    )
}

@Composable
fun LineDataFilling(
    text: Int,
    openDialog: MutableState<Boolean>,
    dateDetails: String,
    tag: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(text),
            modifier = Modifier.weight(1f),
            style = typography.titleLarge
        )
        TextButton(
            onClick = {
                openDialog.value = true
            },
            modifier = Modifier.testTag(tag)
        ) {
            Text(
                text = dateDetails.ifEmpty { stringResource(R.string.current_date) },
                style = typography.titleLarge
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateReportsDataFillingTwoScreenPreview() {
    CreateReportsDataFillingTwoScreen(onProgressReport = {})
}
