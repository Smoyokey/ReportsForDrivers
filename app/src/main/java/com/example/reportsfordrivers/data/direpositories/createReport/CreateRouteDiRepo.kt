package com.example.reportsfordrivers.data.direpositories.createReport

import com.example.reportsfordrivers.data.dao.createReport.CreateRouteDao
import com.example.reportsfordrivers.data.repositories.createReport.CreateRouteRepository
import com.example.reportsfordrivers.data.structure.createReport.CreateRoute
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateRouteDiRepo @Inject constructor(private val createRouteDao: CreateRouteDao) :
    CreateRouteRepository {

    override fun getAllItemStream(): Flow<List<CreateRoute>> = createRouteDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<CreateRoute?> = createRouteDao.getOneItem(id)

    override suspend fun insertItem(item: CreateRoute) = createRouteDao.insert(item)

    override suspend fun deleteItem(item: CreateRoute) = createRouteDao.delete(item)

    override suspend fun updateItem(item: CreateRoute) = createRouteDao.update(item)
}