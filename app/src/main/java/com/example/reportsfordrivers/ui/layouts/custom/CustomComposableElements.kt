package com.example.reportsfordrivers.ui.layouts.custom

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.ObjectVehicle
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateObjectVehicleOrTrailer
import kotlinx.coroutines.launch
import java.util.Date

@Composable
fun OutlinedTextFieldCustomSearch(
    @StringRes label: Int,
    value: String,
    onValueChange: (String) -> Unit,
    tag: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    isOpenSearch: MutableState<Boolean>,
    isOneAndTwoState: MutableState<Int> = mutableIntStateOf(0),
    isOneAndTwo: Int = 0
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(stringResource(label)) },
        modifier = modifier,
        singleLine = true,
        textStyle = typography.bodyLarge,
        keyboardOptions = keyboardOptions,
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(
                    onClick = { onValueChange("") }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = null
                    )
                }
            } else {
                IconButton(
                    onClick = {
                        isOpenSearch.value = true
                        isOneAndTwoState.value = isOneAndTwo
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OutlinedTextFieldCustom(
    @StringRes label: Int,
    value: String,
    onValueChange: (String) -> Unit,
    tag: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    isError: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(stringResource(label)) },
        modifier = modifier,
//        singleLine = true,
        textStyle = typography.bodyLarge,
        keyboardOptions = keyboardOptions,
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(
                    onClick = { onValueChange("") },
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.clear)
                    )
                }
            }
        },
        isError = isError
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
    if (openDialog.value) {
        DatePickerDialog(
            onDismissRequest = { openDialog.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        if (datePickerState.selectedDateMillis != null) {
                            snackScope.launch {
                                onValueChange(datePickerState.selectedDateMillis.toString())
                            }
                        } else {
                            onValueChange(Date().time.toString())
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
    onBack: () -> Unit,
    enabled: Boolean = true
) {
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = onBack,
            icon = {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = stringResource(R.string.back_button)
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.back_button),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            modifier = modifier
        )
        NavigationBarItem(
            selected = false,
            onClick = onNext,
            icon = {
                Icon(
                    imageVector = Icons.Outlined.ArrowForward,
                    contentDescription = stringResource(R.string.next)
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.next),
                )
            },
        )
    }
}

@Composable
fun AlertDialogDeleteElement(
    isOpen: MutableState<Boolean>,
    delete: (Int) -> Unit,
    position: Int
) {
    if (isOpen.value) {
        AlertDialog(
            onDismissRequest = { isOpen.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        delete(position)
                        isOpen.value = false
                    }
                ) {
                    Text(text = stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { isOpen.value = false }
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
        )
    }
}

@Composable
fun RowProgressAndExpenses(@StringRes title: Int, text: String) {
    Row(modifier = Modifier.fillMaxWidth(1f)) {
        Text(
            text = stringResource(title),
            modifier = Modifier.weight(1f),
            style = typography.bodyMedium
        )
        Text(
            text = text,
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.End,
            style = typography.bodyMedium
        )
    }
}

@Composable
fun RowVehicleAndTrailerElement(
    objectVehicle: ObjectVehicle = ObjectVehicle(),
    isOpenDialog: MutableState<Boolean> = mutableStateOf(false),
    onDelete: (ObjectVehicle) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = objectVehicle.make,
            modifier = Modifier.weight(1f),
            style = typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = objectVehicle.rn,
            modifier = Modifier.weight(1f),
            style = typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        IconButton(
            onClick = { isOpenDialog.value = true }
        ) {
            Icon(
                imageVector = Icons.Outlined.Clear,
                contentDescription = stringResource(R.string.clear)
            )
        }
    }
    AlertDialogDeleteElementVehicle(
        isOpenDialog = isOpenDialog,
        onDelete = onDelete,
        objectVehicle = objectVehicle
    )
}

@Composable
fun RowDate(
    @StringRes label: Int,
    openDialog: MutableState<Boolean>,
    date: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(label),
            modifier = Modifier.weight(1f),
            style = typography.titleLarge
        )
        TextButton(
            onClick = { openDialog.value = true }
        ) {
            Text(
                text = date.ifEmpty { stringResource(R.string.select_date) }
            )
        }
    }
}

@Composable
fun AlertDialogDeleteElementVehicle(
    isOpenDialog: MutableState<Boolean>,
    onDelete: (ObjectVehicle) -> Unit = {},
    objectVehicle: ObjectVehicle

) {
    if (isOpenDialog.value) {
        AlertDialog(
            text = { Text(text = stringResource(R.string.are_you_sure)) },
            title = { Text(text = stringResource(R.string.deletion)) },
            onDismissRequest = { isOpenDialog.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete(objectVehicle)
                        isOpenDialog.value = false
                    }
                ) {
                    Text(text = stringResource(R.string.yes))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { isOpenDialog.value = false }
                ) {
                    Text(text = stringResource(R.string.no))
                }
            }
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun AlertDialogAddVehicle(
    isOpenDialog: MutableState<Boolean>,
    @StringRes title: Int,
    @StringRes headText: Int,
    @StringRes labelMake: Int,
    @StringRes labelRn: Int,
    saveInDB: (CreateObjectVehicleOrTrailer) -> Unit,
    type: String
) {
    val uiStateVehicleObject = remember { mutableStateOf(ObjectVehicle(type = type)) }

    AlertDialog(
        onDismissRequest = { isOpenDialog.value = false },
        confirmButton = {
            TextButton(
                onClick = {
                    saveInDB(
                        CreateObjectVehicleOrTrailer(
                            type = uiStateVehicleObject.value.type,
                            make = uiStateVehicleObject.value.make,
                            rn = uiStateVehicleObject.value.rn
                        )
                    )
                    isOpenDialog.value = false
                }
            ) {
                Text(text = stringResource(R.string.save))
            }
        },
        dismissButton = {
            TextButton(
                onClick = { isOpenDialog.value = false }
            ) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        title = {
            Text(text = stringResource(title))
        },
        text = {
            Column {
                Text(text = stringResource(headText))
                OutlinedTextFieldCustom(
                    label = labelMake,
                    value = uiStateVehicleObject.value.make,
                    onValueChange = { text ->
                        uiStateVehicleObject.value = uiStateVehicleObject.value.copy(make = text)
                    },
                    tag = ""
                )
                OutlinedTextFieldCustom(
                    label = labelRn,
                    value = uiStateVehicleObject.value.rn,
                    onValueChange = { text ->
                        uiStateVehicleObject.value = uiStateVehicleObject.value.copy(rn = text)
                    },
                    tag = ""
                )
            }
        }
    )
}

@Composable
fun OutlinedTextFieldDateCustom(
    openDialog: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    date: String,
    isError: Boolean = false
) {
    OutlinedTextField(
        label = { Text(text = stringResource(R.string.date)) },
        value = date,
        onValueChange = {},
        modifier = modifier
            .clickable { openDialog.value = true },
        readOnly = true,
        enabled = false,
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Sharp.DateRange,
                contentDescription = stringResource(R.string.calendar),
                modifier = Modifier.clickable { openDialog.value = true }
            )
        },
        isError = isError
    )
}

@Composable
fun RowDateWithTextField(
    openDialog: MutableState<Boolean>,
    date: String,
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    isError: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(text),
            modifier = Modifier.weight(1f),
            style = typography.bodyLarge
        )
        OutlinedTextFieldDateCustom(
            openDialog = openDialog,
            date = date,
            modifier = modifier,
            isError = isError
        )
    }
}

@Composable
fun TabRowCustom(
    index: Int,
    navController: NavHostController,
    isEnabledOne: Boolean = true,
    isEnabledTwo: Boolean = true,
    isEnabledThree: Boolean = true,
    isEnabledFour: Boolean = true,
    isEnabledFive: Boolean = true,
    isEnabledSix: Boolean = true
) {
    TabRow(selectedTabIndex = index) {
        Tab(
            text = { Text("1") },
            selected = index == 0,
            onClick = {
                if (index != 0)
                    navController.navigate(ReportsForDriversSchema.ReportInfo.name)
            },
            enabled = isEnabledOne
        )
        Tab(
            text = { Text("2") },
            selected = index == 1,
            onClick = {
                if (index != 1)
                    navController.navigate(ReportsForDriversSchema.PersonalInfo.name)
            },
            enabled = isEnabledTwo
        )
        Tab(
            text = { Text("3") },
            selected = index == 2,
            onClick = {
                if (index != 2)
                    navController.navigate(ReportsForDriversSchema.VehicleInfo.name)
            },
            enabled = isEnabledThree
        )
        Tab(
            text = { Text("4") },
            selected = index == 3,
            onClick = {
                if (index != 3)
                    navController.navigate(ReportsForDriversSchema.Route.name)
            },
            enabled = isEnabledFour
        )
        Tab(
            text = { Text("5") },
            selected = index == 4,
            onClick = {
                if (index != 4)
                    navController.navigate(ReportsForDriversSchema.ProgressReport.name)
            },
            enabled = isEnabledFive
        )
        Tab(
            text = { Text("6") },
            selected = index == 5,
            onClick = {
                if (index != 5)
                    navController.navigate(ReportsForDriversSchema.TripExpenses.name)
            },
            enabled = isEnabledSix
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OutlinedTextFieldCustomPreview() {
    Column {
        OutlinedTextFieldCustom(
            label = R.string.last_name,
            value = "",
            onValueChange = {},
            tag = ""
        )
        OutlinedTextFieldCustom(
            label = R.string.first_name,
            value = "Ivan",
            onValueChange = {},
            tag = ""
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun DatePickerDialogCustomPreview() {
    DatePickerDialogCustom(mutableStateOf(true)) {}
}

@Preview(showBackground = true)
@Composable
fun BottomBarCustomPreview() {
    BottomBarCustom(
        onNext = {},
        onBack = {}
    )
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun AlertDialogDeleteElementsPreview() {
    AlertDialogDeleteElement(
        isOpen = mutableStateOf(true),
        delete = {},
        position = 1
    )
}

@Preview(showBackground = true)
@Composable
fun RowProgressAndExpensesPreview() {
    RowProgressAndExpenses(
        title = R.string.date,
        text = "10.02.2024"
    )
}

@Preview(showBackground = true)
@Composable
fun RowVehicleAndTrailerElementPreview() {
    RowVehicleAndTrailerElement(
        objectVehicle = ObjectVehicle(
            type = "Trailer",
            make = "Schmitz",
            rn = "P123EE-7"
        ),
        onDelete = {}
    )
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun RowDatePreview() {
    RowDate(
        label = R.string.date_return,
        openDialog = mutableStateOf(false),
        date = "10.02.2024"
    )
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun AlertDialogDeleteElementVehiclePreview() {
    AlertDialogDeleteElementVehicle(
        isOpenDialog = mutableStateOf(true),
        onDelete = {},
        objectVehicle = ObjectVehicle(
            type = "Vehicle",
            make = "DAF",
            rn = "AE1234-7"
        )
    )
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun AlertDialogAddVehiclePreview() {
    AlertDialogAddVehicle(
        isOpenDialog = mutableStateOf(true),
        title = R.string.test,
        headText = R.string.test,
        labelMake = R.string.make,
        labelRn = R.string.rn,
        saveInDB = {},
        type = "VEHICLE"
    )
}

