package com.example.reportsfordrivers.data.direpositories.editreport

import com.example.reportsfordrivers.data.dao.editreport.EditVehicleTrailerDao
import com.example.reportsfordrivers.data.repositories.editreport.EditVehicleTrailerRepository
import com.example.reportsfordrivers.data.structure.editreport.EditVehicleTrailer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditVehicleTrailerDiRepo @Inject constructor(private val editVehicleTrailerDao: EditVehicleTrailerDao) :
    EditVehicleTrailerRepository {

    override fun getAllItemStream(): Flow<List<EditVehicleTrailer>> =
        editVehicleTrailerDao.getAllItem()

    override fun getOneItemStream(id: Int): Flow<EditVehicleTrailer?> =
        editVehicleTrailerDao.getOneItem(id)

    override suspend fun insertItem(item: EditVehicleTrailer) = editVehicleTrailerDao.insert(item)

    override suspend fun deleteItem(item: EditVehicleTrailer) = editVehicleTrailerDao.delete(item)

    override suspend fun updateItem(item: EditVehicleTrailer) = editVehicleTrailerDao.update(item)

    override suspend fun updateOneElementForIdMakeVehicle(id: Int, makeVehicle: String) =
        editVehicleTrailerDao.updateOneElementForIdMakeVehicle(id, makeVehicle)

    override suspend fun updateOneElementForIdRnVehicle(id: Int, rnVehicle: String) =
        editVehicleTrailerDao.updateOneElementForIdRnVehicle(id, rnVehicle)

    override suspend fun updateOneElementForIdMakeTrailer(id: Int, makeTrailer: String) =
        editVehicleTrailerDao.updateOneElementForIdMakeTrailer(id, makeTrailer)

    override suspend fun updateOneElementForIdRnTrailer(id: Int, rnTrailer: String) =
        editVehicleTrailerDao.updateOneElementForIdRnTrailer(id, rnTrailer)

    override suspend fun deleteAllElements() = editVehicleTrailerDao.deleteAllElements()
}