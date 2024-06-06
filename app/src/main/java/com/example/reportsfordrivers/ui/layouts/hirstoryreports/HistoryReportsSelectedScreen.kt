package com.example.reportsfordrivers.ui.layouts.hirstoryreports

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.viewmodel.historyreports.HistoryExpensesTripDetailingUiState
import com.example.reportsfordrivers.viewmodel.historyreports.HistoryProgressReportDetailingUiState
import com.example.reportsfordrivers.viewmodel.historyreports.HistoryReportsDetailingViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun HistoryReportsSelectedScreen(
    viewModel: HistoryReportsDetailingViewModel = hiltViewModel(),
    navController: NavHostController

) {
    val context = LocalContext.current

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            HistoryReportsSelectedTopBar(
                viewModel = viewModel,
                context = context,
                navController = navController
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            HistoryReportsTextRow(
                label = R.string.last_name,
                text = viewModel.uiState.value.historyPersonalInfoUiState.lastName
            )

            HistoryReportsTextRow(
                label = R.string.first_name,
                text = viewModel.uiState.value.historyPersonalInfoUiState.firstName
            )

            HistoryReportsTextRow(
                label = R.string.patronymic,
                text = viewModel.uiState.value.historyPersonalInfoUiState.patronymic
            )

            HistoryReportsTextRow(
                label = R.string.waybill,
                text = viewModel.uiState.value.waybill
            )

            HistoryReportsTextRow(
                label = R.string.make_vehicle,
                text = viewModel.uiState.value.historyVehicleUiState.makeVehicle
            )

            HistoryReportsTextRow(
                label = R.string.registration_number_vehicle,
                text = viewModel.uiState.value.historyVehicleUiState.rnVehicle
            )

            HistoryReportsTextRow(
                label = R.string.make_trailer,
                text = viewModel.uiState.value.historyTrailerUiState.makeTrailer
            )

            HistoryReportsTextRow(
                label = R.string.registration_number_trailer,
                text = viewModel.uiState.value.historyTrailerUiState.rnTrailer
            )

            HistoryReportsTextRow(
                label = R.string.route,
                text = viewModel.uiState.value.historyRouteUiState.route
            )

            HistoryReportsTextRow(
                label = R.string.date_departure,
                text = viewModel.uiState.value.historyRouteUiState.dateDeparture
            )

            HistoryReportsTextRow(
                label = R.string.date_return,
                text = viewModel.uiState.value.historyRouteUiState.dateReturn
            )

            HistoryReportsTextRow(
                label = R.string.date_border_crossing_departure,
                text = viewModel.uiState.value.historyRouteUiState.dateCrossingDeparture
            )

            HistoryReportsTextRow(
                label = R.string.date_border_crossing_return,
                text = viewModel.uiState.value.historyRouteUiState.dateCrossingReturn
            )

            HistoryReportsTextRow(
                label = R.string.speedometer_reading_departure,
                text = viewModel.uiState.value.historyRouteUiState.speedometerDeparture
            )

            HistoryReportsTextRow(
                label = R.string.speedometer_reading_return,
                text = viewModel.uiState.value.historyRouteUiState.speedometerReturn
            )

            HistoryReportsTextRow(
                label = R.string.fuelled,
                text = viewModel.uiState.value.historyRouteUiState.fuelled
            )

            Text(
                text = stringResource(R.string.progress_report),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            for(i in 0..<viewModel.uiState.value.historyProgressReportsUiState.size) {
                HistoryReportsProgressReports(
                    position = i,
                    element = viewModel.uiState.value.historyProgressReportsUiState[i]
                )
            }

            if (viewModel.uiState.value.historyExpensesTripUiState.size != 0) {
                Text(
                    text = stringResource(R.string.expense_item),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                for(i in 0..<viewModel.uiState.value.historyExpensesTripUiState.size) {
                    HistoryReportsExpensesTrip(
                        position = i,
                        element = viewModel.uiState.value.historyExpensesTripUiState[i]
                    )
                }
            }
        }
        if(viewModel.openSnackBarSaveReport.value) {
            scope.launch {
                viewModel.openSnackBarSaveReport.value = false
                val snackbarResult = snackbarHostState.showSnackbar(
                    message = "Файл сохранен"
                )
                when(snackbarResult) {
                    SnackbarResult.Dismissed -> {
                        viewModel.openSnackBarSaveReport.value = false
                    }

                    SnackbarResult.ActionPerformed -> TODO()
                }
            }
        }
    }
}

@Composable
private fun HistoryReportsTextRow(
    @StringRes label: Int,
    text: String
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(label),
            modifier = Modifier.weight(1f)
        )
        Text(text = "-")
        Text(
            text = text,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun HistoryReportsProgressReports(
    position: Int,
    element: HistoryProgressReportDetailingUiState
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "${position + 1}.",
            textAlign = TextAlign.Center
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            HistoryReportsTextRow(
                label = R.string.date,
                text = element.date
            )
            HistoryReportsTextRow(
                label = R.string.country,
                text = element.country
            )
            HistoryReportsTextRow(
                label = R.string.township,
                text = "${element.townshipOne} - ${element.townshipTwo}"
            )
            HistoryReportsTextRow(
                label = R.string.distance,
                text = element.distance
            )
            HistoryReportsTextRow(
                label = R.string.cargo_weight,
                text = element.weight
            )
        }
    }
}

@Composable
private fun HistoryReportsExpensesTrip(
    position: Int,
    element: HistoryExpensesTripDetailingUiState
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "${position + 1}."
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            HistoryReportsTextRow(
                label = R.string.date,
                text = element.date
            )
            HistoryReportsTextRow(
                label = R.string.document_number,
                text = element.documentNumber
            )
            HistoryReportsTextRow(
                label = R.string.expense_item,
                text = element.expenseItem
            )
            HistoryReportsTextRow(
                label = R.string.sum,
                text = element.sum
            )
            HistoryReportsTextRow(
                label = R.string.currency,
                text = element.currency
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistoryReportsSelectedTopBar(
    viewModel: HistoryReportsDetailingViewModel,
    context: Context,
    navController: NavHostController
) {
    TopAppBar(
        title = {
            Text(
                text = viewModel.uiState.value.nameReport,
                overflow = TextOverflow.Ellipsis
            )
        },

        navigationIcon = {
            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        },

        actions = {
            TopAppBarDropdownMenu(
                viewModel = viewModel,
                context = context,
                navController = navController
            )
        }
    )
}

@Composable
private fun TopAppBarDropdownMenu(
    viewModel: HistoryReportsDetailingViewModel,
    context: Context,
    navController: NavHostController
) {
    Box(
        modifier = Modifier.wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(
            onClick = { viewModel.openDropdownMenu.value = true }
        ) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = null
            )
        }
    }

    DropdownMenu(
        expanded = viewModel.openDropdownMenu.value,
        onDismissRequest = { viewModel.openDropdownMenu.value = false }
    ) {
        DropdownMenuItem(
            onClick = {
                viewModel.openDropdownMenu.value = false
                navController.navigate(ReportsForDriversSchema.EditReportInfo.name)
            },
            text = { Text(text = stringResource(R.string.edit)) }
        )
        Divider()

        DropdownMenuItem(
            onClick = {
                viewModel.openDropdownMenu.value = false
                viewModel.saveFile(context)
                viewModel.openSnackBarSaveReport.value = true
            },
            text = { Text(text = stringResource(R.string.export_to_word)) }
        )
        Divider()

        DropdownMenuItem(
            onClick = {
                viewModel.openDropdownMenu.value = false
                viewModel.testShare(context)
            },
            text = { Text(text = stringResource(R.string.share)) }
        )
        Divider()

        DropdownMenuItem(
            onClick = {
                viewModel.openDropdownMenu.value = false
            },
            text = { Text(text = stringResource(R.string.delete)) }
        )
    }
}

