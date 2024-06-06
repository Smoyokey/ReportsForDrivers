package com.example.reportsfordrivers.data.direpositories.editreport

import com.example.reportsfordrivers.data.dao.editreport.EditProgressReportsDao
import com.example.reportsfordrivers.data.repositories.editreport.EditProgressReportsRepository
import com.example.reportsfordrivers.data.structure.editreport.EditProgressReports
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditProgressReportsDiRepo @Inject constructor(private val editProgressReportsDao: EditProgressReportsDao) :
    EditProgressReportsRepository {

    override fun getAllItemStream(): Flow<List<EditProgressReports>> = editProgressReportsDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<EditProgressReports?> =
        editProgressReportsDao.getOneItem(id)

    override suspend fun insertItem(item: EditProgressReports) = editProgressReportsDao.insert(item)

    override suspend fun deleteItem(item: EditProgressReports) = editProgressReportsDao.delete(item)

    override suspend fun updateItem(item: EditProgressReports) = editProgressReportsDao.update(item)

    override suspend fun updateOneElementForIdDate(id: Int, date: String) =
        editProgressReportsDao.updateOneElementForIdDate(id, date)

    override suspend fun updateOneElementForIdCountry(id: Int, country: String) =
        editProgressReportsDao.updateOneElementForIdCountry(id, country)

    override suspend fun updateOneElementForIdTownshipOne(id: Int, townshipOne: String) =
        editProgressReportsDao.updateOneElementForIdTownshipOne(id, townshipOne)

    override suspend fun updateOneElementForIdTownshipTwo(id: Int, townshipTwo: String) =
        editProgressReportsDao.updateOneElementForIdTownshipTwo(id, townshipTwo)

    override suspend fun updateOneElementForIdDistance(id: Int, distance: String) =
        editProgressReportsDao.updateOneElementForIdDistance(id, distance)

    override suspend fun updateOneElementForIdWeight(id: Int, weight: String) =
        editProgressReportsDao.updateOneElementForIdWeight(id, weight)

    override suspend fun updateOneElementForIdIsAdd(id: Int, isAdd: Int) =
        editProgressReportsDao.updateOneElementForIdIsAdd(id, isAdd)

    override suspend fun deleteAllElements() = editProgressReportsDao.deleteAllElements()

    override suspend fun deleteOneElementForId(id: Int) =
        editProgressReportsDao.deleteOneElementForId(id)
}