package com.example.reportsfordrivers.data.dao.createReport

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.createReport.CreateVehicleTrailer
import kotlinx.coroutines.flow.Flow

@Dao
interface CreateVehicleTrailerDao {

    @Insert
    suspend fun insert(item: CreateVehicleTrailer)

    @Delete
    suspend fun delete(item: CreateVehicleTrailer)

    @Update
    suspend fun update(item: CreateVehicleTrailer)

    @Query("SELECT * FROM create_vehicle_trailer")
    fun getAllItem(): Flow<List<CreateVehicleTrailer>>

    @Query("SELECT * FROM create_vehicle_trailer WHERE id = :id")
    fun getOneItem(id: Int): Flow<CreateVehicleTrailer>

    @Query("UPDATE create_vehicle_trailer SET make_vehicle = :makeVehicle WHERE id = :id")
    suspend fun updateOneElementForIdMakeVehicle(id: Int, makeVehicle: String)

    @Query("UPDATE create_vehicle_trailer SET rn_vehicle = :rnVehicle WHERE id = :id")
    suspend fun updateOneElementForIdRnVehicle(id: Int, rnVehicle: String)

    @Query("UPDATE create_vehicle_trailer SET make_trailer = :makeTrailer WHERE id = :id")
    suspend fun updateOneElementForIdMakeTrailer(id: Int, makeTrailer: String)

    @Query("UPDATE create_vehicle_trailer SET rn_trailer = :rnTrailer WHERE id = :id")
    suspend fun updateOneElementForIdRnTrailer(id: Int, rnTrailer: String)

    @Query("DELETE FROM create_vehicle_trailer")
    suspend fun deleteAllElements()
}