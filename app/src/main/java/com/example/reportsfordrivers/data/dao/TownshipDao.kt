package com.example.reportsfordrivers.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.Township
import kotlinx.coroutines.flow.Flow

@Dao
interface TownshipDao {

    @Insert
    suspend fun insert(item: Township)

    @Delete
    suspend fun delete(item: Township)

    @Update
    suspend fun update(item: Township)

    @Query("SELECT * FROM TOWNSHIP")
    fun getAllItem(): Flow<List<Township>>

    @Query("SELECT * FROM TOWNSHIP WHERE id = :id")
    fun getOneItem(id: Int): Flow<Township>
}