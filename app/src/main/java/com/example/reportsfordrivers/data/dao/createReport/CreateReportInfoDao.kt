package com.example.reportsfordrivers.data.dao.createReport

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.createReport.CreateReportInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface CreateReportInfoDao {

    @Insert
    suspend fun insert(item: CreateReportInfo)

    @Delete
    suspend fun delete(item: CreateReportInfo)

    @Update
    suspend fun update(item: CreateReportInfo)

    @Query("SELECT * FROM create_report_info")
    fun getAllItem(): Flow<List<CreateReportInfo>>

    @Query("SELECT * FROM create_report_info WHERE id = :id")
    fun getOneItem(id: Int): Flow<CreateReportInfo>
}