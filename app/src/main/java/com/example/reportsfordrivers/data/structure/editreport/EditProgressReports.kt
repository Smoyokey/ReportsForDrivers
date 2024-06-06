package com.example.reportsfordrivers.data.structure.editreport

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "edit_progress_reports")
data class EditProgressReports (
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