package com.example.reportsfordrivers.ui.layouts.setting.customsettingmain

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.reportsfordrivers.R

@Composable
fun AlertDialogThemeSettingMain(
    isOpen: MutableState<Boolean>,
    onSave: () -> Unit = {},
    onCancel: () -> Unit = {},
    selectedTheme: MutableState<String>
) {
    if(isOpen.value) {
        AlertDialog(
            title = { Text(text = stringResource(R.string.choosing_theme)) },
            onDismissRequest = onCancel,
            confirmButton = {
                Button(
                    onClick = onSave
                ) {
                    Text( text = stringResource(R.string.save) )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onCancel
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            },
            text = {
                Column {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedTheme.value == "D",
                            onClick = { selectedTheme.value = "D"},
                            modifier = Modifier.semantics { contentDescription = "" } /*TODO*/
                        )
                        Text(
                            text = stringResource(R.string.dark_theme)
                        )
                    }
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedTheme.value == "L",
                            onClick = { selectedTheme.value = "L"},
                            modifier = Modifier.semantics { contentDescription = "" } /*TODO*/
                        )
                        Text(
                            text = stringResource(R.string.light_theme)
                        )
                    }
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedTheme.value == "DE",
                            onClick = { selectedTheme.value = "DE" },
                            modifier = Modifier.semantics { contentDescription = "" } /*TODO*/
                        )
                        Text(
                            text = stringResource(R.string.system_default)
                        )
                    }
                }
            }
        )
    }
}