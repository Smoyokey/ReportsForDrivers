package com.example.reportsfordrivers.data.direpositories.editreport

import com.example.reportsfordrivers.data.dao.editreport.EditPersonalInfoDao
import com.example.reportsfordrivers.data.repositories.editreport.EditPersonalInfoRepository
import com.example.reportsfordrivers.data.structure.editreport.EditPersonalInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditPersonalInfoDiRepo @Inject constructor(private val editPersonalInfoDao: EditPersonalInfoDao) :
    EditPersonalInfoRepository {

    override fun getAllItemStream(): Flow<List<EditPersonalInfo>> = editPersonalInfoDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<EditPersonalInfo> = editPersonalInfoDao.getOneItem(id)

    override suspend fun insertItem(item: EditPersonalInfo) = editPersonalInfoDao.insert(item)

    override suspend fun deleteItem(item: EditPersonalInfo) = editPersonalInfoDao.delete(item)

    override suspend fun updateItem(item: EditPersonalInfo) = editPersonalInfoDao.update(item)

    override suspend fun updateOneElementForIdLastName(id: Int, lastName: String) =
        editPersonalInfoDao.updateOneElementForIdLastName(id, lastName)

    override suspend fun updateOneElementForIdFirstName(id: Int, firstName: String) =
        editPersonalInfoDao.updateOneElementForIdFirstName(id, firstName)

    override suspend fun updateOneElementForIdPatronymic(id: Int, patronymic: String) =
        editPersonalInfoDao.updateOneElementForIdPatronymic(id, patronymic)

    override suspend fun deleteAllElements() = editPersonalInfoDao.deleteAllElements()



}