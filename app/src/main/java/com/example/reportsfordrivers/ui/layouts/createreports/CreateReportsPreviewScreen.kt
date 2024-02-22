package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.ui.OutlinedTextFieldCustom
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportsViewModel

@Composable
fun CreateReportsPreviewScreen(
    onResult: () -> Unit,
    viewModel: CreateReportsViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
    ) {

        TabRow(selectedTabIndex = 5) {
            viewModel.tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = 5 == index,
                    onClick = {  },
                    enabled = false
                )
            }
        }

        LinePreviewText(R.string.date, viewModel.uiState.value.dataFillingOne.date)
        LinePreviewText(R.string.last_name, viewModel.uiState.value.dataFillingOne.lastName)
        LinePreviewText(R.string.first_name, viewModel.uiState.value.dataFillingOne.firstName)
        LinePreviewText(R.string.patronymic, viewModel.uiState.value.dataFillingOne.patronymic)
        LinePreviewText(R.string.make_vehicle, viewModel.uiState.value.dataFillingOne.makeVehicle)
        LinePreviewText(R.string.registration_number_vehicle,
            viewModel.uiState.value.dataFillingOne.rnVehicle)
        LinePreviewText(R.string.make_trailer, viewModel.uiState.value.dataFillingOne.makeTrailer)
        LinePreviewText(R.string.registration_number_trailer,
            viewModel.uiState.value.dataFillingOne.rnTrailer)

        Divider(
            modifier = Modifier.padding(10.dp)
        )

        LinePreviewText(R.string.route, viewModel.uiState.value.dataFillingTwo.route)
        LinePreviewText(R.string.date_departure, viewModel.uiState.value.dataFillingTwo.dateDeparture)
        LinePreviewText(R.string.date_return, viewModel.uiState.value.dataFillingTwo.dateReturn)
        LinePreviewText(R.string.date_border_crossing_departure,
            viewModel.uiState.value.dataFillingTwo.dateCrossingDeparture)
        LinePreviewText(R.string.date_border_crossing_return,
            viewModel.uiState.value.dataFillingTwo.dateCrossingReturn)
        LinePreviewText(R.string.speedometer_reading_departure, viewModel.uiState.value.dataFillingTwo.speedometerDeparture)
        LinePreviewText(R.string.speedometer_reading_return, viewModel.uiState.value.dataFillingTwo.speedometerReturn)
        LinePreviewText(R.string.fuelled, viewModel.uiState.value.dataFillingTwo.fuelled)

        Divider(
            modifier = Modifier.padding(10.dp)
        )

        Text(
            text = stringResource(R.string.progress_report),
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center
            )
        )
        //TABLE 

        Divider(
            modifier = Modifier.padding(10.dp)
        )

        OutlinedTextFieldCustom(
            label = R.string.reports_name,
            value = viewModel.uiState.value.reportName,
            onValueChange = viewModel::updatePreviewReportName,
            tag = Tags.TAG_TEST_PREVIEW_REPORT_NAME,
            modifier = Modifier
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = onResult
            ) {
                Text(
                    text = stringResource(R.string.next)
                )
            }
        }
    }

}

@Composable
fun LinePreviewText(textName: Int, previewText: String) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(textName),
            modifier = Modifier.weight(1f),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(400)
            )
        )
        Text(
            text = "-",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(400)
            )
        )
        Text(
            text = previewText,
            modifier = Modifier.weight(1f),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Right
            )
        )
    }
}

@Composable
fun ProgressReportItem() {
    Row() {
        Text(
            text = "1."
        )
        Column() {
            Text(
                text = "Date - 11.02.2024"
            )
            Text(
                text = "Country - Belarus"
            )
            Text(
                text = "Distance - 900km"
            )
            Text(
                text = "Township - Minsk"
            )
            Text(
                text = "Cargo weight - 19t"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressReportItemPreview() {
    ProgressReportItem()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateReportsPreviewScreenPreview() {
    CreateReportsPreviewScreen(onResult = {})
}