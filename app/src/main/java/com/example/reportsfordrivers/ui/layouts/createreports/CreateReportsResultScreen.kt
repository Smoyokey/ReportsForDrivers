package com.example.reportsfordrivers.ui.layouts.createreports

import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportsViewModel

private const val TAG = "CreateReportsResultScreen"

@Composable
fun CreateReportsResultScreen(
    viewModel: CreateReportsViewModel = hiltViewModel<CreateReportsViewModel>()
) {
    val context = LocalContext.current
    val permission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if(it) {
            Log.i(TAG, "Permission granted")
            viewModel.saveFile(context)
        } else {
            Log.i(TAG, "Permission denied")
        }
    }

    Column(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    viewModel.testShare(context)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text (text = stringResource(R.string.share))
            }

            Button(
                onClick = {
                    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                        val permissionCheckResult = ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        if(permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            viewModel.saveFile(context)
                        } else {
                            permission.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        }
                    } else {
                        viewModel.saveFile(context)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text (text = stringResource(R.string.save_file))
            }

            Button(
                onClick = {
                          viewModel.adShowScreen(context    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            ) {
                Text (text = stringResource(R.string.save_and_exit))
            }

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            ) {
                Text (text = stringResource(R.string.delete))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateReportsResultScreenPreview() {
    CreateReportsResultScreen()
}