package com.example.reportsfordrivers.data.structure

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "progress_report")
data class ProgressReport(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "township")
    val township: String,
    @ColumnInfo(name = "distance")
    val distance: String,
    @ColumnInfo(name = "weight")
    val weight: String,
    @ColumnInfo(name = "report_name_id")
    val reportNameId: Int,
)