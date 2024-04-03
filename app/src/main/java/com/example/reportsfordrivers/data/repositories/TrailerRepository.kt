package com.example.reportsfordrivers.data.repositories

import com.example.reportsfordrivers.data.structure.Trailer
import kotlinx.coroutines.flow.Flow

interface TrailerRepository {
    fun getAllItemStream() : Flow<List<Trailer>>

    fun getOneItemStream(id: Int) : Flow<Trailer?>

    suspend fun deleteOneElementForId(id: Int)

    suspend fun insertItem(item: Trailer)

    suspend fun deleteItem(item: Trailer)

    suspend fun updateItem(item: Trailer)
}