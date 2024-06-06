package com.example.reportsfordrivers.data.dao.editreport

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.editreport.EditReportInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface EditReportInfoDao {

    @Insert
    suspend fun insert(item: EditReportInfo)

    @Delete
    suspend fun delete(item: EditReportInfo)

    @Update
    suspend fun update(item: EditReportInfo)

    @Query("SELECT * FROM edit_report_info")
    fun getAllItem(): Flow<List<EditReportInfo>>

    @Query("SELECT * FROM edit_report_info WHERE id = :id")
    fun getOneItem(id: Int): Flow<EditReportInfo>

    @Query("UPDATE edit_report_info SET date = :date WHERE id = :id")
    suspend fun updateOneElementForIdDate(id: Int, date: String)

    @Query("UPDATE edit_report_info SET waybill = :waybill WHERE id =:id")
    suspend fun updateOneElementForIdWaybill(id: Int, waybill: String)

    @Query("UPDATE edit_report_info SET main_city = :mainCity WHERE id = :id")
    suspend fun updateOneElementForIdMainCity(id: Int, mainCity: String)

    @Query("UPDATE edit_report_info SET report_name = :reportName WHERE id = :id")
    suspend fun updateOneElementForIdReportName(id: Int, reportName: String)

    @Query("UPDATE edit_report_info SET is_start = :isStart WHERE id = :id")
    suspend fun updateOneElementForIdIsStart(id: Int, isStart: Int)

    @Query("DELETE FROM edit_report_info")
    suspend fun deleteAllElements()
}