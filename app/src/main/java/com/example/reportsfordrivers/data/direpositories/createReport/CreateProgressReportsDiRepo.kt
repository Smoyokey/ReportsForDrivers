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

}