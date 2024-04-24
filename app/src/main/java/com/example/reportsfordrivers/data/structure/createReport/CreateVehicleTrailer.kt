package com.example.reportsfordrivers.data.structure.createReport

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "create_vehicle_trailer")
data class CreateVehicleTrailer (
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