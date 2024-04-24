package com.example.reportsfordrivers.data.direpositories.createReport

import com.example.reportsfordrivers.data.dao.createReport.CreatePersonalInfoDao
import com.example.reportsfordrivers.data.repositories.createReport.CreatePersonalInfoRepository
import com.example.reportsfordrivers.data.structure.createReport.CreatePersonalInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreatePersonalInfoDiRepo @Inject constructor(private val createPersonalInfoDao: CreatePersonalInfoDao) :
    CreatePersonalInfoRepository {

    override fun getAllItemStream(): Flow<List<CreatePersonalInfo>> =
        createPersonalInfoDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<CreatePersonalInfo?> =
        createPersonalInfoDao.getOneItem(id)

    override suspend fun insertItem(item: CreatePersonalInfo) = createPersonalInfoDao.insert(item)

    override suspend fun deleteItem(item: CreatePersonalInfo) = createPersonalInfoDao.delete(item)

    override suspend fun updateItem(item: CreatePersonalInfo) = createPersonalInfoDao.update(item)

}