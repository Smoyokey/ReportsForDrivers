package com.example.reportsfordrivers.data.dao

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

    @Query("SELECT id, full_name_country_rus, short_name_country, rating, favorite FROM country " +
            "ORDER BY full_name_country_rus")
    fun getSortNameRus(): Flow<List<CountryRus>>

    @Query("SELECT id, full_name_country_eng, short_name_country, rating, favorite FROM country " +
            "ORDER BY full_name_country_eng")
    fun getSortNameEng(): Flow<List<CountryEng>>

    @Query("SELECT id, full_name_country_rus, short_name_country, rating, favorite FROM country " +
            "ORDER BY rating DESC")
    fun getSortRatingRus(): Flow<List<CountryRus>>

    @Query("SELECT id, full_name_country_eng, short_name_country, rating, favorite FROM country " +
            "ORDER BY rating DESC")
    fun getSortRatingEng(): Flow<List<CountryEng>>

    @Query("SELECT id, full_name_country_rus, short_name_country, rating, favorite FROM country " +
            "WHERE favorite = 1 ORDER BY full_name_country_rus")
    fun getFavoriteSortNameRus(): Flow<List<CountryRus>>

    @Query("SELECT id, full_name_country_eng, short_name_country, rating,favorite FROM country " +
            "WHERE favorite = 1 ORDER BY full_name_country_eng")
    fun getFavoriteSortNameEng(): Flow<List<CountryEng>>

    @Query("SELECT id, full_name_country_rus, short_name_country, rating, favorite FROM country " +
            "WHERE favorite = 1 ORDER BY rating DESC")
    fun getFavoriteSortRatingRus(): Flow<List<CountryRus>>

    @Query("SELECT id, full_name_country_eng, short_name_country, rating, favorite FROM country " +
            "WHERE favorite = 1 ORDER BY rating DESC")
    fun getFavoriteSortRatingEng(): Flow<List<CountryEng>>

    @Query("SELECT id, full_name_country_rus, short_name_country, rating, favorite FROM country " +
            "WHERE full_name_country_rus LIKE :fullNameCountryRus || '%' ORDER BY full_name_country_rus")
    fun getNameCountrySortNameRus(fullNameCountryRus: String): Flow<List<CountryRus>>

    @Query("SELECT id, full_name_country_eng, short_name_country, rating, favorite FROM country " +
            "WHERE full_name_country_eng LIKE :fullNameCountryEng || '%' ORDER BY full_name_country_eng")
    fun getNameCountrySortNameEng(fullNameCountryEng: String): Flow<List<CountryEng>>

    @Query("SELECT id, full_name_country_rus, short_name_country, rating, favorite FROM country " +
            "WHERE full_name_country_rus LIKE :fullNameCountryRus || '%' ORDER BY rating DESC")
    fun getNameCountrySortRatingRus(fullNameCountryRus: String): Flow<List<CountryRus>>

    @Query("SELECT id, full_name_country_eng, short_name_country, rating, favorite FROM country " +
            "WHERE full_name_country_eng LIKE :fullNameCountryEng || '%' ORDER BY rating DESC")
    fun getNameCountrySortRatingEng(fullNameCountryEng: String): Flow<List<CountryEng>>

    @Query("SELECT id, full_name_country_rus, short_name_country, rating, favorite FROM country " +
            "WHERE favorite = 1 AND full_name_country_rus LIKE :fullNameCountryRus || '%' " +
            "ORDER BY full_name_country_rus")
    fun getNameCountryFavoriteSortNameRus(fullNameCountryRus: String): Flow<List<CountryRus>>

    @Query("SELECT id, full_name_country_eng, short_name_country, rating, favorite FROM country " +
            "WHERE favorite = 1 AND full_name_country_eng LIKE :fullNameCountryEng || '%' " +
            "ORDER BY full_name_country_eng")
    fun getNameCountryFavoriteSortNameEng(fullNameCountryEng: String): Flow<List<CountryEng>>

    @Query("SELECT id, full_name_country_rus, short_name_country, rating, favorite FROM country " +
            "WHERE favorite = 1 AND full_name_country_rus LIKE :fullNameCountryRus || '%' " +
            "ORDER BY rating DESC")
    fun getNameCountryFavoriteSortRatingRus(fullNameCountryRus: String): Flow<List<CountryRus>>

    @Query("SELECT id, full_name_country_eng, short_name_country, rating, favorite FROM country " +
            "WHERE favorite = 1 AND full_name_country_eng LIKE :fullNameCountryEng || '%' " +
            "ORDER BY rating DESC")
    fun getNameCountryFavoriteSortRatingEng(fullNameCountryEng: String): Flow<List<CountryEng>>
}