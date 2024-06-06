package com.example.reportsfordrivers.data.repositories.createreport

import com.example.reportsfordrivers.data.structure.createreport.CreateRoute
import kotlinx.coroutines.flow.Flow

interface CreateRouteRepository {

    fun getAllItemStream(): Flow<List<CreateRoute>>

    fun getOneItemStream(id: Int): Flow<CreateRoute?>

    suspend fun insertItem(item: CreateRoute)

    suspend fun deleteItem(item: CreateRoute)

    suspend fun updateItem(item: CreateRoute)

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