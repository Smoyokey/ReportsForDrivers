package com.example.reportsfordrivers.viewmodel.historyreports

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reportsfordrivers.data.dao.MainInfoDao
import com.example.reportsfordrivers.data.dao.PersonalInfoDao
import com.example.reportsfordrivers.data.dao.ProgressReportDao
import com.example.reportsfordrivers.data.dao.ReportNameDao
import com.example.reportsfordrivers.data.dao.RouteDao
import com.example.reportsfordrivers.data.dao.TrailerDao
import com.example.reportsfordrivers.data.dao.TripExpensesDao
import com.example.reportsfordrivers.data.dao.VehicleDao
import com.example.reportsfordrivers.viewmodel.createreports.HtmlText
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateExpensesTripDetailingUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateProgressReportsDetailingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import javax.inject.Inject

private const val TAG = "HistoryReportsDetailingViewModel"

@HiltViewModel
class HistoryReportsDetailingViewModel @Inject constructor(

) : ViewModel() {

    @Inject
    lateinit var mainInfoDb: MainInfoDao

    @Inject
    lateinit var reportNameDb: ReportNameDao

    @Inject
    lateinit var personalInfoDb: PersonalInfoDao

    @Inject
    lateinit var vehicleDb: VehicleDao

    @Inject
    lateinit var trailerDb: TrailerDao

    @Inject
    lateinit var routeDb: RouteDao

    @Inject
    lateinit var progressReportsDb: ProgressReportDao

    @Inject
    lateinit var expensesTripDb: TripExpensesDao

    var uiState = mutableStateOf(HistoryReportsDetailingUiState())

    var firstOpenHistoryDetailing = mutableStateOf(false)
    var openDropdownMenu = mutableStateOf(false)
    var openSnackBarSaveReport = mutableStateOf(false)


    fun firstEntrySelectedReport(id: Int) = runBlocking {
        Log.i(TAG, id.toString())
        val mainInfo = mainInfoDb.getOneItem(id).first()
//        Log.i(TAG, mainInfo.id.toString())
        val reportName = reportNameDb.getOneItem(mainInfo.reportNameId).first()
        val personalInfo = personalInfoDb.getOneItem(reportName.personalInfoId).first()
        val vehicle = vehicleDb.getOneItem(reportName.vehicleId).first()
        val trailer =  trailerDb.getOneItem(reportName.trailerId).first()
        val route = routeDb.getOneItem(reportName.routeId).first()
        val progressReport = progressReportsDb.getAllElementsForReportNameId(reportName.id).first()
        val expensesTrip = expensesTripDb.getAllElementsForReportNameId(reportName.id).first()
        Log.i(TAG, progressReport.joinToString("::"))
        Log.i(TAG, expensesTrip.joinToString("::"))
//        val progressReports = progressReportsDb. //Дополнить запросы на выгрузку по id отчета

        uiState.value = uiState.value.copy(
            waybill = reportName.waybill,
            mainCity = reportName.mainCity,
            date = reportName.date,
            nameReport = mainInfo.nameReport,
            historyPersonalInfoUiState = HistoryPersonalInfoUiState(
                lastName = personalInfo.lastName,
                firstName = personalInfo.firstName,
                patronymic = personalInfo.patronymic
            ),
            historyVehicleUiState = HistoryVehicleUiState(
                makeVehicle = vehicle.makeVehicle,
                rnVehicle = vehicle.registrationNumberVehicle
            ),
            historyTrailerUiState = HistoryTrailerUiState(
                makeTrailer = trailer.makeTrailer,
                rnTrailer = trailer.registrationNumberTrailer
            ),
            historyRouteUiState = HistoryRouteUiState(
                route = route.route,
                dateDeparture = route.dateDeparture,
                dateReturn = route.dateReturn,
                dateCrossingDeparture = route.dateBorderCrossingDeparture,
                dateCrossingReturn = route.dateBorderCrossingReturn,
                speedometerDeparture = route.speedometerReadingDeparture,
                speedometerReturn = route.speedometerReadingReturn,
                fuelled = route.fuelled
            )
        )
        for(i in progressReport) {
            uiState.value.historyProgressReportsUiState.add(
                HistoryProgressReportDetailingUiState(
                    date = i.date,
                    country = i.country,
                    townshipOne = i.townshipOne,
                    townshipTwo = i.townshipTwo,
                    distance = i.distance,
                    weight = i.weight
                )
            )
        }

        for(i in expensesTrip) {
            uiState.value.historyExpensesTripUiState.add(
                HistoryExpensesTripDetailingUiState(
                    date = i.date,
                    documentNumber = i.documentNumber,
                    expenseItem = i.expenseItem,
                    sum = i.sum,
                    currency = i.currency
                )
            )
        }

        firstOpenHistoryDetailing.value = true
    }

    fun saveFile(context: Context) {
        writeFile(context)
    }

    private fun writeFile(context: Context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValue = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, "${uiState.value.nameReport}.docx")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }
            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValue)

            if(uri != null) {
                val fileOutputStream = resolver.openOutputStream(uri)
                fileOutputStream?.write(
                    HtmlText.onePageHtml(
                        dataCreateReportInfoDate = uiState.value.date,
                        dataCreateReportInfoMainCity = uiState.value.mainCity,
                        lastName = uiState.value.historyPersonalInfoUiState.lastName,
                        firstName = uiState.value.historyPersonalInfoUiState.firstName,
                        patronymic = uiState.value.historyPersonalInfoUiState.patronymic,
                        waybill = uiState.value.waybill,
                        makeVehicle = uiState.value.historyVehicleUiState.makeVehicle,
                        rnVehicle = uiState.value.historyVehicleUiState.rnVehicle,
                        makeTrailer = uiState.value.historyTrailerUiState.makeTrailer,
                        rnTrailer = uiState.value.historyTrailerUiState.rnTrailer,
                        route = uiState.value.historyRouteUiState.route,
                        dateDeparture = uiState.value.historyRouteUiState.dateDeparture,
                        dateReturn = uiState.value.historyRouteUiState.dateReturn,
                        dateCrossingDeparture = uiState.value.historyRouteUiState.dateCrossingDeparture,
                        dateCrossingReturn = uiState.value.historyRouteUiState.dateCrossingReturn,
                        speedometerDeparture = uiState.value.historyRouteUiState.speedometerDeparture,
                        speedometerReturn = uiState.value.historyRouteUiState.speedometerReturn,
                        fuelled = uiState.value.historyRouteUiState.fuelled,
                        progressReportsList = progressReports(uiState.value.historyProgressReportsUiState),
                        expensesTripList = expensesTrip(uiState.value.historyExpensesTripUiState)
                    ).toByteArray()
                )
                fileOutputStream?.close()
            }
        } else {
            try {
                val myFile = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "${uiState.value.nameReport}.docx"
                )
                myFile.delete()
                myFile.createNewFile()
                val outputStream = FileOutputStream(myFile)
                outputStream.write(
                    HtmlText.onePageHtml(
                        dataCreateReportInfoDate = uiState.value.date,
                        dataCreateReportInfoMainCity = uiState.value.mainCity,
                        lastName = uiState.value.historyPersonalInfoUiState.lastName,
                        firstName = uiState.value.historyPersonalInfoUiState.firstName,
                        patronymic = uiState.value.historyPersonalInfoUiState.patronymic,
                        waybill = uiState.value.waybill,
                        makeVehicle = uiState.value.historyVehicleUiState.makeVehicle,
                        rnVehicle = uiState.value.historyVehicleUiState.rnVehicle,
                        makeTrailer = uiState.value.historyTrailerUiState.makeTrailer,
                        rnTrailer = uiState.value.historyTrailerUiState.rnTrailer,
                        route = uiState.value.historyRouteUiState.route,
                        dateDeparture = uiState.value.historyRouteUiState.dateDeparture,
                        dateReturn = uiState.value.historyRouteUiState.dateReturn,
                        dateCrossingDeparture = uiState.value.historyRouteUiState.dateCrossingDeparture,
                        dateCrossingReturn = uiState.value.historyRouteUiState.dateCrossingReturn,
                        speedometerDeparture = uiState.value.historyRouteUiState.speedometerDeparture,
                        speedometerReturn = uiState.value.historyRouteUiState.speedometerReturn,
                        fuelled = uiState.value.historyRouteUiState.fuelled,
                        progressReportsList = progressReports(uiState.value.historyProgressReportsUiState),
                        expensesTripList = expensesTrip(uiState.value.historyExpensesTripUiState)
                    ).toByteArray()
                )
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun testShare(context: Context) {
        downloadFile(context)
    }

    private fun downloadFile(context: Context) {
        runBlocking {
            val filePath = File(context.filesDir, "docs")
            filePath.mkdir()
            val newFile = File(filePath, "${uiState.value.nameReport}.docx")
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
                        dataCreateReportInfoDate = uiState.value.date,
                        dataCreateReportInfoMainCity = uiState.value.mainCity,
                        lastName = uiState.value.historyPersonalInfoUiState.lastName,
                        firstName = uiState.value.historyPersonalInfoUiState.firstName,
                        patronymic = uiState.value.historyPersonalInfoUiState.patronymic,
                        waybill = uiState.value.waybill,
                        makeVehicle = uiState.value.historyVehicleUiState.makeVehicle,
                        rnVehicle = uiState.value.historyVehicleUiState.rnVehicle,
                        makeTrailer = uiState.value.historyTrailerUiState.makeTrailer,
                        rnTrailer = uiState.value.historyTrailerUiState.rnTrailer,
                        route = uiState.value.historyRouteUiState.route,
                        dateDeparture = uiState.value.historyRouteUiState.dateDeparture,
                        dateReturn = uiState.value.historyRouteUiState.dateReturn,
                        dateCrossingDeparture = uiState.value.historyRouteUiState.dateCrossingDeparture,
                        dateCrossingReturn = uiState.value.historyRouteUiState.dateCrossingReturn,
                        speedometerDeparture = uiState.value.historyRouteUiState.speedometerDeparture,
                        speedometerReturn = uiState.value.historyRouteUiState.speedometerReturn,
                        fuelled = uiState.value.historyRouteUiState.fuelled,
                        progressReportsList = progressReports(uiState.value.historyProgressReportsUiState),
                        expensesTripList = expensesTrip(uiState.value.historyExpensesTripUiState)
                    )
                )
            } catch (e: Exception) {}
            fileWriter!!.close()

            createFile(contentUri, context)
        }
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

    private fun progressReports(list: List<HistoryProgressReportDetailingUiState>):
            List<CreateProgressReportsDetailingUiState> {
        val a = mutableListOf<CreateProgressReportsDetailingUiState>()
        for(i in list) {
            a.add(
                CreateProgressReportsDetailingUiState(
                    country = i.country,
                    townshipOne = i.townshipOne,
                    townshipTwo = i.townshipTwo,
                    distance = i.distance,
                    cargoWeight = i.weight,
                    date = i.date,
                    isAdd = 1
                )
            )
        }
        return a
    }

    private fun expensesTrip(list: List<HistoryExpensesTripDetailingUiState>) :
            List<CreateExpensesTripDetailingUiState> {
        val a = mutableListOf<CreateExpensesTripDetailingUiState>()
        for(i in list) {
            a.add(
                CreateExpensesTripDetailingUiState(
                    date = i.date,
                    documentNumber = i.documentNumber,
                    expenseItem = i.expenseItem,
                    sum = i.sum,
                    currency = i.currency,
                    isAdd = 1
                )
            )
        }
        return a
    }
}