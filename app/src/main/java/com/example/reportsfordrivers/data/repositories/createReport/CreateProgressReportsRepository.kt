package com.example.reportsfordrivers.data.repositories.createReport

import com.example.reportsfordrivers.data.structure.createReport.CreateProgressReports
import kotlinx.coroutines.flow.Flow

interface CreateProgressReportsRepository {

    fun getAllItemStream(): Flow<List<CreateProgressReports>>

    fun getOneItemStream(id: Int): Flow<CreateProgressReports?>

    suspend fun insertItem(item: CreateProgressReports)

    suspend fun deleteItem(item: CreateProgressReports)

    suspend fun updateItem(item: CreateProgressReports)

    suspend fun updateOneElementForIdDate(id: Int, date: String)

    suspend fun updateOneElementForIdCountry(id: Int, country: String)

    suspend fun updateOneElementForIdTownshipOne(id: Int, townshipOne: String)

    suspend fun updateOneElementForIdTownshipTwo(id: Int, townshipTwo: String)

    suspend fun updateOneElementForIdDistance(id: Int, distance: String)

    suspend fun updateOneElementForIdWeight(id: Int, weight: String)

    suspend fun updateOneElementForIdIsAdd(id: Int, isAdd: Int)

    suspend fun deleteAllElements()

    suspend fun deleteOneElementForId(id: Int)
}