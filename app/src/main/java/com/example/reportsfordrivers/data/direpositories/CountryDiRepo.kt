package com.example.reportsfordrivers.data.direpositories

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.reportsfordrivers.data.dao.CountryDao
import com.example.reportsfordrivers.data.repositories.CountryRepository
import com.example.reportsfordrivers.data.structure.Country
import com.example.reportsfordrivers.data.structure.CountryEng
import com.example.reportsfordrivers.data.structure.CountryRus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryDiRepo @Inject constructor(private val countryDao: CountryDao): CountryRepository {
    override fun getAllItemStream(): Flow<List<Country>> = countryDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<Country?> = countryDao.getOneItem(id)

    override suspend fun insertItem(item: Country) = countryDao.insert(item)

    override suspend fun deleteItem(item: Country) = countryDao.delete(item)

    override suspend fun updateItem(item: Country) = countryDao.update(item)

    override suspend fun updateFavorite(value: Int, id: Int) = countryDao.updateFavorite(value, id)

    override suspend fun getSortNameRus(): List<CountryRus> = countryDao.getSortNameRus()

    override suspend fun getSortNameEng(): List<CountryEng> = countryDao.getSortNameEng()

    override suspend fun getSortRatingRus(): List<CountryRus> = countryDao.getSortRatingRus()

    override suspend fun getSortRatingEng(): List<CountryEng> = countryDao.getSortRatingEng()

    override suspend fun getFavoriteSortNameRus(): List<CountryRus> =
        countryDao.getFavoriteSortNameRus()

    override suspend fun getFavoriteSortNameEng(): List<CountryEng> =
        countryDao.getFavoriteSortNameEng()

    override suspend fun getFavoriteSortRatingRus(): List<CountryRus> =
        countryDao.getFavoriteSortRatingRus()

    override suspend fun getFavoriteSortRatingEng(): List<CountryEng> =
        countryDao.getFavoriteSortRatingEng()

    override suspend fun getNameCountrySortNameRus(fullNameCountryRus: String): List<CountryRus> =
        countryDao.getNameCountrySortNameRus(fullNameCountryRus)

    override suspend fun getNameCountrySortNameEng(fullNameCountryEng: String): List<CountryEng> =
        countryDao.getNameCountrySortNameEng(fullNameCountryEng)

    override suspend fun getNameCountrySortRatingRus(fullNameCountryRus: String): List<CountryRus> =
        countryDao.getNameCountrySortRatingRus(fullNameCountryRus)

    override suspend fun getNameCountrySortRatingEng(fullNameCountryEng: String): List<CountryEng> =
        countryDao.getNameCountrySortRatingEng(fullNameCountryEng)

    override suspend fun getNameCountryFavoriteSortNameRus(fullNameCountryRus: String): List<CountryRus> =
        countryDao.getNameCountryFavoriteSortNameRus(fullNameCountryRus)

    override suspend fun getNameCountryFavoriteSortNameEng(fullNameCountryEng: String): List<CountryEng> =
        countryDao.getNameCountryFavoriteSortNameEng(fullNameCountryEng)

    override suspend fun getNameCountryFavoriteSortRatingRus(fullNameCountryRus: String): List<CountryRus> =
        countryDao.getNameCountryFavoriteSortRatingRus(fullNameCountryRus)

    override suspend fun getNameCountryFavoriteSortRatingEng(fullNameCountryEng: String): List<CountryEng> =
        countryDao.getNameCountryFavoriteSortRatingEng(fullNameCountryEng)

    override suspend fun updateRatingForId(id: Int, rating: Int) = countryDao.updateRatingForId(id, rating)
}