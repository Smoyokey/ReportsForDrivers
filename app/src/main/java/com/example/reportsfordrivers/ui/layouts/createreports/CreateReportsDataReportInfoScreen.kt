package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportsViewModel

@Composable
fun CreateReportsDataReportInfoScreen(
    onPersonalInfo: () -> Unit,
    viewModel: CreateReportsViewModel = hiltViewModel()
) {
    val items = listOf("Back", "Next")

    Column(modifier = Modifier.fillMaxSize()) {



        Column(modifier = Modifier.weight(1f)) {}

        NavigationBar {
            NavigationBarItem(
                selected = false,
                onClick = { /*TODO*/ },
                icon = {  },
                label = { Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) { Text(
                    text = stringResource(R.string.back_button),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )}}
            )
            NavigationBarItem(
                selected = false,
                onClick = { /*TODO*/ },
                icon = {  },
                label = { Button(
                    onClick = {},
                ) { Text(text = stringResource(R.string.next))} }
            )
        }
    }

}