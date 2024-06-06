package com.example.reportsfordrivers.ui.layouts.hirstoryreports.editreport

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
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.data.structure.Currency
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.ui.layouts.custom.AlertDialogDeleteElement
import com.example.reportsfordrivers.ui.layouts.custom.BottomBarCustom
import com.example.reportsfordrivers.ui.layouts.custom.DatePickerDialogCustom
import com.example.reportsfordrivers.ui.layouts.custom.RowDateWithTextField
import com.example.reportsfordrivers.ui.layouts.custom.RowProgressAndExpenses
import com.example.reportsfordrivers.ui.layouts.custom.TabRowCustom
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditExpensesTripDetailingUiState
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.viewmodel.EditExpensesTripViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditReportExpensesScreen(
    viewModel: EditExpensesTripViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val (date, documentNumber, expenseItem, sum, currency) = remember { FocusRequester.createRefs() }
    val keyboardController = LocalSoftwareKeyboardController.current

    BackHandler {
        navController.navigate(
            ReportsForDriversSchema.EditProgressReport.name,
            navOptions = NavOptions.Builder()
                .setPopUpTo(ReportsForDriversSchema.EditProgressReport.name, true)
                .build()
        )
    }

    val scrollState = rememberScrollState()

    val source = remember { MutableInteractionSource() }
    if(source.collectIsPressedAsState().value)
        viewModel.openDialogDateEditExpensesTrip.value = true

    Column {
        TabRowCustom(index = 5, navController = navController, isEnabledSix = false)

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            RowDateWithTextField(
                openDialog = viewModel.openDialogDateEditExpensesTrip,
                date = viewModel.uiStateEditExpensesTripDetailing.value.date,
                modifier = Modifier.weight(1f),
                text = R.string.date
            )

            OutlinedTextField(
                value = viewModel.uiStateEditExpensesTripDetailing.value.documentNumber,
                label = { Text(stringResource(R.string.document_number)) },
                onValueChange = { viewModel.updateEditExpensesTripDocumentNumber(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(documentNumber),
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if(viewModel.uiStateEditExpensesTripDetailing.value.documentNumber.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.updateEditExpensesTripDocumentNumber("") }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = stringResource(R.string.clear)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {expenseItem.requestFocus()})
            )

            OutlinedTextField(
                value = viewModel.uiStateEditExpensesTripDetailing.value.expenseItem,
                label = { Text(stringResource(R.string.expense_item)) },
                onValueChange = { viewModel.updateEditExpensesTripExpenseItem(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(expenseItem),
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if(viewModel.uiStateEditExpensesTripDetailing.value.expenseItem.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.updateEditExpensesTripExpenseItem("") }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = stringResource(R.string.clear)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {sum.requestFocus()})
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = viewModel.uiStateEditExpensesTripDetailing.value.sum,
                    label = { Text(stringResource(R.string.sum)) },
                    onValueChange = { viewModel.updateEditExpensesTripSum(it) },
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(sum),
                    textStyle = typography.bodyLarge,
                    trailingIcon = {
                        if(viewModel.uiStateEditExpensesTripDetailing.value.sum.isNotEmpty()) {
                            IconButton(
                                onClick = { viewModel.updateEditExpensesTripSum("") }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Clear,
                                    contentDescription = stringResource(R.string.clear)
                                )
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        viewModel.openBottomSheetCurrencyEditExpensesTrip.value = true
                    })
                )

                OutlinedTextField(
                    label = { Text(text = stringResource(R.string.currency)) },
                    value = viewModel.uiStateEditExpensesTripDetailing.value.currency,
                    onValueChange = {},
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            viewModel.openBottomSheetCurrencyEditExpensesTrip.value = true
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
                                viewModel.openBottomSheetCurrencyEditExpensesTrip.value = true
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
                    onClick = { viewModel.updateExpensesTrip() },
                    enabled = viewModel.isValidateAddExpensesTrip()
                ) {
                    Text(
                        text = stringResource(R.string.add)
                    )
                }
            }

            Column {
                for(i in 0..<viewModel.uiStateEditExpensesTrip.value.editExpensesTripList.size) {
                    ColumnTripExpense(
                        tripExpenses = viewModel.uiStateEditExpensesTrip.value.editExpensesTripList[i],
                        delete = viewModel::deletePositionExpensesTrip,
                        isOpen = viewModel.openDialogDeleteEditExpensesTrip,
                        position = 1,
                        size = viewModel.uiStateEditExpensesTrip.value.editExpensesTripList.size
                    )
                }
            }
        }

        BottomBarCustom(
            onNext = { navController.navigate(ReportsForDriversSchema.EditPreview.name) },
            onBack = { navController.popBackStack() }
        )

        DatePickerDialogCustom(
            viewModel.openDialogDateEditExpensesTrip,
            viewModel::updateEditExpensesTripDate
        )
    }

    if (viewModel.openBottomSheetCurrencyEditExpensesTrip.value) {
        ModalBottomSheetCurrency(
            openMenu = viewModel.openBottomSheetCurrencyEditExpensesTrip,
            listCurrency = viewModel.listCurrency.value,
            updateCurrency = viewModel::updateEditExpensesTripCurrency
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
        modifier = Modifier.fillMaxHeight(0.75f)
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
                                updateCurrency(listCurrency[element].shortNameCurrency)
                                openMenu.value = false
                            },
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
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
    tripExpenses: EditExpensesTripDetailingUiState,
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
                text = tripExpenses.expenseItem
            )
            RowProgressAndExpenses(
                title = R.string.sum,
                text = tripExpenses.sum
            )
            RowProgressAndExpenses(
                title = R.string.currency,
                text = tripExpenses.currency
            )
            if(size - 1 != position) {
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
            onClick = { isOpen.value = true }
        ) {
            Icon(
                imageVector = Icons.Outlined.Clear,
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