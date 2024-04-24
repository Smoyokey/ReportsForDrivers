package com.example.reportsfordrivers.data.dao.createReport

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.createReport.CreateRoute
import kotlinx.coroutines.flow.Flow

@Dao
interface CreateRouteDao {

    @Insert
    suspend fun insert(item: CreateRoute)

    @Delete
    suspend fun delete(item: CreateRoute)

    @Update
    suspend fun update(item: CreateRoute)

    @Query("SELECT * FROM create_route")
    fun getAllItem(): Flow<List<CreateRoute>>

    @Query("SELECT * FROM create_route WHERE id = :id")
    fun getOneItem(id: Int): Flow<CreateRoute>
}