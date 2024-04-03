package com.example.reportsfordrivers.data.repositories

import com.example.reportsfordrivers.data.structure.PersonalInfo
import kotlinx.coroutines.flow.Flow

interface PersonalInfoRepository {
    fun getAllItemStream() : Flow<List<PersonalInfo>>

    fun getOneItemStream(id: Int) : Flow<PersonalInfo?>

    suspend fun deleteOneElementForId(id: Int)

    suspend fun insertItem(item: PersonalInfo)

    suspend fun deleteItem(item: PersonalInfo)

    suspend fun updateItem(item: PersonalInfo)
}