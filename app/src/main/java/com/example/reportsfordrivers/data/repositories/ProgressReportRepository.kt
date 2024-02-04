package com.example.reportsfordrivers.data.repositories

import com.example.reportsfordrivers.data.structure.ProgressReport
import kotlinx.coroutines.flow.Flow

interface ProgressReportRepository {
    fun getAllItemStream() : Flow<List<ProgressReport>>

    fun getOneItemStream(id: Int) : Flow<ProgressReport?>

    suspend fun insertItem(item: ProgressReport)

    suspend fun deleteItem(item: ProgressReport)

    suspend fun updateItem(item: ProgressReport)
}