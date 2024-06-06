package com.example.reportsfordrivers.data.dao.editreport

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.editreport.EditProgressReports
import kotlinx.coroutines.flow.Flow

@Dao
interface EditProgressReportsDao {

    @Insert
    suspend fun insert(item: EditProgressReports)

    @Delete
    suspend fun delete(item: EditProgressReports)

    @Update
    suspend fun update(item: EditProgressReports)

    @Query("SELECT * FROM edit_progress_reports")
    fun getAllItem(): Flow<List<EditProgressReports>>

    @Query("SELECT * FROM edit_progress_reports WHERE id = :id")
    fun getOneItem(id: Int): Flow<EditProgressReports>

    @Query("UPDATE edit_progress_reports SET date = :date WHERE id = :id")
    suspend fun updateOneElementForIdDate(id: Int, date: String)

    @Query("UPDATE edit_progress_reports SET country = :country WHERE id = :id")
    suspend fun updateOneElementForIdCountry(id: Int, country: String)

    @Query("UPDATE edit_progress_reports SET township_one = :townshipOne WHERE id = :id")
    suspend fun updateOneElementForIdTownshipOne(id: Int, townshipOne: String)

    @Query("UPDATE edit_progress_reports SET township_two = :townshipTwo WHERE id = :id")
    suspend fun updateOneElementForIdTownshipTwo(id: Int, townshipTwo: String)

    @Query("UPDATE edit_progress_reports SET distance = :distance WHERE id = :id")
    suspend fun updateOneElementForIdDistance(id: Int, distance: String)

    @Query("UPDATE edit_progress_reports SET weight = :weight WHERE id = :id")
    suspend fun updateOneElementForIdWeight(id: Int, weight: String)

    @Query("UPDATE edit_progress_reports SET is_add = :isAdd WHERE id = :id")
    suspend fun updateOneElementForIdIsAdd(id: Int, isAdd: Int)

    @Query("DELETE FROM edit_progress_reports")
    suspend fun deleteAllElements()

    @Query("DELETE FROM edit_progress_reports WHERE id = :id")
    suspend fun deleteOneElementForId(id: Int)
}