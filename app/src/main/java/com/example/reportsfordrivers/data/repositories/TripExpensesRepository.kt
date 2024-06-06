package com.example.reportsfordrivers.data.repositories

import com.example.reportsfordrivers.data.structure.TripExpenses
import kotlinx.coroutines.flow.Flow

interface TripExpensesRepository {
    fun getAllItemStream() : Flow<List<TripExpenses>>

    fun getOneItemStream(id: Int) : Flow<TripExpenses?>

    suspend fun deleteOneElementForId(id: Int)

    suspend fun deleteAllElementsForReportNameId(reportNameId: Int)

    suspend fun insertItem(item: TripExpenses)

    suspend fun deleteItem(item: TripExpenses)

    suspend fun updateItem(item: TripExpenses)

    fun getAllElementsForReportNameId(reportNameId: Int): Flow<List<TripExpenses>>
}