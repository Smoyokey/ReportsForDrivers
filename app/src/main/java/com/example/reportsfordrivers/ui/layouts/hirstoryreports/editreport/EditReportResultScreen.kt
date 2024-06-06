package com.example.reportsfordrivers.ui.layouts.hirstoryreports.editreport

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.viewmodel.EditPreviewAndResultViewModel

@Composable
fun EditReportResultScreen(
    viewModel: EditPreviewAndResultViewModel = hiltViewModel<EditPreviewAndResultViewModel>(),
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            ) {
                Text(
                    text = stringResource(R.string.save),
                    style = typography.headlineMedium
                )
            }

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            ) {
                Text(
                    text = stringResource(R.string.delete),
                    style = typography.headlineMedium
                )
            }
        }
    }
}

