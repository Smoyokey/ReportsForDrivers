package com.example.reportsfordrivers.ui.layouts.hirstoryreports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
fun HistoryReportsEmptyScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        EmptyCard()
    }
}

@Composable
fun EmptyCard() {
    Card(
        modifier = Modifier.width(200.dp)
    ) {
        Column() {
            Text(
                text = stringResource(R.string.empty_create_first_report),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight(400),
                    textAlign = TextAlign.Center
                )
            )
            Button(
                onClick = { }
            ) {
                Text(
                    text = stringResource(R.string.create)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyCardPreview() {
    EmptyCard()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HistoryReportsEmptyScreenPreview() {
    HistoryReportsEmptyScreen()
}