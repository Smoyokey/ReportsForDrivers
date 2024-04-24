package com.example.reportsfordrivers.data.dao.createReport

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.createReport.CreateProgressReports
import kotlinx.coroutines.flow.Flow

@Dao
interface CreateProgressReportsDao {

    @Insert
    suspend fun insert(item: CreateProgressReports)

    @Delete
    suspend fun delete(item: CreateProgressReports)

    @Update
    suspend fun update(item: CreateProgressReports)

    @Query("SELECT * FROM create_progress_reports")
    fun getAllItem(): Flow<List<CreateProgressReports>>

    @Query("SELECT * FROM create_progress_reports WHERE id = :id")
    fun getOneItem(id: Int): Flow<CreateProgressReports>
}