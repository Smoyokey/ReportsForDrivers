package com.example.reportsfordrivers.data.repositories.editreport

import com.example.reportsfordrivers.data.structure.editreport.EditReportInfo
import kotlinx.coroutines.flow.Flow

interface EditReportInfoRepository {

    fun getAllItemStream(): Flow<List<EditReportInfo>>

    fun getOneItemStream(id: Int): Flow<EditReportInfo?>

    suspend fun insertItem(item: EditReportInfo)

    suspend fun deleteItem(item: EditReportInfo)

    suspend fun updateItem(item: EditReportInfo)

    suspend fun updateOneElementForIdDate(id: Int, date: String)

    suspend fun updateOneElementForIdWaybill(id: Int, waybill: String)

    suspend fun updateOneElementForIdMainCity(id: Int, mainCity: String)

    suspend fun updateOneElementForIdReportName(id: Int, reportName: String)

    suspend fun updateOneElementForIdIsStart(id: Int, isStart: Int)

    suspend fun deleteAllElements()
}