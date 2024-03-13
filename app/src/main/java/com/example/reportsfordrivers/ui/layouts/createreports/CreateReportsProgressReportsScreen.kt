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
import com.example.reportsfordrivers.viewmodel.createreports.uistate.ProgressReports

@Composable
fun CreateReportsProgressReportsScreen(
    viewModel: CreateReportsViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    BackHandler {
        navController.navigate(
            ReportsForDriversSchema.FillingDataTwo.name,
            navOptions = NavOptions.Builder()
                .setPopUpTo(ReportsForDriversSchema.FillingDataTwo.name, true)
                .build()
        )
    }

    val scrollState = rememberScrollState()

    val source = remember { MutableInteractionSource() }
    if (source.collectIsPressedAsState().value) viewModel.openDialogProgressReportsDate.value = true

    Column {
        TabRowProgressReports(navController = navController, viewModel = viewModel)

        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 16.dp)
                .weight(1f)
                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {

            RowDate(
                label = R.string.date,
                openDialog = viewModel.openDialogProgressReportsDate,
                date = viewModel.uiStateProgressReports.value.progressDetails.date
            )

            OutlinedTextFieldCustom(
                label = R.string.country,
                value = viewModel.uiStateProgressReports.value.progressDetails.country,
                onValueChange = viewModel::updateProgressReportsCountry,
                tag = Tags.TAG_TEST_PROGRESS_REPORTS_COUNTRY,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )

            OutlinedTextFieldCustom(
                label = R.string.township_one,
                value = viewModel.uiStateProgressReports.value.progressDetails.townshipOne,
                onValueChange = viewModel::updateProgressReportsTownshipOne,
                tag = Tags.TAG_TEST_PROGRESS_REPORTS_TOWNSHIP_ONE,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )

            OutlinedTextFieldCustom(
                label = R.string.township_two,
                value = viewModel.uiStateProgressReports.value.progressDetails.townshipTwo,
                onValueChange = viewModel::updateProgressReportsTownshipTwo,
                tag = Tags.TAG_TEST_PROGRESS_REPORTS_TOWNSHIP_TWO,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextFieldCustom(
                    label = R.string.distance,
                    value = viewModel.uiStateProgressReports.value.progressDetails.distance,
                    onValueChange = viewModel::updateProgressReportsDistance,
                    tag = Tags.TAG_TEST_PROGRESS_REPORTS_DISTANCE,
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                OutlinedTextFieldCustom(
                    label = R.string.cargo_weight,
                    value = viewModel.uiStateProgressReports.value.progressDetails.cargoWeight,
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
                        viewModel.updateProgressReports()
                    },
                    enabled = viewModel.validateAddProgressReports()
                ) {
                    Text(
                        text = stringResource(R.string.add)
                    )
                }
            }

            Column {
                for (i in 0..<viewModel.uiState.value.listProgress.size) {
                    ColumnProgressReports(
                        viewModel.uiState.value.listProgress[i],
                        viewModel::deletePositionProgressReports,
                        viewModel.openDialogProgressReportsDelete,
                        i,
                        viewModel.uiState.value.listProgress.size
                    )

                }
            }

            DatePickerDialogCustom(
                viewModel.openDialogProgressReportsDate,
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
    progressReports: ProgressReports,
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
                text = progressReports.progressDetails.date
            )
            RowProgressAndExpenses(
                title = R.string.country,
                text = progressReports.progressDetails.country
            )
            RowProgressAndExpenses(
                title = R.string.township,
                text = "${progressReports.progressDetails.townshipOne} " +
                        "- ${progressReports.progressDetails.townshipTwo}"
            )
            RowProgressAndExpenses(
                title = R.string.distance,
                text = progressReports.progressDetails.distance
            )
            RowProgressAndExpenses(
                title = R.string.cargo_weight,
                text = progressReports.progressDetails.cargoWeight
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
fun TabRowProgressReports(
    navController: NavHostController,
    viewModel: CreateReportsViewModel
) {
    TabRow(selectedTabIndex = 4) {
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
}
