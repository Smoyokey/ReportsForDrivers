package com.example.reportsfordrivers.data.structure.createReport

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "create_progress_reports")
data class CreateProgressReports (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "township_one")
    val townshipOne: String,
    @ColumnInfo(name = "township_two")
    val townshipTwo: String,
    @ColumnInfo(name = "distance")
    val distance: String,
    @ColumnInfo(name = "weight")
    val weight: String,
    @ColumnInfo(name = "is_add")
    val isAdd: Int
)