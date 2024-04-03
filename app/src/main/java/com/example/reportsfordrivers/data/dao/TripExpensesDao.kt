package com.example.reportsfordrivers.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.TripExpenses
import kotlinx.coroutines.flow.Flow

@Dao
interface TripExpensesDao {

    @Insert
    suspend fun insert(item: TripExpenses)

    @Delete
    suspend fun delete(item: TripExpenses)

    @Update
    suspend fun update(item: TripExpenses)

    @Query("SELECT * FROM TRIP_EXPENSES")
    fun getAllItem(): Flow<List<TripExpenses>>

    @Query("SELECT * FROM TRIP_EXPENSES WHERE id = :id")
    fun getOneItem(id: Int): Flow<TripExpenses>

    @Query("DELETE FROM TRIP_EXPENSES WHERE id = :id")
    suspend fun deleteOneElementForId(id: Int)
}