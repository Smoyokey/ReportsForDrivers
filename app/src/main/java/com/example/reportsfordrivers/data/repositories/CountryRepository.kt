package com.example.reportsfordrivers.data.repositories

import androidx.compose.runtime.snapshots.SnapshotStateList
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

    suspend fun updateFavorite(value: Int, id: Int)

    suspend fun getSortNameRus(): List<CountryRus>

    suspend fun getSortNameEng(): List<CountryEng>

    suspend fun getSortRatingRus(): List<CountryRus>

    suspend fun getSortRatingEng(): List<CountryEng>

    suspend fun getFavoriteSortNameRus(): List<CountryRus>

    suspend fun getFavoriteSortNameEng(): List<CountryEng>

    suspend fun getFavoriteSortRatingRus(): List<CountryRus>

    suspend fun getFavoriteSortRatingEng(): List<CountryEng>

    suspend fun getNameCountrySortNameRus(fullNameCountryRus: String): List<CountryRus>

    suspend fun getNameCountrySortNameEng(fullNameCountryEng: String): List<CountryEng>

    suspend fun getNameCountrySortRatingRus(fullNameCountryRus: String): List<CountryRus>

    suspend fun getNameCountrySortRatingEng(fullNameCountryEng: String): List<CountryEng>

    suspend fun getNameCountryFavoriteSortNameRus(fullNameCountryRus: String): List<CountryRus>

    suspend fun getNameCountryFavoriteSortNameEng(fullNameCountryEng: String): List<CountryEng>

    suspend fun getNameCountryFavoriteSortRatingRus(fullNameCountryRus: String): List<CountryRus>

    suspend fun getNameCountryFavoriteSortRatingEng(fullNameCountryEng: String): List<CountryEng>

    suspend fun updateRatingForId(id: Int, rating: Int)
}