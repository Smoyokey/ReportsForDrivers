package com.example.reportsfordrivers.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.Country
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {

    @Insert
    suspend fun insert(item: Country)

    @Delete
    suspend fun delete(item: Country)

    @Update
    suspend fun update(item: Country)

    @Query("SELECT * FROM COUNTRY")
    fun getAllItem(): Flow<List<Country>>

    @Query("SELECT * FROM COUNTRY WHERE id = :id")
    fun getOneItem(id: Int): Flow<Country>
}