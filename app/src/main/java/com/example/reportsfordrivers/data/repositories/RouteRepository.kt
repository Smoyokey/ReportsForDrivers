package com.example.reportsfordrivers.data.repositories

import com.example.reportsfordrivers.data.structure.Route
import kotlinx.coroutines.flow.Flow

interface RouteRepository {
    fun getAllItemStream() : Flow<List<Route>>

    fun getOneItemStream(id: Int) : Flow<Route?>

    suspend fun deleteOneElementForId(id: Int)

    suspend fun insertItem(item: Route)

    suspend fun deleteItem(item: Route)

    suspend fun updateItem(item: Route)

    suspend fun getLastId(): Int
}