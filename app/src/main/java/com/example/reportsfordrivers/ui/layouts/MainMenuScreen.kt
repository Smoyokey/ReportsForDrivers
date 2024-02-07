package com.example.reportsfordrivers.ui.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.viewmodel.AppViewModelProvider
import com.example.reportsfordrivers.viewmodel.MainMenuViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject


@ViewModelScoped
@Composable
fun MainMenuScreen(
    modifier: Modifier = Modifier,
    onCreateReport: () -> Unit,
    onHistoryReports: () -> Unit,
    onSetting: () -> Unit,
    viewModel: MainMenuViewModel = viewModel()
) {

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = onCreateReport) {
            Text(text = stringResource(R.string.create_report))
        }
        Button(onClick = {viewModel.logs()}) {
            Text(text = stringResource(R.string.be_continued))
        }
        Button(onClick = onHistoryReports) {
            Text(text = stringResource(R.string.history_report))
        }
        Button(onClick = onSetting) {
            Text(text = stringResource(R.string.settings))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainMenuScreenPreview() {
    MainMenuScreen( onCreateReport = {}, onHistoryReports = {}, onSetting = {} )
}