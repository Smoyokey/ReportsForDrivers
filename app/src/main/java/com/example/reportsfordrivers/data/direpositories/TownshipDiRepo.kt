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

    override suspend fun insertItem(item: Township) = townshipDao.insert(item)

    override suspend fun deleteItem(item: Township) = townshipDao.delete(item)

    override suspend fun updateItem(item: Township) = townshipDao.update(item)

    override fun getSortNameRus(): Flow<List<TownshipRus>> = townshipDao.getSortNameRus()

    override fun getSortNameEng(): Flow<List<TownshipEng>> = townshipDao.getSortNameEng()

    override fun getSortRatingRus(): Flow<List<TownshipRus>> = townshipDao.getSortRatingRus()

    override fun getSortRatingEng(): Flow<List<TownshipEng>> = townshipDao.getSortRatingEng()

    override fun getFavoriteSortNameRus(): Flow<List<TownshipRus>> =
        townshipDao.getFavoriteSortNameRus()

    override fun getFavoriteSortNameEng(): Flow<List<TownshipEng>> =
        townshipDao.getFavoriteSortNameEng()

    override fun getFavoriteSortRatingRus(): Flow<List<TownshipRus>> =
        townshipDao.getFavoriteSortRatingRus()

    override fun getFavoriteSortRatingEng(): Flow<List<TownshipEng>> =
        townshipDao.getFavoriteSortRatingEng()

    override fun getNameTownshipSortNameRus(townshipRus: String): Flow<List<TownshipRus>> =
        townshipDao.getNameTownshipSortNameRus(townshipRus)

    override fun getNameTownshipSortNameEng(townshipEng: String): Flow<List<TownshipEng>> =
        townshipDao.getNameTownshipSortNameEng(townshipEng)

    override fun getNameTownshipSortRatingRus(townshipRus: String): Flow<List<TownshipRus>> =
        townshipDao.getNameTownshipSortRatingRus(townshipRus)

    override fun getNameTownshipSortRatingEng(townshipEng: String): Flow<List<TownshipEng>> =
        townshipDao.getNameTownshipSortRatingEng(townshipEng)

    override fun getCountryIdTownshipSortNameRus(countryId: Int): Flow<List<TownshipRus>> =
        townshipDao.getCountryIdTownshipSortNameRus(countryId)

    override fun getCountryIdTownshipSortNameEng(countryId: Int): Flow<List<TownshipEng>> =
        townshipDao.getCountryIdTownshipSortNameEng(countryId)

    override fun getCountryIdTownshipSortRatingRus(countryId: Int): Flow<List<TownshipRus>> =
        townshipDao.getCountryIdTownshipSortRatingRus(countryId)

    override fun getCountryIdTownshipSortRatingEng(countryId: Int): Flow<List<TownshipEng>> =
        townshipDao.getCountryIdTownshipSortRatingEng(countryId)

    override fun getCountryIdNameTownshipSortNameRus(countryId: Int, townshipRus: String): Flow<List<TownshipRus>> =
        townshipDao.getCountryIdNameTownshipSortNameRus(countryId, townshipRus)

    override fun getCountryIdNameTownshipSortNameEng(countryId: Int, townshipEng: String): Flow<List<TownshipEng>> =
        townshipDao.getCountryIdNameTownshipSortNameEng(countryId, townshipEng)

    override fun getCountryIdNameTownshipSortRatingRus(countryId: Int, townshipRus: String): Flow<List<TownshipRus>> =
        townshipDao.getCountryIdNameTownshipSortRatingRus(countryId, townshipRus)

    override fun getCountryIdNameTownshipSortRatingEng(countryId: Int, townshipEng: String): Flow<List<TownshipEng>> =
        townshipDao.getCountryIdNameTownshipSortRatingEng(countryId, townshipEng)

    override fun getNameTownshipFavoriteSortNameRus(townshipRus: String): Flow<List<TownshipRus>> =
        townshipDao.getNameTownshipFavoriteSortNameRus(townshipRus)

    override fun getNameTownshipFavoriteSortNameEng(townshipEng: String): Flow<List<TownshipEng>> =
        townshipDao.getNameTownshipFavoriteSortNameEng(townshipEng)

    override fun getNameTownshipFavoriteSortRatingRus(townshipRus: String): Flow<List<TownshipRus>> =
        townshipDao.getNameTownshipFavoriteSortRatingRus(townshipRus)

    override fun getNameTownshipFavoriteSortRatingEng(townshipEng: String): Flow<List<TownshipEng>> =
        townshipDao.getNameTownshipFavoriteSortRatingEng(townshipEng)

    override fun getCountryIdFavoriteSortNameRus(countryId: Int): Flow<List<TownshipRus>> =
        townshipDao.getCountryIdFavoriteSortNameRus(countryId)

    override fun getCountryIdFavoriteSortNameEng(countryId: Int): Flow<List<TownshipEng>> =
        townshipDao.getCountryIdFavoriteSortNameEng(countryId)

    override fun getCountryIdFavoriteSortRatingRus(countryId: Int): Flow<List<TownshipRus>> =
        townshipDao.getCountryIdFavoriteSortRatingRus(countryId)

    override fun getCountryIdFavoriteSortRatingEng(countryId: Int): Flow<List<TownshipEng>> =
        townshipDao.getCountryIdFavoriteSortRatingEng(countryId)

    override fun getCountryIdNameTownshipFavoriteSortNameRus(countryId: Int, townshipRus: String): Flow<List<TownshipRus>> =
        townshipDao.getCountryIdNameTownshipFavoriteSortNameRus(countryId, townshipRus)

    override fun getCountryIdNameTownshipFavoriteSortNameEng(countryId: Int, townshipEng: String): Flow<List<TownshipEng>> =
        townshipDao.getCountryIdNameTownshipFavoriteSortNameEng(countryId, townshipEng)

    override fun getCountryIdNameTownshipFavoriteSortRatingRus(countryId: Int, townshipRus: String): Flow<List<TownshipRus>> =
        townshipDao.getCountryIdNameTownshipFavoriteSortRatingRus(countryId, townshipRus)

    override fun getCountryIdNameTownshipFavoriteSortRatingEng(countryId: Int, townshipEng: String): Flow<List<TownshipEng>> =
        townshipDao.getCountryIdNameTownshipFavoriteSortRatingEng(countryId, townshipEng)


}