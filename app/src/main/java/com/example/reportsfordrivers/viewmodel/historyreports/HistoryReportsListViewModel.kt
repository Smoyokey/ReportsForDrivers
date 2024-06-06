package com.example.reportsfordrivers.viewmodel.historyreports

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.data.dao.MainInfoDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HistoryReportsListViewModel @Inject constructor(

) : ViewModel() {

    @Inject
    lateinit var mainInfoDb: MainInfoDao

    var uiState = mutableStateOf(HistoryReportsListUiState())
        private set

    var firstOpenHistoryReportsList = mutableStateOf(false)

    var selectedId = mutableStateOf(0)

    fun startLoadScreen() = runBlocking {
        loadMainInfo()
        firstOpenHistoryReportsList.value = true
    }

    private fun loadMainInfo() = runBlocking {
        val a = mainInfoDb.getAllItem().first()
        for(i in a) {
            uiState.value.historyReportsList.add(
                HistoryReportsListDetailingUiState(
                    id = i.id,
                    nameReport = i.nameReport,
                    dateCreate = i.dateCreate,
                    routeMainInfo = i.routeMainInfo
                )
            )
        }
    }
}