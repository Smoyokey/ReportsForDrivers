package com.example.reportsfordrivers.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.PersonalInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonalInfoDao {

    @Insert
    suspend fun insert(item: PersonalInfo)

    @Delete
    suspend fun delete(item: PersonalInfo)

    @Update
    suspend fun update(item: PersonalInfo)

    @Query("SELECT * FROM PERSONAL_INFO")
    fun getAllItem(): Flow<List<PersonalInfo>>

    @Query("SELECT * FROM PERSONAL_INFO WHERE id = :id")
    fun getOneItem(id: Int): Flow<PersonalInfo>

    @Query("DELETE FROM PERSONAL_INFO WHERE id = :id")
    suspend fun deleteOneElementForId(id: Int)

    @Query("SELECT id FROM PERSONAL_INFO ORDER BY id DESC LIMIT 1")
    suspend fun getLastId(): Int
}