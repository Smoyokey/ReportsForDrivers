package com.example.reportsfordrivers.data.repositories.editreport

import com.example.reportsfordrivers.data.structure.editreport.EditVehicleTrailer
import kotlinx.coroutines.flow.Flow

interface EditVehicleTrailerRepository {

    fun getAllItemStream(): Flow<List<EditVehicleTrailer>>

    fun getOneItemStream(id: Int): Flow<EditVehicleTrailer?>

    suspend fun insertItem(item: EditVehicleTrailer)

    suspend fun deleteItem(item: EditVehicleTrailer)

    suspend fun updateItem(item: EditVehicleTrailer)

    suspend fun updateOneElementForIdMakeVehicle(id: Int, makeVehicle: String)

    suspend fun updateOneElementForIdRnVehicle(id: Int, rnVehicle: String)

    suspend fun updateOneElementForIdMakeTrailer(id: Int, makeTrailer: String)

    suspend fun updateOneElementForIdRnTrailer(id: Int, rnTrailer: String)

    suspend fun deleteAllElements()
}