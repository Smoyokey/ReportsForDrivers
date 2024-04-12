package com.example.reportsfordrivers.data.repositories

import com.example.reportsfordrivers.data.structure.Township
import com.example.reportsfordrivers.data.structure.TownshipEng
import com.example.reportsfordrivers.data.structure.TownshipRus
import kotlinx.coroutines.flow.Flow

interface TownshipRepository {
    fun getAllItemStream(): Flow<List<Township>>

    fun getOneItemStream(id: Int) : Flow<Township?>

    suspend fun getOneItemName(name: String): Township

    suspend fun insertItem(item: Township)

    suspend fun deleteItem(item: Township)

    suspend fun updateItem(item: Township)

    suspend fun updateFavorite(value: Int, id: Int)

    suspend fun updateFavoriteName(value: Int, name: String)

    suspend fun getSortNameRus(): List<TownshipRus>

    suspend fun getSortNameEng(): List<TownshipEng>

    suspend fun getSortRatingRus(): List<TownshipRus>

    suspend fun getSortRatingEng(): List<TownshipEng>

    suspend fun getFavoriteSortNameRus(): List<TownshipRus>

    suspend fun getFavoriteSortNameEng(): List<TownshipEng>

    suspend fun getFavoriteSortRatingRus(): List<TownshipRus>

    suspend fun getFavoriteSortRatingEng(): List<TownshipEng>

    suspend fun getNameTownshipSortNameRus(townshipRus: String): List<TownshipRus>

    suspend fun getNameTownshipSortNameEng(townshipEng: String): List<TownshipEng>

    suspend fun getNameTownshipSortRatingRus(townshipRus: String): List<TownshipRus>

    suspend fun getNameTownshipSortRatingEng(townshipEng: String): List<TownshipEng>

    suspend fun getCountryIdTownshipSortNameRus(countryId: Int): List<TownshipRus>

    suspend fun getCountryIdTownshipSortNameEng(countryId: Int): List<TownshipEng>

    suspend fun getCountryIdTownshipSortRatingRus(countryId: Int): List<TownshipRus>

    suspend fun getCountryIdTownshipSortRatingEng(countryId: Int): List<TownshipEng>

    suspend fun getCountryIdNameTownshipSortNameRus(countryId: Int, townshipRus: String): List<TownshipRus>

    suspend fun getCountryIdNameTownshipSortNameEng(countryId: Int, townshipEng: String): List<TownshipEng>

    suspend fun getCountryIdNameTownshipSortRatingRus(countryId: Int, townshipRus: String): List<TownshipRus>

    suspend fun getCountryIdNameTownshipSortRatingEng(countryId: Int, townshipEng: String): List<TownshipEng>

    suspend fun getNameTownshipFavoriteSortNameRus(townshipRus: String): List<TownshipRus>

    suspend fun getNameTownshipFavoriteSortNameEng(townshipEng: String): List<TownshipEng>

    suspend fun getNameTownshipFavoriteSortRatingRus(townshipRus: String): List<TownshipRus>

    suspend fun getNameTownshipFavoriteSortRatingEng(townshipEng: String): List<TownshipEng>

    suspend fun getCountryIdFavoriteSortNameRus(countryId: Int): List<TownshipRus>

    suspend fun getCountryIdFavoriteSortNameEng(countryId: Int): List<TownshipEng>

    suspend fun getCountryIdFavoriteSortRatingRus(countryId: Int): List<TownshipRus>

    suspend fun getCountryIdFavoriteSortRatingEng(countryId: Int): List<TownshipEng>

    suspend fun getCountryIdNameTownshipFavoriteSortNameRus(countryId: Int, townshipRus: String): List<TownshipRus>

    suspend fun getCountryIdNameTownshipFavoriteSortNameEng(countryId: Int, townshipEng: String): List<TownshipEng>

    suspend fun getCountryIdNameTownshipFavoriteSortRatingRus(countryId: Int, townshipRus: String): List<TownshipRus>

    suspend fun getCountryIdNameTownshipFavoriteSortRatingEng(countryId: Int, townshipRus: String): List<TownshipEng>
}