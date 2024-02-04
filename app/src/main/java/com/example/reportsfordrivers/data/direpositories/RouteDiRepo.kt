package com.example.reportsfordrivers.data.direpositories

import com.example.reportsfordrivers.data.dao.RouteDao
import com.example.reportsfordrivers.data.repositories.RouteRepository
import com.example.reportsfordrivers.data.structure.Route
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RouteDiRepo @Inject constructor(private val routeDao: RouteDao) : RouteRepository {
    override fun getAllItemStream(): Flow<List<Route>> = routeDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<Route?> = routeDao.getOneItem(id)

    override suspend fun insertItem(item: Route) = routeDao.insert(item)

    override suspend fun deleteItem(item: Route) = routeDao.delete(item)

    override suspend fun updateItem(item: Route) = routeDao.update(item)
}