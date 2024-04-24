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
}