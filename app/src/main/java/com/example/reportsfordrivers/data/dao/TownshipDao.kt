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

    @Query("SELECT * FROM TOWNSHIP WHERE township_eng = :name")
    suspend fun getOneItemName(name: String): Township

    @Query("UPDATE TOWNSHIP SET favorite = :value WHERE id = :id")
    suspend fun updateFavorite(value: Int, id: Int)
    @Query("UPDATE TOWNSHIP SET favorite = :value WHERE township_rus = :name")
    suspend fun updateFavoriteName(value: Int, name: String)

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township ORDER BY township_rus")
    suspend fun getSortNameRus(): List<TownshipRus>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township ORDER BY township_eng")
    suspend fun getSortNameEng(): List<TownshipEng>

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township ORDER BY rating DESC")
    suspend fun getSortRatingRus(): List<TownshipRus>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township ORDER BY rating DESC")
    suspend fun getSortRatingEng(): List<TownshipEng>

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township WHERE favorite = 1 " +
            "ORDER BY township_rus")
    suspend fun getFavoriteSortNameRus(): List<TownshipRus>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township WHERE favorite = 1 " +
            "ORDER BY township_eng")
    suspend fun getFavoriteSortNameEng(): List<TownshipEng>

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township WHERE favorite = 1 " +
            "ORDER BY rating DESC")
    suspend fun getFavoriteSortRatingRus(): List<TownshipRus>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township WHERE favorite = 1 " +
            "ORDER BY rating DESC")
    suspend fun getFavoriteSortRatingEng(): List<TownshipEng>

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE township_rus LIKE :townshipRus || '%' ORDER BY township_rus")
    suspend fun getNameTownshipSortNameRus(townshipRus: String): List<TownshipRus>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE township_eng LIKE :townshipEng || '%' ORDER BY township_eng")
    suspend fun getNameTownshipSortNameEng(townshipEng: String): List<TownshipEng>

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE township_rus LIKE :townshipRus || '%' ORDER BY rating DESC")
    suspend fun getNameTownshipSortRatingRus(townshipRus: String): List<TownshipRus>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE township_eng LIKE :townshipEng || '%' ORDER BY rating DESC")
    suspend fun getNameTownshipSortRatingEng(townshipEng: String): List<TownshipEng>

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE country_id = :countryId ORDER BY township_rus")
    suspend fun getCountryIdTownshipSortNameRus(countryId: Int): List<TownshipRus>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE country_id = :countryId ORDER BY township_eng")
    suspend fun getCountryIdTownshipSortNameEng(countryId: Int): List<TownshipEng>

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE country_id = :countryId ORDER BY rating DESC")
    suspend fun getCountryIdTownshipSortRatingRus(countryId: Int): List<TownshipRus>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE country_id = :countryId ORDER BY rating DESC")
    suspend fun getCountryIdTownshipSortRatingEng(countryId: Int): List<TownshipEng>

    //name + country (nameRus, nameEng, ratingRus, ratingEng)
    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE country_id = :countryId AND township_rus LIKE :townshipRus || '%' " +
            "ORDER BY township_rus")
    suspend fun getCountryIdNameTownshipSortNameRus(countryId: Int, townshipRus: String): List<TownshipRus>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE country_id = :countryId AND township_eng LIKE :townshipEng || '%' " +
            "ORDER BY township_eng")
    suspend fun getCountryIdNameTownshipSortNameEng(countryId: Int, townshipEng: String): List<TownshipEng>

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE country_id = :countryId AND township_rus LIKE :townshipRus || '%' " +
            "ORDER BY rating DESC")
    suspend fun getCountryIdNameTownshipSortRatingRus(countryId: Int, townshipRus: String): List<TownshipRus>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE country_id = :countryId AND township_eng LIKE :townshipEng || '%' " +
            "ORDER BY rating DESC")
    suspend fun getCountryIdNameTownshipSortRatingEng(countryId: Int, townshipEng: String): List<TownshipEng>

    //name + favorite (nameRus+, nameEng+, ratingRus+, ratingEng+)
    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND township_rus LIKE :townshipRus || '%' " +
            "ORDER BY township_rus")
    suspend fun getNameTownshipFavoriteSortNameRus(townshipRus: String): List<TownshipRus>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND township_eng LIKE :townshipEng || '%' " +
            "ORDER BY township_eng")
    suspend fun getNameTownshipFavoriteSortNameEng(townshipEng: String): List<TownshipEng>

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND township_rus LIKE :townshipRus || '%' " +
            "ORDER BY rating DESC")
    suspend fun getNameTownshipFavoriteSortRatingRus(townshipRus: String): List<TownshipRus>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND township_eng LIKE :townshipEng || '%' " +
            "ORDER BY rating DESC")
    suspend fun getNameTownshipFavoriteSortRatingEng(townshipEng: String): List<TownshipEng>

    //country + favorite (nameRus+, nameEng+, ratingRus+, ratingEng+)
    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND country_id = :countryId ORDER BY township_rus")
    suspend fun getCountryIdFavoriteSortNameRus(countryId: Int): List<TownshipRus>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND country_id = :countryId ORDER BY township_eng")
    suspend fun getCountryIdFavoriteSortNameEng(countryId: Int): List<TownshipEng>

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND country_id = :countryId ORDER BY rating DESC")
    suspend fun getCountryIdFavoriteSortRatingRus(countryId: Int): List<TownshipRus>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND country_id = :countryId ORDER BY rating DESC")
    suspend fun getCountryIdFavoriteSortRatingEng(countryId: Int): List<TownshipEng>

    //name + favorite + country (nameRus+, nameEng+, ratingRus, ratingEng)
    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND country_id = :countryId AND township_rus LIKE :townshipRus || '%' " +
            "ORDER BY township_rus")
    suspend fun getCountryIdNameTownshipFavoriteSortNameRus(countryId: Int, townshipRus: String): List<TownshipRus>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND country_id = :countryId AND township_eng LIKE :townshipEng || '%' " +
            "ORDER BY township_eng")
    suspend fun getCountryIdNameTownshipFavoriteSortNameEng(countryId: Int, townshipEng: String): List<TownshipEng>

    @Query("SELECT id, township_rus, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND country_id = :countryId AND township_rus LIKE :townshipRus || '%' " +
            "ORDER BY rating DESC")
    suspend fun getCountryIdNameTownshipFavoriteSortRatingRus(countryId: Int, townshipRus: String): List<TownshipRus>

    @Query("SELECT id, township_eng, country_id, favorite, rating FROM township " +
            "WHERE favorite = 1 AND country_id = :countryId AND township_eng LIKE :townshipEng || '%' " +
            "ORDER BY rating DESC")
    suspend fun getCountryIdNameTownshipFavoriteSortRatingEng(countryId: Int, townshipEng: String): List<TownshipEng>

    @Query("UPDATE township SET rating = :rating WHERE id = :id")
    suspend fun updateRatingForId(id: Int, rating: Int)

}