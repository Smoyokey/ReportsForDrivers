package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.ui.DatePickerDialogCustom
import com.example.reportsfordrivers.ui.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportsViewModel
import com.example.reportsfordrivers.viewmodel.createreports.uistate.ProgressDetails
import com.example.reportsfordrivers.viewmodel.createreports.uistate.ProgressReports

@Composable
fun CreateReportsProgressReportsScreen(
    onTripExpenses: () -> Unit,
    viewModel: CreateReportsViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    Column {

        TabRow(selectedTabIndex = 3) {
            viewModel.tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = 3 == index,
                    onClick = { },
                    enabled = false
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 16.dp)
                .weight(1f)
                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.spacedBy(6.dp),
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
                        viewModel.openDialogProgressReportsDate.value = true
                    },
                    modifier = Modifier.testTag(Tags.TAG_TEST_PROGRESS_REPORTS_DATE)
                ) {
                    Text(
                        text = viewModel.uiStateProgressReports.value.progressDetails.date.ifEmpty
                        { stringResource(R.string.current_date) }
                    )
                }
            }

            OutlinedTextFieldCustom(
                label = R.string.country,
                value = viewModel.uiStateProgressReports.value.progressDetails.country,
                onValueChange = viewModel::updateProgressReportsCountry,
                tag = Tags.TAG_TEST_PROGRESS_REPORTS_COUNTRY,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextFieldCustom(
                label = R.string.township,
                value = viewModel.uiStateProgressReports.value.progressDetails.township,
                onValueChange = viewModel::updateProgressReportsTownship,
                tag = Tags.TAG_TEST_PROGRESS_REPORTS_TOWNSHIP,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
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
                OutlinedButton(
                    onClick = {
                        viewModel.updateProgressReports()
                    },
                    enabled = viewModel.validateAddProgressReports()
                ) {
                    Text(
                        text = stringResource(R.string.add)
                    )
                }
            }

            Column() {
                for (i in viewModel.uiState.value.listProgress) {
                    ColumnProgressReports(i)
                }
            }

            DatePickerDialogCustom(
                viewModel.openDialogProgressReportsDate,
                viewModel::updateProgressReportsDate
            )
        }

        Column {
            Divider(modifier = Modifier.padding(start = 16.dp, end = 16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(end = 10.dp, bottom = 10.dp, top = 5.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onTripExpenses
                ) {
                    Text(
                        text = stringResource(R.string.next),
                        style = typography.titleLarge
                    )
                }
            }
        }
    }
}

@Composable
fun ColumnProgressReports(progressReports: ProgressReports) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            RowProgressReports(
                title = R.string.date,
                text = progressReports.progressDetails.date
            )
            RowProgressReports(
                title = R.string.country,
                text = progressReports.progressDetails.country
            )
            RowProgressReports(
                title = R.string.township,
                text = progressReports.progressDetails.township
            )
            RowProgressReports(
                title = R.string.distance,
                text = progressReports.progressDetails.distance
            )
            RowProgressReports(
                title = R.string.cargo_weight,
                text = progressReports.progressDetails.cargoWeight
            )
            Divider(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp)
            )
        }
        IconButton(
            onClick = {}
        ) {
            Icon(
                Icons.Outlined.Clear,
                contentDescription = stringResource(R.string.delete)
            )
        }
    }
}

@Composable
fun RowProgressReports(@StringRes title: Int, text: String) {
    Row(modifier = Modifier.fillMaxWidth(1f)) {
        Text(
            text = stringResource(title),
            modifier = Modifier.weight(1f)
        )
        Text(text = "-")
        Text(
            text = text,
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.End
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ColumnProgressReportsPreview() {
    ColumnProgressReports(ProgressReports(ProgressDetails(
        country = "BY",
        township = "Minsk-Kazan",
        distance = "1500",
        cargoWeight = "15",
        date = "02.11"
    )))
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateReportsProgressReportsScreenPreview() {
    CreateReportsProgressReportsScreen(onTripExpenses = {})
}