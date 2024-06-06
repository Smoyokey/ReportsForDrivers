package com.example.reportsfordrivers.data.dao.createreport

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.createreport.CreateExpensesTrip
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

    @Query("UPDATE create_expenses_trip SET date = :date WHERE id = :id")
    suspend fun updateOneElementForIdDate(id: Int, date: String)

    @Query("UPDATE create_expenses_trip SET document_number = :documentNumber WHERE id = :id")
    suspend fun updateOneElementForIdDocumentNumber(id: Int, documentNumber: String)

    @Query("UPDATE create_expenses_trip SET expense_item = :expenseItem WHERE id = :id")
    suspend fun updateOneElementForIdExpenseItem(id: Int, expenseItem: String)

    @Query("UPDATE create_expenses_trip SET sum = :sum WHERE id = :id")
    suspend fun updateOneElementForIdSum(id: Int, sum: String)

    @Query("UPDATE create_expenses_trip SET currency = :currency WHERE id = :id")
    suspend fun updateOneElementForIdCurrency(id: Int, currency: String)

    @Query("DELETE FROM create_expenses_trip")
    suspend fun deleteAllElements()

    @Query("DELETE FROM create_expenses_trip WHERE id = :id")
    suspend fun deleteOneElementForId(id: Int)

    @Query("UPDATE create_expenses_trip SET is_add = :isAdd WHERE id = :id")
    suspend fun updateOneElementForIdIsAdd(id: Int, isAdd: Int)
}