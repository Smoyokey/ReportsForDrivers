package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags

@Composable
fun CreateReportsProgressReportsScreen(
    onPreview: () -> Unit
) {
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

        Divider(
            modifier = Modifier.padding(10.dp)
        )

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

        OutlinedTextFieldProgressReport()

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
                onClick = onPreview
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
fun OutlinedTextFieldProgressReport() {
    Column() {
        Row() {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = stringResource(R.string.country)) },
                modifier = Modifier.weight(1f),
                singleLine = true,
                trailingIcon = {
                    if(true) {
                        Icon(
                            imageVector = Icons.Outlined.Clear,
                            contentDescription = stringResource(R.string.clear),
                            modifier = Modifier
                                .clickable {
//                                    onValueChange(itemDetails.copy(tt = ""))
                                }
                                .testTag(Tags.TAG_TEST_PROGRESS_REPORTS_COUNTRY)
                        )
                    }
                }
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = stringResource(R.string.township)) },
                modifier = Modifier.weight(1f),
                singleLine = true,
                trailingIcon = {
                    if(true) {
                        Icon(
                            imageVector = Icons.Outlined.Clear,
                            contentDescription = stringResource(R.string.clear),
                            modifier = Modifier
                                .clickable {
//                                    onValueChange(itemDetails.copy(tt = ""))
                                }
                                .testTag(Tags.TAG_TEST_PROGRESS_REPORTS_TOWNSHIP)
                        )
                    }
                }
            )
        }
        Row() {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = stringResource(R.string.distance)) },
                modifier = Modifier.weight(1f),
                singleLine = true,
                trailingIcon = {
                    if(true) {
                        Icon(
                            imageVector = Icons.Outlined.Clear,
                            contentDescription = stringResource(R.string.clear),
                            modifier = Modifier
                                .clickable {
//                                    onValueChange(itemDetails.copy(tt = ""))
                                }
                                .testTag(Tags.TAG_TEST_PROGRESS_REPORTS_DISTANCE)
                        )
                    }
                }
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = stringResource(R.string.cargo_weight)) },
                modifier = Modifier.weight(1f),
                singleLine = true,
                trailingIcon = {
                    if(true) {
                        Icon(
                            imageVector = Icons.Outlined.Clear,
                            contentDescription = stringResource(R.string.clear),
                            modifier = Modifier
                                .clickable {
//                                    onValueChange(itemDetails.copy(tt = ""))
                                }
                                .testTag(Tags.TAG_TEST_PROGRESS_REPORTS_CARGO_WEIGHT)
                        )
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LineProgressReportsPreview() {
    LineProgressReports()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateReportsProgressReportsScreenPreview() {
    CreateReportsProgressReportsScreen(onPreview = {})
}