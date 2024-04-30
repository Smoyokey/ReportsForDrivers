package com.example.reportsfordrivers.data.direpositories.createReport

import com.example.reportsfordrivers.data.dao.createReport.CreateProgressReportsDao
import com.example.reportsfordrivers.data.repositories.createReport.CreateProgressReportsRepository
import com.example.reportsfordrivers.data.structure.createReport.CreateProgressReports
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateProgressReportsDiRepo @Inject constructor(private val createProgressReportsDao: CreateProgressReportsDao) :
    CreateProgressReportsRepository {

    override fun getAllItemStream(): Flow<List<CreateProgressReports>> =
        createProgressReportsDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<CreateProgressReports?> =
        createProgressReportsDao.getOneItem(id)

    override suspend fun insertItem(item: CreateProgressReports) = createProgressReportsDao.insert(item)

    override suspend fun deleteItem(item: CreateProgressReports) = createProgressReportsDao.delete(item)

    override suspend fun updateItem(item: CreateProgressReports) = createProgressReportsDao.update(item)

    override suspend fun updateOneElementForIdDate(id: Int, date: String) =
        createProgressReportsDao.updateOneElementForIdDate(id, date)

    override suspend fun updateOneElementForIdCountry(id: Int, country: String) =
        createProgressReportsDao.updateOneElementForIdCountry(id, country)

    override suspend fun updateOneElementForIdTownshipOne(id: Int, townshipOne: String) =
        createProgressReportsDao.updateOneElementForIdTownshipOne(id, townshipOne)

    override suspend fun updateOneElementForIdTownshipTwo(id: Int, townshipTwo: String) =
        createProgressReportsDao.updateOneElementForIdTownshipTwo(id, townshipTwo)

    override suspend fun updateOneElementForIdDistance(id: Int, distance: String) =
        createProgressReportsDao.updateOneElementForIdDistance(id, distance)

    override suspend fun updateOneElementForIdWeight(id: Int, weight: String) =
        createProgressReportsDao.updateOneElementForIdWeight(id, weight)

    override suspend fun deleteAllElements() = createProgressReportsDao.deleteAllElements()
}