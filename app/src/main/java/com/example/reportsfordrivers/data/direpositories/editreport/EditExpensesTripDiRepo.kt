package com.example.reportsfordrivers.data.direpositories.editreport

import com.example.reportsfordrivers.data.dao.editreport.EditExpensesTripDao
import com.example.reportsfordrivers.data.repositories.editreport.EditExpensesTripRepository
import com.example.reportsfordrivers.data.structure.editreport.EditExpensesTrip
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditExpensesTripDiRepo @Inject constructor(private val editExpensesTripDao: EditExpensesTripDao) :
    EditExpensesTripRepository {

    override fun getAllItemStream(): Flow<List<EditExpensesTrip>> = editExpensesTripDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<EditExpensesTrip?> = editExpensesTripDao.getOneItem(id)

    override suspend fun insertItem(item: EditExpensesTrip) = editExpensesTripDao.insert(item)

    override suspend fun deleteItem(item: EditExpensesTrip) = editExpensesTripDao.delete(item)

    override suspend fun updateItem(item: EditExpensesTrip) = editExpensesTripDao.update(item)

    override suspend fun updateOneElementForIdDate(id: Int, date: String) =
        editExpensesTripDao.updateOneElementForIdDate(id, date)

    override suspend fun updateOneElementForIdDocumentNumber(id: Int, documentNumber: String) =
        editExpensesTripDao.updateOneElementForIdDocumentNumber(id, documentNumber)

    override suspend fun updateOneElementForIdExpenseItem(id: Int, expenseItem: String) =
        editExpensesTripDao.updateOneElementForIdExpenseItem(id, expenseItem)

    override suspend fun updateOneElementForIdSum(id: Int, sum: String) =
        editExpensesTripDao.updateOneElementForIdSum(id, sum)

    override suspend fun updateOneElementForIdCurrency(id: Int, currency: String) =
        editExpensesTripDao.updateOneElementForIdCurrency(id, currency)

    override suspend fun updateOneElementForIdIsAdd(id: Int, isAdd: Int) =
        editExpensesTripDao.updateOneElementForIdIsAdd(id, isAdd)

    override suspend fun deleteAllItems() = editExpensesTripDao.deleteAllElements()

    override suspend fun deleteOneElementForId(id: Int) =
        editExpensesTripDao.deleteOneElementForId(id)

}