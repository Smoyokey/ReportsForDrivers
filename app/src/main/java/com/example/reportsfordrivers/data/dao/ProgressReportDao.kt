package com.example.reportsfordrivers.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.PersonalInfo
import com.example.reportsfordrivers.data.structure.ProgressReport
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressReportDao {

    @Insert
    suspend fun insert(item: ProgressReport)

    @Delete
    suspend fun delete(item: ProgressReport)

    @Update
    suspend fun update(item: ProgressReport)

    @Query("SELECT * FROM progress_report")
    fun getAllItem(): Flow<List<ProgressReport>>

    @Query("SELECT * FROM progress_report WHERE id = :id")
    fun getOneItem(id: Int): Flow<ProgressReport>

    @Query("DELETE FROM PROGRESS_REPORT WHERE id = :id")
    suspend fun deleteOneElementForId(id: Int)
}