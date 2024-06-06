package com.example.reportsfordrivers.ui.layouts.createreports

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
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.ui.layouts.custom.BottomBarCustom
import com.example.reportsfordrivers.ui.layouts.custom.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.layouts.custom.RowProgressAndExpenses
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.createreports.CreatePreviewAndResultViewModel
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateExpensesTripDetailingUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateProgressReportsDetailingUiState

@Composable
fun CreateReportsPreviewScreen(
    onNext: () -> Unit,
    onBack: () -> Unit,
    viewModel: CreatePreviewAndResultViewModel = hiltViewModel()
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
                tag = Tags.TAG_TEST_PREVIEW_REPORT_NAME,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = true
                )
            )

            Divider(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp))

            LinePreviewText(R.string.date_create_report, viewModel.uiState.value.dataCreateReportInfo.date)
            LinePreviewText(R.string.last_name, viewModel.uiState.value.dataCreatePersonalInfo.lastName)
            LinePreviewText(R.string.first_name, viewModel.uiState.value.dataCreatePersonalInfo.firstName)
            LinePreviewText(R.string.patronymic, viewModel.uiState.value.dataCreatePersonalInfo.patronymic)
            LinePreviewText(R.string.waybill, viewModel.uiState.value.dataCreateReportInfo.waybill)
            LinePreviewText(R.string.make_vehicle, viewModel.uiState.value.dataCreateVehicleInfo.makeVehicle)
            LinePreviewText(R.string.registration_number_vehicle,
                viewModel.uiState.value.dataCreateVehicleInfo.rnVehicle)
            LinePreviewText(R.string.make_trailer, viewModel.uiState.value.dataCreateVehicleInfo.makeTrailer)
            LinePreviewText(R.string.registration_number_trailer,
                viewModel.uiState.value.dataCreateVehicleInfo.rnTrailer)

            Divider(
                modifier = Modifier.padding(10.dp)
            )

            LinePreviewText(
                R.string.route,
                viewModel.uiState.value.dataCreateRoute.route
            )
            LinePreviewText(R.string.date_departure, viewModel.uiState.value.dataCreateRoute.dateDeparture)
            LinePreviewText(R.string.date_return, viewModel.uiState.value.dataCreateRoute.dateReturn)
            LinePreviewText(R.string.date_border_crossing_departure,
                viewModel.uiState.value.dataCreateRoute.dateCrossingDeparture)
            LinePreviewText(R.string.date_border_crossing_return,
                viewModel.uiState.value.dataCreateRoute.dateCrossingReturn)
            LinePreviewText(R.string.speedometer_reading_departure, viewModel.uiState.value.dataCreateRoute.speedometerDeparture)
            LinePreviewText(R.string.speedometer_reading_return, viewModel.uiState.value.dataCreateRoute.speedometerReturn)
            LinePreviewText(R.string.fuelled, viewModel.uiState.value.dataCreateRoute.fuelled)

            Divider(
                modifier = Modifier.padding(10.dp)
            )

            Text(
                text = stringResource(R.string.progress_report),
                modifier = Modifier.fillMaxWidth(),
                style = typography.titleLarge,
                textAlign = TextAlign.Center
            )
            
            for(i in 0..<viewModel.uiState.value.listDataCreateProgressReports.size) {
                ProgressReportItem(
                    item = viewModel.uiState.value.listDataCreateProgressReports[i],
                    size = viewModel.uiState.value.listDataCreateProgressReports.size,
                    current = i
                )
            }

            Divider(
                modifier = Modifier.padding(10.dp)
            )

            if(viewModel.uiState.value.listDataCreateExpensesTrip.size > 0) {
                Text(
                    text = stringResource(id = R.string.business_trip_expenses),
                    modifier = Modifier.fillMaxWidth(),
                    style = typography.titleLarge,
                    textAlign = TextAlign.Center
                )

                for(i in 0..<viewModel.uiState.value.listDataCreateExpensesTrip.size) {
                    TripExpensesItem(
                        item = viewModel.uiState.value.listDataCreateExpensesTrip[i],
                        size = viewModel.uiState.value.listDataCreateExpensesTrip.size,
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
private fun ProgressReportItem(item: CreateProgressReportsDetailingUiState, size: Int, current: Int) {
    Column {
        RowProgressAndExpenses(title = R.string.date, text = item.date)
        RowProgressAndExpenses(title = R.string.country, text = item.country)
        RowProgressAndExpenses(title = R.string.township
            , text = "${item.townshipOne} - ${item.townshipTwo}")
        RowProgressAndExpenses(title = R.string.distance, text = item.distance)
        RowProgressAndExpenses(title = R.string.cargo_weight, text = item.cargoWeight)
        if(size - 1 != current) Divider()
    }
}

@Composable
private fun TripExpensesItem(item: CreateExpensesTripDetailingUiState, size: Int, current: Int) {
    Column {
        RowProgressAndExpenses(title = R.string.date, text = item.date)
        RowProgressAndExpenses(title = R.string.document_number, text = item.documentNumber)
        RowProgressAndExpenses(title = R.string.expense_item, text = item.expenseItem)
        RowProgressAndExpenses(title = R.string.sum, text = item.sum)
        RowProgressAndExpenses(title = R.string.currency, text = item.currency)
        if(size - 1 != current) Divider()
    }
}
