package com.example.reportsfordrivers.data.structure

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "report_name")
data class ReportName(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name_report")
    val nameReport: String,
    @ColumnInfo(name = "waybill")
    val waybill: String,
    @ColumnInfo(name = "main_city")
    val mainCity: String,
    @ColumnInfo(name = "date_create")
    val dateCreate: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "personal_info_id")
    val personalInfoId: Int,
    @ColumnInfo(name = "vehicle_id")
    val vehicleId: Int,
    @ColumnInfo(name = "trailer_id")
    val trailerId: Int,
    @ColumnInfo(name = "route_id")
    val routeId: Int
)
