package com.example.reportsfordrivers.data.direpositories

import com.example.reportsfordrivers.data.dao.TownshipDao
import com.example.reportsfordrivers.data.repositories.TownshipRepository
import com.example.reportsfordrivers.data.structure.Township
import com.example.reportsfordrivers.data.structure.TownshipEng
import com.example.reportsfordrivers.data.structure.TownshipRus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TownshipDiRepo @Inject constructor(private val townshipDao: TownshipDao): TownshipRepository {
    override fun getAllItemStream(): Flow<List<Township>> = townshipDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<Township?> = townshipDao.getOneItem(id)

    override suspend fun getOneItemName(name: String): Township = townshipDao.getOneItemName(name)

    override suspend fun insertItem(item: Township) = townshipDao.insert(item)

    override suspend fun deleteItem(item: Township) = townshipDao.delete(item)

    override suspend fun updateItem(item: Township) = townshipDao.update(item)

    override suspend fun updateFavorite(value: Int, id: Int) = townshipDao.updateFavorite(value, id)

    override suspend fun updateFavoriteName(value: Int, name: String) =
        townshipDao.updateFavoriteName(value, name)

    override suspend fun getSortNameRus(): List<TownshipRus> = townshipDao.getSortNameRus()

    override suspend fun getSortNameEng(): List<TownshipEng> = townshipDao.getSortNameEng()

    override suspend fun getSortRatingRus(): List<TownshipRus> = townshipDao.getSortRatingRus()

    override suspend fun getSortRatingEng(): List<TownshipEng> = townshipDao.getSortRatingEng()

    override suspend fun getFavoriteSortNameRus(): List<TownshipRus> =
        townshipDao.getFavoriteSortNameRus()

    override suspend fun getFavoriteSortNameEng(): List<TownshipEng> =
        townshipDao.getFavoriteSortNameEng()

    override suspend fun getFavoriteSortRatingRus(): List<TownshipRus> =
        townshipDao.getFavoriteSortRatingRus()

    override suspend fun getFavoriteSortRatingEng(): List<TownshipEng> =
        townshipDao.getFavoriteSortRatingEng()

    override suspend fun getNameTownshipSortNameRus(townshipRus: String): List<TownshipRus> =
        townshipDao.getNameTownshipSortNameRus(townshipRus)

    override suspend fun getNameTownshipSortNameEng(townshipEng: String): List<TownshipEng> =
        townshipDao.getNameTownshipSortNameEng(townshipEng)

    override suspend fun getNameTownshipSortRatingRus(townshipRus: String): List<TownshipRus> =
        townshipDao.getNameTownshipSortRatingRus(townshipRus)

    override suspend fun getNameTownshipSortRatingEng(townshipEng: String): List<TownshipEng> =
        townshipDao.getNameTownshipSortRatingEng(townshipEng)

    override suspend fun getCountryIdTownshipSortNameRus(countryId: Int): List<TownshipRus> =
        townshipDao.getCountryIdTownshipSortNameRus(countryId)

    override suspend fun getCountryIdTownshipSortNameEng(countryId: Int): List<TownshipEng> =
        townshipDao.getCountryIdTownshipSortNameEng(countryId)

    override suspend fun getCountryIdTownshipSortRatingRus(countryId: Int): List<TownshipRus> =
        townshipDao.getCountryIdTownshipSortRatingRus(countryId)

    override suspend fun getCountryIdTownshipSortRatingEng(countryId: Int): List<TownshipEng> =
        townshipDao.getCountryIdTownshipSortRatingEng(countryId)

    override suspend fun getCountryIdNameTownshipSortNameRus(countryId: Int, townshipRus: String): List<TownshipRus> =
        townshipDao.getCountryIdNameTownshipSortNameRus(countryId, townshipRus)

    override suspend fun getCountryIdNameTownshipSortNameEng(countryId: Int, townshipEng: String): List<TownshipEng> =
        townshipDao.getCountryIdNameTownshipSortNameEng(countryId, townshipEng)

    override suspend fun getCountryIdNameTownshipSortRatingRus(countryId: Int, townshipRus: String): List<TownshipRus> =
        townshipDao.getCountryIdNameTownshipSortRatingRus(countryId, townshipRus)

    override suspend fun getCountryIdNameTownshipSortRatingEng(countryId: Int, townshipEng: String): List<TownshipEng> =
        townshipDao.getCountryIdNameTownshipSortRatingEng(countryId, townshipEng)

    override suspend fun getNameTownshipFavoriteSortNameRus(townshipRus: String): List<TownshipRus> =
        townshipDao.getNameTownshipFavoriteSortNameRus(townshipRus)

    override suspend fun getNameTownshipFavoriteSortNameEng(townshipEng: String): List<TownshipEng> =
        townshipDao.getNameTownshipFavoriteSortNameEng(townshipEng)

    override suspend fun getNameTownshipFavoriteSortRatingRus(townshipRus: String): List<TownshipRus> =
        townshipDao.getNameTownshipFavoriteSortRatingRus(townshipRus)

    override suspend fun getNameTownshipFavoriteSortRatingEng(townshipEng: String): List<TownshipEng> =
        townshipDao.getNameTownshipFavoriteSortRatingEng(townshipEng)

    override suspend fun getCountryIdFavoriteSortNameRus(countryId: Int): List<TownshipRus> =
        townshipDao.getCountryIdFavoriteSortNameRus(countryId)

    override suspend fun getCountryIdFavoriteSortNameEng(countryId: Int): List<TownshipEng> =
        townshipDao.getCountryIdFavoriteSortNameEng(countryId)

    override suspend fun getCountryIdFavoriteSortRatingRus(countryId: Int): List<TownshipRus> =
        townshipDao.getCountryIdFavoriteSortRatingRus(countryId)

    override suspend fun getCountryIdFavoriteSortRatingEng(countryId: Int): List<TownshipEng> =
        townshipDao.getCountryIdFavoriteSortRatingEng(countryId)

    override suspend fun getCountryIdNameTownshipFavoriteSortNameRus(countryId: Int, townshipRus: String): List<TownshipRus> =
        townshipDao.getCountryIdNameTownshipFavoriteSortNameRus(countryId, townshipRus)

    override suspend fun getCountryIdNameTownshipFavoriteSortNameEng(countryId: Int, townshipEng: String): List<TownshipEng> =
        townshipDao.getCountryIdNameTownshipFavoriteSortNameEng(countryId, townshipEng)

    override suspend fun getCountryIdNameTownshipFavoriteSortRatingRus(countryId: Int, townshipRus: String): List<TownshipRus> =
        townshipDao.getCountryIdNameTownshipFavoriteSortRatingRus(countryId, townshipRus)

    override suspend fun getCountryIdNameTownshipFavoriteSortRatingEng(countryId: Int, townshipEng: String): List<TownshipEng> =
        townshipDao.getCountryIdNameTownshipFavoriteSortRatingEng(countryId, townshipEng)

    override suspend fun updateRatingForId(id: Int, rating: Int) = townshipDao.updateRatingForId(id, rating)
}