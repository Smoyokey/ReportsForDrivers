package com.example.reportsfordrivers.data.direpositories

import com.example.reportsfordrivers.data.dao.TripExpensesDao
import com.example.reportsfordrivers.data.repositories.TripExpensesRepository
import com.example.reportsfordrivers.data.structure.TripExpenses
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripExpensesDiRepo @Inject constructor(private val tripExpensesDao: TripExpensesDao) :
    TripExpensesRepository {
    override fun getAllItemStream(): Flow<List<TripExpenses>> = tripExpensesDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<TripExpenses?> = tripExpensesDao.getOneItem(id)

    override suspend fun insertItem(item: TripExpenses) = tripExpensesDao.insert(item)

    override suspend fun deleteItem(item: TripExpenses) = tripExpensesDao.delete(item)

    override suspend fun updateItem(item: TripExpenses) = tripExpensesDao.update(item)
}