package com.example.reportsfordrivers.viewmodel.historyreports.editreport.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.data.dao.editreport.EditPersonalInfoDao
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditPersonalInfoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

private const val TAG = "EditPersonalInfoViewModel"

@HiltViewModel
class EditPersonalInfoViewModel @Inject constructor(
    private val fioPreferencesRepository: FioFirstEntryRepository
) : ViewModel(){

    @Inject
    lateinit var editPersonalInfoDb: EditPersonalInfoDao

    var uiStateEditPersonalInfo = mutableStateOf(EditPersonalInfoUiState())

    var firstOpenPersonalScreen = mutableStateOf(false)

    fun startLoadEditPersonalInfo() = runBlocking {
        val editPersonalInfo = editPersonalInfoDb.getAllItem().first()
        uiStateEditPersonalInfo.value = uiStateEditPersonalInfo.value.copy(
            id = editPersonalInfo[0].id,
            lastName = editPersonalInfo[0].lastName.ifEmpty { "" },
            firstName = editPersonalInfo[0].firstName.ifEmpty { "" },
            patronymic = editPersonalInfo[0].patronymic.ifEmpty { "" }
        )
        fioPreferencesRepository.setEditSelectedPage(2)
        firstOpenPersonalScreen.value = true
    }

    fun updateDataEditPersonalInfoLastName(lastName: String) {
        uiStateEditPersonalInfo.value = uiStateEditPersonalInfo.value.copy(
            lastName = lastName
        )
        runBlocking {
            editPersonalInfoDb.updateOneElementForIdLastName(
                id = uiStateEditPersonalInfo.value.id,
                lastName = uiStateEditPersonalInfo.value.lastName
            )
        }
    }

    fun updateDataEditPersonalInfoFirstName(firstName: String) {
        uiStateEditPersonalInfo.value = uiStateEditPersonalInfo.value.copy(
            firstName = firstName
        )
        runBlocking {
            editPersonalInfoDb.updateOneElementForIdFirstName(
                id = uiStateEditPersonalInfo.value.id,
                firstName = uiStateEditPersonalInfo.value.firstName
            )
        }
    }

    fun updateDataEditPersonalInfoPatronymic(patronymic: String) {
        uiStateEditPersonalInfo.value = uiStateEditPersonalInfo.value.copy(
            patronymic = patronymic
        )
        runBlocking {
            editPersonalInfoDb.updateOneElementForIdPatronymic(
                id = uiStateEditPersonalInfo.value.id,
                patronymic = uiStateEditPersonalInfo.value.patronymic
            )
        }
    }

    fun isValidateDataEditPersonalInfo(): Boolean {
        return uiStateEditPersonalInfo.value.lastName != "" &&
                uiStateEditPersonalInfo.value.firstName != "" &&
                uiStateEditPersonalInfo.value.patronymic != ""
    }
}