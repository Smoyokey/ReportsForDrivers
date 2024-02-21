package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.ui.OutlinedTextFieldCustom
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportsViewModel

@Composable
fun CreateReportsExpensesScreen(
    onPreview: () -> Unit,
    viewModel: CreateReportsViewModel = hiltViewModel()
) {
    Column() {
        TabRow(selectedTabIndex = 4) {
            viewModel.tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = 4 == index,
                    onClick = {  },
                    enabled = false
                )
            }
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
                onClick = { }
            ) {
                Text(
                    text = stringResource(R.string.current_date)
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
                onClick = {}
            ) {
                Text(text = stringResource(R.string.add))
            }
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {}

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            OutlinedButton(onClick = onPreview) {
                Text(text = stringResource(R.string.next))
            }
        }
    }
}