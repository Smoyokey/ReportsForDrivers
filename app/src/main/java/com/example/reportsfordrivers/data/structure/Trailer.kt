package com.example.reportsfordrivers.data.structure

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trailer")
data class Trailer(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "make_trailer")
    val makeTrailer: String,
    @ColumnInfo(name = "registration_number_trailer")
    val registrationNumberTrailer: String
)