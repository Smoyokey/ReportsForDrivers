package com.example.reportsfordrivers.ui.layouts

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.MainMenuViewModel
import com.example.reportsfordrivers.viewmodel.createreports.CreatePreviewAndResultViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch

private const val TAG = "MainMenuScreen"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@ViewModelScoped
@Composable
fun MainMenuScreen(
    onCreate: () -> Unit,
    onContinued: () -> Unit,
    onHistory: () -> Unit,
    onSetting: () -> Unit,
    viewModel: MainMenuViewModel = viewModel(),
    viewModelResult: CreatePreviewAndResultViewModel
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterVertically),
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
            OutlinedButton(
                onClick = onCreate,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.create_report),
                    style = typography.headlineMedium,
                )
            }
            OutlinedButton(
                onClick = onContinued,
                enabled = viewModel.isToBeContinued.value,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.be_continued),
                    style = typography.headlineMedium
                )
            }
            OutlinedButton(
                onClick = onHistory,
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

    if(viewModel.openSnackBarSaveReport.value) {
        scope.launch {
            viewModel.openSnackBarSaveReport.value = false
            val snackbarResult = snackbarHostState.showSnackbar(
                message = "Файл сохранен",
                actionLabel = "Поделиться"
            )
            when(snackbarResult) {
                SnackbarResult.Dismissed -> {
                    Log.i(TAG, "DISMISSED")
                }
                SnackbarResult.ActionPerformed -> {
                    Log.i(TAG, "Action PERFORMED")
                    viewModel.testShare(context, viewModelResult)
                }
            }
        }

    }

}