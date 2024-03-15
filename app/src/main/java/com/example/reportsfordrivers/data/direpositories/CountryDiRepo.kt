package com.example.reportsfordrivers.data.direpositories

import com.example.reportsfordrivers.data.dao.CountryDao
import com.example.reportsfordrivers.data.repositories.CountryRepository
import com.example.reportsfordrivers.data.structure.Country
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
}