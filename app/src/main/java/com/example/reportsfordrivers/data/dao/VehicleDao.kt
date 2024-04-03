package com.example.reportsfordrivers.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.Vehicle
import kotlinx.coroutines.flow.Flow


@Dao
interface VehicleDao {

    @Insert
    suspend fun insert(item: Vehicle)

    @Delete
    suspend fun delete(item: Vehicle)

    @Update
    suspend fun update(item: Vehicle)

    @Query("SELECT * FROM VEHICLE")
    fun getAllItem(): Flow<List<Vehicle>>

    @Query("SELECT * FROM VEHICLE WHERE id = :id")
    fun getOneItem(id: Int): Flow<Vehicle>

    @Query("DELETE FROM VEHICLE WHERE id = :id")
    suspend fun deleteOneElementForId(id: Int)
}