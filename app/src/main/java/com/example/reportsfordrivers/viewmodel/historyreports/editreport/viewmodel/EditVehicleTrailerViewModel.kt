package com.example.reportsfordrivers.viewmodel.historyreports.editreport.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.data.dao.VehicleAndTrailerSaveDataDao
import com.example.reportsfordrivers.data.dao.editreport.EditVehicleTrailerDao
import com.example.reportsfordrivers.data.structure.VehicleAndTrailer
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateObjectVehicleOrTrailer
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditVehicleTrailerDetailingListTrailer
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditVehicleTrailerDetailingListVehicle
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditVehicleTrailerListTrailer
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditVehicleTrailerListVehicle
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditVehicleTrailerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

private const val TAG = "EditVehicleTrailerViewModel"

@HiltViewModel
class EditVehicleTrailerViewModel @Inject constructor(
    private val fioPreferencesRepository: FioFirstEntryRepository
) : ViewModel() {

    @Inject
    lateinit var editVehicleTrailerDb: EditVehicleTrailerDao

    @Inject
    lateinit var vehicleAndTrailerDb: VehicleAndTrailerSaveDataDao

    var uiStateEditVehicleTrailer = mutableStateOf(EditVehicleTrailerUiState())

    var uiStateListVehicle = mutableStateOf(EditVehicleTrailerListVehicle())
    var uiStateListTrailer = mutableStateOf(EditVehicleTrailerListTrailer())

    var openDialogCreateVehicleEditVehicleTrailer = mutableStateOf(false)
    var openDialogCreateTrailerEditVehicleTrailer = mutableStateOf(false)
    var openMenuMakeVehicleEditVehicleTrailer = mutableStateOf(false)
    var openMenuMakeTrailerEditVehicleTrailer = mutableStateOf(false)

    var firstOpenEditVehicleTrailer = mutableStateOf(false)

    fun startLoadEditVehicleTrailer() = runBlocking {
        val editVehicleTrailer = editVehicleTrailerDb.getAllItem().first()
        uiStateEditVehicleTrailer.value = uiStateEditVehicleTrailer.value.copy(
            id = editVehicleTrailer[0].id,
            makeVehicle = editVehicleTrailer[0].makeVehicle.ifEmpty { "" },
            rnVehicle = editVehicleTrailer[0].rnVehicle.ifEmpty { "" },
            makeTrailer = editVehicleTrailer[0].makeTrailer.ifEmpty { "" },
            rnTrailer = editVehicleTrailer[0].rnTrailer.ifEmpty { "" }
        )
        fioPreferencesRepository.setCreateSelectedPage(3)
        loadVehicleAndTrailerInDb()
        firstOpenEditVehicleTrailer.value = true
    }

    fun updateDataEditVehicleTrailerMakeVehicle(makeVehicle: String) {
        uiStateEditVehicleTrailer.value = uiStateEditVehicleTrailer.value.copy(
            makeVehicle = makeVehicle
        )
        runBlocking {
            editVehicleTrailerDb.updateOneElementForIdMakeVehicle(
                id = uiStateEditVehicleTrailer.value.id,
                makeVehicle = uiStateEditVehicleTrailer.value.makeVehicle
            )
        }
    }

    fun updateDataEditVehicleTrailerRnVehicle(rnVehicle: String) {
        uiStateEditVehicleTrailer.value = uiStateEditVehicleTrailer.value.copy(
            rnVehicle = rnVehicle
        )
        runBlocking {
            editVehicleTrailerDb.updateOneElementForIdRnVehicle(
                id = uiStateEditVehicleTrailer.value.id,
                rnVehicle = uiStateEditVehicleTrailer.value.rnVehicle
            )
        }
    }

    fun updateDataEditVehicleTrailerMakeTrailer(makeTrailer: String) {
        uiStateEditVehicleTrailer.value = uiStateEditVehicleTrailer.value.copy(
            makeTrailer = makeTrailer
        )
        runBlocking {
            editVehicleTrailerDb.updateOneElementForIdMakeTrailer(
                id = uiStateEditVehicleTrailer.value.id,
                makeTrailer = uiStateEditVehicleTrailer.value.makeTrailer
            )
        }
    }

    fun updateDataEditVehicleTrailerRnTrailer(rnTrailer: String) {
        uiStateEditVehicleTrailer.value = uiStateEditVehicleTrailer.value.copy(
            rnTrailer = rnTrailer
        )
        runBlocking {
            editVehicleTrailerDb.updateOneElementForIdRnTrailer(
                id = uiStateEditVehicleTrailer.value.id,
                rnTrailer = uiStateEditVehicleTrailer.value.rnTrailer
            )
        }
    }

    fun isValidateDataEditVehicleTrailer(): Boolean {
        return uiStateEditVehicleTrailer.value.makeVehicle != "" &&
                uiStateEditVehicleTrailer.value.rnVehicle != "" &&
                uiStateEditVehicleTrailer.value.makeTrailer != "" &&
                uiStateEditVehicleTrailer.value.rnTrailer != ""
     }

    fun saveVehicleInDb(createVehicle: CreateObjectVehicleOrTrailer) = runBlocking {
        vehicleAndTrailerDb.insert(
            VehicleAndTrailer(
                vehicleOrTrailer = createVehicle.rn,
                make = createVehicle.make,
                registrationNumber = createVehicle.rn
            )
        )
    }

    fun saveTrailerInDb(createTrailer: CreateObjectVehicleOrTrailer) = runBlocking {
        vehicleAndTrailerDb.insert(
            VehicleAndTrailer(
                vehicleOrTrailer = createTrailer.type,
                make = createTrailer.make,
                registrationNumber = createTrailer.rn
            )
        )
    }

    private fun loadVehicleAndTrailerInDb() = runBlocking {
        val a = vehicleAndTrailerDb.getAllItem().first()
        for (i in a) {
            if(i.vehicleOrTrailer == "VEHICLE") {
                uiStateListVehicle.value.listVehicle.add(
                    EditVehicleTrailerDetailingListVehicle(
                        makeVehicle = i.make,
                        rnVehicle = i.registrationNumber
                    )
                )
            } else {
                uiStateListTrailer.value.listTrailer.add(
                    EditVehicleTrailerDetailingListTrailer(
                        makeTrailer = i.make,
                        rnTrailer = i.registrationNumber
                    )
                )
            }
        }
    }
}