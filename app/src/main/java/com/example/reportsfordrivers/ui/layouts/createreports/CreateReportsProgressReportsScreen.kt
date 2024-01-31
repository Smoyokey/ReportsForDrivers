package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.sp
import com.example.reportsfordrivers.R

@Composable
fun CreateReportsProgressReportsScreen() {
    Column {
        Text(
            text = stringResource(R.string.progress_report),
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth()
        )

        //DIVIDER

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.date_departure),
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

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextFieldProgressReports(R.string.country, modifier = Modifier.weight(1f))
            OutlinedTextFieldProgressReports(R.string.township, modifier = Modifier.weight(1f))
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextFieldProgressReports(R.string.distance, modifier = Modifier.weight(1f))
            OutlinedTextFieldProgressReports(R.string.cargo_weight, modifier = Modifier.weight(1f))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {}
            ) {
                Text(
                    text = stringResource(R.string.add)
                )
            }
        }

        //DIVIDER

        Column(
            modifier = Modifier.weight(1f)
        ) {
            LazyColumn(
                content = {},
                modifier = Modifier.fillMaxWidth()
            )
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
fun LineProgressReports() {
    Row() {
        Text(
            text = "1."
        )
        Column() {
            Text(
                text = "Date - TEST"
            )
            Text(
                text = "Country - TEST"
            )
            Text(
                text = "Township - TEST"
            )
            Text(
                text = "Distance - TEST"
            )
            Text(
                text = "Weight - TEST"
            )
        }
    }
}

@Composable
fun OutlinedTextFieldProgressReports(label: Int, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        label = {
            Text(
                text = stringResource(label)
            )
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun LineProgressReportsPreview() {
    LineProgressReports()
}

@Preview(showBackground = true)
@Composable
fun OutlinedTextFieldProgressReportsPreview() {
    OutlinedTextFieldProgressReports(R.string.country)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateReportsProgressReportsScreenPreview() {
    CreateReportsProgressReportsScreen()
}