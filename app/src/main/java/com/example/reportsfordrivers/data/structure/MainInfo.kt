package com.example.reportsfordrivers.data.structure

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "main_info")
data class MainInfo (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name_report")
    val nameReport: String,
    @ColumnInfo(name = "date_create")
    val dateCreate: String,
    @ColumnInfo(name = "route")
    val route: String,
    @ColumnInfo(name = "report_name_id")
    val reportNameId: Int
)