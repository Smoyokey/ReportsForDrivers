package com.example.reportsfordrivers.data.repositories.createReport

import com.example.reportsfordrivers.data.structure.createReport.CreateExpensesTrip
import kotlinx.coroutines.flow.Flow

interface CreateExpensesTripRepository {

    fun getAllItemStream(): Flow<List<CreateExpensesTrip>>

    fun getOneItemStream(id: Int): Flow<CreateExpensesTrip?>

    suspend fun insertItem(item: CreateExpensesTrip)

    suspend fun deleteItem(item: CreateExpensesTrip)

    suspend fun updateItem(item: CreateExpensesTrip)

    suspend fun updateOneElementForIdDate(id: Int, date: String)

    suspend fun updateOneElementForIdDocumentNumber(id: Int, documentNumber: String)

    suspend fun updateOneElementForIdExpenseItem(id: Int, expenseItem: String)

    suspend fun updateOneElementForIdSum(id: Int, sum: String)

    suspend fun updateOneElementForIdCurrency(id: Int, currency: String)

    suspend fun deleteAllItems()
}