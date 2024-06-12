package com.example.reportsfordrivers.ui.layouts.setting

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.ui.layouts.setting.customsettingmain.BottomSheetCurrency
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.setting.SettingMainViewModel

@Composable
fun SettingMainScreen(
    onPersonalData: () -> Unit,
    onVehicleAndTrailerDate: () -> Unit,
    onCountriesCities: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingMainViewModel = hiltViewModel()
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp, bottom = 10.dp, end = 20.dp, start = 20.dp)
    ) {
        ButtonRowSetting(
            text = R.string.personal_data,
            icon = R.drawable.person_24px,
            onClick = onPersonalData
        )

        ButtonRowSetting(
            text = R.string.data_vehicles_trailers,
            icon = R.drawable.local_shipping_24px,
            onClick = onVehicleAndTrailerDate
        )

        ButtonRowSetting(
            text = R.string.countries_cities,
            icon = R.drawable.apartment_24px,
            onClick = onCountriesCities
        )

        ButtonRowSetting(
            text = R.string.report_language,
            icon = R.drawable.language_24px,
            onClick = {}
        )

        ButtonRowSetting(
            text = R.string.default_currency,
            icon = R.drawable.attach_money_24px,
            onClick = { viewModel.openBottomSheetCurrency() }
        )

        ButtonRowSetting(
            text = R.string.dark_theme,
            icon = R.drawable.palette_24px,
            onClick = {}
        )

        HorizontalDivider(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            thickness = 1.dp
        )

        ButtonRowSetting(
            text = R.string.information,
            icon = R.drawable.info_24px,
            onClick = {}
        )

        ButtonRowSetting(
            text = R.string.send_questions_suggestions,
            icon = R.drawable.mail_24px,
            onClick = {}
        )

        ButtonRowSetting(
            text = R.string.privacy_policy,
            icon = R.drawable.policy_24px,
            onClick = {}
        )
    }

    AlertDialogTheme(viewModel = viewModel)
    AlertDialogLanguage(viewModel = viewModel)
    AlertDialogInformation(viewModel = viewModel)
    BottomSheetCurrency(
        isOpen = viewModel.isOpenBottomSheetCurrency,
        listCurrency = viewModel.listCurrency.value,
        selectedCurrency = viewModel.selectedCurrency,
        onSave = viewModel::onSaveCurrency,
        onCancel = viewModel::onCancelCurrency
    )
}

@Composable
private fun ButtonRowSetting(
    @StringRes text: Int,
    @DrawableRes icon: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(top = 10.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),

        ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = stringResource(text)
        )
        Text(
            text = stringResource(text),
            modifier = Modifier.weight(1f),
            style = typography.bodyLarge
        )
        Icon(
            imageVector = Icons.Outlined.KeyboardArrowRight,
            contentDescription = stringResource(text)
        )
    }
}

@Composable
private fun AlertDialogLanguage(
    viewModel: SettingMainViewModel
) {

    if (viewModel.isOpenDialogLanguage.value) {
        AlertDialog(
            text = { Text(text = stringResource(R.string.report_language)) },
            title = {
                Column() {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = true,
                            onClick = {},
                            modifier = Modifier.semantics { contentDescription = "" }
                        )
                        Text(
                            text = stringResource(R.string.russian)
                        )
                    }
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = false,
                            onClick = {},
                            modifier = Modifier.semantics { contentDescription = "" }
                        )
                        Text(
                            text = stringResource(R.string.english)
                        )
                    }
                }
            },
            onDismissRequest = { viewModel.isOpenDialogLanguage.value = false },
            confirmButton = {
                Button(
                    onClick = {}
                ) {
                    Text(text = stringResource(R.string.save))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {}
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
        )
    }
}

@Composable
fun AlertDialogTheme(
    viewModel: SettingMainViewModel
) {
    if (viewModel.isOpenDialogTheme.value) {
        AlertDialog(
            title = { Text(text = stringResource(R.string.test)) },
            onDismissRequest = { viewModel.isOpenDialogTheme.value = false },
            confirmButton = {
                Button(
                    onClick = {}
                ) {
                    Text(text = stringResource(R.string.save))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {}
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            },
            text = {
                Column() {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = true,
                            onClick = { },
                            modifier = Modifier.semantics { contentDescription = "" }
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
                            selected = false,
                            onClick = { },
                            modifier = Modifier.semantics { contentDescription = "" }
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
                            selected = false,
                            onClick = {},
                            modifier = Modifier.semantics { contentDescription = "" }
                        )
                        Text(
                            text = stringResource(R.string.system_default)
                        )
                    }
                }
            },
        )
    }
}

@Composable
fun AlertDialogInformation(
    viewModel: SettingMainViewModel
) {
    if (viewModel.isOpenDialogInformation.value) {
        AlertDialog(
            title = { Text(text = stringResource(R.string.information)) },
            onDismissRequest = { viewModel.isOpenDialogInformation.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.isOpenDialogInformation.value = false
                    }
                ) {
                    Text(text = stringResource(R.string.ok))
                }
            },
            text = {
                Column() {
                    Text(
                        text = stringResource(R.string.app_name_version)
                    )
                    Text(text = "\n")
                    Text(
                        text = stringResource(R.string.text_information)
                    )
                }
            },
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingMainScreenPreview() {
    SettingMainScreen({}, {}, {})
}

