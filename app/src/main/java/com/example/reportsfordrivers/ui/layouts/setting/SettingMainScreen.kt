package com.example.reportsfordrivers.ui.layouts.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reportsfordrivers.R

@Composable
fun SettingMainScreen(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .padding(10.dp)
    ) {
        ButtonSetting(R.string.personal_data)
        ButtonSetting(R.string.data_vehicles_trailers)
        ButtonSetting(R.string.feedback)
    }
}

@Composable
fun ButtonSetting(text: Int, modifier: Modifier = Modifier) {
    Button(
        onClick = { },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(text),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingMainScreenPreview() {
    SettingMainScreen()
}