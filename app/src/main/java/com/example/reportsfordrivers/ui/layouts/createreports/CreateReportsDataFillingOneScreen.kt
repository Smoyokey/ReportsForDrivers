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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportsViewModel
import com.example.reportsfordrivers.viewmodel.createreports.uistate.DataFillingOne
import kotlinx.coroutines.launch

@Composable
fun CreateReportsDataFillingOneScreen(
    onDataFillingTwo: () -> Unit,
    viewModel: CreateReportsViewModel = hiltViewModel()
) {
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
                onClick = {
                    viewModel.openDialogDateFillingOne.value = true
                }
            ) {
                Text(
                    text = viewModel.uiState.value.dataFillingOne.date.ifEmpty {
                        stringResource(R.string.date)
                    }
                )
            }
        }

        OutlinedTextFieldCustom(
            label = R.string.last_name,
            value = viewModel.uiState.value.dataFillingOne.lastName,
            onValueChange = viewModel::updateDataFillingOneLastName,
            tag = Tags.TAG_TEST_DATA_FILLING_ONE_LAST_NAME,
            modifier = Modifier
        )

        OutlinedTextFieldCustom(
            label = R.string.first_name,
            value = viewModel.uiState.value.dataFillingOne.firstName,
            onValueChange = viewModel::updateDataFillingOneFirstName,
            tag = Tags.TAG_TEST_DATA_FILLING_ONE_FIRST_NAME,
            modifier = Modifier
        )

        OutlinedTextFieldCustom(
            label = R.string.patronymic,
            value = viewModel.uiState.value.dataFillingOne.patronymic,
            onValueChange = viewModel::updateDataFillingOnePatronymic,
            tag = Tags.TAG_TEST_DATA_FILLING_ONE_PATRONYMIC,
            modifier = Modifier
        )

        Divider(
            modifier = Modifier.padding(10.dp)
        )

        OutlinedTextFieldCustom(
            label = R.string.make_vehicle,
            value = viewModel.uiState.value.dataFillingOne.makeVehicle,
            onValueChange = viewModel::updateDataFillingOneMakeVehicle,
            tag = Tags.TAG_TEST_DATA_FILLING_ONE_MAKE_VEHICLE,
            modifier = Modifier
        )

        OutlinedTextFieldCustom(
            label = R.string.rn_vehicle,
            value = viewModel.uiState.value.dataFillingOne.rnVehicle,
            onValueChange = viewModel::updateDataFillingOneRnVehicle,
            tag = Tags.TAG_TEST_DATA_FILLING_ONE_RN_VEHICLE,
            modifier = Modifier
        )

        OutlinedTextFieldCustom(
            label = R.string.make_trailer,
            value = viewModel.uiState.value.dataFillingOne.makeTrailer,
            onValueChange = viewModel::updateDataFillingOneMakeTrailer,
            tag = Tags.TAG_TEST_DATA_FILLING_ONE_MAKE_TRAILER,
            modifier = Modifier
        )

        OutlinedTextFieldCustom(
            label = R.string.rn_trailer,
            value = viewModel.uiState.value.dataFillingOne.rnTrailer,
            onValueChange = viewModel::updateDataFillingOneRnTrailer,
            tag = Tags.TAG_TEST_DATA_FILLING_ONE_RN_TRAILER,
            modifier = Modifier
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
    DatePickerDialogCustom(
        openDialog = viewModel.openDialogDateFillingOne,
        onValueChange = viewModel::updateDataFillingOneDate
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateReportsDataFillingOneScreenPreview() {
    CreateReportsDataFillingOneScreen(onDataFillingTwo = {})
}