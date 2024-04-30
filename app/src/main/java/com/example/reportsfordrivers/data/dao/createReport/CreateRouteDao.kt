package com.example.reportsfordrivers.data.dao.createReport

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.createReport.CreateRoute
import kotlinx.coroutines.flow.Flow

@Dao
interface CreateRouteDao {

    @Insert
    suspend fun insert(item: CreateRoute)

    @Delete
    suspend fun delete(item: CreateRoute)

    @Update
    suspend fun update(item: CreateRoute)

    @Query("SELECT * FROM create_route")
    fun getAllItem(): Flow<List<CreateRoute>>

    @Query("SELECT * FROM create_route WHERE id = :id")
    fun getOneItem(id: Int): Flow<CreateRoute>

    @Query("UPDATE create_route SET route = :route WHERE id = :id")
    suspend fun updateOneElementForIdRoute(id: Int, route: String)

    @Query("UPDATE create_route SET date_departure = :dateDeparture WHERE id = :id")
    suspend fun updateOneElementForIdDateDeparture(id: Int, dateDeparture: String)

    @Query("UPDATE create_route SET date_return = :dateReturn WHERE id = :id")
    suspend fun updateOneElementForIdDateReturn(id: Int, dateReturn: String)

    @Query("UPDATE create_route SET date_border_crossing_departure = :dateBorderCrossingDeparture " +
            "WHERE id = :id")
    suspend fun updateOneElementForIdDateBorderCrossingDeparture(id: Int, dateBorderCrossingDeparture: String)

    @Query("UPDATE create_route SET date_border_crossing_return = :dateBorderCrossingReturn " +
            "WHERE id = :id")
    suspend fun updateOneElementForIdDateBorderCrossingReturn(id: Int, dateBorderCrossingReturn: String)

    @Query("UPDATE create_route SET speedometer_reading_departure = :speedometerReadingDeparture " +
            "WHERE id = :id")
    suspend fun updateOneElementForIdSpeedometerReadingDeparture(id: Int, speedometerReadingDeparture: String)

    @Query("UPDATE create_route SET speedometer_reading_return = :speedometerReadingReturn " +
            "WHERE id = :id")
    suspend fun updateOneElementForIdSpeedometerReadingReturn(id: Int, speedometerReadingReturn: String)

    @Query("UPDATE create_route SET fuelled = :fuelled WHERE id = :id")
    suspend fun updateOneElementForIdFuelled(id: Int, fuelled: String)

    @Query("DELETE FROM create_route")
    suspend fun deleteAllElements()
}