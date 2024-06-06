package com.example.reportsfordrivers.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.MainInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface MainInfoDao {

    @Insert
    suspend fun insert(item: MainInfo)

    @Delete
    suspend fun delete(item: MainInfo)

    @Update
    suspend fun update(item: MainInfo)

    @Query("UPDATE main_info SET name_report = :nameReport, date_create = :dateCreate, " +
            "route_main_info = :routeMainInfo WHERE id = :id")
    fun updateNameReportDateCreateRouteMainInfoForId(
        nameReport: String,
        dateCreate: String,
        routeMainInfo: String,
        id: Int
    )

    @Query("SELECT * FROM MAIN_INFO")
    fun getAllItem(): Flow<List<MainInfo>>

    @Query("SELECT * FROM MAIN_INFO WHERE id = :id")
    fun getOneItem(id: Int): Flow<MainInfo>

    @Query("DELETE FROM MAIN_INFO WHERE id = :id")
    suspend fun deleteOneElementForId(id: Int)

    @Query("SELECT name_report FROM MAIN_INFO ORDER BY id DESC LIMIT 1")
    suspend fun getLastNameReport(): String
}