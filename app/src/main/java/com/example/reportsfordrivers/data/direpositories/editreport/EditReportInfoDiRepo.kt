package com.example.reportsfordrivers.data.direpositories.editreport

import com.example.reportsfordrivers.data.dao.editreport.EditReportInfoDao
import com.example.reportsfordrivers.data.repositories.editreport.EditReportInfoRepository
import com.example.reportsfordrivers.data.structure.editreport.EditPersonalInfo
import com.example.reportsfordrivers.data.structure.editreport.EditReportInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditReportInfoDiRepo @Inject constructor(private val editReportInfoDao: EditReportInfoDao) :
    EditReportInfoRepository {

    override fun getAllItemStream(): Flow<List<EditReportInfo>> = editReportInfoDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<EditReportInfo> = editReportInfoDao.getOneItem(id)

    override suspend fun insertItem(item: EditReportInfo) = editReportInfoDao.insert(item)

    override suspend fun deleteItem(item: EditReportInfo) = editReportInfoDao.delete(item)

    override suspend fun updateItem(item: EditReportInfo) = editReportInfoDao.update(item)

    override suspend fun updateOneElementForIdDate(id: Int, date: String) =
        editReportInfoDao.updateOneElementForIdDate(id, date)

    override suspend fun updateOneElementForIdWaybill(id: Int, waybill: String) =
        editReportInfoDao.updateOneElementForIdWaybill(id, waybill)

    override suspend fun updateOneElementForIdMainCity(id: Int, mainCity: String) =
        editReportInfoDao.updateOneElementForIdMainCity(id, mainCity)

    override suspend fun updateOneElementForIdReportName(id: Int, reportName: String) =
        editReportInfoDao.updateOneElementForIdReportName(id, reportName)

    override suspend fun updateOneElementForIdIsStart(id: Int, isStart: Int) =
        editReportInfoDao.updateOneElementForIdIsStart(id, isStart)

    override suspend fun deleteAllElements() = editReportInfoDao.deleteAllElements()
}