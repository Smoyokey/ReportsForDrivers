package com.example.reportsfordrivers.viewmodel.createreports

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.data.dao.VehicleAndTrailerSaveDataDao
import com.example.reportsfordrivers.data.dao.createReport.CreateVehicleTrailerDao
import com.example.reportsfordrivers.data.structure.VehicleAndTrailer
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateObjectVehicleOrTrailer
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateVehicleTrailerDetailingListTrailer
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateVehicleTrailerDetailingListVehicle
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateVehicleTrailerListTrailer
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateVehicleTrailerListVehicle
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateVehicleTrailerUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.SelectedNavigationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class CreateVehicleTrailerViewModel @Inject constructor(
    private val fioPreferencesRepository: FioFirstEntryRepository
) : ViewModel() {

    @Inject
    lateinit var createVehicleTrailerDb:  CreateVehicleTrailerDao

    @Inject
    lateinit var vehicleAndTrailerDb: VehicleAndTrailerSaveDataDao

    var uiStateCreateVehicleTrailer = mutableStateOf(CreateVehicleTrailerUiState())
        private set
    var uiStateIsValidate = mutableStateOf(SelectedNavigationUiState())

    var uiStateListVehicle = mutableStateOf(CreateVehicleTrailerListVehicle())
    var uiStateListTrailer = mutableStateOf(CreateVehicleTrailerListTrailer())

    var openDialogCreateVehicleCreateVehicleTrailer = mutableStateOf(false)
    var openDialogCreateTrailerCreateVehicleTrailer = mutableStateOf(false)
    var openMenuMakeVehicleCreateVehicleTrailer = mutableStateOf(false)
    var openMenuMakeTrailerCreateVehicleTrailer = mutableStateOf(false)

    var firstOpenCreateVehicleTrailer = mutableStateOf(false)

    fun startLoadCreateVehicleTrailer() = runBlocking {
        val createVehicleTrailer = createVehicleTrailerDb.getAllItem().first()
        uiStateCreateVehicleTrailer.value = uiStateCreateVehicleTrailer.value.copy(
            id = createVehicleTrailer[0].id,
            makeVehicle = createVehicleTrailer[0].makeVehicle.ifEmpty { "" },
            rnVehicle = createVehicleTrailer[0].rnVehicle.ifEmpty { "" },
            makeTrailer = createVehicleTrailer[0].makeTrailer.ifEmpty { "" },
            rnTrailer = createVehicleTrailer[0].rnTrailer.ifEmpty { "" }
        )
        fioPreferencesRepository.setCreateSelectedPage(3)
        loadVehicleAndTrailerInDb()
        firstOpenCreateVehicleTrailer.value = true
    }

    fun updateDataCreateVehicleTrailerMakeVehicle(makeVehicle: String) {
        uiStateCreateVehicleTrailer.value = uiStateCreateVehicleTrailer.value.copy(
            makeVehicle = makeVehicle
        )
        runBlocking {
            createVehicleTrailerDb.updateOneElementForIdMakeVehicle(
                id = uiStateCreateVehicleTrailer.value.id,
                makeVehicle = uiStateCreateVehicleTrailer.value.makeVehicle
            )
        }
    }

    fun updateDataCreateVehicleTrailerRnVehicle(rnVehicle: String) {
        uiStateCreateVehicleTrailer.value = uiStateCreateVehicleTrailer.value.copy(
            rnVehicle = rnVehicle
        )
        runBlocking {
            createVehicleTrailerDb.updateOneElementForIdRnVehicle(
                id = uiStateCreateVehicleTrailer.value.id,
                rnVehicle = uiStateCreateVehicleTrailer.value.rnVehicle
            )
        }
    }

    fun updateDataCreateVehicleTrailerMakeTrailer(makeTrailer: String) {
        uiStateCreateVehicleTrailer.value = uiStateCreateVehicleTrailer.value.copy(
            makeTrailer = makeTrailer
        )
        runBlocking {
            createVehicleTrailerDb.updateOneElementForIdMakeTrailer(
                id = uiStateCreateVehicleTrailer.value.id,
                makeTrailer = uiStateCreateVehicleTrailer.value.makeTrailer
            )
        }
    }

    fun updateDataCreateVehicleTrailerRnTrailer(rnTrailer: String) {
        uiStateCreateVehicleTrailer.value = uiStateCreateVehicleTrailer.value.copy(
            rnTrailer = rnTrailer
        )
        runBlocking {
            createVehicleTrailerDb.updateOneElementForIdRnTrailer(
                id = uiStateCreateVehicleTrailer.value.id,
                rnTrailer = uiStateCreateVehicleTrailer.value.rnTrailer
            )
        }
    }

    fun isValidateDataCreateVehicleTrailer(): Boolean {
        return uiStateCreateVehicleTrailer.value.makeVehicle != "" &&
                uiStateCreateVehicleTrailer.value.rnVehicle != "" &&
                uiStateCreateVehicleTrailer.value.makeTrailer != "" &&
                uiStateCreateVehicleTrailer.value.rnTrailer != ""
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
        for(i in a) {
            if(i.vehicleOrTrailer == "VEHICLE") {
                uiStateListVehicle.value.listVehicle.add(
                    CreateVehicleTrailerDetailingListVehicle(
                        makeVehicle = i.make,
                        rnVehicle = i.registrationNumber
                    )
                )
            } else {
                uiStateListTrailer.value.listTrailer.add(
                    CreateVehicleTrailerDetailingListTrailer(
                        makeTrailer = i.make,
                        rnTrailer = i.registrationNumber
                    )
                )
            }
        }
    }
}