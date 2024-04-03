package com.example.reportsfordrivers.data.direpositories

import com.example.reportsfordrivers.data.dao.ProgressReportDao
import com.example.reportsfordrivers.data.repositories.ProgressReportRepository
import com.example.reportsfordrivers.data.structure.ProgressReport
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgressReportDiRepo @Inject constructor(private val progressReportDao: ProgressReportDao) :
    ProgressReportRepository {
    override fun getAllItemStream(): Flow<List<ProgressReport>> = progressReportDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<ProgressReport?> = progressReportDao.getOneItem(id)

    override suspend fun deleteOneElementForId(id: Int) = progressReportDao.deleteOneElementForId(id)

    override suspend fun insertItem(item: ProgressReport) = progressReportDao.insert(item)

    override suspend fun deleteItem(item: ProgressReport) = progressReportDao.delete(item)

    override suspend fun updateItem(item: ProgressReport) = progressReportDao.update(item)
}