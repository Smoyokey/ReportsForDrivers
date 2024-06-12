package com.example.reportsfordrivers.ui.layouts.setting.customsettingmain

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.data.structure.Currency
import com.example.reportsfordrivers.ui.theme.typography
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetCurrency(
    isOpen: MutableState<Boolean> = mutableStateOf(false),
    listCurrency: List<Currency>,
    selectedCurrency: MutableState<String> = mutableStateOf(""),
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if(isOpen.value) {
        ModalBottomSheet(
            onDismissRequest = { isOpen.value = false },
            sheetState = sheetState,
            modifier = Modifier.fillMaxHeight(0.75f)
        ) {
            BottomSheetCurrencyColumn(
                modifier = Modifier.weight(1f)
                    .padding(20.dp),
                listCurrency = listCurrency,
                selectedCurrency = selectedCurrency,
                onSave = onSave,
                onCancel = onCancel
            )
        }
    }
}

@Composable
private fun BottomSheetCurrencyColumn(
    modifier: Modifier = Modifier,
    listCurrency: List<Currency>,
    selectedCurrency: MutableState<String> = mutableStateOf(""),
    onSave: () -> Unit = { },
    onCancel: () -> Unit = { },
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.select_currency),
                style = typography.headlineSmall,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = selectedCurrency.value,
                style = typography.headlineSmall
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(start = 12.dp, end = 12.dp),
            thickness = 1.4.dp
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(listCurrency.size) { element ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedCurrency.value = listCurrency[element].shortNameCurrency
                        }
                ) {
                    Text(
                        text = if (Locale.getDefault().language == "ru") {
                            listCurrency[element].fullNameCurrencyRus
                        } else {
                            listCurrency[element].fullNameCurrencyEng
                        },
                        modifier = Modifier.weight(1f),
                        style = typography.titleLarge
                    )
                    Text(
                        text = listCurrency[element].shortNameCurrency,
                        style = typography.titleLarge
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextButton(
                onClick = onCancel,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    style = typography.titleLarge
                )
            }
            Button(
                onClick = onSave,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.save),
                    style = typography.titleLarge
                )
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(showBackground = true)
fun BottomSheetCurrencyColumnPreview() {
    BottomSheetCurrencyColumn(
        listCurrency = resList(),
        selectedCurrency = mutableStateOf("BYN")
    )
}

fun resList(): List<Currency> {
    val a = listOf(
        Currency(
            fullNameCurrencyEng = "Dollar",
            fullNameCurrencyRus = "Доллар",
            shortNameCurrency = "USD"
        ),
        Currency(
            fullNameCurrencyEng = "Euro",
            fullNameCurrencyRus = "Евро",
            shortNameCurrency = "EUR"
        )
    )
    return a
}
