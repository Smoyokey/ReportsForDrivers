package com.example.reportsfordrivers.data.repositories.createreport

import com.example.reportsfordrivers.data.structure.createreport.CreateVehicleTrailer
import kotlinx.coroutines.flow.Flow

interface CreateVehicleTrailerRepository {

    fun getAllItemStream(): Flow<List<CreateVehicleTrailer>>

    fun getOneItemStream(id: Int): Flow<CreateVehicleTrailer?>

    suspend fun insertItem(item: CreateVehicleTrailer)

    suspend fun deleteItem(item: CreateVehicleTrailer)

    suspend fun updateItem(item: CreateVehicleTrailer)

    suspend fun updateOneElementForIdMakeVehicle(id: Int, makeVehicle: String)

    suspend fun updateOneElementForIdRnVehicle(id: Int, rnVehicle: String)

    suspend fun updateOneElementForIdMakeTrailer(id: Int, makeTrailer: String)

    suspend fun updateOneElementForIdRnTrailer(id: Int, rnTrailer: String)

    suspend fun deleteAllElements()
}