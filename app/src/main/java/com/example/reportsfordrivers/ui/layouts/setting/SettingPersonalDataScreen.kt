package com.example.reportsfordrivers.ui.layouts.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags

/**
 * Класс отображения экрана, возможность изменения персональных данных
 * Что надо сделать:
 * 1. Добавить разделители
 */

@Composable
fun SettingPersonalDataScreen() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            text = stringResource(R.string.personal_data),
            style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth(),
        )
        Divider(
            modifier = Modifier.padding(10.dp)
        )

        SettingPersonalDataOutlinedTextFieldScreen()

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
                onClick = {},
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.save)
                )
            }
        }
    }
}

@Composable
fun SettingPersonalDataOutlinedTextFieldScreen() {
    Column() {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(text = stringResource(R.string.last_name)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if(true) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.clear),
                        modifier = Modifier
                            .clickable {
//                                onValueChane(itemDetails.copy(tt = ""))
                            }
                            .testTag(Tags.TAG_TEST_SETTING_PERSONAL_DATA_LAST_NAME)
                    )
                }
            }
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(text = stringResource(R.string.first_name)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if(true) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.clear),
                        modifier = Modifier
                            .clickable {
//                                onValueChane(itemDetails.copy(tt = ""))
                            }
                            .testTag(Tags.TAG_TEST_SETTING_PERSONAL_DATA_FIRST_NAME)
                    )
                }
            }
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(text = stringResource(R.string.patronymic)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if(true) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.clear),
                        modifier = Modifier
                            .clickable {
//                                onValueChane(itemDetails.copy(tt = ""))
                            }
                            .testTag(Tags.TAG_TEST_SETTING_PERSONAL_DATA_PATRONYMIC)
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingPersonalDataScreenPreview() {
    SettingPersonalDataScreen()
}