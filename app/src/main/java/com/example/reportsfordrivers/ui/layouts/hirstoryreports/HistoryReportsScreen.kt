package com.example.reportsfordrivers.ui.layouts.hirstoryreports

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HistoryReportsScreen() {
    LazyColumn(
        content = {}
    )
}

@Composable
fun HistoryReportsCard(route: String, date: String) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = route
                )
                Text(
                    text = date
                )
            }
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryReportsCardPreview() {
    HistoryReportsCard("Minsk - Kazan", "11.02.2024")
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HistoryReportsScreenPreview() {
    HistoryReportsScreen()
}

