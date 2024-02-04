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
fun CreateReportsDataFillingTwoScreen() {
    Column() {
        //TOP APP BAR

        Text(
            text = stringResource(R.string.data_filling),
            style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight(400)
            ),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Divider(
            modifier = Modifier.padding(10.dp)
        )

        OutlinedTextFieldDataFillingTwo(R.string.route)

        LineDataFilling(R.string.date_departure)
        LineDataFilling(R.string.date_return)
        LineDataFilling(R.string.date_border_crossing_departure)
        LineDataFilling(R.string.date_border_crossing_return)

        OutlinedTextFieldDataFillingTwo(R.string.speedometer_reading_departure)
        OutlinedTextFieldDataFillingTwo(R.string.speedometer_reading_return)
        OutlinedTextFieldDataFillingTwo(R.string.fuelled)

        Column(
            modifier = Modifier.weight(1f)
        ) {}

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
fun OutlinedTextFieldDataFillingTwo(label: Int) {
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

@Composable
fun LineDataFilling(text: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(text),
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
}

@Preview(showBackground = true)
@Composable
fun LineDataFillingPreview() {
    LineDataFilling(R.string.date_departure)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateReportsDataFillingTwoScreenPreview() {
    CreateReportsDataFillingTwoScreen()
}
