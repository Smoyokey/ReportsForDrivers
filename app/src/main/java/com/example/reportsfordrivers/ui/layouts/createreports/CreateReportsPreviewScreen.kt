package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.reportsfordrivers.ui.BottomBarCustom
import com.example.reportsfordrivers.ui.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportsViewModel
import com.example.reportsfordrivers.viewmodel.createreports.uistate.ProgressDetails
import com.example.reportsfordrivers.viewmodel.createreports.uistate.ProgressReports
import com.example.reportsfordrivers.viewmodel.createreports.uistate.TripExpensesDetails

@Composable
fun CreateReportsPreviewScreen(
    onNext: () -> Unit,
    onBack: () -> Unit,
    viewModel: CreateReportsViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
    ) {

        TabRow(selectedTabIndex = 5) {
            viewModel.tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = 5 == index,
                    onClick = {  },
                    enabled = false
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp)
                .verticalScroll(state = scrollState)
                .weight(1f)
        ) {
            OutlinedTextFieldCustom(
                label = R.string.reports_name,
                value = viewModel.uiState.value.reportName,
                onValueChange = viewModel::updatePreviewReportName,
                tag = Tags.TAG_TEST_PREVIEW_REPORT_NAME,
                modifier = Modifier.fillMaxWidth()
            )

            Divider(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp))

            LinePreviewText(R.string.date, viewModel.uiState.value.dataReportInfo.date)
            LinePreviewText(R.string.last_name, viewModel.uiState.value.dataPersonalInfo.lastName)
            LinePreviewText(R.string.first_name, viewModel.uiState.value.dataPersonalInfo.firstName)
            LinePreviewText(R.string.patronymic, viewModel.uiState.value.dataPersonalInfo.patronymic)
            LinePreviewText(R.string.waybill, viewModel.uiState.value.dataReportInfo.waybill)
            LinePreviewText(R.string.make_vehicle, viewModel.uiState.value.dataVehicleInfo.makeVehicle)
            LinePreviewText(R.string.registration_number_vehicle,
                viewModel.uiState.value.dataVehicleInfo.rnVehicle)
            LinePreviewText(R.string.make_trailer, viewModel.uiState.value.dataVehicleInfo.makeTrailer)
            LinePreviewText(R.string.registration_number_trailer,
                viewModel.uiState.value.dataVehicleInfo.rnTrailer)

            Divider(
                modifier = Modifier.padding(10.dp)
            )

            LinePreviewText(R.string.route, viewModel.uiState.value.dataFillingTwo.route)
            LinePreviewText(R.string.date_departure, viewModel.uiState.value.dataFillingTwo.dateDeparture)
            LinePreviewText(R.string.date_return, viewModel.uiState.value.dataFillingTwo.dateReturn)
            LinePreviewText(R.string.date_border_crossing_departure,
                viewModel.uiState.value.dataFillingTwo.dateCrossingDeparture)
            LinePreviewText(R.string.date_border_crossing_return,
                viewModel.uiState.value.dataFillingTwo.dateCrossingReturn)
            LinePreviewText(R.string.speedometer_reading_departure, viewModel.uiState.value.dataFillingTwo.speedometerDeparture)
            LinePreviewText(R.string.speedometer_reading_return, viewModel.uiState.value.dataFillingTwo.speedometerReturn)
            LinePreviewText(R.string.fuelled, viewModel.uiState.value.dataFillingTwo.fuelled)

            Divider(
                modifier = Modifier.padding(10.dp)
            )

            Text(
                text = stringResource(R.string.progress_report),
                modifier = Modifier.fillMaxWidth(),
                style = typography.titleLarge,
                textAlign = TextAlign.Center
            )
            
            for(i in 0..<viewModel.uiState.value.listProgress.size) {
                ProgressReportItem(
                    item = viewModel.uiState.value.listProgress[i].progressDetails,
                    size = viewModel.uiState.value.listProgress.size,
                    current = i
                )
            }

            Divider(
                modifier = Modifier.padding(10.dp)
            )

            if(viewModel.uiState.value.listTripExpenses.size > 0) {
                Text(
                    text = stringResource(id = R.string.business_trip_expenses),
                    modifier = Modifier.fillMaxWidth(),
                    style = typography.titleLarge,
                    textAlign = TextAlign.Center
                )

                for(i in 0..<viewModel.uiState.value.listTripExpenses.size) {
                    TripExpensesItem(
                        item = viewModel.uiState.value.listTripExpenses[i].tripExpensesDetails,
                        size = viewModel.uiState.value.listTripExpenses.size,
                        current = i
                    )
                }
            }


        }

        BottomBarCustom(
            onNext = onNext, onBack = onBack
        )
    }
}

@Composable
fun LinePreviewText(@StringRes textName: Int, previewText: String) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(textName),
            modifier = Modifier.weight(1f),
            style = typography.bodyMedium
        )
        Text(
            text = "-",
            style = typography.bodyMedium
        )
        Text(
            text = previewText,
            modifier = Modifier.weight(1f),
            style = typography.bodyMedium,
            textAlign = TextAlign.Right
        )
    }
}




@Composable
fun ProgressReportItem(item: ProgressDetails, size: Int, current: Int) {
    Column {
        RowItem(title = R.string.date, text = item.date)
        RowItem(title = R.string.country, text = item.country)
        RowItem(title = R.string.township, text = item.township)
        RowItem(title = R.string.distance, text = item.distance)
        RowItem(title = R.string.cargo_weight, text = item.cargoWeight)
        if(size - 1 != current) Divider()
    }
}

@Composable
fun TripExpensesItem(item: TripExpensesDetails, size: Int, current: Int) {
    Column {
        RowItem(title = R.string.date, text = item.date)
        RowItem(title = R.string.document_number, text = item.documentNumber)
        RowItem(title = R.string.expense_item, text = item.expenseItem)
        RowItem(title = R.string.sum, text = item.sum)
        RowItem(title = R.string.currency, text = item.currency)
        if(size - 1 != current) Divider()
    }
}

@Composable
fun RowItem(@StringRes title: Int, text: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(title),
            modifier = Modifier.weight(1f),
            style = typography.bodyMedium
        )
        Text(
            text = "-",
            style = typography.bodyMedium
        )
        Text(
            text = text,
            modifier = Modifier.weight(2f),
            style = typography.bodyMedium,
            textAlign = TextAlign.End
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressReportItemPreview() {
    ProgressReportItem(
        item = ProgressDetails("BY", "Minsk-Kazan", "1500", "15", "10.02"),
        size = 1,
        current = 1
    )
}