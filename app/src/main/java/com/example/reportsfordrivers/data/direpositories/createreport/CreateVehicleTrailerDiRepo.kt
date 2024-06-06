package com.example.reportsfordrivers.data.direpositories.createreport

import com.example.reportsfordrivers.data.dao.createreport.CreateVehicleTrailerDao
import com.example.reportsfordrivers.data.repositories.createreport.CreateVehicleTrailerRepository
import com.example.reportsfordrivers.data.structure.createreport.CreateVehicleTrailer
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

    override suspend fun updateOneElementForIdMakeVehicle(id: Int, makeVehicle: String) =
        createVehicleTrailerDao.updateOneElementForIdMakeVehicle(id, makeVehicle)

    override suspend fun updateOneElementForIdRnVehicle(id: Int, rnVehicle: String) =
        createVehicleTrailerDao.updateOneElementForIdRnVehicle(id, rnVehicle)

    override suspend fun updateOneElementForIdMakeTrailer(id: Int, makeTrailer: String) =
        createVehicleTrailerDao.updateOneElementForIdMakeTrailer(id, makeTrailer)

    override suspend fun updateOneElementForIdRnTrailer(id: Int, rnTrailer: String) =
        createVehicleTrailerDao.updateOneElementForIdRnTrailer(id, rnTrailer)

    override suspend fun deleteAllElements() = createVehicleTrailerDao.deleteAllElements()
}