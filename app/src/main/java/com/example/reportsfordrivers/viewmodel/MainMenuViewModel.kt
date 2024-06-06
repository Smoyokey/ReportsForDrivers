package com.example.reportsfordrivers.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.data.dao.MainInfoDao
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.viewmodel.createreports.CreatePreviewAndResultViewModel
import com.example.reportsfordrivers.viewmodel.createreports.HtmlText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileWriter
import javax.inject.Inject

private const val TAG = "MainMenuViewModel"

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    private val firstEntryPreferencesRepository: FioFirstEntryRepository
) : ViewModel() {

    val isToBeContinued = mutableStateOf(isToBeContinued())

    val openSnackBarSaveReport = mutableStateOf(false)

    @Inject
    lateinit var mainInfoDb: MainInfoDao

    fun isFirstEntry(): Boolean = runBlocking {
        return@runBlocking firstEntryPreferencesRepository.getFirstEntry().getOrNull() ?: false
    }

    private fun isToBeContinued(): Boolean = runBlocking {
        return@runBlocking firstEntryPreferencesRepository.getStartCreateReport().getOrNull()
            ?: false
    }

    fun selectedPage(): Int = runBlocking {
        return@runBlocking firstEntryPreferencesRepository.getCreateSelectedPage().getOrNull() ?: 0
    }

    fun testShare(context: Context, viewModelResult: CreatePreviewAndResultViewModel) {
        downloadFile(context, viewModelResult)
    }

    private fun downloadFile(context: Context, viewModelResult: CreatePreviewAndResultViewModel) =
        runBlocking {
            val filePath = File(context.filesDir, "docs")
            filePath.mkdir()
            val newFile = File(filePath, "${mainInfoDb.getLastNameReport()}.docx")
            newFile.delete()
            newFile.createNewFile()
            val contentUri = FileProvider.getUriForFile(
                context,
                "com.example.reportsfordrivers.fileprovider",
                newFile
            )
            var fileWriter: FileWriter? = null
            try {
                fileWriter = FileWriter(newFile)
                fileWriter.append(
                    HtmlText.onePageHtml(
                        dataCreateReportInfoDate = viewModelResult.uiState.value.dataCreateReportInfo.date,
                        dataCreateReportInfoMainCity = viewModelResult.uiState.value.dataCreateReportInfo.mainCity,
                        lastName = viewModelResult.uiState.value.dataCreatePersonalInfo.lastName,
                        firstName = viewModelResult.uiState.value.dataCreatePersonalInfo.firstName,
                        patronymic = viewModelResult.uiState.value.dataCreatePersonalInfo.patronymic,
                        waybill = viewModelResult.uiState.value.dataCreateReportInfo.waybill,
                        makeVehicle = viewModelResult.uiState.value.dataCreateVehicleInfo.makeVehicle,
                        rnVehicle = viewModelResult.uiState.value.dataCreateVehicleInfo.rnVehicle,
                        makeTrailer = viewModelResult.uiState.value.dataCreateVehicleInfo.makeTrailer,
                        rnTrailer = viewModelResult.uiState.value.dataCreateVehicleInfo.rnTrailer,
                        route = viewModelResult.uiState.value.dataCreateRoute.route,
                        dateDeparture = viewModelResult.uiState.value.dataCreateRoute.dateDeparture,
                        dateReturn = viewModelResult.uiState.value.dataCreateRoute.dateReturn,
                        dateCrossingDeparture = viewModelResult.uiState.value.dataCreateRoute.dateCrossingDeparture,
                        dateCrossingReturn = viewModelResult.uiState.value.dataCreateRoute.dateCrossingReturn,
                        speedometerDeparture = viewModelResult.uiState.value.dataCreateRoute.speedometerDeparture,
                        speedometerReturn = viewModelResult.uiState.value.dataCreateRoute.speedometerReturn,
                        fuelled = viewModelResult.uiState.value.dataCreateRoute.fuelled,
                        progressReportsList = viewModelResult.uiState.value.listDataCreateProgressReports,
                        expensesTripList = viewModelResult.uiState.value.listDataCreateExpensesTrip
                    )
                )
            } catch (e: Exception) {
//            Log.e(com.example.reportsfordrivers.viewmodel.createreports.TAG, e.printStackTrace().toString())
            }
            fileWriter!!.close()

            createFile(contentUri, context)
        }

    private fun createFile(pickerInitialUri: Uri, context: Context) {
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, pickerInitialUri)
            type = "text/plain"
            setDataAndType(pickerInitialUri, context.contentResolver.getType(pickerInitialUri))
        }
        context.startActivity(Intent.createChooser(shareIntent, "My First Doc"))
    }

}