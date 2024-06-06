package com.example.reportsfordrivers.data.structure.createreport

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "create_report_info")
data class CreateReportInfo (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "waybill")
    val waybill: String,
    @ColumnInfo(name = "main_city")
    val mainCity: String,
    @ColumnInfo(name = "report_name")
    val reportName: String,
    @ColumnInfo(name = "is_start")
    val isStart: Int
)