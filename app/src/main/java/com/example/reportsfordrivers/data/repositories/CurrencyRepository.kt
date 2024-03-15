package com.example.reportsfordrivers.data.repositories

import com.example.reportsfordrivers.data.structure.Currency
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    fun getAllItemStream() : Flow<List<Currency>>

    fun getOneItemStream(id: Int) : Flow<Currency?>

    suspend fun insertItem(item: Currency)

    suspend fun deleteItem(item: Currency)

    suspend fun updateItem(item: Currency)
}