package com.example.reportsfordrivers.data.structure.createReport

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "create_personal_info")
data class CreatePersonalInfo (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "patronymic")
    val patronymic: String
)