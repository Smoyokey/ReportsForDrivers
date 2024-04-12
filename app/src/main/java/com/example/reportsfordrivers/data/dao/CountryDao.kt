package com.example.reportsfordrivers.data.dao

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.reportsfordrivers.data.structure.Country
import com.example.reportsfordrivers.data.structure.CountryEng
import com.example.reportsfordrivers.data.structure.CountryRus
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {

    @Insert
    suspend fun insert(item: Country)

    @Delete
    suspend fun delete(item: Country)

    @Update
    suspend fun update(item: Country)

    @Query("SELECT * FROM COUNTRY")
    fun getAllItem(): Flow<List<Country>>

    @Query("SELECT * FROM COUNTRY WHERE id = :id")
    fun getOneItem(id: Int): Flow<Country>

    @Query("UPDATE COUNTRY SET favorite = :value WHERE id = :id")
    suspend fun updateFavorite(value: Int, id: Int)

    @Query("SELECT id, full_name_country_rus, short_name_country, rating, favorite FROM country " +
            "ORDER BY full_name_country_rus")
    suspend fun getSortNameRus(): List<CountryRus>

    @Query("SELECT id, full_name_country_eng, short_name_country, rating, favorite FROM country " +
            "ORDER BY full_name_country_eng")
    suspend fun getSortNameEng(): List<CountryEng>

    @Query("SELECT id, full_name_country_rus, short_name_country, rating, favorite FROM country " +
            "ORDER BY rating DESC")
    suspend fun getSortRatingRus(): List<CountryRus>

    @Query("SELECT id, full_name_country_eng, short_name_country, rating, favorite FROM country " +
            "ORDER BY rating DESC")
    suspend fun getSortRatingEng(): List<CountryEng>

    @Query("SELECT id, full_name_country_rus, short_name_country, rating, favorite FROM country " +
            "WHERE favorite = 1 ORDER BY full_name_country_rus")
    suspend fun getFavoriteSortNameRus(): List<CountryRus>

    @Query("SELECT id, full_name_country_eng, short_name_country, rating,favorite FROM country " +
            "WHERE favorite = 1 ORDER BY full_name_country_eng")
    suspend fun getFavoriteSortNameEng(): List<CountryEng>

    @Query("SELECT id, full_name_country_rus, short_name_country, rating, favorite FROM country " +
            "WHERE favorite = 1 ORDER BY rating DESC")
    suspend fun getFavoriteSortRatingRus(): List<CountryRus>

    @Query("SELECT id, full_name_country_eng, short_name_country, rating, favorite FROM country " +
            "WHERE favorite = 1 ORDER BY rating DESC")
    suspend fun getFavoriteSortRatingEng(): List<CountryEng>

    @Query("SELECT id, full_name_country_rus, short_name_country, rating, favorite FROM country " +
            "WHERE full_name_country_rus LIKE :fullNameCountryRus || '%' ORDER BY full_name_country_rus")
    suspend fun getNameCountrySortNameRus(fullNameCountryRus: String): List<CountryRus>

    @Query("SELECT id, full_name_country_eng, short_name_country, rating, favorite FROM country " +
            "WHERE full_name_country_eng LIKE :fullNameCountryEng || '%' ORDER BY full_name_country_eng")
    suspend fun getNameCountrySortNameEng(fullNameCountryEng: String): List<CountryEng>

    @Query("SELECT id, full_name_country_rus, short_name_country, rating, favorite FROM country " +
            "WHERE full_name_country_rus LIKE :fullNameCountryRus || '%' ORDER BY rating DESC")
    suspend fun getNameCountrySortRatingRus(fullNameCountryRus: String): List<CountryRus>

    @Query("SELECT id, full_name_country_eng, short_name_country, rating, favorite FROM country " +
            "WHERE full_name_country_eng LIKE :fullNameCountryEng || '%' ORDER BY rating DESC")
    suspend fun getNameCountrySortRatingEng(fullNameCountryEng: String): List<CountryEng>

    @Query("SELECT id, full_name_country_rus, short_name_country, rating, favorite FROM country " +
            "WHERE favorite = 1 AND full_name_country_rus LIKE :fullNameCountryRus || '%' " +
            "ORDER BY full_name_country_rus")
    suspend fun getNameCountryFavoriteSortNameRus(fullNameCountryRus: String): List<CountryRus>

    @Query("SELECT id, full_name_country_eng, short_name_country, rating, favorite FROM country " +
            "WHERE favorite = 1 AND full_name_country_eng LIKE :fullNameCountryEng || '%' " +
            "ORDER BY full_name_country_eng")
    suspend fun getNameCountryFavoriteSortNameEng(fullNameCountryEng: String): List<CountryEng>

    @Query("SELECT id, full_name_country_rus, short_name_country, rating, favorite FROM country " +
            "WHERE favorite = 1 AND full_name_country_rus LIKE :fullNameCountryRus || '%' " +
            "ORDER BY rating DESC")
    suspend fun getNameCountryFavoriteSortRatingRus(fullNameCountryRus: String): List<CountryRus>

    @Query("SELECT id, full_name_country_eng, short_name_country, rating, favorite FROM country " +
            "WHERE favorite = 1 AND full_name_country_eng LIKE :fullNameCountryEng || '%' " +
            "ORDER BY rating DESC")
    suspend fun getNameCountryFavoriteSortRatingEng(fullNameCountryEng: String): List<CountryEng>
}