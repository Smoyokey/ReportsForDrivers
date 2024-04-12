package com.example.reportsfordrivers.data.repositories

import com.example.reportsfordrivers.data.structure.Currency
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun getAllItemStream() : List<Currency>

    fun getOneItemStream(id: Int) : Flow<Currency?>

    suspend fun insertItem(item: Currency)

    suspend fun deleteItem(item: Currency)

    suspend fun updateItem(item: Currency)
}