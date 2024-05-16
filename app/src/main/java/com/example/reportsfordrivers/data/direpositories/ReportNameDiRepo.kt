package com.example.reportsfordrivers.data.direpositories

import com.example.reportsfordrivers.data.dao.ReportNameDao
import com.example.reportsfordrivers.data.repositories.ReportNameRepository
import com.example.reportsfordrivers.data.structure.ReportName
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReportNameDiRepo @Inject constructor(private val reportNameDao: ReportNameDao) : ReportNameRepository {
    override fun getAllItemStream(): Flow<List<ReportName>> = reportNameDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<ReportName?> = reportNameDao.getOneItem(id)

    override suspend fun deleteOneElementForId(id: Int) = reportNameDao.deleteOneElementForId(id)

    override suspend fun insertItem(item: ReportName) = reportNameDao.insert(item)

    override suspend fun deleteItem(item: ReportName) = reportNameDao.delete(item)

    override suspend fun updateItem(item: ReportName) = reportNameDao.update(item)

    override suspend fun getLastId(): Int = reportNameDao.getLastId()
}