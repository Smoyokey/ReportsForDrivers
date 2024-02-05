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
import androidx.navigation.NavController
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema


@Composable
fun MainMenuScreen(
    modifier: Modifier = Modifier,
    onCreateReport: () -> Unit,
    onHistoryReports: () -> Unit,
    onSetting: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = onCreateReport) {
            Text(text = stringResource(R.string.create_report))
        }
        Button(onClick = {}) {
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