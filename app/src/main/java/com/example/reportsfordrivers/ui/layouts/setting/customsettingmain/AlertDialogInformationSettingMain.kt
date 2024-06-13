package com.example.reportsfordrivers.ui.layouts.setting.customsettingmain

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.example.reportsfordrivers.R

@Composable
fun AlertDialogInformationSettingMain(
    isOpen: MutableState<Boolean>,
    onSave: () -> Unit = {},
    onCancel: () -> Unit = {},
    selectedLanguage: MutableState<String>
) {
    if(isOpen.value) {
        AlertDialog(
            title = { Text(text = stringResource(R.string.selecting_report_language)) },
            onDismissRequest = onCancel,
            confirmButton = {

            }
        )
    }
}