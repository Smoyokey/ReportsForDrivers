package com.example.reportsfordrivers.data.direpositories.editreport

import com.example.reportsfordrivers.data.dao.editreport.EditRouteDao
import com.example.reportsfordrivers.data.repositories.editreport.EditRouteRepository
import com.example.reportsfordrivers.data.structure.editreport.EditRoute
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditRouteDiRepo @Inject constructor(private val editRouteDao: EditRouteDao) :
    EditRouteRepository {

    override fun getAllItemStream(): Flow<List<EditRoute>> = editRouteDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<EditRoute?> = editRouteDao.getOneItem(id)

    override suspend fun insertItem(item: EditRoute) = editRouteDao.insert(item)

    override suspend fun deleteItem(item: EditRoute) = editRouteDao.insert(item)

    override suspend fun updateItem(item: EditRoute) = editRouteDao.update(item)

    override suspend fun updateOneElementForIdRoute(id: Int, route: String) =
        editRouteDao.updateOneElementForIdRoute(id, route)

    override suspend fun updateOneElementForIdDateDeparture(id: Int, dateDeparture: String) =
        editRouteDao.updateOneElementForIdDateDeparture(id, dateDeparture)

    override suspend fun updateOneElementForIdDateReturn(id: Int, dateReturn: String) =
        editRouteDao.updateOneElementForIdDateReturn(id, dateReturn)

    override suspend fun updateOneElementForIdDateBorderCrossingDeparture(id: Int, dateBorderCrossingDeparture: String) =
        editRouteDao.updateOneElementForIdDateBorderCrossingDeparture(id, dateBorderCrossingDeparture)

    override suspend fun updateOneElementForIdDateBorderCrossingReturn(id: Int, dateBorderCrossingReturn: String) =
        editRouteDao.updateOneElementForIdDateBorderCrossingReturn(id, dateBorderCrossingReturn)

    override suspend fun updateOneElementForIdSpeedometerReadingDeparture(id: Int, speedometerReadingDeparture: String) =
        editRouteDao.updateOneElementForIdSpeedometerReadingDeparture(id, speedometerReadingDeparture)

    override suspend fun updateOneElementForIdSpeedometerReadingReturn(id: Int, speedometerReadingReturn: String) =
        editRouteDao.updateOneElementForIdSpeedometerReadingReturn(id, speedometerReadingReturn)

    override suspend fun updateOneElementForIdFuelled(id: Int, fuelled: String) =
        editRouteDao.updateOneElementForIdFuelled(id, fuelled)

    override suspend fun deleteAllElements() = editRouteDao.deleteAllElements()
}