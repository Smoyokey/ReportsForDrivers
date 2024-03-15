package com.example.reportsfordrivers.data.direpositories

import com.example.reportsfordrivers.data.dao.TownshipDao
import com.example.reportsfordrivers.data.repositories.TownshipRepository
import com.example.reportsfordrivers.data.structure.Township
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TownshipDiRepo @Inject constructor(private val townshipDao: TownshipDao): TownshipRepository {
    override fun getAllItemStream(): Flow<List<Township>> = townshipDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<Township?> = townshipDao.getOneItem(id)

    override suspend fun insertItem(item: Township) = townshipDao.insert(item)

    override suspend fun deleteItem(item: Township) = townshipDao.delete(item)

    override suspend fun updateItem(item: Township) = townshipDao.update(item)
}