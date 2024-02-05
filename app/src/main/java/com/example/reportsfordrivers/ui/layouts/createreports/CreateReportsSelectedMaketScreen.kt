package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.reportsfordrivers.R

@Composable
fun CreateReportsSelectedMaketScreen(
    onFillingDataOne: () -> Unit
) {
    Column() {
        //Тут верхняя навигация

        Divider(
            modifier = Modifier.padding(10.dp)
        )

        CardMaket(
            modifier = Modifier.weight(1f)
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = onFillingDataOne
            ) {
                Text(
                    text = stringResource(R.string.next)
                )
            }
        }
    }

}

@Composable
fun CardMaket(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,

    ) {
        Text(
            text = stringResource(R.string.test),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateReportsSelectedMaketScreenPreview() {
    CreateReportsSelectedMaketScreen( onFillingDataOne = {} )
}