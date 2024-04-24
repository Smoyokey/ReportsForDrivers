package com.example.reportsfordrivers.data.repositories.createReport

import com.example.reportsfordrivers.data.structure.createReport.CreateExpensesTrip
import kotlinx.coroutines.flow.Flow

interface CreateExpensesTripRepository {

    fun getAllItemStream(): Flow<List<CreateExpensesTrip>>

    fun getOneItemStream(id: Int): Flow<CreateExpensesTrip?>

    suspend fun insertItem(item: CreateExpensesTrip)

    suspend fun deleteItem(item: CreateExpensesTrip)

    suspend fun updateItem(item: CreateExpensesTrip)
}