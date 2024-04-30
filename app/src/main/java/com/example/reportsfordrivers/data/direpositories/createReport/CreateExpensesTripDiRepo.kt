package com.example.reportsfordrivers.data.direpositories.createReport

import com.example.reportsfordrivers.data.dao.createReport.CreateExpensesTripDao
import com.example.reportsfordrivers.data.repositories.createReport.CreateExpensesTripRepository
import com.example.reportsfordrivers.data.structure.createReport.CreateExpensesTrip
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateExpensesTripDiRepo @Inject constructor(private val createExpensesTripDao: CreateExpensesTripDao) :
    CreateExpensesTripRepository {

    override fun getAllItemStream(): Flow<List<CreateExpensesTrip>> =
        createExpensesTripDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<CreateExpensesTrip?> =
        createExpensesTripDao.getOneItem(id)

    override suspend fun insertItem(item: CreateExpensesTrip) = createExpensesTripDao.insert(item)

    override suspend fun deleteItem(item: CreateExpensesTrip) = createExpensesTripDao.delete(item)

    override suspend fun updateItem(item: CreateExpensesTrip) = createExpensesTripDao.update(item)

    override suspend fun updateOneElementForIdDate(id: Int, date: String) =
        createExpensesTripDao.updateOneElementForIdDate(id, date)

    override suspend fun updateOneElementForIdDocumentNumber(id: Int, documentNumber: String) =
        createExpensesTripDao.updateOneElementForIdDocumentNumber(id, documentNumber)

    override suspend fun updateOneElementForIdExpenseItem(id: Int, expenseItem: String) =
        createExpensesTripDao.updateOneElementForIdExpenseItem(id, expenseItem)

    override suspend fun updateOneElementForIdSum(id: Int, sum: String) =
        createExpensesTripDao.updateOneElementForIdSum(id, sum)

    override suspend fun updateOneElementForIdCurrency(id: Int, currency: String) =
        createExpensesTripDao.updateOneElementForIdCurrency(id, currency)

    override suspend fun deleteAllItems() = createExpensesTripDao.deleteAllElements()
}