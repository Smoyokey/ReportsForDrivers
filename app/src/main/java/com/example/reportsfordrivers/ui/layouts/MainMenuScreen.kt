package com.example.reportsfordrivers.ui.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.AppViewModelProvider
import com.example.reportsfordrivers.viewmodel.MainMenuViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject


@ViewModelScoped
@Composable
fun MainMenuScreen(
    onCreateReport: () -> Unit,
    onHistoryReports: () -> Unit,
    onSetting: () -> Unit,
    viewModel: MainMenuViewModel = viewModel(),
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterVertically),
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        OutlinedButton(
            onClick = onCreateReport,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.create_report),
                style = typography.headlineMedium,
//                modifier = Modifier.padding(start = 30.dp, end = 30.dp)
            )
        }
        OutlinedButton(
            onClick = {},
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.be_continued),
                style = typography.headlineMedium
            )
        }
        OutlinedButton(
            onClick = onHistoryReports,
            enabled = true,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.history_report),
                style = typography.headlineMedium,

                )
        }
        OutlinedButton(
            onClick = onSetting,
            enabled = true,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.settings),
                style = typography.headlineMedium
            )
        }
    }
}