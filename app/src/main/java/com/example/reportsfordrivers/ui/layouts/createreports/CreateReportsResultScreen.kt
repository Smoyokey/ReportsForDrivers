package com.example.reportsfordrivers.ui.layouts.createreports

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reportsfordrivers.MainActivity
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.createreports.CreatePreviewAndResultViewModel

private const val TAG = "CreateReportsResultScreen"

data class PermissionScreenState(
    val title: String,
    val buttonText: String,
    val errorText: String? = null,
    val rationale: String? = null
)

@Composable
fun CreateReportsResultScreen(
    viewModel: CreatePreviewAndResultViewModel = hiltViewModel<CreatePreviewAndResultViewModel>()
) {
//    val permissionScreenState: MutableState<>

    val context = LocalContext.current
    val permission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Log.i(TAG, "Permission granted")
            viewModel.saveFile(context)
        } else {
            Log.i(TAG, "Permission denied")
            viewModel.isOpenPermissionSaveFile.value = true
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
                    Log.i(TAG, "Click SAVE")

                    when {
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        ) == PackageManager.PERMISSION_GRANTED -> {
                            Log.i(TAG, "Permission GRANTED")
                            viewModel.saveFile(context)
                        }
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            viewModel.activity, Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) -> {
                            viewModel.isOpenPermissionSaveFile.value = true
                            Log.i(TAG, "Permission DENIED")
                        }
                        else -> {
//                            permission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                            viewModel.isOpenPermissionSaveFile.value = true
                            viewModel.saveFile(context)
                            Log.i(TAG, "ELSE")
                        }
                    }

                },
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


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateReportsResultScreenPreview() {
    CreateReportsResultScreen()
}