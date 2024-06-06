package com.example.reportsfordrivers.data.repositories.editreport

import com.example.reportsfordrivers.data.structure.editreport.EditProgressReports
import kotlinx.coroutines.flow.Flow

interface EditProgressReportsRepository {

    fun getAllItemStream(): Flow<List<EditProgressReports>>

    fun getOneItemStream(id: Int): Flow<EditProgressReports?>

    suspend fun insertItem(item: EditProgressReports)

    suspend fun deleteItem(item: EditProgressReports)

    suspend fun updateItem(item: EditProgressReports)

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