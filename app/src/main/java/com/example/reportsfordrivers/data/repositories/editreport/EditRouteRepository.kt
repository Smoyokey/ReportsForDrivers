package com.example.reportsfordrivers.data.repositories.editreport

import com.example.reportsfordrivers.data.structure.editreport.EditRoute
import kotlinx.coroutines.flow.Flow

interface EditRouteRepository {

    fun getAllItemStream(): Flow<List<EditRoute>>

    fun getOneItemStream(id: Int): Flow<EditRoute?>

    suspend fun insertItem(item: EditRoute)

    suspend fun deleteItem(item: EditRoute)

    suspend fun updateItem(item: EditRoute)

    suspend fun updateOneElementForIdRoute(id: Int, route: String)

    suspend fun updateOneElementForIdDateDeparture(id: Int, dateDeparture: String)

    suspend fun updateOneElementForIdDateReturn(id: Int, dateReturn: String)

    suspend fun updateOneElementForIdDateBorderCrossingDeparture(id: Int, dateBorderCrossingDeparture: String)

    suspend fun updateOneElementForIdDateBorderCrossingReturn(id: Int, dateBorderCrossingReturn: String)

    suspend fun updateOneElementForIdSpeedometerReadingDeparture(id: Int, speedometerReadingDeparture: String)

    suspend fun updateOneElementForIdSpeedometerReadingReturn(id: Int, speedometerReadingReturn: String)

    suspend fun updateOneElementForIdFuelled(id: Int, fuelled: String)

    suspend fun deleteAllElements()
}