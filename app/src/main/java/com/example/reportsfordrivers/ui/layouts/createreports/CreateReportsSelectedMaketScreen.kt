package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.ui.theme.typography
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportsViewModel

@Composable
fun CreateReportsSelectedMaketScreen(
    onFillingDataOne: () -> Unit,
    viewModel: CreateReportsViewModel = hiltViewModel()
) {
    Column {
        TabRow(selectedTabIndex = 0) {
            viewModel.tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = 0 == index,
                    onClick = { },
                    enabled = false
                )
            }
        }

        Column(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 16.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CardMaket(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
        }

        Column {
            Divider(modifier = Modifier.padding(start = 16.dp, end = 16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(end = 10.dp, bottom = 10.dp, top = 5.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        onFillingDataOne()
                    }
                ) {
                    Text(
                        text = stringResource(R.string.next),
                        style = typography.titleLarge
                    )
                }
            }
        }
    }
}

@Composable
fun CardMaket(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,

        ) {
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateReportsSelectedMaketScreenPreview() {
    CreateReportsSelectedMaketScreen(onFillingDataOne = {})
}