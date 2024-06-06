package com.example.reportsfordrivers.data.structure.editreport

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "edit_route")
data class EditRoute (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "route")
    val route: String,
    @ColumnInfo(name = "date_departure")
    val dateDeparture: String,
    @ColumnInfo(name = "date_return")
    val dateReturn: String,
    @ColumnInfo(name = "date_border_crossing_departure")
    val dateBorderCrossingDeparture: String,
    @ColumnInfo(name = "date_border_crossing_return")
    val dateBorderCrossingReturn: String,
    @ColumnInfo(name = "speedometer_reading_departure")
    val speedometerReadingDeparture: String,
    @ColumnInfo(name = "speedometer_reading_return")
    val speedometerReadingReturn: String,
    @ColumnInfo(name = "fuelled")
    val fuelled: String
)