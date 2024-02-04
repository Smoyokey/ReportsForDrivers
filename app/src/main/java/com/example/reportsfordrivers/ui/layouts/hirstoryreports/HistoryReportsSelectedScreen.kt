package com.example.reportsfordrivers.ui.layouts.hirstoryreports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.reportsfordrivers.R

@Composable
fun HistoryReportsSelectedScreen() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.test)
        )
        Text(
            text = stringResource(R.string.test)
        )

        Divider(
            modifier = Modifier.padding(10.dp)
        )

        HistoryReportsButton(R.string.preview)
        HistoryReportsButton(R.string.edit)
        HistoryReportsButton(R.string.delete)
        HistoryReportsButton(R.string.share)
        HistoryReportsButton(R.string.save_pdf)
    }
}

@Composable
fun HistoryReportsButton(text: Int) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {}
    ) {
        Text(
            text = stringResource(text)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HistoryReportsSelectedScreenPreview() {
    HistoryReportsSelectedScreen()
}
