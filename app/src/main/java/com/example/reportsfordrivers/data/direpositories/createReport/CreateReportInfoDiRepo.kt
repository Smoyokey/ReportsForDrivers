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

    override suspend fun updateOneElementForIdDate(id: Int, date: String) =
        createReportInfoDao.updateOneElementForIdDate(id, date)

    override suspend fun updateOneElementForIdWaybill(id: Int, waybill: String) =
        createReportInfoDao.updateOneElementForIdWaybill(id, waybill)

    override suspend fun updateOneElementForIdMainCity(id: Int, mainCity: String) =
        createReportInfoDao.updateOneElementForIdMainCity(id, mainCity)

    override suspend fun updateOneElementForIdReportName(id: Int, reportName: String) =
        createReportInfoDao.updateOneElementForIdReportName(id, reportName)

    override suspend fun updateOneElementForIdIsStart(id: Int, isStart: Int) =
        createReportInfoDao.updateOneElementForIdIsStart(id, isStart)

    override suspend fun deleteAllElements() = createReportInfoDao.deleteAllElements()
}