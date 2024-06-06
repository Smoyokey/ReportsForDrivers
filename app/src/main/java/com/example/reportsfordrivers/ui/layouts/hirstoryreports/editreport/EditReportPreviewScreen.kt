package com.example.reportsfordrivers.ui.layouts.hirstoryreports.editreport

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.ui.layouts.custom.BottomBarCustom
import com.example.reportsfordrivers.ui.layouts.custom.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.layouts.custom.RowProgressAndExpenses
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditExpensesTripDetailingUiState
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditProgressReportsDetailingUiState
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.viewmodel.EditPreviewAndResultViewModel

private const val TAG = "EditReportPreviewScreen"

@Composable
fun EditReportsPreviewScreen(
    onNext: () -> Unit,
    onBack: () -> Unit,
    viewModel: EditPreviewAndResultViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    Column {

        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 16.dp)
                .verticalScroll(state = scrollState)
                .weight(1f)
        ) {
            OutlinedTextFieldCustom(
                label = R.string.reports_name,
                value = viewModel.uiState.value.reportName,
                onValueChange = viewModel::updatePreviewReportName,
                tag = "",
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = true
                )
            )

            Divider(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp))

            LinePreviewText(R.string.date_create_report, viewModel.uiState.value.dataEditReportInfo.date)
            LinePreviewText(R.string.last_name, viewModel.uiState.value.dataEditPersonalInfo.lastName)
            LinePreviewText(R.string.first_name, viewModel.uiState.value.dataEditPersonalInfo.firstName)
            LinePreviewText(R.string.patronymic, viewModel.uiState.value.dataEditPersonalInfo.patronymic)
            LinePreviewText(R.string.waybill, viewModel.uiState.value.dataEditReportInfo.waybill)
            LinePreviewText(R.string.make_vehicle, viewModel.uiState.value.dataEditVehicleInfo.makeVehicle)
            LinePreviewText(R.string.registration_number_vehicle, viewModel.uiState.value.dataEditVehicleInfo.rnVehicle)
            LinePreviewText(R.string.make_trailer, viewModel.uiState.value.dataEditVehicleInfo.makeTrailer)
            LinePreviewText(R.string.registration_number_trailer, viewModel.uiState.value.dataEditVehicleInfo.rnTrailer)

            Divider(modifier = Modifier.padding(10.dp))

            LinePreviewText(R.string.route, viewModel.uiState.value.dataEditRoute.route)
            LinePreviewText(R.string.date_departure, viewModel.uiState.value.dataEditRoute.dateDeparture)
            LinePreviewText(R.string.date_return, viewModel.uiState.value.dataEditRoute.dateReturn)
            LinePreviewText(R.string.date_border_crossing_departure, viewModel.uiState.value.dataEditRoute.dateCrossingDeparture)
            LinePreviewText(R.string.date_border_crossing_return, viewModel.uiState.value.dataEditRoute.dateCrossingReturn)
            LinePreviewText(R.string.speedometer_reading_departure, viewModel.uiState.value.dataEditRoute.speedometerDeparture)
            LinePreviewText(R.string.speedometer_reading_return, viewModel.uiState.value.dataEditRoute.speedometerReturn)
            LinePreviewText(R.string.fuelled, viewModel.uiState.value.dataEditRoute.fuelled)

            Divider(modifier = Modifier.padding(10.dp))

            Text(
                text = stringResource(R.string.progress_report),
                modifier = Modifier.fillMaxWidth(),
                style = typography.titleLarge,
                textAlign = TextAlign.Center
            )

            for(i in 0..<viewModel.uiState.value.listDataEditProgressReports.size) {
                ProgressReportItem(
                    item = viewModel.uiState.value.listDataEditProgressReports[i],
                    size = viewModel.uiState.value.listDataEditProgressReports.size,
                    current = i
                )
            }

            Divider(modifier = Modifier.padding(10.dp))

            if(viewModel.uiState.value.listDataEditExpensesTrip.size > 0) {
                Text(
                    text = stringResource(R.string.business_trip_expenses),
                    modifier = Modifier.fillMaxWidth(),
                    style = typography.titleLarge,
                    textAlign = TextAlign.Center
                )

                for(i in 0..<viewModel.uiState.value.listDataEditExpensesTrip.size) {
                    TripExpensesItem(
                        item = viewModel.uiState.value.listDataEditExpensesTrip[i],
                        size = viewModel.uiState.value.listDataEditExpensesTrip.size,
                        current = i
                    )
                }
            }
        }

        BottomBarCustom(
            onNext = onNext, onBack = onBack, enabled = viewModel.uiState.value.reportName != ""
        )
    }
}

@Composable
private fun LinePreviewText(@StringRes textName: Int, previewText: String) {
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
private fun ProgressReportItem(item: EditProgressReportsDetailingUiState, size: Int, current: Int) {
    Column {
        RowProgressAndExpenses(title = R.string.date, text = item.date)
        RowProgressAndExpenses(title = R.string.country, text = item.country)
        RowProgressAndExpenses(title = R.string.township, text = "${item.townshipOne} - ${item.townshipTwo}")
        RowProgressAndExpenses(title = R.string.distance, text = item.distance)
        RowProgressAndExpenses(title = R.string.cargo_weight, text = item.cargoWeight)
        if(size - 1 != current) Divider()
    }
}

@Composable
private fun TripExpensesItem(item: EditExpensesTripDetailingUiState, size: Int, current: Int) {
    Column {
        RowProgressAndExpenses(title = R.string.date, text = item.date)
        RowProgressAndExpenses(title = R.string.document_number, text = item.documentNumber)
        RowProgressAndExpenses(title = R.string.expense_item, text = item.expenseItem)
        RowProgressAndExpenses(title = R.string.sum, text = item.sum)
        RowProgressAndExpenses(title = R.string.currency, text = item.currency)
        if(size - 1 != current) Divider()
    }
}