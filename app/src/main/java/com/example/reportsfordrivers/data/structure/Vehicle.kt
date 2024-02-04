package com.example.reportsfordrivers.data.structure

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicle")
data class Vehicle(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "make_vehicle")
    val makeVehicle: String,
    @ColumnInfo(name = "registration_number_vehicle")
    val registrationNumberVehicle: String
)