package com.example.reportsfordrivers.data.repositories.createreport

import com.example.reportsfordrivers.data.structure.createreport.CreateExpensesTrip
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

    suspend fun deleteOneElementForId(id: Int)

    suspend fun updateOneElementForIdIsAdd(id: Int, isAdd: Int)
}