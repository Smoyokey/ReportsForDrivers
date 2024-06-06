package com.example.reportsfordrivers.data.repositories.editreport

import com.example.reportsfordrivers.data.structure.editreport.EditExpensesTrip
import kotlinx.coroutines.flow.Flow

interface EditExpensesTripRepository {

    fun getAllItemStream(): Flow<List<EditExpensesTrip>>

    fun getOneItemStream(id: Int): Flow<EditExpensesTrip?>

    suspend fun insertItem(item: EditExpensesTrip)

    suspend fun deleteItem(item: EditExpensesTrip)

    suspend fun updateItem(item: EditExpensesTrip)

    suspend fun updateOneElementForIdDate(id: Int, date: String)

    suspend fun updateOneElementForIdDocumentNumber(id: Int, documentNumber: String)

    suspend fun updateOneElementForIdExpenseItem(id: Int, expenseItem: String)

    suspend fun updateOneElementForIdSum(id: Int, sum: String)

    suspend fun updateOneElementForIdCurrency(id: Int, currency: String)

    suspend fun deleteAllItems()

    suspend fun deleteOneElementForId(id: Int)

    suspend fun updateOneElementForIdIsAdd(id: Int, isAdd: Int)
}