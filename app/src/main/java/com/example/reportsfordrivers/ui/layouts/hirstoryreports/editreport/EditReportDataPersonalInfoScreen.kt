package com.example.reportsfordrivers.ui.layouts.hirstoryreports.editreport

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.ui.layouts.custom.BottomBarCustom
import com.example.reportsfordrivers.ui.layouts.custom.TabRowCustom
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.viewmodel.EditPersonalInfoViewModel

private const val TAG = "EditReportDataPersonalInfoScreen"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditReportDataPersonalInfoScreen(
    viewModel: EditPersonalInfoViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val (lastName, firstName, patronymic) = remember { FocusRequester.createRefs() }
    val keyboardController = LocalSoftwareKeyboardController.current

    DisposableEffect(Unit) {
        if (viewModel.uiStateEditPersonalInfo.value.lastName.isNotEmpty()) {
            lastName.requestFocus()
        }
        onDispose { }
    }

    BackHandler {
        navController.navigate(
            ReportsForDriversSchema.EditReportInfo.name,
            navOptions = NavOptions.Builder()
                .setPopUpTo(ReportsForDriversSchema.EditReportInfo.name, true)
                .build()
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TabRowCustom(
            index = 1,
            navController = navController,
            isEnabledTwo = false
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp, bottom = 16.dp, start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            OutlinedTextField(
                value = viewModel.uiStateEditPersonalInfo.value.lastName,
                label = { Text(stringResource(R.string.last_name)) },
                onValueChange = { viewModel.updateDataEditPersonalInfoLastName(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(lastName),
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if (viewModel.uiStateEditPersonalInfo.value.lastName.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.updateDataEditPersonalInfoLastName("") }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = stringResource(R.string.clear)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { firstName.requestFocus() } )
            )

            OutlinedTextField(
                value = viewModel.uiStateEditPersonalInfo.value.firstName,
                label = { Text(stringResource(R.string.first_name)) },
                onValueChange = { viewModel.updateDataEditPersonalInfoFirstName(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(firstName),
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if(viewModel.uiStateEditPersonalInfo.value.firstName.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.updateDataEditPersonalInfoFirstName("") }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = stringResource(R.string.clear)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions( onNext = { patronymic.requestFocus()} )
            )

            OutlinedTextField(
                value = viewModel.uiStateEditPersonalInfo.value.patronymic,
                label = { Text(stringResource(R.string.patronymic)) },
                onValueChange = { viewModel.updateDataEditPersonalInfoPatronymic(it) },
                modifier = Modifier.fillMaxWidth()
                    .focusRequester(patronymic),
                textStyle = typography.bodyLarge,
                trailingIcon = {
                    if (viewModel.uiStateEditPersonalInfo.value.patronymic.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.updateDataEditPersonalInfoPatronymic("") }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = stringResource(R.string.clear)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide()})
            )
        }

        BottomBarCustom(
            onNext = { navController.navigate(ReportsForDriversSchema.EditVehicleInfo.name) },
            onBack = { navController.navigateUp() },
            enabled = viewModel.isValidateDataEditPersonalInfo()
        )
    }
}