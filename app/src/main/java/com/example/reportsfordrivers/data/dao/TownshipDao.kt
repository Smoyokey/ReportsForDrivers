package com.example.reportsfordrivers.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.Township
import com.example.reportsfordrivers.data.structure.TownshipEng
import com.example.reportsfordrivers.data.structure.TownshipRus
import kotlinx.coroutines.flow.Flow

@Dao
interface TownshipDao {

    @Insert
    suspend fun insert(item: Township)

    @Delete
    suspend fun delete(item: Township)

    @Update
    suspend fun update(item: Township)

    @Query("SELECT * FROM TOWNSHIP")
    fun getAllItem(): Flow<List<Township>>

    @Query("SELECT * FROM TOWNSHIP WHERE id = :id")
    fun getOneItem(id: Int): Flow<Township>

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township ORDER BY township_rus")
    fun getSortNameRus(): Flow<List<TownshipRus>>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township ORDER BY township_eng")
    fun getSortNameEng(): Flow<List<TownshipEng>>

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township ORDER BY rating DESC")
    fun getSortRatingRus(): Flow<List<TownshipRus>>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township ORDER BY rating DESC")
    fun getSortRatingEng(): Flow<List<TownshipEng>>

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township WHERE favorite = 1 " +
            "ORDER BY township_rus")
    fun getFavoriteSortNameRus(): Flow<List<TownshipRus>>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township WHERE favorite = 1 " +
            "ORDER BY township_eng")
    fun getFavoriteSortNameEng(): Flow<List<TownshipEng>>

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township WHERE favorite = 1 " +
            "ORDER BY rating DESC")
    fun getFavoriteSortRatingRus(): Flow<List<TownshipRus>>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township WHERE favorite = 1 " +
            "ORDER BY rating DESC")
    fun getFavoriteSortRatingEng(): Flow<List<TownshipEng>>

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE township_rus LIKE :townshipRus || '%' ORDER BY township_rus")
    fun getNameTownshipSortNameRus(townshipRus: String): Flow<List<TownshipRus>>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE township_eng LIKE :townshipEng || '%' ORDER BY township_eng")
    fun getNameTownshipSortNameEng(townshipEng: String): Flow<List<TownshipEng>>

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE township_rus LIKE :townshipRus || '%' ORDER BY rating DESC")
    fun getNameTownshipSortRatingRus(townshipRus: String): Flow<List<TownshipRus>>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE township_eng LIKE :townshipEng || '%' ORDER BY rating DESC")
    fun getNameTownshipSortRatingEng(townshipEng: String): Flow<List<TownshipEng>>

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE country_id = :countryId ORDER BY township_rus")
    fun getCountryIdTownshipSortNameRus(countryId: Int): Flow<List<TownshipRus>>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE country_id = :countryId ORDER BY township_eng")
    fun getCountryIdTownshipSortNameEng(countryId: Int): Flow<List<TownshipEng>>

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE country_id = :countryId ORDER BY rating DESC")
    fun getCountryIdTownshipSortRatingRus(countryId: Int): Flow<List<TownshipRus>>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE country_id = :countryId ORDER BY rating DESC")
    fun getCountryIdTownshipSortRatingEng(countryId: Int): Flow<List<TownshipEng>>

    //name + country (nameRus, nameEng, ratingRus, ratingEng)
    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE country_id = :countryId AND township_rus LIKE :townshipRus || '%' " +
            "ORDER BY township_rus")
    fun getCountryIdNameTownshipSortNameRus(countryId: Int, townshipRus: String): Flow<List<TownshipRus>>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE country_id = :countryId AND township_eng LIKE :townshipEng || '%' " +
            "ORDER BY township_eng")
    fun getCountryIdNameTownshipSortNameEng(countryId: Int, townshipEng: String): Flow<List<TownshipEng>>

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE country_id = :countryId AND township_rus LIKE :townshipRus || '%' " +
            "ORDER BY rating DESC")
    fun getCountryIdNameTownshipSortRatingRus(countryId: Int, townshipRus: String): Flow<List<TownshipRus>>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE country_id = :countryId AND township_eng LIKE :townshipEng || '%' " +
            "ORDER BY rating DESC")
    fun getCountryIdNameTownshipSortRatingEng(countryId: Int, townshipEng: String): Flow<List<TownshipEng>>

    //name + favorite (nameRus+, nameEng+, ratingRus+, ratingEng+)
    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND township_rus LIKE :townshipRus || '%' " +
            "ORDER BY township_rus")
    fun getNameTownshipFavoriteSortNameRus(townshipRus: String): Flow<List<TownshipRus>>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND township_eng LIKE :townshipEng || '%' " +
            "ORDER BY township_eng")
    fun getNameTownshipFavoriteSortNameEng(townshipEng: String): Flow<List<TownshipEng>>

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND township_rus LIKE :townshipRus || '%' " +
            "ORDER BY rating DESC")
    fun getNameTownshipFavoriteSortRatingRus(townshipRus: String): Flow<List<TownshipRus>>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND township_eng LIKE :townshipEng || '%' " +
            "ORDER BY rating DESC")
    fun getNameTownshipFavoriteSortRatingEng(townshipEng: String): Flow<List<TownshipEng>>

    //country + favorite (nameRus+, nameEng+, ratingRus+, ratingEng+)
    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND country_id = :countryId ORDER BY township_rus")
    fun getCountryIdFavoriteSortNameRus(countryId: Int): Flow<List<TownshipRus>>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND country_id = :countryId ORDER BY township_eng")
    fun getCountryIdFavoriteSortNameEng(countryId: Int): Flow<List<TownshipEng>>

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND country_id = :countryId ORDER BY rating DESC")
    fun getCountryIdFavoriteSortRatingRus(countryId: Int): Flow<List<TownshipRus>>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND country_id = :countryId ORDER BY rating DESC")
    fun getCountryIdFavoriteSortRatingEng(countryId: Int): Flow<List<TownshipEng>>

    //name + favorite + country (nameRus+, nameEng+, ratingRus, ratingEng)
    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND country_id = :countryId AND township_rus LIKE :townshipRus || '%' " +
            "ORDER BY township_rus")
    fun getCountryIdNameTownshipFavoriteSortNameRus(countryId: Int, townshipRus: String): Flow<List<TownshipRus>>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND country_id = :countryId AND township_eng LIKE :townshipEng || '%' " +
            "ORDER BY township_eng")
    fun getCountryIdNameTownshipFavoriteSortNameEng(countryId: Int, townshipEng: String): Flow<List<TownshipEng>>

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND country_id = :countryId AND township_rus LIKE :townshipRus || '%' " +
            "ORDER BY rating DESC")
    fun getCountryIdNameTownshipFavoriteSortRatingRus(countryId: Int, townshipRus: String): Flow<List<TownshipRus>>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND country_id = :countryId AND township_eng LIKE :townshipEng || '%' " +
            "ORDER BY rating DESC")
    fun getCountryIdNameTownshipFavoriteSortRatingEng(countryId: Int, townshipEng: String): Flow<List<TownshipEng>>

}