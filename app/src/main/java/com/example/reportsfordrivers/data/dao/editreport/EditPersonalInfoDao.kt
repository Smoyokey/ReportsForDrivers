package com.example.reportsfordrivers.data.dao.editreport

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.editreport.EditPersonalInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface EditPersonalInfoDao {

    @Insert
    suspend fun insert(item: EditPersonalInfo)

    @Delete
    suspend fun delete(item: EditPersonalInfo)

    @Update
    suspend fun update(item: EditPersonalInfo)

    @Query("SELECT * FROM edit_personal_info")
    fun getAllItem(): Flow<List<EditPersonalInfo>>

    @Query("SELECT * FROM edit_personal_info WHERE id = :id")
    fun getOneItem(id: Int): Flow<EditPersonalInfo>

    @Query("UPDATE edit_personal_info SET last_name = :lastName WHERE id = :id")
    suspend fun updateOneElementForIdLastName(id: Int, lastName: String)

    @Query("UPDATE edit_personal_info SET first_name = :firstName WHERE id = :id")
    suspend fun updateOneElementForIdFirstName(id: Int, firstName: String)

    @Query("UPDATE edit_personal_info SET patronymic = :patronymic WHERE id = :id")
    suspend fun updateOneElementForIdPatronymic(id: Int, patronymic: String)

    @Query("DELETE FROM edit_personal_info")
    suspend fun deleteAllElements()
}