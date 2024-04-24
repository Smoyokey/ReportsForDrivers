package com.example.reportsfordrivers.data.repositories.createReport

import com.example.reportsfordrivers.data.structure.createReport.CreatePersonalInfo
import kotlinx.coroutines.flow.Flow

interface CreatePersonalInfoRepository {

    fun getAllItemStream(): Flow<List<CreatePersonalInfo>>

    fun getOneItemStream(id: Int): Flow<CreatePersonalInfo?>

    suspend fun insertItem(item: CreatePersonalInfo)

    suspend fun deleteItem(item: CreatePersonalInfo)

    suspend fun updateItem(item: CreatePersonalInfo)
}