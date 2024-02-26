package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.annotation.StringRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.ui.BottomBarCustom
import com.example.reportsfordrivers.ui.DatePickerDialogCustom
import com.example.reportsfordrivers.ui.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.OutlinedTextFieldDatePicker
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportsViewModel
import com.example.reportsfordrivers.viewmodel.createreports.uistate.ProgressDetails
import com.example.reportsfordrivers.viewmodel.createreports.uistate.ProgressReports

@Composable
fun CreateReportsProgressReportsScreen(
    onNext: () -> Unit,
    onBack: () -> Unit,
    viewModel: CreateReportsViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val scrollState = rememberScrollState()

    val source = remember { MutableInteractionSource() }
    if(source.collectIsPressedAsState().value) viewModel.openDialogProgressReportsDate.value = true

    Column {

        TabRow(selectedTabIndex = 4) {
            Tab(
                text = { Text("1") },
                selected = false,
                onClick = { navController.navigate(ReportsForDriversSchema.ReportInfo.name) }
            )
            Tab(
                text = { Text("2") },
                selected = false,
                onClick = { navController.navigate(ReportsForDriversSchema.PersonalInfo.name) },
            )
            Tab(
                text = { Text("3") },
                selected = false,
                onClick = { navController.navigate(ReportsForDriversSchema.VehicleInfo.name) }
            )
            Tab(
                text = { Text("4") },
                selected = false,
                onClick = { navController.navigate(ReportsForDriversSchema.ProgressReport.name) }
            )
            Tab(
                text = { Text("5") },
                selected = false,
                onClick = { },
                enabled = false
            )
            Tab(
                text = { Text("6") },
                selected = false,
                onClick = { navController.navigate(ReportsForDriversSchema.TripExpenses.name) },
                enabled = viewModel.isValidateNextProgressReports()
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 16.dp)
                .weight(1f)
                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            OutlinedTextFieldDatePicker(
                label = R.string.date,
                value = viewModel.uiStateProgressReports.value.progressDetails.date,
                interactionSource = source,
                onValueChange = viewModel::updateProgressReportsDate,
                tag = Tags.TAG_TEST_PROGRESS_REPORTS_DATE,
                modifier = Modifier.fillMaxWidth()
            )

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

        BottomBarCustom(
            onNext = onNext, onBack = onBack
        )
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
