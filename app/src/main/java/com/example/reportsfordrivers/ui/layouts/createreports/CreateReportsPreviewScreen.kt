package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.reportsfordrivers.R

@Composable
fun CreateReportsPreviewScreen() {
    Column() {
        Text(
            text = stringResource(R.string.preview),
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center
            )
        )
        
        //DIVIDER

        LinePreviewText(R.string.date)
        LinePreviewText(R.string.last_name)
        LinePreviewText(R.string.first_name)
        LinePreviewText(R.string.make_vehicle)
        LinePreviewText(R.string.registration_number_vehicle)
        LinePreviewText(R.string.make_trailer)
        LinePreviewText(R.string.registration_number_trailer)

        //DIVIDER

        LinePreviewText(R.string.route)
        LinePreviewText(R.string.date_departure)
        LinePreviewText(R.string.date_return)
        LinePreviewText(R.string.date_border_crossing_departure)
        LinePreviewText(R.string.date_border_crossing_return)
        LinePreviewText(R.string.speedometer_reading_departure)
        LinePreviewText(R.string.speedometer_reading_return)
        LinePreviewText(R.string.fuelled)

        //DIVIDER

        Text(
            text = stringResource(R.string.progress_report),
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center
            )
        )
        //TABLE

        //DIVIDER

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = {
                Text(
                    text = stringResource(R.string.reports_name)
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

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
fun LinePreviewText(textName: Int) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(textName),
            modifier = Modifier.weight(1f),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(400)
            )
        )
        Text(
            text = "-",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(400)
            )
        )
        Text(
            text = stringResource(R.string.test),
            modifier = Modifier.weight(1f),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Right
            )
        )
    }
}

@Composable
fun ProgressReportItem() {
    Row() {
        Text(
            text = "1."
        )
        Column() {
            Text(
                text = "Date - 11.02.2024"
            )
            Text(
                text = "Country - Belarus"
            )
            Text(
                text = "Distance - 900km"
            )
            Text(
                text = "Township - Minsk"
            )
            Text(
                text = "Cargo weight - 19t"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressReportItemPreview() {
    ProgressReportItem()
}

@Preview(showBackground = true)
@Composable
fun LinePreviewTextPreview() {
    LinePreviewText(R.string.date)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateReportsPreviewScreenPreview() {
    CreateReportsPreviewScreen()
}