package com.example.reportsfordrivers.data.direpositories.createReport

import com.example.reportsfordrivers.data.dao.createReport.CreateVehicleTrailerDao
import com.example.reportsfordrivers.data.repositories.createReport.CreateVehicleTrailerRepository
import com.example.reportsfordrivers.data.structure.createReport.CreateVehicleTrailer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateVehicleTrailerDiRepo @Inject constructor(private val createVehicleTrailerDao: CreateVehicleTrailerDao) :
    CreateVehicleTrailerRepository {

    override fun getAllItemStream(): Flow<List<CreateVehicleTrailer>> =
        createVehicleTrailerDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<CreateVehicleTrailer?> =
        createVehicleTrailerDao.getOneItem(id)

    override suspend fun insertItem(item: CreateVehicleTrailer) = createVehicleTrailerDao.insert(item)

    override suspend fun deleteItem(item: CreateVehicleTrailer) = createVehicleTrailerDao.delete(item)

    override suspend fun updateItem(item: CreateVehicleTrailer) = createVehicleTrailerDao.update(item)
}