package com.example.reportsfordrivers.ui.layouts.hirstoryreports

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.viewmodel.historyreports.HistoryReportsListViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HistoryReportsScreen(
    viewModel: HistoryReportsListViewModel = hiltViewModel(),
    navigate: NavHostController,
) {
    Scaffold(
        topBar = {
            HistoryReportsListTopBar(navController = navigate)
        }
    ) { innerPadding ->
        if(viewModel.uiState.value.historyReportsList.size != 0) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(viewModel.uiState.value.historyReportsList.size) { element ->
                    HistoryReportsCard(
                        route = viewModel.uiState.value.historyReportsList[element].routeMainInfo,
                        date = viewModel.uiState.value.historyReportsList[element].dateCreate,
                        id = viewModel.uiState.value.historyReportsList[element].id,
                        viewModel = viewModel,
                        navigate = navigate
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier.width(200.dp)
                ) {
                    Column() {
                        Text(
                            text = stringResource(R.string.empty_create_first_report),
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight(400),
                                textAlign = TextAlign.Center
                            )
                        )
                        Button(
                            onClick = { }
                        ) {
                            Text(
                                text = stringResource(R.string.create)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryReportsCard(
    route: String,
    date: String,
    id: Int,
    viewModel: HistoryReportsListViewModel,
    navigate: NavHostController,
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .clickable {
                viewModel.selectedId.value = id
                navigate.navigate(ReportsForDriversSchema.SelectElementHistory.name)
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = route
                )
                Text(
                    text = date
                )
            }
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = null
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistoryReportsListTopBar(
    navController: NavHostController
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.history_report)
            )
        },

        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate(
                        ReportsForDriversSchema.Start.name,
                        navOptions = NavOptions.Builder()
                            .setPopUpTo(ReportsForDriversSchema.Start.name, true)
                            .build()
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        }
    )
}

