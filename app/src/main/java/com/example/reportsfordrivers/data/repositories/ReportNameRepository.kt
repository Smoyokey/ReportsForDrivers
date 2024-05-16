package com.example.reportsfordrivers.data.repositories

import com.example.reportsfordrivers.data.structure.ReportName
import kotlinx.coroutines.flow.Flow

interface ReportNameRepository {
    fun getAllItemStream() : Flow<List<ReportName>>

    fun getOneItemStream(id: Int) : Flow<ReportName?>

    suspend fun deleteOneElementForId(id: Int)

    suspend fun insertItem(item: ReportName)

    suspend fun deleteItem(item: ReportName)

    suspend fun updateItem(item: ReportName)

    suspend fun getLastId(): Int
}