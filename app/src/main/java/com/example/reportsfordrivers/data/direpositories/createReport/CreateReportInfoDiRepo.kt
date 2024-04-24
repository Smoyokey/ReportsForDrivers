package com.example.reportsfordrivers.data.direpositories.createReport

import com.example.reportsfordrivers.data.dao.createReport.CreateReportInfoDao
import com.example.reportsfordrivers.data.repositories.createReport.CreateReportInfoRepository
import com.example.reportsfordrivers.data.structure.createReport.CreateReportInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateReportInfoDiRepo @Inject constructor(private val createReportInfoDao: CreateReportInfoDao) :
    CreateReportInfoRepository {

    override fun getAllItemStream(): Flow<List<CreateReportInfo>> = createReportInfoDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<CreateReportInfo> = createReportInfoDao.getOneItem(id)

    override suspend fun insertItem(item: CreateReportInfo) = createReportInfoDao.insert(item)

    override suspend fun deleteItem(item: CreateReportInfo) = createReportInfoDao.delete(item)

    override suspend fun updateItem(item: CreateReportInfo) = createReportInfoDao.update(item)
}