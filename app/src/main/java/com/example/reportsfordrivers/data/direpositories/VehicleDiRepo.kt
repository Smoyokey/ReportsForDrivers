package com.example.reportsfordrivers.data.direpositories

import com.example.reportsfordrivers.data.dao.VehicleDao
import com.example.reportsfordrivers.data.repositories.VehicleRepository
import com.example.reportsfordrivers.data.structure.Vehicle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehicleDiRepo @Inject constructor(private val vehicleDao: VehicleDao) : VehicleRepository {
    override fun getAllItemStream() : Flow<List<Vehicle>> = vehicleDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<Vehicle?> = vehicleDao.getOneItem(id)

    override suspend fun insertItem(item: Vehicle) = vehicleDao.insert(item)

    override suspend fun deleteItem(item: Vehicle) = vehicleDao.delete(item)

    override suspend fun updateItem(item: Vehicle) = vehicleDao.delete(item)
}