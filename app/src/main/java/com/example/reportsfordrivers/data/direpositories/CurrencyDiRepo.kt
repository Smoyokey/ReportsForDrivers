package com.example.reportsfordrivers.data.direpositories

import com.example.reportsfordrivers.data.dao.CurrencyDao
import com.example.reportsfordrivers.data.repositories.CurrencyRepository
import com.example.reportsfordrivers.data.structure.Currency
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyDiRepo @Inject constructor(private val currencyDao: CurrencyDao) : CurrencyRepository
{
    override fun getAllItemStream(): Flow<List<Currency>> = currencyDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<Currency?> = currencyDao.getOneItem(id)

    override suspend fun insertItem(item: Currency) = currencyDao.insert(item)

    override suspend fun deleteItem(item: Currency) = currencyDao.delete(item)

    override suspend fun updateItem(item: Currency) = currencyDao.update(item)
}