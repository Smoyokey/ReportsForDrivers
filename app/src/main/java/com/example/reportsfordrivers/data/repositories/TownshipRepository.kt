package com.example.reportsfordrivers.data.repositories

import com.example.reportsfordrivers.data.structure.Township
import com.example.reportsfordrivers.data.structure.TownshipEng
import com.example.reportsfordrivers.data.structure.TownshipRus
import kotlinx.coroutines.flow.Flow

interface TownshipRepository {
    fun getAllItemStream(): Flow<List<Township>>

    fun getOneItemStream(id: Int) : Flow<Township?>

    suspend fun insertItem(item: Township)

    suspend fun deleteItem(item: Township)

    suspend fun updateItem(item: Township)

    fun getSortNameRus(): Flow<List<TownshipRus>>

    fun getSortNameEng(): Flow<List<TownshipEng>>

    fun getSortRatingRus(): Flow<List<TownshipRus>>

    fun getSortRatingEng(): Flow<List<TownshipEng>>

    fun getFavoriteSortNameRus(): Flow<List<TownshipRus>>

    fun getFavoriteSortNameEng(): Flow<List<TownshipEng>>

    fun getFavoriteSortRatingRus(): Flow<List<TownshipRus>>

    fun getFavoriteSortRatingEng(): Flow<List<TownshipEng>>

    fun getNameTownshipSortNameRus(townshipRus: String): Flow<List<TownshipRus>>

    fun getNameTownshipSortNameEng(townshipEng: String): Flow<List<TownshipEng>>

    fun getNameTownshipSortRatingRus(townshipRus: String): Flow<List<TownshipRus>>

    fun getNameTownshipSortRatingEng(townshipEng: String): Flow<List<TownshipEng>>

    fun getCountryIdTownshipSortNameRus(countryId: Int): Flow<List<TownshipRus>>

    fun getCountryIdTownshipSortNameEng(countryId: Int): Flow<List<TownshipEng>>

    fun getCountryIdTownshipSortRatingRus(countryId: Int): Flow<List<TownshipRus>>

    fun getCountryIdTownshipSortRatingEng(countryId: Int): Flow<List<TownshipEng>>

    fun getCountryIdNameTownshipSortNameRus(countryId: Int, townshipRus: String): Flow<List<TownshipRus>>

    fun getCountryIdNameTownshipSortNameEng(countryId: Int, townshipEng: String): Flow<List<TownshipEng>>

    fun getCountryIdNameTownshipSortRatingRus(countryId: Int, townshipRus: String): Flow<List<TownshipRus>>

    fun getCountryIdNameTownshipSortRatingEng(countryId: Int, townshipEng: String): Flow<List<TownshipEng>>

    fun getNameTownshipFavoriteSortNameRus(townshipRus: String): Flow<List<TownshipRus>>

    fun getNameTownshipFavoriteSortNameEng(townshipEng: String): Flow<List<TownshipEng>>

    fun getNameTownshipFavoriteSortRatingRus(townshipRus: String): Flow<List<TownshipRus>>

    fun getNameTownshipFavoriteSortRatingEng(townshipEng: String): Flow<List<TownshipEng>>

    fun getCountryIdFavoriteSortNameRus(countryId: Int): Flow<List<TownshipRus>>

    fun getCountryIdFavoriteSortNameEng(countryId: Int): Flow<List<TownshipEng>>

    fun getCountryIdFavoriteSortRatingRus(countryId: Int): Flow<List<TownshipRus>>

    fun getCountryIdFavoriteSortRatingEng(countryId: Int): Flow<List<TownshipEng>>

    fun getCountryIdNameTownshipFavoriteSortNameRus(countryId: Int, townshipRus: String): Flow<List<TownshipRus>>

    fun getCountryIdNameTownshipFavoriteSortNameEng(countryId: Int, townshipEng: String): Flow<List<TownshipEng>>

    fun getCountryIdNameTownshipFavoriteSortRatingRus(countryId: Int, townshipRus: String): Flow<List<TownshipRus>>

    fun getCountryIdNameTownshipFavoriteSortRatingEng(countryId: Int, townshipRus: String): Flow<List<TownshipEng>>
}