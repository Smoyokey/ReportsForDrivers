package com.example.reportsfordrivers.data.direpositories.createreport

import com.example.reportsfordrivers.data.dao.createreport.CreateRouteDao
import com.example.reportsfordrivers.data.repositories.createreport.CreateRouteRepository
import com.example.reportsfordrivers.data.structure.createreport.CreateRoute
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

    override suspend fun updateOneElementForIdRoute(id: Int, route: String) =
        createRouteDao.updateOneElementForIdRoute(id, route)

    override suspend fun updateOneElementForIdDateDeparture(id: Int, dateDeparture: String) =
        createRouteDao.updateOneElementForIdDateDeparture(id, dateDeparture)

    override suspend fun updateOneElementForIdDateReturn(id: Int, dateReturn: String) =
        createRouteDao.updateOneElementForIdDateReturn(id, dateReturn)

    override suspend fun updateOneElementForIdDateBorderCrossingDeparture(id: Int, dateBorderCrossingDeparture: String) =
        createRouteDao.updateOneElementForIdDateBorderCrossingDeparture(id, dateBorderCrossingDeparture)

    override suspend fun updateOneElementForIdDateBorderCrossingReturn(id: Int, dateBorderCrossingReturn: String) =
        createRouteDao.updateOneElementForIdDateBorderCrossingReturn(id, dateBorderCrossingReturn)

    override suspend fun updateOneElementForIdSpeedometerReadingDeparture(id: Int, speedometerReadingDeparture: String) =
        createRouteDao.updateOneElementForIdSpeedometerReadingDeparture(id, speedometerReadingDeparture)

    override suspend fun updateOneElementForIdSpeedometerReadingReturn(id: Int, speedometerReadingReturn: String) =
        createRouteDao.updateOneElementForIdSpeedometerReadingReturn(id, speedometerReadingReturn)

    override suspend fun updateOneElementForIdFuelled(id: Int, fuelled: String) =
        createRouteDao.updateOneElementForIdFuelled(id, fuelled)

    override suspend fun deleteAllElements() = createRouteDao.deleteAllElements()
}