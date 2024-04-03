package com.example.reportsfordrivers.data.structure

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "township")
data class Township (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "township_rus")
    val townshipRus: String,
    @ColumnInfo(name = "township_eng")
    val townshipEng: String,
    @ColumnInfo(name = "country_id")
    val countryId: Int,
    @ColumnInfo(name = "rating")
    val rating: Int,
    @ColumnInfo(name = "favorite")
    val favorite: Int
)