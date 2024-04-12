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
    val shortNameCountry: String,
    @ColumnInfo(name = "rating")
    val rating: Int = 0,
    @ColumnInfo(name = "favorite")
    val favorite: Int = 0
)

data class CountryRus (
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "full_name_country_rus")
    val fullNameCountryRus: String,
    @ColumnInfo(name = "short_name_country")
    val shortNameCountry: String,
    @ColumnInfo(name = "rating")
    val rating: Int,
    @ColumnInfo(name = "favorite")
    var favorite: Int
)

data class CountryEng (
    val id: Int,
    @ColumnInfo(name = "full_name_country_eng")
    val fullNameCountryEng: String,
    @ColumnInfo(name = "short_name_country")
    val shortNameCountry: String,
    val rating: Int,
    val favorite: Int
)