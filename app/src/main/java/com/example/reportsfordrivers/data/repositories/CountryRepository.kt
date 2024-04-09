package com.example.reportsfordrivers.data.repositories

import com.example.reportsfordrivers.data.structure.Country
import com.example.reportsfordrivers.data.structure.CountryEng
import com.example.reportsfordrivers.data.structure.CountryRus
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    fun getAllItemStream(): Flow<List<Country>>

    fun getOneItemStream(id: Int) : Flow<Country?>

    suspend fun insertItem(item: Country)

    suspend fun deleteItem(item: Country)

    suspend fun updateItem(item: Country)

    fun getSortNameRus(): Flow<List<CountryRus>>

    fun getSortNameEng(): Flow<List<CountryEng>>

    fun getSortRatingRus(): Flow<List<CountryRus>>

    fun getSortRatingEng(): Flow<List<CountryEng>>

    fun getFavoriteSortNameRus(): Flow<List<CountryRus>>

    fun getFavoriteSortNameEng(): Flow<List<CountryEng>>

    fun getFavoriteSortRatingRus(): Flow<List<CountryRus>>

    fun getFavoriteSortRatingEng(): Flow<List<CountryEng>>

    fun getNameCountrySortNameRus(fullNameCountryRus: String): Flow<List<CountryRus>>

    fun getNameCountrySortNameEng(fullNameCountryEng: String): Flow<List<CountryEng>>

    fun getNameCountrySortRatingRus(fullNameCountryRus: String): Flow<List<CountryRus>>

    fun getNameCountrySortRatingEng(fullNameCountryEng: String): Flow<List<CountryEng>>

    fun getNameCountryFavoriteSortNameRus(fullNameCountryRus: String): Flow<List<CountryRus>>

    fun getNameCountryFavoriteSortNameEng(fullNameCountryEng: String): Flow<List<CountryEng>>

    fun getNameCountryFavoriteSortRatingRus(fullNameCountryRus: String): Flow<List<CountryRus>>

    fun getNameCountryFavoriteSortRatingEng(fullNameCountryEng: String): Flow<List<CountryEng>>
}