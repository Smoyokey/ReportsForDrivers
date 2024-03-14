package com.example.reportsfordrivers.data.structure

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trip_expenses")
data class TripExpenses(
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
    @ColumnInfo(name = "report_name_id")
    val reportNameId: Int
)