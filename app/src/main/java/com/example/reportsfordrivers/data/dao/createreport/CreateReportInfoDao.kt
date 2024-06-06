package com.example.reportsfordrivers.data.dao.createreport

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.createreport.CreateReportInfo
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

    @Query("UPDATE create_report_info SET date = :date WHERE id = :id")
    suspend fun updateOneElementForIdDate(id: Int, date: String)

    @Query("UPDATE create_report_info set waybill = :waybill WHERE id = :id")
    suspend fun updateOneElementForIdWaybill(id: Int, waybill: String)

    @Query("UPDATE create_report_info SET main_city = :mainCity WHERE id = :id")
    suspend fun updateOneElementForIdMainCity(id: Int, mainCity: String)

    @Query("UPDATE create_report_info SET report_name = :reportName WHERE id = :id")
    suspend fun updateOneElementForIdReportName(id: Int, reportName: String)

    @Query("UPDATE create_report_info SET is_start = :isStart WHERE id = :id")
    suspend fun updateOneElementForIdIsStart(id: Int, isStart: Int)

    @Query("DELETE FROM create_report_info")
    suspend fun deleteAllElements()
}