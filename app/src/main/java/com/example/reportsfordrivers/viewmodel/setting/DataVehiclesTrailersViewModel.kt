package com.example.reportsfordrivers.viewmodel.setting

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.data.dao.VehicleAndTrailerSaveDataDao
import com.example.reportsfordrivers.viewmodel.ObjectVehicle
import com.example.reportsfordrivers.viewmodel.setting.uistate.DataVehicleTrailerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DataVehiclesTrailersViewModel @Inject constructor(
    private var vehicleAndTrailer: VehicleAndTrailerSaveDataDao
) : ViewModel() {

    var uiState = mutableStateOf(startScreen())

    val objectVehicleUiState = mutableStateOf(ObjectVehicle())

    val isOpenDialogSaveElement: MutableState<Boolean> = mutableStateOf(false)

    private fun startScreen(): DataVehicleTrailerUiState = runBlocking {
        val b = SnapshotStateList<ObjectVehicle>()
        vehicleAndTrailer.getAllItem().first().forEach { element ->
            Log.i("TAG", element.make)
            b.add(
                ObjectVehicle(
                    make = element.make,
                    rn = element.registrationNumber,
                    type = element.vehicleOrTrailer
                )
            )
        }
        return@runBlocking DataVehicleTrailerUiState(b)
    }

    fun updateMake(text: String) {
        objectVehicleUiState.value = objectVehicleUiState.value.copy(make = text)
    }

    fun updateRn(text: String) {
        objectVehicleUiState.value = objectVehicleUiState.value.copy(rn = text)
    }

    fun deletePositionVehicle(item: ObjectVehicle) {
        uiState.value.listVehicles.remove(item)

    }
}