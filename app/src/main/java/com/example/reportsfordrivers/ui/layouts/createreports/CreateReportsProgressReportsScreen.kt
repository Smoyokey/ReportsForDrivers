package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import com.example.reportsfordrivers.ui.DatePickerDialogCustom
import com.example.reportsfordrivers.ui.OutlinedTextFieldCustom
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportsViewModel
import com.example.reportsfordrivers.viewmodel.createreports.uistate.ProgressDetails

@Composable
fun CreateReportsProgressReportsScreen(
    onPreview: () -> Unit,
    viewModel: CreateReportsViewModel = hiltViewModel()
) {
    Column {

        TabRow(selectedTabIndex = 3) {
            viewModel.tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = 3 == index,
                    onClick = {  },
                    enabled = false
                )
            }
        }

        Text(
            text = stringResource(R.string.progress_report),
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Divider(
            modifier = Modifier.padding(10.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.date_departure),
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    viewModel.openDialogProgressReportsDate.value = true
                }
            ) {
                Text(
                    text = viewModel.uiStateProgressReports.value.progressDetails.date.ifEmpty
                    { stringResource(R.string.date) }
                )
            }
        }

        Row() {
            OutlinedTextFieldCustom(
                label = R.string.country,
                value = viewModel.uiStateProgressReports.value.progressDetails.country,
                onValueChange = viewModel::updateProgressReportsCountry,
                tag = Tags.TAG_TEST_PROGRESS_REPORTS_COUNTRY,
                modifier = Modifier.weight(1f)
            )
            OutlinedTextFieldCustom(
                label = R.string.township,
                value = viewModel.uiStateProgressReports.value.progressDetails.township,
                onValueChange = viewModel::updateProgressReportsTownship,
                tag = Tags.TAG_TEST_PROGRESS_REPORTS_TOWNSHIP,
                modifier = Modifier.weight(1f)
            )
        }

        Row() {
            OutlinedTextFieldCustom(
                label = R.string.distance,
                value = viewModel.uiStateProgressReports.value.progressDetails.distance,
                onValueChange = viewModel::updateProgressReportsDistance,
                tag = Tags.TAG_TEST_PROGRESS_REPORTS_DISTANCE,
                modifier = Modifier.weight(1f)
            )
            OutlinedTextFieldCustom(
                label = R.string.cargo_weight,
                value = viewModel.uiStateProgressReports.value.progressDetails.cargoWeight,
                onValueChange = viewModel::updateProgressReportsCargoWeight,
                tag = Tags.TAG_TEST_PROGRESS_REPORTS_CARGO_WEIGHT,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {}
            ) {
                Text(
                    text = stringResource(R.string.add)
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            LazyColumn(
                content = {},
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = onPreview
            ) {
                Text(
                    text = stringResource(R.string.next)
                )
            }
        }
        DatePickerDialogCustom(
            viewModel.openDialogProgressReportsDate,
            viewModel::updateProgressReportsDate
        )
    }
}

@Composable
fun LineProgressReports() {
    Row() {
        Text(
            text = "1."
        )
        Column() {
            Text(
                text = "Date - TEST"
            )
            Text(
                text = "Country - TEST"
            )
            Text(
                text = "Township - TEST"
            )
            Text(
                text = "Distance - TEST"
            )
            Text(
                text = "Weight - TEST"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LineProgressReportsPreview() {
    LineProgressReports()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateReportsProgressReportsScreenPreview() {
    CreateReportsProgressReportsScreen(onPreview = {})
}