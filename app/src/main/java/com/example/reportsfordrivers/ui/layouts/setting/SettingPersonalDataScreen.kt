package com.example.reportsfordrivers.ui.layouts.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.ui.layouts.custom.OutlinedTextFieldCustom
import com.example.reportsfordrivers.viewmodel.setting.PersonalDataViewModel

/**
 * Класс отображения экрана, возможность изменения персональных данных
 * Что надо сделать:
 * 1. Добавить разделители
 */

@Composable
fun SettingPersonalDataScreen(
    viewModel: PersonalDataViewModel = hiltViewModel()
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        OutlinedTextFieldCustom(
            label = R.string.last_name,
            value = viewModel.uiState.value.lastName,
            onValueChange = viewModel::updateLastName,
            tag = Tags.TAG_TEST_DATA_PERSONAL_INFO_LAST_NAME,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextFieldCustom(
            label = R.string.first_name,
            value = viewModel.uiState.value.firstName,
            onValueChange = viewModel::updateFirstName,
            tag = Tags.TAG_TEST_DATA_PERSONAL_INFO_FIRST_NAME,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextFieldCustom(
            label = R.string.patronymic,
            value = viewModel.uiState.value.patronymic,
            onValueChange = viewModel::updatePatronymic,
            tag = Tags.TAG_TEST_DATA_PERSONAL_INFO_PATRONYMIC,
            modifier = Modifier.fillMaxWidth()
        )

        Divider(
            modifier = Modifier.padding(10.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {},
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.cancel)
                )
            }
            Button(
                onClick = {
                    viewModel.saveButton()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.save)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingPersonalDataScreenPreview() {
    SettingPersonalDataScreen()
}