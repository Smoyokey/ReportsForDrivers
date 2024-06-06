package com.example.reportsfordrivers.data.direpositories.createreport

import com.example.reportsfordrivers.data.dao.createreport.CreatePersonalInfoDao
import com.example.reportsfordrivers.data.repositories.createreport.CreatePersonalInfoRepository
import com.example.reportsfordrivers.data.structure.createreport.CreatePersonalInfo
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

    override suspend fun updateOneElementForIdLastName(id: Int, lastName: String) =
        createPersonalInfoDao.updateOneElementForIdLastName(id, lastName)

    override suspend fun updateOneElementForIdFirstName(id: Int, firstName: String) =
        createPersonalInfoDao.updateOneElementForIdFirstName(id, firstName)

    override suspend fun updateOneElementForIdPatronymic(id: Int, patronymic: String) =
        createPersonalInfoDao.updateOneElementForIdPatronymic(id, patronymic)

    override suspend fun deleteAllElements() = createPersonalInfoDao.deleteAllElements()

}