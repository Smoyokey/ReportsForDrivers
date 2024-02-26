package com.example.reportsfordrivers.ui

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.ui.theme.typography
import kotlinx.coroutines.launch

@Composable
fun OutlinedTextFieldCustom(
    @StringRes label: Int,
    value: String,
    onValueChange: (String) -> Unit,
    tag: String,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(stringResource(label)) },
        modifier = modifier,
        singleLine = true,
        textStyle = typography.bodyLarge,
        trailingIcon = {
            if (value.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = stringResource(R.string.clear),
                    modifier = Modifier
                        .clickable { onValueChange("") }
                        .testTag(tag)
                )
            }
        }
    )
}

@Composable
fun OutlinedTextFieldDatePicker(
    @StringRes label: Int,
    value: String,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource,
    onValueChange: (String) -> Unit,
    tag: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = { },
        label = { Text(stringResource(label)) },
        modifier = modifier,
        singleLine = true,
        textStyle = typography.bodyLarge,
        interactionSource = interactionSource,
        readOnly = true,
        trailingIcon = {
            if(value.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = stringResource(id = R.string.clear),
                    modifier = Modifier
                        .clickable { onValueChange("") }
                        .testTag(tag)
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogCustom(
    openDialog: MutableState<Boolean>,
    onValueChange: (String) -> Unit
) {
    val snackScope = rememberCoroutineScope()
    val datePickerState = rememberDatePickerState()
    if(openDialog.value) {
        DatePickerDialog(
            onDismissRequest = { openDialog.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        snackScope.launch {
                            onValueChange(datePickerState.selectedDateMillis.toString())
                        }
                    }
                ) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { openDialog.value = false }
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
            )
        }
    }
}

@Composable
fun BottomBarCustom(
    modifier: Modifier = Modifier,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { },
            label = {
                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Text(
                        text = stringResource(R.string.back_button),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { },
            label = {
                Button(onClick = onNext) {
                    Text(text = stringResource(R.string.next))
                }
            }
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun DatePickerDialogCustomPreview() {
    DatePickerDialogCustom(mutableStateOf(true), {})
}