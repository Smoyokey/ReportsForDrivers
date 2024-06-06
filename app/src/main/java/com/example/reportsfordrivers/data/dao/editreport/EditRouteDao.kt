package com.example.reportsfordrivers.data.dao.editreport

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.editreport.EditRoute
import kotlinx.coroutines.flow.Flow

@Dao
interface EditRouteDao {

    @Insert
    suspend fun insert(item: EditRoute)

    @Delete
    suspend fun delete(item: EditRoute)

    @Update
    suspend fun update(item: EditRoute)

    @Query("SELECT * FROM edit_route")
    fun getAllItem(): Flow<List<EditRoute>>

    @Query("SELECT * FROM edit_route WHERE id = :id")
    fun getOneItem(id: Int): Flow<EditRoute>

    @Query("UPDATE edit_route SET route = :route WHERE id = :id")
    suspend fun updateOneElementForIdRoute(id: Int, route: String)

    @Query("UPDATE edit_route SET date_departure = :dateDeparture WHERE id = :id")
    suspend fun updateOneElementForIdDateDeparture(id: Int, dateDeparture: String)

    @Query("UPDATE edit_route SET date_return = :dateReturn WHERE id = :id")
    suspend fun updateOneElementForIdDateReturn(id: Int, dateReturn: String)

    @Query("UPDATE edit_route SET date_border_crossing_departure = :dateBorderCrossingDeparture " +
            "WHERE id = :id")
    suspend fun updateOneElementForIdDateBorderCrossingDeparture(id: Int, dateBorderCrossingDeparture: String)

    @Query("UPDATE edit_route SET date_border_crossing_return = :dateBorderCrossingReturn " +
            "WHERE id = :id")
    suspend fun updateOneElementForIdDateBorderCrossingReturn(id: Int, dateBorderCrossingReturn: String)

    @Query("UPDATE edit_route SET speedometer_reading_departure = :speedometerReadingDeparture " +
            "WHERE id = :id")
    suspend fun updateOneElementForIdSpeedometerReadingDeparture(id: Int, speedometerReadingDeparture: String)

    @Query("UPDATE edit_route SET speedometer_reading_return = :speedometerReadingReturn " +
            "WHERE id = :id")
    suspend fun updateOneElementForIdSpeedometerReadingReturn(id: Int, speedometerReadingReturn: String)

    @Query("UPDATE edit_route SET fuelled = :fuelled WHERE id = :id")
    suspend fun updateOneElementForIdFuelled(id: Int, fuelled: String)

    @Query("DELETE FROM edit_route")
    suspend fun deleteAllElements()
}