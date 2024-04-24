package com.example.reportsfordrivers.data.repositories.createReport

import com.example.reportsfordrivers.data.structure.createReport.CreateProgressReports
import kotlinx.coroutines.flow.Flow

interface CreateProgressReportsRepository {

    fun getAllItemStream(): Flow<List<CreateProgressReports>>

    fun getOneItemStream(id: Int): Flow<CreateProgressReports?>

    suspend fun insertItem(item: CreateProgressReports)

    suspend fun deleteItem(item: CreateProgressReports)

    suspend fun updateItem(item: CreateProgressReports)
}