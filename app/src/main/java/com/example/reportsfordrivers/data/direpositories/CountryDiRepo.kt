package com.example.reportsfordrivers.data.direpositories

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

    override fun getSortNameRus(): Flow<List<CountryRus>> = countryDao.getSortNameRus()

    override fun getSortNameEng(): Flow<List<CountryEng>> = countryDao.getSortNameEng()

    override fun getSortRatingRus(): Flow<List<CountryRus>> = countryDao.getSortRatingRus()

    override fun getSortRatingEng(): Flow<List<CountryEng>> = countryDao.getSortRatingEng()

    override fun getFavoriteSortNameRus(): Flow<List<CountryRus>> =
        countryDao.getFavoriteSortNameRus()

    override fun getFavoriteSortNameEng(): Flow<List<CountryEng>> =
        countryDao.getFavoriteSortNameEng()

    override fun getFavoriteSortRatingRus(): Flow<List<CountryRus>> =
        countryDao.getFavoriteSortRatingRus()

    override fun getFavoriteSortRatingEng(): Flow<List<CountryEng>> =
        countryDao.getFavoriteSortRatingEng()

    override fun getNameCountrySortNameRus(fullNameCountryRus: String): Flow<List<CountryRus>> =
        countryDao.getNameCountrySortNameRus(fullNameCountryRus)

    override fun getNameCountrySortNameEng(fullNameCountryEng: String): Flow<List<CountryEng>> =
        countryDao.getNameCountrySortNameEng(fullNameCountryEng)

    override fun getNameCountrySortRatingRus(fullNameCountryRus: String): Flow<List<CountryRus>> =
        countryDao.getNameCountrySortRatingRus(fullNameCountryRus)

    override fun getNameCountrySortRatingEng(fullNameCountryEng: String): Flow<List<CountryEng>> =
        countryDao.getNameCountrySortRatingEng(fullNameCountryEng)

    override fun getNameCountryFavoriteSortNameRus(fullNameCountryRus: String): Flow<List<CountryRus>> =
        countryDao.getNameCountryFavoriteSortNameRus(fullNameCountryRus)

    override fun getNameCountryFavoriteSortNameEng(fullNameCountryEng: String): Flow<List<CountryEng>> =
        countryDao.getNameCountryFavoriteSortNameEng(fullNameCountryEng)

    override fun getNameCountryFavoriteSortRatingRus(fullNameCountryRus: String): Flow<List<CountryRus>> =
        countryDao.getNameCountryFavoriteSortRatingRus(fullNameCountryRus)

    override fun getNameCountryFavoriteSortRatingEng(fullNameCountryEng: String): Flow<List<CountryEng>> =
        countryDao.getNameCountryFavoriteSortRatingEng(fullNameCountryEng)
}