package com.example.reportsfordrivers.data.repositories

import com.example.reportsfordrivers.data.structure.Township
import kotlinx.coroutines.flow.Flow

interface TownshipRepository {
    fun getAllItemStream(): Flow<List<Township>>

    fun getOneItemStream(id: Int) : Flow<Township?>

    suspend fun insertItem(item: Township)

    suspend fun deleteItem(item: Township)

    suspend fun updateItem(item: Township)
}