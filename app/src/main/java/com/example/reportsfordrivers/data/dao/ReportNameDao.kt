package com.example.reportsfordrivers.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.ReportName
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportNameDao {

    @Insert
    suspend fun insert(item: ReportName)

    @Delete
    suspend fun delete(item: ReportName)

    @Update
    suspend fun update(item: ReportName)

    @Query("UPDATE report_name SET waybill = :waybill, main_city = :mainCity, date = :date WHERE id = :id")
    suspend fun updateWaybillMainCityDate(waybill: String, mainCity: String, date: String, id: Int)

    @Query("SELECT * FROM report_name")
    fun getAllItem(): Flow<List<ReportName>>

    @Query("SELECT * FROM report_name WHERE id = :id")
    fun getOneItem(id: Int): Flow<ReportName>

    @Query("DELETE FROM REPORT_NAME WHERE id = :id")
    suspend fun deleteOneElementForId(id: Int)

    @Query("SELECT id FROM REPORT_NAME ORDER BY id DESC LIMIT 1")
    suspend fun getLastId(): Int
}