package com.example.reportsfordrivers.data.structure

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicle_trailer_save_data")
data class VehicleAndTrailer(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "vehicle_or_trailer")
    val vehicleOrTrailer: String,
    @ColumnInfo(name = "make")
    val make: String,
    @ColumnInfo(name = "registration_number")
    val registrationNumber: String
)