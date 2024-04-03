package com.example.reportsfordrivers.data.direpositories

import com.example.reportsfordrivers.data.dao.TrailerDao
import com.example.reportsfordrivers.data.repositories.TrailerRepository
import com.example.reportsfordrivers.data.structure.Trailer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrailerDiRepo @Inject constructor(private val trailerDao: TrailerDao) : TrailerRepository {
    override fun getAllItemStream(): Flow<List<Trailer>> = trailerDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<Trailer?> = trailerDao.getOneItem(id)

    override suspend fun deleteOneElementForId(id: Int) = trailerDao.deleteOneElementForId(id)

    override suspend fun insertItem(item: Trailer) = trailerDao.insert(item)

    override suspend fun deleteItem(item: Trailer) = trailerDao.delete(item)

    override suspend fun updateItem(item: Trailer) = trailerDao.update(item)
}