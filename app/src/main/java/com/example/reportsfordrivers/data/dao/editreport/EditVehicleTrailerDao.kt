package com.example.reportsfordrivers.data.dao.editreport

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.editreport.EditVehicleTrailer
import kotlinx.coroutines.flow.Flow

@Dao
interface EditVehicleTrailerDao {

    @Insert
    suspend fun insert(item: EditVehicleTrailer)

    @Delete
    suspend fun delete(item: EditVehicleTrailer)

    @Update
    suspend fun update(item: EditVehicleTrailer)

    @Query("SELECT * FROM edit_vehicle_trailer")
    fun getAllItem(): Flow<List<EditVehicleTrailer>>

    @Query("SELECT * FROM edit_vehicle_trailer WHERE id = :id")
    fun getOneItem(id: Int): Flow<EditVehicleTrailer>

    @Query("UPDATE edit_vehicle_trailer SET make_vehicle = :makeVehicle WHERE id = :id")
    suspend fun updateOneElementForIdMakeVehicle(id: Int, makeVehicle: String)

    @Query("UPDATE edit_vehicle_trailer SET rn_vehicle = :rnVehicle WHERE id = :id")
    suspend fun updateOneElementForIdRnVehicle(id: Int, rnVehicle: String)

    @Query("UPDATE edit_vehicle_trailer SET make_trailer = :makeTrailer WHERE id = :id")
    suspend fun updateOneElementForIdMakeTrailer(id: Int, makeTrailer: String)

    @Query("UPDATE edit_vehicle_trailer SET rn_trailer = :rnTrailer WHERE id = :id")
    suspend fun updateOneElementForIdRnTrailer(id: Int, rnTrailer: String)

    @Query("DELETE FROM edit_vehicle_trailer")
    suspend fun deleteAllElements()
}