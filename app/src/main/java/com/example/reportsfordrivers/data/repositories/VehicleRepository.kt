package com.example.reportsfordrivers.data.repositories

import com.example.reportsfordrivers.data.structure.Vehicle
import kotlinx.coroutines.flow.Flow

interface  VehicleRepository {
    fun getAllItemStream() : Flow<List<Vehicle>>

    fun getOneItemStream(id: Int) : Flow<Vehicle?>

    suspend fun insertItem(item: Vehicle)

    suspend fun deleteItem(item: Vehicle)

    suspend fun updateItem(item: Vehicle)
}