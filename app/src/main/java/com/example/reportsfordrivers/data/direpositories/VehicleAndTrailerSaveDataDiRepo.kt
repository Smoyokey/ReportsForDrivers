package com.example.reportsfordrivers.data.direpositories

import com.example.reportsfordrivers.data.dao.VehicleAndTrailerSaveDataDao
import com.example.reportsfordrivers.data.repositories.VehicleAndTrailerSaveDataRepository
import com.example.reportsfordrivers.data.structure.VehicleAndTrailer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehicleAndTrailerSaveDataDiRepo @Inject constructor(
    private val vehicleAndTrailerSaveDataDao: VehicleAndTrailerSaveDataDao
) : VehicleAndTrailerSaveDataRepository {

    override fun getAllItemStream(): Flow<List<VehicleAndTrailer>> =
        vehicleAndTrailerSaveDataDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<VehicleAndTrailer?> =
        vehicleAndTrailerSaveDataDao.getOneItem(id)

    override suspend fun insertItem(item: VehicleAndTrailer) =
        vehicleAndTrailerSaveDataDao.insert(item)

    override suspend fun deleteItem(item: VehicleAndTrailer) =
        vehicleAndTrailerSaveDataDao.delete(item)

    override suspend fun updateItem(item: VehicleAndTrailer) =
        vehicleAndTrailerSaveDataDao.update(item)
}