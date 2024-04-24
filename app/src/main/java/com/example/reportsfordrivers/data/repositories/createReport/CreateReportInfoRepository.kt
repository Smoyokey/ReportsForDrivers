package com.example.reportsfordrivers.data.repositories.createReport

import com.example.reportsfordrivers.data.structure.createReport.CreateReportInfo
import kotlinx.coroutines.flow.Flow

interface CreateReportInfoRepository {

    fun getAllItemStream(): Flow<List<CreateReportInfo>>

    fun getOneItemStream(id: Int): Flow<CreateReportInfo?>

    suspend fun insertItem(item: CreateReportInfo)

    suspend fun deleteItem(item: CreateReportInfo)

    suspend fun updateItem(item: CreateReportInfo)
}