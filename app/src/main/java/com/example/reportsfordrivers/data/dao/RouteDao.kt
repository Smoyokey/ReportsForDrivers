package com.example.reportsfordrivers.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.Route
import kotlinx.coroutines.flow.Flow

@Dao
interface RouteDao {

    @Insert
    suspend fun insert(item: Route)

    @Delete
    suspend fun delete(item: Route)

    @Update
    suspend fun update(item: Route)

    @Query("SELECT * FROM ROUTE")
    fun getAllItem(): Flow<List<Route>>

    @Query("SELECT * FROM ROUTE WHERE id = :id")
    fun getOneItem(id: Int): Flow<Route>

    @Query("DELETE FROM ROUTE WHERE id = :id")
    suspend fun deleteOneElementForId(id: Int)

    @Query("SELECT id FROM ROUTE ORDER BY id DESC LIMIT 1")
    suspend fun getLastId(): Int
}