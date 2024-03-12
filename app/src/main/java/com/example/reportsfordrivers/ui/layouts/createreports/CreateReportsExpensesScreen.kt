package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.ui.AlertDialogDeleteElement
import com.example.reportsfordrivers.ui.BottomBarCustom
import com.example.reportsfordrivers.ui.DatePickerDialogCustom
import com.example.reportsfordrivers.ui.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.OutlinedTextFieldDatePicker
import com.example.reportsfordrivers.ui.RowDate
import com.example.reportsfordrivers.ui.RowProgressAndExpenses
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportsViewModel
import com.example.reportsfordrivers.viewmodel.createreports.uistate.TripExpensesReports

@Composable
fun CreateReportsExpensesScreen(
    viewModel: CreateReportsViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    BackHandler {
        navController.navigate(
            ReportsForDriversSchema.ProgressReport.name,
            navOptions = NavOptions.Builder()
                .setPopUpTo(ReportsForDriversSchema.ProgressReport.name, true)
                .build()
        )
    }

    val scrollState = rememberScrollState()

    val source = remember { MutableInteractionSource() }
    if (source.collectIsPressedAsState().value) viewModel.openDialogTripExpenseDate.value = true

    Column {
        TabRowExpenses(navController = navController)

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            RowDate(
                label = R.string.date,
                openDialog = viewModel.openDialogTripExpenseDate,
                date = viewModel.uiStateTripExpenses.value.tripExpensesDetails.date
            )

            OutlinedTextFieldCustom(
                label = R.string.document_number,
                value = viewModel.uiStateTripExpenses.value.tripExpensesDetails.documentNumber,
                onValueChange = viewModel::updateTripExpensesDocumentNumber,
                tag = Tags.TAG_TEST_EXPENSES_DOCUMENT_NUMBER,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
            )

            OutlinedTextFieldCustom(
                label = R.string.expense_item,
                value = viewModel.uiStateTripExpenses.value.tripExpensesDetails.expenseItem,
                onValueChange = viewModel::updateTripExpensesExpenseItem,
                tag = Tags.TAG_TEST_EXPENSES_EXPENSE_ITEM,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextFieldCustom(
                    label = R.string.sum,
                    value = viewModel.uiStateTripExpenses.value.tripExpensesDetails.sum,
                    onValueChange = viewModel::updateTripExpensesSum,
                    tag = Tags.TAG_TEST_EXPENSES_SUM,
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        viewModel.updateTripExpense()
                    },
                    enabled = viewModel.validateAddTripExpenses()
                ) {
                    Text(
                        text = stringResource(R.string.add)
                    )
                }
            }

            Column {
                for (i in 0..<viewModel.uiState.value.listTripExpenses.size) {
                    ColumnTripExpense(
                        tripExpenses = viewModel.uiState.value.listTripExpenses[i],
                        delete = viewModel::deletePositionTripExpense,
                        isOpen = viewModel.openDialogTripExpensesDelete,
                        position = i,
                        size = viewModel.uiState.value.listTripExpenses.size
                    )
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
fun ColumnTripExpense(
    tripExpenses: TripExpensesReports,
    delete: (Int) -> Unit,
    isOpen: MutableState<Boolean>,
    position: Int,
    size: Int
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            RowProgressAndExpenses(
                title = R.string.date,
                text = tripExpenses.tripExpensesDetails.date
            )
            RowProgressAndExpenses(
                title = R.string.document_number,
                text = tripExpenses.tripExpensesDetails.documentNumber
            )
            RowProgressAndExpenses(
                title = R.string.expense_item,
                text = tripExpenses.tripExpensesDetails.expenseItem,
            )
            RowProgressAndExpenses(
                title = R.string.sum,
                text = tripExpenses.tripExpensesDetails.sum
            )
            RowProgressAndExpenses(
                title = R.string.currency,
                text = tripExpenses.tripExpensesDetails.currency
            )
            if(size - 1 != position) {
                Divider(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp)
                )
            }
        }
        IconButton(
            onClick = {
                isOpen.value = true
            }
        ) {
            Icon(
                Icons.Outlined.Clear,
                contentDescription = stringResource(R.string.delete)
            )
        }
    }
    AlertDialogDeleteElement(
        isOpen = isOpen,
        delete = delete,
        position = position
    )
}

@Composable
private fun TabRowExpenses(
    navController: NavHostController
) {
    TabRow(selectedTabIndex = 5) {
        Tab(
            text = { Text("1") },
            selected = false,
            onClick = { navController.navigate(ReportsForDriversSchema.ReportInfo.name) }
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
}