package com.example.reportsfordrivers.data.structure

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
data class Country (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "full_name_country_rus")
    val fullNameCountryRus: String,
    @ColumnInfo(name = "full_name_country_eng")
    val fullNameCountryEng: String,
    @ColumnInfo(name = "short_name_country")
    val shortNameCountry: String
)