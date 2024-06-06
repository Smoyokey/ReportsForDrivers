package com.example.reportsfordrivers.data.structure.editreport

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "edit_vehicle_trailer")
data class EditVehicleTrailer (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "make_vehicle")
    val makeVehicle: String,
    @ColumnInfo(name = "rn_vehicle")
    val rnVehicle: String,
    @ColumnInfo(name = "make_trailer")
    val makeTrailer: String,
    @ColumnInfo(name = "rn_trailer")
    val rnTrailer: String
)