package com.example.reportsfordrivers.ui.layouts.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.reportsfordrivers.R

@Composable
fun SettingFeedbackScreen() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.feedback)
        )

        //Разделитель

        OutlinedTextFieldSettingFeedback(R.string.your_name)
        OutlinedTextFieldSettingFeedback(R.string.your_contact_email)

        //Divider

        OutlinedTextFieldSettingFeedback(R.string.text_appeal, Modifier.weight(1f))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {},
                ) {
                    Text(
                        text = stringResource(R.string.attach_file)
                    )
                }
            }
            Text(
                text = stringResource(R.string.attached_files)
            )
            LazyColumn(
                content = {}
            )
        }
    }
}

@Composable
fun AttachedFilesLine(number: String, name: String, size: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = number
        )
        Text(
            text = name,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = size
        )
        IconButton(
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Outlined.Clear,
                contentDescription = stringResource(R.string.clear)
            )
        }
    }
}

@Composable
fun OutlinedTextFieldSettingFeedback(label: Int, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        label = {
            Text(
                text = stringResource(label)
            )
        },
        modifier = modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun AttachedFilesLinePreview() {
    AttachedFilesLine("1.", "Test.doc", "235 bytes")
}

@Preview(showBackground = true)
@Composable
fun OutlinedTextFieldSettingFeedbackPreview() {
    OutlinedTextFieldSettingFeedback(R.string.your_name)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingFeedbackScreenPreview() {
    SettingFeedbackScreen()
}