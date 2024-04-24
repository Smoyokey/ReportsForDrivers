package com.example.reportsfordrivers.viewmodel.createreports

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.data.dao.createReport.CreateReportInfoDao
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateReportInfoUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.SelectedNavigationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CreateReportInfoViewModel @Inject constructor(
    private val fioPreferencesRepository: FioFirstEntryRepository
) : ViewModel() {

    @Inject
    lateinit var createReportInfoDb: CreateReportInfoDao

    var uiStateCreateReportInfo = mutableStateOf(CreateReportInfoUiState())
    var uiStateIsValidate = mutableStateOf(SelectedNavigationUiState())

    var openDialogDateCreateReportInfo = mutableStateOf(false)

    fun startLoadCreateReportInfo() = runBlocking {
        val createReportInfo = createReportInfoDb.getAllItem().first()
        uiStateCreateReportInfo.value = uiStateCreateReportInfo.value.copy(
            date = createReportInfo[0].date.ifEmpty { "" },
            waybill = createReportInfo[0].waybill.ifEmpty { "" },
            mainCity = createReportInfo[0].mainCity.ifEmpty { "" }
        )
    }

    fun updateDataCreateReportInfoDate(date: String) {
        val parseDate = if (date.isNotEmpty()) parseDate(date) else date
        uiStateCreateReportInfo.value = uiStateCreateReportInfo.value.copy( date = parseDate )
    }

    fun updateDataCreateReportInfoMainCity(mainCity: String) {
        uiStateCreateReportInfo.value = uiStateCreateReportInfo.value.copy( mainCity = mainCity )
    }

    fun updateDataCreateReportInfoWaybill(waybill: String) {
        uiStateCreateReportInfo.value = uiStateCreateReportInfo.value.copy( waybill = waybill )
    }

    fun isValidateDateCreateReportInfo(): Boolean {
        return uiStateCreateReportInfo.value.date != "" &&
                uiStateCreateReportInfo.value.mainCity != "" &&
                uiStateCreateReportInfo.value.waybill != ""
    }

    @SuppressLint("SimpleDateFormat")
    private fun parseDate(date: String): String {
        val format = SimpleDateFormat("dd.MM.yyyy")
        return format.format(Date(date.toLong()))
    }
}