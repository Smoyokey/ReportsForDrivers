package com.example.reportsfordrivers.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.VehicleAndTrailer
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleAndTrailerSaveDataDao {

    @Insert
    suspend fun insert(item: VehicleAndTrailer)

    @Delete
    suspend fun delete(item: VehicleAndTrailer)

    @Update
    suspend fun update(item: VehicleAndTrailer)

    @Query("SELECT * FROM VEHICLE_TRAILER_SAVE_DATA")
    fun getAllItem(): Flow<List<VehicleAndTrailer>>

    @Query("SELECT * FROM VEHICLE_TRAILER_SAVE_DATA WHERE id = :id")
    fun getOneItem(id: Int): Flow<VehicleAndTrailer>

    @Query("DELETE FROM VEHICLE_TRAILER_SAVE_DATA WHERE id = :id")
    suspend fun deleteOneElementForId(id: Int)
}