package com.example.reportsfordrivers.data.dao.createReport

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.createReport.CreateExpensesTrip
import kotlinx.coroutines.flow.Flow

@Dao
interface CreateExpensesTripDao {

    @Insert
    suspend fun insert(item: CreateExpensesTrip)

    @Delete
    suspend fun delete(item: CreateExpensesTrip)

    @Update
    suspend fun update(item: CreateExpensesTrip)

    @Query("SELECT * FROM create_expenses_trip")
    fun getAllItem(): Flow<List<CreateExpensesTrip>>

    @Query("SELECT * FROM create_expenses_trip WHERE id = :id")
    fun getOneItem(id: Int): Flow<CreateExpensesTrip>
}