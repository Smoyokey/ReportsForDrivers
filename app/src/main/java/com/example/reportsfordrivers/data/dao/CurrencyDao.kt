package com.example.reportsfordrivers.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.Currency
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Insert
    suspend fun insert(item: Currency)

    @Delete
    suspend fun delete(item: Currency)

    @Update
    suspend fun update(item: Currency)

    @Query("SELECT * FROM CURRENCY")
    fun getAllItem() : Flow<List<Currency>>

    @Query("SELECT * FROM CURRENCY WHERE id = :id")
    fun getOneItem(id: Int): Flow<Currency>
}