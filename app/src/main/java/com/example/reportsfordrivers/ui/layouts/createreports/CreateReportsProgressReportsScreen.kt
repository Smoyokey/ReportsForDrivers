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
import com.example.reportsfordrivers.ui.layouts.custom.AlertDialogDeleteElement
import com.example.reportsfordrivers.ui.layouts.custom.BottomBarCustom
import com.example.reportsfordrivers.ui.layouts.custom.DatePickerDialogCustom
import com.example.reportsfordrivers.ui.layouts.custom.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.layouts.custom.RowDateWithTextField
import com.example.reportsfordrivers.ui.layouts.custom.RowProgressAndExpenses
import com.example.reportsfordrivers.ui.layouts.custom.TabRowCustom
import com.example.reportsfordrivers.viewmodel.createreports.CreateProgressReportsViewModel
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateProgressReportsDetailingUiState

@Composable
fun CreateReportsProgressReportsScreen(
    viewModel: CreateProgressReportsViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    BackHandler {
        navController.navigate(
            ReportsForDriversSchema.Route.name,
            navOptions = NavOptions.Builder()
                .setPopUpTo(ReportsForDriversSchema.Route.name, true)
                .build()
        )
    }

    val scrollState = rememberScrollState()

    val source = remember { MutableInteractionSource() }
    if (source.collectIsPressedAsState().value)
        viewModel.openDialogDateCreateProgressReports.value = true

    Column {
        TabRowCustom(
            index = 4,
            navController = navController,
            isEnabledFive = false,
            isEnabledSix = viewModel.uiStateIsValidate.value.isValidateCreateProgressReports
        )

        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 16.dp)
                .weight(1f)
                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {

            RowDateWithTextField(
                openDialog = viewModel.openDialogDateCreateProgressReports,
                date = viewModel.uiStateCreateProgressReportsDetailing.value.date,
                modifier = Modifier.weight(1f),
                text = R.string.date
            )

            OutlinedTextFieldCustom(
                label = R.string.country,
                value = viewModel.uiStateCreateProgressReportsDetailing.value.country,
                onValueChange = viewModel::updateProgressReportsCountry,
                tag = Tags.TAG_TEST_PROGRESS_REPORTS_COUNTRY,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = true
                )
            )

            OutlinedTextFieldCustom(
                label = R.string.township_one,
                value = viewModel.uiStateCreateProgressReportsDetailing.value.townshipOne,
                onValueChange = viewModel::updateProgressReportsTownshipOne,
                tag = Tags.TAG_TEST_PROGRESS_REPORTS_TOWNSHIP_ONE,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = true
                )
            )

            OutlinedTextFieldCustom(
                label = R.string.township_two,
                value = viewModel.uiStateCreateProgressReportsDetailing.value.townshipTwo,
                onValueChange = viewModel::updateProgressReportsTownshipTwo,
                tag = Tags.TAG_TEST_PROGRESS_REPORTS_TOWNSHIP_TWO,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = true
                )
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextFieldCustom(
                    label = R.string.distance,
                    value = viewModel.uiStateCreateProgressReportsDetailing.value.distance,
                    onValueChange = viewModel::updateProgressReportsDistance,
                    tag = Tags.TAG_TEST_PROGRESS_REPORTS_DISTANCE,
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                OutlinedTextFieldCustom(
                    label = R.string.cargo_weight,
                    value = viewModel.uiStateCreateProgressReportsDetailing.value.cargoWeight,
                    onValueChange = viewModel::updateProgressReportsCargoWeight,
                    tag = Tags.TAG_TEST_PROGRESS_REPORTS_CARGO_WEIGHT,
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        viewModel.addListProgressReports()
                    },
                    enabled = viewModel.isValidateAddProgressReports()
                ) {
                    Text(
                        text = stringResource(R.string.add)
                    )
                }
            }

            Column {
                for (i in 0..<viewModel.uiStateCreateProgressReports.value.createProgressReportsList.size) {
                    ColumnProgressReports(
                        viewModel.uiStateCreateProgressReports.value.createProgressReportsList[i],
                        viewModel::deletePositionProgressReports,
                        viewModel.openDialogDeleteCreateProgressReports,
                        i,
                        viewModel.uiStateCreateProgressReports.value.createProgressReportsList.size
                    )
                }
            }

            DatePickerDialogCustom(
                viewModel.openDialogDateCreateProgressReports,
                viewModel::updateProgressReportsDate
            )
        }

        BottomBarCustom(
            onNext = { navController.navigate(ReportsForDriversSchema.TripExpenses.name) },
            onBack = { navController.navigateUp() },
            enabled = viewModel.isValidateNextProgressReports()
        )
    }
}

@Composable
fun ColumnProgressReports(
    progressReportsDetailing: CreateProgressReportsDetailingUiState,
    delete: (Int) -> Unit,
    isOpen: MutableState<Boolean>,
    position: Int,
    size: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            RowProgressAndExpenses(
                title = R.string.date,
                text = progressReportsDetailing.date
            )
            RowProgressAndExpenses(
                title = R.string.country,
                text = progressReportsDetailing.country
            )
            RowProgressAndExpenses(
                title = R.string.township,
                text = "${progressReportsDetailing.townshipOne} " +
                        "- ${progressReportsDetailing.townshipTwo}"
            )
            RowProgressAndExpenses(
                title = R.string.distance,
                text = progressReportsDetailing.distance
            )
            RowProgressAndExpenses(
                title = R.string.cargo_weight,
                text = progressReportsDetailing.cargoWeight
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