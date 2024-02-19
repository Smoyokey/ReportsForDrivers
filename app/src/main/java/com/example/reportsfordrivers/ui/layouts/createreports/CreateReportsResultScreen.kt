package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.viewmodel.createreports.ResultViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun CreateReportsResultScreen(
    viewModel: ResultViewModel = hiltViewModel<ResultViewModel>()
) {
    val context = LocalContext.current
    Column() {
        Text(
            text = stringResource(R.string.result),
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center
            )
        )

        Divider(
            modifier = Modifier.padding(10.dp)
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    viewModel.testClick()
                }
            ) {
                Text (text = stringResource(R.string.test))
            }
            Button(
                onClick = {
                    viewModel.testShare(context)
                }
            ) {
                Text (text = stringResource(R.string.share))
            }
            ButtonReportsResult(R.string.share)
            ButtonReportsResult(R.string.save_pdf)
            ButtonReportsResult(R.string.create_new_report)
            ButtonReportsResult(R.string.cancel)
            ButtonReportsResult(R.string.delete)
        }
    }
}

@Composable
fun ButtonReportsResult(text: Int) {
    Button(
        onClick = {
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(text)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonReportsResultPreview() {
    ButtonReportsResult(R.string.share)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateReportsResultScreenPreview() {
    CreateReportsResultScreen()
}