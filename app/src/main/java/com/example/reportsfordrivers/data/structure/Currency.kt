package com.example.reportsfordrivers.data.structure

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class Currency (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "full_name_currency_rus")
    val fullNameCurrencyRus: String,
    @ColumnInfo(name = "full_name_currency_eng")
    val fullNameCurrencyEng: String,
    @ColumnInfo(name = "short_name_currency")
    val shortNameCurrency: String
)