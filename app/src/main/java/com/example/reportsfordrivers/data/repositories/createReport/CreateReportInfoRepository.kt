package com.example.reportsfordrivers.data.repositories.createReport

import com.example.reportsfordrivers.data.structure.createReport.CreateReportInfo
import kotlinx.coroutines.flow.Flow

interface CreateReportInfoRepository {

    fun getAllItemStream(): Flow<List<CreateReportInfo>>

    fun getOneItemStream(id: Int): Flow<CreateReportInfo?>

    suspend fun insertItem(item: CreateReportInfo)

    suspend fun deleteItem(item: CreateReportInfo)

    suspend fun updateItem(item: CreateReportInfo)

    suspend fun updateOneElementForIdDate(id: Int, date: String)

    suspend fun updateOneElementForIdWaybill(id: Int, date: String)

    suspend fun updateOneElementForIdMainCity(id: Int, mainCity: String)

    suspend fun updateOneElementForIdReportName(id: Int, reportName: String)

    suspend fun updateOneElementForIdIsStart(id: Int, isStart: Int)

    suspend fun deleteAllElements()
}