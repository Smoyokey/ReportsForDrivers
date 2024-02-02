package com.example.reportsfordrivers.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.Trailer
import kotlinx.coroutines.flow.Flow

@Dao
interface TrailerDao {

    @Insert
    suspend fun insert(item: Trailer)

    @Delete
    suspend fun delete(item: Trailer)

    @Update
    suspend fun update(item: Trailer)

    @Query("SELECT * FROM TRAILER")
    fun getAllItem(): Flow<List<Trailer>>

    @Query("SELECT * FROM TRAILER WHERE id = :id")
    fun getOneItem(id: Int): Flow<Trailer>
}