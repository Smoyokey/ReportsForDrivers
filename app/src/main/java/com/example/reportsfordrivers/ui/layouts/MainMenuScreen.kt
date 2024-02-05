package com.example.reportsfordrivers.ui.layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.reportsfordrivers.R


@Composable
fun MainMenuScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = {}) {
            Text(text = stringResource(R.string.create_report))
        }
        Button(onClick = {}) {
            Text(text = stringResource(R.string.be_continued))
        }
        Button(onClick = {}) {
            Text(text = stringResource(R.string.history_report))
        }
        Button(onClick = {}) {
            Text(text = stringResource(R.string.settings))
        }
    }
}
