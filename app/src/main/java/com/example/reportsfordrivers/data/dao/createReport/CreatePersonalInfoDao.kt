package com.example.reportsfordrivers.data.dao.createReport

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.createReport.CreatePersonalInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface CreatePersonalInfoDao {

    @Insert
    suspend fun insert(item: CreatePersonalInfo)

    @Delete
    suspend fun delete(item: CreatePersonalInfo)

    @Update
    suspend fun update(item: CreatePersonalInfo)

    @Query("SELECT * FROM create_personal_info")
    fun getAllItem(): Flow<List<CreatePersonalInfo>>

    @Query("SELECT * FROM create_personal_info WHERE id = :id")
    fun getOneItem(id: Int): Flow<CreatePersonalInfo>

    @Query("UPDATE create_personal_info SET last_name = :lastName WHERE id = :id")
    suspend fun updateOneElementForIdLastName(id: Int, lastName: String)

    @Query("UPDATE create_personal_info SET first_name = :firstName WHERE id = :id")
    suspend fun updateOneElementForIdFirstName(id: Int, firstName: String)

    @Query("UPDATE create_personal_info SET patronymic = :patronymic WHERE id = :id")
    suspend fun updateOneElementForIdPatronymic(id: Int, patronymic: String)

    @Query("DELETE FROM create_personal_info")
    suspend fun deleteAllElements()
}