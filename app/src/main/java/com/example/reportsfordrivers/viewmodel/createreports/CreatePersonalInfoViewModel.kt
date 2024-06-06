package com.example.reportsfordrivers.viewmodel.createreports

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.data.dao.createreport.CreatePersonalInfoDao
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreatePersonalInfoUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.SelectedNavigationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

private const val TAG = "CreatePersonalInfoViewModel"

@HiltViewModel
class CreatePersonalInfoViewModel @Inject constructor(
    private val fioPreferencesRepository: FioFirstEntryRepository
) : ViewModel() {

    @Inject
    lateinit var createPersonalInfoDb: CreatePersonalInfoDao

    var uiStateCreatePersonalInfo = mutableStateOf(CreatePersonalInfoUiState())
        private set
    var uiStateIsValidate = mutableStateOf(SelectedNavigationUiState())

    var firstOpenPersonalScreen = mutableStateOf(false)

    fun startFio() = runBlocking {
        startLoadCreatePersonalInfo()
        uiStateCreatePersonalInfo.value = uiStateCreatePersonalInfo.value.copy(
            lastName = fioPreferencesRepository.getLastName()
                .getOrDefault(uiStateCreatePersonalInfo.value.lastName),
            firstName = fioPreferencesRepository.getFirstName()
                .getOrDefault(uiStateCreatePersonalInfo.value.firstName),
            patronymic = fioPreferencesRepository.getPatronymic()
                .getOrDefault(uiStateCreatePersonalInfo.value.patronymic)
        )
        firstOpenPersonalScreen.value = true
        Log.i(TAG, fioPreferencesRepository.getFirstName().getOrDefault("EMPTY"))
    }


    fun updateDataCreatePersonalInfoLastName(lastName: String) {
        uiStateCreatePersonalInfo.value = uiStateCreatePersonalInfo.value.copy(
            lastName = lastName
        )
        runBlocking {
            createPersonalInfoDb.updateOneElementForIdLastName(
                id = uiStateCreatePersonalInfo.value.id,
                lastName = uiStateCreatePersonalInfo.value.lastName
            )
        }
    }

    fun updateDataCreatePersonalInfoFirstName(firstName: String) {
        uiStateCreatePersonalInfo.value = uiStateCreatePersonalInfo.value.copy(
            firstName = firstName
        )
        runBlocking {
            createPersonalInfoDb.updateOneElementForIdFirstName(
                id = uiStateCreatePersonalInfo.value.id,
                firstName = uiStateCreatePersonalInfo.value.firstName
            )
        }
    }

    fun updateDataCreatePersonalInfoPatronymic(patronymic: String) {
        uiStateCreatePersonalInfo.value = uiStateCreatePersonalInfo.value.copy(
            patronymic = patronymic
        )
        runBlocking {
            createPersonalInfoDb.updateOneElementForIdPatronymic(
                id = uiStateCreatePersonalInfo.value.id,
                patronymic = uiStateCreatePersonalInfo.value.patronymic
            )
        }
    }

    fun isValidateDataCreatePersonalInfo(): Boolean {
        return uiStateCreatePersonalInfo.value.lastName != "" &&
                uiStateCreatePersonalInfo.value.firstName != "" &&
                uiStateCreatePersonalInfo.value.patronymic != ""
    }

    private fun startLoadCreatePersonalInfo() = runBlocking {
        val createPersonalInfo = createPersonalInfoDb.getAllItem().first()
        uiStateCreatePersonalInfo.value = uiStateCreatePersonalInfo.value.copy(
            id = createPersonalInfo[0].id,
            lastName = createPersonalInfo[0].lastName.ifEmpty { "" },
            firstName = createPersonalInfo[0].firstName.ifEmpty { "" },
            patronymic = createPersonalInfo[0].patronymic.ifEmpty { "" }
        )
        fioPreferencesRepository.setCreateSelectedPage(2)
    }
}