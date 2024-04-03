package com.example.reportsfordrivers.data.repositories

import com.example.reportsfordrivers.data.structure.VehicleAndTrailer
import kotlinx.coroutines.flow.Flow

interface VehicleAndTrailerSaveDataRepository {
    fun getAllItemStream() : Flow<List<VehicleAndTrailer>>

    fun getOneItemStream(id: Int) : Flow<VehicleAndTrailer?>

    suspend fun deleteOneElementForId(id: Int)

    suspend fun insertItem(item: VehicleAndTrailer)

    suspend fun deleteItem(item: VehicleAndTrailer)

    suspend fun updateItem(item: VehicleAndTrailer)
}