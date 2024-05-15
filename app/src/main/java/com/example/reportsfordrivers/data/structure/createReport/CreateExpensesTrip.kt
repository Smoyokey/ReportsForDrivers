package com.example.reportsfordrivers.data.structure.createReport

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "create_expenses_trip")
data class CreateExpensesTrip (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "document_number")
    val documentNumber: String,
    @ColumnInfo(name = "expense_item")
    val expenseItem: String,
    @ColumnInfo(name = "sum")
    val sum: String,
    @ColumnInfo(name = "currency")
    val currency: String,
    @ColumnInfo(name = "is_add")
    val isAdd: Int
)