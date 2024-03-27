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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
fun SettingMainScreen(onPersonalData: () -> Unit,
                      onVehicleAndTrailerDate: () -> Unit,
                      onCountriesCities: () -> Unit,
                      onFeedback: () -> Unit,
                      modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .padding(10.dp)
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
            text = R.string.feedback,
            icon = R.drawable.mail_24px,
            onClick = onFeedback
        )
    }
}

@Composable
fun ButtonRowSetting(
    @StringRes text: Int,
    @DrawableRes icon: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = stringResource(text)
        )
        Text(
            text = stringResource(text)
        )
        Icon(
            imageVector = Icons.Outlined.KeyboardArrowRight,
            contentDescription = stringResource(text)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingMainScreenPreview() {
    SettingMainScreen({}, {}, {}, {})
}