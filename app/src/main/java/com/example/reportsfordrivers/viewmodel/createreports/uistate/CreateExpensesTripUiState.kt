package com.example.reportsfordrivers.viewmodel.createreports.uistate

import androidx.compose.runtime.snapshots.SnapshotStateList

data class CreateExpensesTripUiState (
    val createExpensesTripList: SnapshotStateList<CreateExpensesTripDetailingUiState> =
        SnapshotStateList()
)

data class CreateExpensesTripDetailingUiState(
    val id: Int = 0,
    val date: String = "",
    val documentNumber: String = "",
    val expenseItem: String = "",
    val sum: String = "",
    val currency: String = ""
)