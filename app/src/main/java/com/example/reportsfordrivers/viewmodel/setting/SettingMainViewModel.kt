package com.example.reportsfordrivers.viewmodel.setting

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.data.dao.CurrencyDao
import com.example.reportsfordrivers.data.structure.Currency
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SettingMainViewModel @Inject constructor(
    private val fioPreferencesRepository: FioFirstEntryRepository
) : ViewModel() {

    @Inject
    lateinit var currencyDb: CurrencyDao

    var isOpenDialogLanguage = mutableStateOf(false)
    var isOpenBottomSheetCurrency = mutableStateOf(false)
    var isOpenDialogTheme = mutableStateOf(false)
    var isOpenDialogInformation = mutableStateOf(false)

    var listCurrency = mutableStateOf(listOf<Currency>())

    var selectedCurrency = mutableStateOf("")
    var selectedTheme = mutableStateOf("")
    var selectedLanguage = mutableStateOf("")

    fun openBottomSheetCurrency() = runBlocking {
        isOpenBottomSheetCurrency.value = true
        if (listCurrency.value.isEmpty()) {
            listCurrency.value = currencyDb.getAllItem()
        }
        selectedCurrency.value = fioPreferencesRepository.getDefaultCurrency().getOrDefault("")
    }

    fun onSaveCurrency() = runBlocking {
        isOpenBottomSheetCurrency.value = false
        fioPreferencesRepository.setDefaultCurrency(selectedCurrency.value)
        selectedCurrency.value = ""
    }

    fun onCancelCurrency() {
        isOpenBottomSheetCurrency.value = false
        selectedCurrency.value = ""
    }

    fun openDialogTheme() = runBlocking {
        isOpenDialogTheme.value = true
    }

    fun onSaveTheme() = runBlocking {
        isOpenDialogTheme.value = false
//        fioPreferencesRepository.
    }

    fun onCancelTheme() {
        isOpenDialogTheme.value = false
        selectedTheme.value = ""
    }
}