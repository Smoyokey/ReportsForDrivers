package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reportsfordrivers.R

@Composable
fun CreateReportsDataFillingOneScreen() {
    Column() {
        //Верхнее меню

        Text(
            text = stringResource(R.string.data_filling),
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight(400),
            ),
            textAlign = TextAlign.Center
        )

        Divider(
            modifier = Modifier.padding(10.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.date),
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {}
            ) {
                Text(
                    text = stringResource(R.string.date)
                )
            }
        }

        OutlinedTextFieldDataFilling(R.string.last_name)
        OutlinedTextFieldDataFilling(R.string.first_name)
        OutlinedTextFieldDataFilling(R.string.patronymic)

        Divider(
            modifier = Modifier.padding(10.dp)
        )

        OutlinedTextFieldDataFilling(R.string.make_vehicle)
        OutlinedTextFieldDataFilling(R.string.registration_number_vehicle)
        OutlinedTextFieldDataFilling(R.string.make_trailer)
        OutlinedTextFieldDataFilling(R.string.registration_number_trailer)

        Column(
            modifier = Modifier.weight(1f)
        ) {
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {}
            ) {
                Text(
                    text = stringResource(R.string.next)
                )
            }
        }
    }
}

@Composable
fun OutlinedTextFieldDataFilling(label: Int) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        label = {
            Text(
                text = stringResource(label)
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun OutlinedTextFieldDataFillingPreview() {
    Column() {
        OutlinedTextFieldDataFilling(R.string.last_name)
        OutlinedTextFieldDataFilling(R.string.first_name)
        OutlinedTextFieldDataFilling(R.string.patronymic)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateReportsDataFillingOneScreenPreview() {
    CreateReportsDataFillingOneScreen()
}