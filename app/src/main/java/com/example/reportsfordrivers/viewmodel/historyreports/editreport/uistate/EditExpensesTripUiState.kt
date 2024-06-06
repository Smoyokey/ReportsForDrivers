package com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate

import androidx.compose.runtime.snapshots.SnapshotStateList

data class EditExpensesTripUiState (
    val editExpensesTripList: SnapshotStateList<EditExpensesTripDetailingUiState> =
        SnapshotStateList()
)

data class EditExpensesTripDetailingUiState(
    val id: Int = 0,
    val date: String = "",
    val documentNumber: String = "",
    val expenseItem: String = "",
    val sum: String = "",
    val currency: String = "",
    val isAdd: Int = 0
)