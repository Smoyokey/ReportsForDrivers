package com.example.reportsfordrivers.data.repositories.createReport

import com.example.reportsfordrivers.data.structure.createReport.CreateRoute
import kotlinx.coroutines.flow.Flow

interface CreateRouteRepository {

    fun getAllItemStream(): Flow<List<CreateRoute>>

    fun getOneItemStream(id: Int): Flow<CreateRoute?>

    suspend fun insertItem(item: CreateRoute)

    suspend fun deleteItem(item: CreateRoute)

    suspend fun updateItem(item: CreateRoute)
}