package com.example.reportsfordrivers.data.repositories.editreport

import com.example.reportsfordrivers.data.structure.editreport.EditPersonalInfo
import kotlinx.coroutines.flow.Flow

interface EditPersonalInfoRepository {

    fun getAllItemStream(): Flow<List<EditPersonalInfo>>

    fun getOneItemStream(id: Int): Flow<EditPersonalInfo?>

    suspend fun insertItem(item: EditPersonalInfo)

    suspend fun deleteItem(item: EditPersonalInfo)

    suspend fun updateItem(item: EditPersonalInfo)

    suspend fun updateOneElementForIdLastName(id: Int, lastName: String)

    suspend fun updateOneElementForIdFirstName(id: Int, firstName: String)

    suspend fun updateOneElementForIdPatronymic(id: Int, patronymic: String)

    suspend fun deleteAllElements()
}