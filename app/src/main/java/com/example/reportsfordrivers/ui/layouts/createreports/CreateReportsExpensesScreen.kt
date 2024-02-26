package com.example.reportsfordrivers.ui.layouts.createreports

import android.app.appsearch.ReportSystemUsageRequest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.ui.BottomBarCustom
import com.example.reportsfordrivers.ui.DatePickerDialogCustom
import com.example.reportsfordrivers.ui.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportsViewModel
import com.example.reportsfordrivers.viewmodel.createreports.uistate.TripExpensesReports

@Composable
fun CreateReportsExpensesScreen(
    viewModel: CreateReportsViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    Column(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
    ) {
        TabRow(selectedTabIndex = 5) {
            Tab(
                text = { Text("1") },
                selected = false,
                onClick = { navController.navigate(ReportsForDriversSchema.ReportInfo.name, ) }
            )
            Tab(
                text = { Text("2") },
                selected = false,
                onClick = { navController.navigate(ReportsForDriversSchema.PersonalInfo.name) }
            )
            Tab(
                text = { Text("3") },
                selected = false,
                onClick = { navController.navigate(ReportsForDriversSchema.VehicleInfo.name) }
            )
            Tab(
                text = { Text("4") },
                selected = false,
                onClick = { navController.navigate(ReportsForDriversSchema.FillingDataTwo.name) }
            )
            Tab(
                text = { Text("5") },
                selected = false,
                onClick = { navController.navigate(ReportsForDriversSchema.ProgressReport.name) }
            )
            Tab(
                text = { Text("6") },
                selected = false,
                onClick = { },
                enabled = false
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.date),
                modifier = Modifier.weight(1f)
            )
            TextButton(
                onClick = { viewModel.openDialogTripExpenseDate.value = true },
                modifier = Modifier.testTag(Tags.TAG_TEST_TRIP_EXPENSES_DATE)
            ) {
                Text(
                    text = viewModel.uiStateTripExpenses.value.tripExpensesDetails.date.ifEmpty
                    { stringResource(R.string.current_date) }
                )
            }
        }

        OutlinedTextFieldCustom(
            label = R.string.document_number,
            value = viewModel.uiStateTripExpenses.value.tripExpensesDetails.documentNumber,
            onValueChange = viewModel::updateTripExpensesDocumentNumber,
            tag = Tags.TAG_TEST_EXPENSES_DOCUMENT_NUMBER,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextFieldCustom(
            label = R.string.expense_item,
            value = viewModel.uiStateTripExpenses.value.tripExpensesDetails.expenseItem,
            onValueChange = viewModel::updateTripExpensesExpenseItem,
            tag = Tags.TAG_TEST_EXPENSES_EXPENSE_ITEM,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextFieldCustom(
                label = R.string.sum,
                value = viewModel.uiStateTripExpenses.value.tripExpensesDetails.sum,
                onValueChange = viewModel::updateTripExpensesSum,
                tag = Tags.TAG_TEST_EXPENSES_SUM,
                modifier = Modifier.weight(1f)
            )

            OutlinedTextFieldCustom(
                label = R.string.currency,
                value = viewModel.uiStateTripExpenses.value.tripExpensesDetails.currency,
                onValueChange = viewModel::updateTripExpensesCurrency,
                tag = Tags.TAG_TEST_EXPENSES_CURRENCY,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = { viewModel.updateTripExpense() },
                enabled = viewModel.validateAddTripExpenses()
            ) {
                Text(text = stringResource(R.string.add))
            }
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(viewModel.uiState.value.listTripExpenses) { element ->
                    LineElementTripExpenses(tripExpenseReport = element)
                }
            }
        }

        BottomBarCustom(
            onNext = { navController.navigate(ReportsForDriversSchema.Preview.name) },
            onBack = { navController.popBackStack() }
        )

        DatePickerDialogCustom(
            viewModel.openDialogTripExpenseDate,
            viewModel::updateTripExpensesDate
        )
    }
}

@Composable
fun LineElementTripExpenses(tripExpenseReport: TripExpensesReports) {
    Column() {
        Text("Date - ${tripExpenseReport.tripExpensesDetails.date}")
        Text("Document number - ${tripExpenseReport.tripExpensesDetails.documentNumber}")
        Text("Expense item - ${tripExpenseReport.tripExpensesDetails.expenseItem}")
        Text("Sum - ${tripExpenseReport.tripExpensesDetails.sum}")
        Text("Currency - ${tripExpenseReport.tripExpensesDetails.currency}")
    }
}