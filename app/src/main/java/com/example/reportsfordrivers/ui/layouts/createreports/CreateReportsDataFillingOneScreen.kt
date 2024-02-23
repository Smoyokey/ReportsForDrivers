package com.example.reportsfordrivers.ui.layouts.createreports

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.reportsfordrivers.ui.DatePickerDialogCustom
import com.example.reportsfordrivers.ui.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateReportsDataFillingOneScreen(
    onDataFillingTwo: () -> Unit,
    viewModel: CreateReportsViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    Column() {
        TabRow(selectedTabIndex = 1) {
            viewModel.tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = 1 == index,
                    onClick = {  },
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.test),
                    modifier = Modifier.weight(1f)
                )
                TextButton(
                    onClick = {
                        viewModel.openDialogDateFillingOne.value = true
                    }
                ) {
                    Text(
                        text = viewModel.uiState.value.dataFillingOne.date.ifEmpty {
                            stringResource(R.string.current_date)
                        }
                    )
                }
            }

            OutlinedTextFieldCustom(
                label = R.string.last_name,
                value = viewModel.uiState.value.dataFillingOne.lastName,
                onValueChange = viewModel::updateDataFillingOneLastName,
                tag = Tags.TAG_TEST_DATA_FILLING_ONE_LAST_NAME,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextFieldCustom(
                label = R.string.first_name,
                value = viewModel.uiState.value.dataFillingOne.firstName,
                onValueChange = viewModel::updateDataFillingOneFirstName,
                tag = Tags.TAG_TEST_DATA_FILLING_ONE_FIRST_NAME,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextFieldCustom(
                label = R.string.patronymic,
                value = viewModel.uiState.value.dataFillingOne.patronymic,
                onValueChange = viewModel::updateDataFillingOnePatronymic,
                tag = Tags.TAG_TEST_DATA_FILLING_ONE_PATRONYMIC,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextFieldCustom(
                label = R.string.waybill,
                value = viewModel.uiState.value.dataFillingOne.waybill,
                onValueChange = viewModel::updateDataFillingOneWaybill,
                tag = Tags.TAG_TEST_DATA_FILLING_ONE_WAYBILL,
                modifier = Modifier.fillMaxWidth()
            )

            Divider(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 6.dp)
            )

            OutlinedTextFieldCustom(
                label = R.string.make_vehicle,
                value = viewModel.uiState.value.dataFillingOne.makeVehicle,
                onValueChange = viewModel::updateDataFillingOneMakeVehicle,
                tag = Tags.TAG_TEST_DATA_FILLING_ONE_MAKE_VEHICLE,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextFieldCustom(
                label = R.string.rn_vehicle,
                value = viewModel.uiState.value.dataFillingOne.rnVehicle,
                onValueChange = viewModel::updateDataFillingOneRnVehicle,
                tag = Tags.TAG_TEST_DATA_FILLING_ONE_RN_VEHICLE,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextFieldCustom(
                label = R.string.make_trailer,
                value = viewModel.uiState.value.dataFillingOne.makeTrailer,
                onValueChange = viewModel::updateDataFillingOneMakeTrailer,
                tag = Tags.TAG_TEST_DATA_FILLING_ONE_MAKE_TRAILER,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextFieldCustom(
                label = R.string.rn_trailer,
                value = viewModel.uiState.value.dataFillingOne.rnTrailer,
                onValueChange = viewModel::updateDataFillingOneRnTrailer,
                tag = Tags.TAG_TEST_DATA_FILLING_ONE_RN_TRAILER,
                modifier = Modifier.fillMaxWidth()
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
            }
        }
        Column {
            Divider(modifier = Modifier.padding(start = 16.dp, end = 16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(end = 10.dp, bottom = 10.dp, top = 5.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onDataFillingTwo,
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
        openDialog = viewModel.openDialogDateFillingOne,
        onValueChange = viewModel::updateDataFillingOneDate
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateReportsDataFillingOneScreenPreview() {
    CreateReportsDataFillingOneScreen(onDataFillingTwo = {})
}