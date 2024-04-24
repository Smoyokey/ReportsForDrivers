package com.example.reportsfordrivers.data.repositories.createReport

import com.example.reportsfordrivers.data.structure.createReport.CreateVehicleTrailer
import kotlinx.coroutines.flow.Flow

interface CreateVehicleTrailerRepository {

    fun getAllItemStream(): Flow<List<CreateVehicleTrailer>>

    fun getOneItemStream(id: Int): Flow<CreateVehicleTrailer?>

    suspend fun insertItem(item: CreateVehicleTrailer)

    suspend fun deleteItem(item: CreateVehicleTrailer)

    suspend fun updateItem(item: CreateVehicleTrailer)
}