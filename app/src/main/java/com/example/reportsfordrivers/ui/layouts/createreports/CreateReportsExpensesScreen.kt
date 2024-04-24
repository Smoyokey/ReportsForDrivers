package com.example.reportsfordrivers.ui.layouts.createreports

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.data.structure.Currency
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.ui.AlertDialogDeleteElement
import com.example.reportsfordrivers.ui.BottomBarCustom
import com.example.reportsfordrivers.ui.DatePickerDialogCustom
import com.example.reportsfordrivers.ui.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.RowDateWithTextField
import com.example.reportsfordrivers.ui.RowProgressAndExpenses
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.createreports.CreateExpensesTripViewModel
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateExpensesTripDetailingUiState

@Composable
fun CreateReportsExpensesScreen(
    viewModel: CreateExpensesTripViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {

//    if (viewModel.listCurrency.value.isEmpty()) {
//        viewModel.startCurrency()
//    }

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
    if (source.collectIsPressedAsState().value)
        viewModel.openDialogDateCreateExpensesTrip.value = true

    Column {
        TabRowExpenses(navController = navController)

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            RowDateWithTextField(
                openDialog = viewModel.openDialogDateCreateExpensesTrip,
                date = viewModel.uiStateCreateExpensesTripDetailing.value.date,
                modifier = Modifier.weight(1f),
                text = R.string.date
            )

            OutlinedTextFieldCustom(
                label = R.string.document_number,
                value = viewModel.uiStateCreateExpensesTripDetailing.value.documentNumber,
                onValueChange = viewModel::updateCreateExpensesTripDocumentNumber,
                tag = Tags.TAG_TEST_EXPENSES_DOCUMENT_NUMBER,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrect = true
                )
            )

            OutlinedTextFieldCustom(
                label = R.string.expense_item,
                value = viewModel.uiStateCreateExpensesTripDetailing.value.expenseItem,
                onValueChange = viewModel::updateCreateExpensesTripExpenseItem,
                tag = Tags.TAG_TEST_EXPENSES_EXPENSE_ITEM,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrect = true
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextFieldCustom(
                    label = R.string.sum,
                    value = viewModel.uiStateCreateExpensesTripDetailing.value.sum,
                    onValueChange = viewModel::updateCreateExpensesTripSum,
                    tag = Tags.TAG_TEST_EXPENSES_SUM,
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                OutlinedTextField(
                    label = { Text(text = stringResource(R.string.currency)) },
                    value = viewModel.uiStateCreateExpensesTripDetailing.value.currency,
                    onValueChange = {},
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            viewModel.openBottomSheetCurrencyCreateExpensesTrip.value = true
                        },
                    readOnly = true,
                    enabled = false,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                viewModel.openBottomSheetCurrencyCreateExpensesTrip.value = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowDropDown,
                                contentDescription = stringResource(R.string.currency)
                            )
                        }
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        viewModel.updateExpensesTrip()
                    },
                    enabled = viewModel.isValidateAddExpensesTrip()
                ) {
                    Text(
                        text = stringResource(R.string.add)
                    )
                }
            }

            Column {
                for (i in 0..<viewModel.uiStateCreateExpensesTrip.value.createExpensesTripList.size) {
                    ColumnTripExpense(
                        tripExpenses = viewModel.uiStateCreateExpensesTrip.value.createExpensesTripList[i],
                        delete = viewModel::deletePositionExpensesTrip,
                        isOpen = viewModel.openDialogDeleteCreateExpensesTrip,
                        position = i,
                        size = viewModel.uiStateCreateExpensesTrip.value.createExpensesTripList.size
                    )
                }
            }
        }

        BottomBarCustom(
            onNext = { navController.navigate(ReportsForDriversSchema.Preview.name) },
            onBack = { navController.popBackStack() }
        )

        DatePickerDialogCustom(
            viewModel.openDialogDateCreateExpensesTrip,
            viewModel::updateCreateExpensesTripDate
        )
    }

    if (viewModel.openBottomSheetCurrencyCreateExpensesTrip.value) {
        ModalBottomSheetCurrency(
            openMenu = viewModel.openBottomSheetCurrencyCreateExpensesTrip,
            listCurrency = viewModel.listCurrency.value,
            updateCurrency = viewModel::updateCreateExpensesTripCurrency
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheetCurrency(
    openMenu: MutableState<Boolean>,
    listCurrency: List<Currency>,
    updateCurrency: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = { openMenu.value = false },
        sheetState = sheetState,
        modifier = Modifier.fillMaxHeight(0.75f),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 20.dp, top = 20.dp, end = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = stringResource(R.string.select_currency),
                style = typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            LazyColumn(
                modifier = Modifier
                    .weight(1f),
            ) {
                items(listCurrency.size) { element ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
                            .clickable {
                                Log.i("TAGS", listCurrency[element].fullNameCurrencyRus)
                                updateCurrency(listCurrency[element].shortNameCurrency)
                                openMenu.value = false
                            },
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Text(
                            text = listCurrency[element].fullNameCurrencyRus,
                            modifier = Modifier.weight(1f),
                            style = typography.titleLarge
                        )
                        Text(
                            text = listCurrency[element].shortNameCurrency,
                            style = typography.titleLarge
                        )
                    }
                }
            }
            Button(
                onClick = { openMenu.value = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    style = typography.titleLarge
                )
            }
        }
    }
}

@Composable
fun ColumnTripExpense(
    tripExpenses: CreateExpensesTripDetailingUiState,
    delete: (Int) -> Unit,
    isOpen: MutableState<Boolean>,
    position: Int,
    size: Int
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            RowProgressAndExpenses(
                title = R.string.date,
                text = tripExpenses.date
            )
            RowProgressAndExpenses(
                title = R.string.document_number,
                text = tripExpenses.documentNumber
            )
            RowProgressAndExpenses(
                title = R.string.expense_item,
                text = tripExpenses.expenseItem,
            )
            RowProgressAndExpenses(
                title = R.string.sum,
                text = tripExpenses.sum
            )
            RowProgressAndExpenses(
                title = R.string.currency,
                text = tripExpenses.currency
            )
            if (size - 1 != position) {
                Divider(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 10.dp,
                        bottom = 10.dp
                    )
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