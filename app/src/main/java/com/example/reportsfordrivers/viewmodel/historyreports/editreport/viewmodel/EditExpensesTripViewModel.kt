package com.example.reportsfordrivers.viewmodel.historyreports.editreport.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.data.dao.CurrencyDao
import com.example.reportsfordrivers.data.dao.TripExpensesDao
import com.example.reportsfordrivers.data.dao.createreport.CreateExpensesTripDao
import com.example.reportsfordrivers.data.dao.editreport.EditExpensesTripDao
import com.example.reportsfordrivers.data.structure.Currency
import com.example.reportsfordrivers.data.structure.editreport.EditExpensesTrip
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateExpensesTripDetailingUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateExpensesTripUiState
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditExpensesTripDetailingUiState
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditExpensesTripUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class EditExpensesTripViewModel @Inject constructor(
    private val fioPreferencesRepository: FioFirstEntryRepository
) : ViewModel() {

    @Inject
    lateinit var editExpensesTripDb: EditExpensesTripDao

    @Inject
    lateinit var tripExpensesDb: TripExpensesDao

    @Inject
    lateinit var currencyDb: CurrencyDao

    var listCurrency = mutableStateOf(listOf<Currency>())

    var uiStateEditExpensesTrip = mutableStateOf(EditExpensesTripUiState())
        private set

    var uiStateEditExpensesTripDetailing = mutableStateOf(EditExpensesTripDetailingUiState())

    var firstOpenReportExpensesTrip = mutableStateOf(false)

    var openDialogDateEditExpensesTrip = mutableStateOf(false)
    var openDialogDeleteEditExpensesTrip = mutableStateOf(false)
    var openBottomSheetCurrencyEditExpensesTrip = mutableStateOf(false)

    fun startLoadEditExpensesTrip() = runBlocking {
        val editExpensesTrip = editExpensesTripDb.getAllItem().first()
        for(i in editExpensesTrip) {
            if(i.isAdd == 1) {
                uiStateEditExpensesTrip.value.editExpensesTripList.add(
                    EditExpensesTripDetailingUiState(
                        id = i.id,
                        date = i.date,
                        documentNumber = i.documentNumber,
                        expenseItem = i.expenseItem,
                        sum = i.sum,
                        currency = i.currency
                    )
                )
            } else {
                uiStateEditExpensesTripDetailing.value =
                    uiStateEditExpensesTripDetailing.value.copy(
                        id = i.id,
                        date = i.date,
                        documentNumber = i.documentNumber,
                        expenseItem = i.expenseItem,
                        sum = i.sum,
                        currency = i.currency
                    )
            }
        }
        fioPreferencesRepository.setEditSelectedPage(6)
        if(!firstOpenReportExpensesTrip.value) {
            uiStateEditExpensesTripDetailing.value = uiStateEditExpensesTripDetailing.value.copy(
                currency = fioPreferencesRepository.getDefaultCurrency().getOrDefault("")
            )
        }
        openBottomSheetCurrency()
        firstOpenReportExpensesTrip.value = true
    }

    fun updateEditExpensesTripDate(date: String) {
        val parseDate = if(date.isNotEmpty()) parseDate(date) else date
        uiStateEditExpensesTripDetailing.value = uiStateEditExpensesTripDetailing.value.copy(
            date = parseDate
        )
        runBlocking {
            editExpensesTripDb.updateOneElementForIdDate(
                id = uiStateEditExpensesTripDetailing.value.id,
                date = uiStateEditExpensesTripDetailing.value.date
            )
        }
    }

    fun updateEditExpensesTripDocumentNumber(documentNumber: String) {
        uiStateEditExpensesTripDetailing.value = uiStateEditExpensesTripDetailing.value.copy(
            documentNumber = documentNumber
        )
        runBlocking {
            editExpensesTripDb.updateOneElementForIdDocumentNumber(
                id = uiStateEditExpensesTripDetailing.value.id,
                documentNumber = uiStateEditExpensesTripDetailing.value.documentNumber
            )
        }
    }

    fun updateEditExpensesTripExpenseItem(expenseItem: String) {
        uiStateEditExpensesTripDetailing.value = uiStateEditExpensesTripDetailing.value.copy(
            expenseItem = expenseItem
        )
        runBlocking {
            editExpensesTripDb.updateOneElementForIdExpenseItem(
                id = uiStateEditExpensesTripDetailing.value.id,
                expenseItem = uiStateEditExpensesTripDetailing.value.expenseItem
            )
        }
    }

    fun updateEditExpensesTripSum(sum: String) {
        uiStateEditExpensesTripDetailing.value = uiStateEditExpensesTripDetailing.value.copy(
            sum = sum
        )
        runBlocking {
            editExpensesTripDb.updateOneElementForIdSum(
                id = uiStateEditExpensesTripDetailing.value.id,
                sum = uiStateEditExpensesTripDetailing.value.sum
            )
        }
    }

    fun updateEditExpensesTripCurrency(currency: String) {
        uiStateEditExpensesTripDetailing.value = uiStateEditExpensesTripDetailing.value.copy(
            currency = currency
        )
        runBlocking {
            editExpensesTripDb.updateOneElementForIdCurrency(
                id = uiStateEditExpensesTripDetailing.value.id,
                currency = uiStateEditExpensesTripDetailing.value.currency
            )
        }
    }

    fun isValidateAddExpensesTrip(): Boolean {
        return uiStateEditExpensesTripDetailing.value.date != "" &&
                uiStateEditExpensesTripDetailing.value.documentNumber != "" &&
                uiStateEditExpensesTripDetailing.value.expenseItem != "" &&
                uiStateEditExpensesTripDetailing.value.sum != "" &&
                uiStateEditExpensesTripDetailing.value.currency != ""
    }

    fun updateExpensesTrip() {
        uiStateEditExpensesTripDetailing.value =
            uiStateEditExpensesTripDetailing.value.copy(isAdd = 1)
        runBlocking {
            editExpensesTripDb.updateOneElementForIdIsAdd(
                id = uiStateEditExpensesTripDetailing.value.id,
                isAdd = 1
            )
        }
        uiStateEditExpensesTrip.value.editExpensesTripList.add(uiStateEditExpensesTripDetailing.value)
        val id = uiStateEditExpensesTripDetailing.value.id + 1
        uiStateEditExpensesTripDetailing.value = EditExpensesTripDetailingUiState()
        runBlocking {
            editExpensesTripDb.insert(
                EditExpensesTrip(
                    date = "",
                    documentNumber = "",
                    expenseItem = "",
                    sum = "",
                    currency = "",
                    isAdd = 0
                )
            )
        }
        uiStateEditExpensesTripDetailing.value = uiStateEditExpensesTripDetailing.value.copy(
            id = id
        )
    }

    fun deletePositionExpensesTrip(position: Int) = runBlocking {
        editExpensesTripDb.deleteOneElementForId(
            uiStateEditExpensesTrip.value.editExpensesTripList[position].id
        )
        uiStateEditExpensesTrip.value.editExpensesTripList.removeAt(position)
    }

    @SuppressLint("SimpleDateFormat")
    private fun parseDate(date: String): String {
        val format = SimpleDateFormat("dd.MM.yyyy")
        return format.format(Date(date.toLong()))
    }

    private fun openBottomSheetCurrency() = runBlocking {
        if(listCurrency.value.isEmpty()) {
            listCurrency.value = currencyDb.getAllItem()
        }
    }
}