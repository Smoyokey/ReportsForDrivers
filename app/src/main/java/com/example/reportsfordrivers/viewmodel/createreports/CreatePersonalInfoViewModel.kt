package com.example.reportsfordrivers.viewmodel.createreports

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.data.dao.createReport.CreatePersonalInfoDao
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

    private var firstOpenPersonalScreen = mutableStateOf(false)

    fun startLoadCreatePersonalInfo() = runBlocking {
        val createPersonalInfo = createPersonalInfoDb.getAllItem().first()
        uiStateCreatePersonalInfo.value = uiStateCreatePersonalInfo.value.copy(
            lastName = createPersonalInfo[0].lastName.ifEmpty { "" },
            firstName = createPersonalInfo[0].firstName.ifEmpty { "" },
            patronymic = createPersonalInfo[0].patronymic.ifEmpty { "" }
        )
    }

    fun startFio() = runBlocking {
        if (!firstOpenPersonalScreen.value) {
            uiStateCreatePersonalInfo.value = uiStateCreatePersonalInfo.value.copy(
                lastName = fioPreferencesRepository.getLastName().getOrDefault(""),
                firstName = fioPreferencesRepository.getFirstName().getOrDefault(""),
                patronymic = fioPreferencesRepository.getPatronymic().getOrDefault("")
            )
            firstOpenPersonalScreen.value = true
        }
    }

    fun updateDataCreatePersonalInfoLastName(lastName: String) {
        uiStateCreatePersonalInfo.value = uiStateCreatePersonalInfo.value.copy(
            lastName = lastName
        )
    }

    fun updateDataCreatePersonalInfoFirstName(firstName: String) {
        uiStateCreatePersonalInfo.value = uiStateCreatePersonalInfo.value.copy(
            firstName = firstName
        )
    }

    fun updateDataCreatePersonalInfoPatronymic(patronymic: String) {
        uiStateCreatePersonalInfo.value = uiStateCreatePersonalInfo.value.copy(
            patronymic = patronymic
        )
    }

    fun isValidateDataCreatePersonalInfo(): Boolean {
        return uiStateCreatePersonalInfo.value.lastName != "" &&
                uiStateCreatePersonalInfo.value.firstName != "" &&
                uiStateCreatePersonalInfo.value.patronymic != ""
    }
}