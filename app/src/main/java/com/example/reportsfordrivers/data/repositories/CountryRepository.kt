package com.example.reportsfordrivers.data.repositories

import com.example.reportsfordrivers.data.structure.Country
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    fun getAllItemStream(): Flow<List<Country>>

    fun getOneItemStream(id: Int) : Flow<Country?>

    suspend fun insertItem(item: Country)

    suspend fun deleteItem(item: Country)

    suspend fun updateItem(item: Country)
}