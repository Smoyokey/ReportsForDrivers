package com.example.reportsfordrivers.data.direpositories

import com.example.reportsfordrivers.data.dao.PersonalInfoDao
import com.example.reportsfordrivers.data.repositories.PersonalInfoRepository
import com.example.reportsfordrivers.data.structure.PersonalInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonalInfoDiRepo @Inject constructor(private val personalInfoDao: PersonalInfoDao) :
    PersonalInfoRepository {
    override fun getAllItemStream(): Flow<List<PersonalInfo>> = personalInfoDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<PersonalInfo?> = personalInfoDao.getOneItem(id)

    override suspend fun deleteOneElementForId(id: Int) = personalInfoDao.deleteOneElementForId(id)

    override suspend fun insertItem(item: PersonalInfo) = personalInfoDao.insert(item)

    override suspend fun deleteItem(item: PersonalInfo) = personalInfoDao.delete(item)

    override suspend fun updateItem(item: PersonalInfo) = personalInfoDao.update(item)

    override suspend fun getLastId(): Int = personalInfoDao.getLastId()
}