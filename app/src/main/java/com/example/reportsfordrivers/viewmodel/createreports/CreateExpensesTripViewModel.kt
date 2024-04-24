package com.example.reportsfordrivers.viewmodel.createreports

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.data.dao.CurrencyDao
import com.example.reportsfordrivers.data.dao.createReport.CreateExpensesTripDao
import com.example.reportsfordrivers.data.structure.Currency
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateExpensesTripDetailingUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateExpensesTripUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CreateExpensesTripViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var createExpensesTripDb: CreateExpensesTripDao

    @Inject
    lateinit var currencyDb: CurrencyDao

    var listCurrency = mutableStateOf(listOf<Currency>())

    var uiStateCreateExpensesTrip = mutableStateOf(CreateExpensesTripUiState())
        private set

    var uiStateCreateExpensesTripDetailing = mutableStateOf(CreateExpensesTripDetailingUiState())
        private set

    var openDialogDateCreateExpensesTrip = mutableStateOf(false)
    var openDialogDeleteCreateExpensesTrip = mutableStateOf(false)
    var openBottomSheetCurrencyCreateExpensesTrip = mutableStateOf(false)

    fun startLoadCreateExpensesTrip() = runBlocking {
        val createExpensesTrip = createExpensesTripDb.getAllItem().first()
        for(i in createExpensesTrip) {
            uiStateCreateExpensesTrip.value.createExpensesTripList.add(
                CreateExpensesTripDetailingUiState(
                    date = i.date,
                    documentNumber = i.documentNumber,
                    expenseItem = i.expenseItem,
                    sum = i.sum,
                    currency = i.currency
                )
            )
        }
    }

    fun updateCreateExpensesTripDate(date: String) {
        val parseDate = if(date.isNotEmpty()) parseDate(date) else date
        uiStateCreateExpensesTripDetailing.value = uiStateCreateExpensesTripDetailing.value.copy(
            date = parseDate
        )
    }

    fun updateCreateExpensesTripDocumentNumber(documentNumber: String) {
        uiStateCreateExpensesTripDetailing.value = uiStateCreateExpensesTripDetailing.value.copy(
            documentNumber = documentNumber
        )
    }

    fun updateCreateExpensesTripExpenseItem(expenseItem: String) {
        uiStateCreateExpensesTripDetailing.value = uiStateCreateExpensesTripDetailing.value.copy(
            expenseItem = expenseItem
        )
    }

    fun updateCreateExpensesTripSum(sum: String) {
        uiStateCreateExpensesTripDetailing.value = uiStateCreateExpensesTripDetailing.value.copy(
            sum = sum
        )
    }

    fun updateCreateExpensesTripCurrency(currency: String) {
        uiStateCreateExpensesTripDetailing.value = uiStateCreateExpensesTripDetailing.value.copy(
            currency = currency
        )
    }

    fun isValidateAddExpensesTrip(): Boolean {
        return uiStateCreateExpensesTripDetailing.value.date != "" &&
                uiStateCreateExpensesTripDetailing.value.documentNumber != "" &&
                uiStateCreateExpensesTripDetailing.value.expenseItem != "" &&
                uiStateCreateExpensesTripDetailing.value.sum != "" &&
                uiStateCreateExpensesTripDetailing.value.currency != ""
    }

    fun updateExpensesTrip() {
        uiStateCreateExpensesTrip.value.createExpensesTripList.add(uiStateCreateExpensesTripDetailing.value)
        uiStateCreateExpensesTripDetailing.value = CreateExpensesTripDetailingUiState()
    }

    fun deletePositionExpensesTrip(position: Int) {
        uiStateCreateExpensesTrip.value.createExpensesTripList.removeAt(position)
    }

    @SuppressLint("SimpleDateFormat")
    private fun parseDate(date: String): String {
        val format = SimpleDateFormat("dd.MM.yyyy")
        return format.format(Date(date.toLong()))
    }
}