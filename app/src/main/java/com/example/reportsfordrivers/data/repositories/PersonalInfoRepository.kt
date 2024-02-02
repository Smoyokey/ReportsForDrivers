package com.example.reportsfordrivers.data.repositories

import com.example.reportsfordrivers.data.dao.PersonalInfoDao
import com.example.reportsfordrivers.data.structure.PersonalInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface PersonalInfoRepository {
    fun getAllItemStream() : Flow<List<PersonalInfo>>

    fun getOneItemStream(id: Int) : Flow<PersonalInfo?>

    suspend fun insertItem(item: PersonalInfo)

    suspend fun deleteItem(item: PersonalInfo)

    suspend fun updateItem(item: PersonalInfo)
}