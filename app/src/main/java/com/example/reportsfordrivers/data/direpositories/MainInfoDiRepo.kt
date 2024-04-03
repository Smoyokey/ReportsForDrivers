package com.example.reportsfordrivers.data.direpositories

import com.example.reportsfordrivers.data.dao.MainInfoDao
import com.example.reportsfordrivers.data.repositories.MainInfoRepository
import com.example.reportsfordrivers.data.structure.MainInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainInfoDiRepo @Inject constructor(private val mainInfoDao: MainInfoDao) : MainInfoRepository
{
    override fun getAllItemStream(): Flow<List<MainInfo>> = mainInfoDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<MainInfo?> = mainInfoDao.getOneItem(id)

    override suspend fun deleteOneElementForId(id: Int) = mainInfoDao.deleteOneElementForId(id)

    override suspend fun insertItem(item: MainInfo) = mainInfoDao.insert(item)

    override suspend fun deleteItem(item: MainInfo) = mainInfoDao.delete(item)

    override suspend fun updateItem(item: MainInfo) = mainInfoDao.update(item)
}