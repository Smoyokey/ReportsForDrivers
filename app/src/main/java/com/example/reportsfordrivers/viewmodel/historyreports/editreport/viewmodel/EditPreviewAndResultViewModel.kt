package com.example.reportsfordrivers.viewmodel.historyreports.editreport.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.data.dao.MainInfoDao
import com.example.reportsfordrivers.data.dao.PersonalInfoDao
import com.example.reportsfordrivers.data.dao.ProgressReportDao
import com.example.reportsfordrivers.data.dao.ReportNameDao
import com.example.reportsfordrivers.data.dao.RouteDao
import com.example.reportsfordrivers.data.dao.TrailerDao
import com.example.reportsfordrivers.data.dao.TripExpensesDao
import com.example.reportsfordrivers.data.dao.VehicleDao
import com.example.reportsfordrivers.data.dao.editreport.EditExpensesTripDao
import com.example.reportsfordrivers.data.dao.editreport.EditPersonalInfoDao
import com.example.reportsfordrivers.data.dao.editreport.EditProgressReportsDao
import com.example.reportsfordrivers.data.dao.editreport.EditReportInfoDao
import com.example.reportsfordrivers.data.dao.editreport.EditRouteDao
import com.example.reportsfordrivers.data.dao.editreport.EditVehicleTrailerDao
import com.example.reportsfordrivers.data.structure.PersonalInfo
import com.example.reportsfordrivers.data.structure.ProgressReport
import com.example.reportsfordrivers.data.structure.Route
import com.example.reportsfordrivers.data.structure.Trailer
import com.example.reportsfordrivers.data.structure.TripExpenses
import com.example.reportsfordrivers.data.structure.Vehicle
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.viewmodel.createreports.HtmlText
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditExpensesTripDetailingUiState
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditPersonalInfoUiState
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditProgressReportsDetailingUiState
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditReportInfoUiState
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditReports
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditReportsId
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditReportsRouteUiState
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditVehicleTrailerUiState
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

private const val TAG = "EditPreviewAndResultViewModel"

@HiltViewModel
class EditPreviewAndResultViewModel @Inject constructor(
    private val fioPreferencesRepository: FioFirstEntryRepository
) : ViewModel() {

    @Inject
    lateinit var editReportInfoDb: EditReportInfoDao

    @Inject
    lateinit var editPersonalInfoDb: EditPersonalInfoDao

    @Inject
    lateinit var editVehicleTrailerDb: EditVehicleTrailerDao

    @Inject
    lateinit var editRouteDb: EditRouteDao

    @Inject
    lateinit var editProgressReportsDb: EditProgressReportsDao

    @Inject
    lateinit var editExpensesTripDb: EditExpensesTripDao


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

    @SuppressLint("StaticFieldLeak")
    lateinit var activity: Activity

    var uiState = mutableStateOf(EditReports())
        private set

    var firstOpenReportsEditPreviewAndResult = mutableStateOf(false)

    var isOpenPermissionSaveFile = mutableStateOf(false)

    fun startLoadScreen() = runBlocking {
        loadEditReportInfo()
        loadEditPersonalInfo()
        loadEditVehicleTrailer()
        loadEditRoute()
        loadEditProgressReports()
        loadEditExpensesTrip()
        loadReportId()
        fioPreferencesRepository.setEditSelectedPage(7)
        firstOpenReportsEditPreviewAndResult.value = true
    }

    private fun loadEditReportInfo() = runBlocking {
        val editReportInfo = editReportInfoDb.getAllItem().first()
        val editReportInfoUiState = EditReportInfoUiState(
            date = editReportInfo[0].date,
            waybill = editReportInfo[0].waybill,
            mainCity = editReportInfo[0].mainCity
        )

        uiState.value = uiState.value.copy(dataEditReportInfo = editReportInfoUiState)
    }

    private fun loadEditPersonalInfo() = runBlocking {
        val editPersonalInfo = editPersonalInfoDb.getAllItem().first()
        val editPersonalInfoUiState = EditPersonalInfoUiState(
            lastName = editPersonalInfo[0].lastName,
            firstName = editPersonalInfo[0].firstName,
            patronymic = editPersonalInfo[0].patronymic
        )
        uiState.value = uiState.value.copy(dataEditPersonalInfo = editPersonalInfoUiState)
    }

    private fun loadEditVehicleTrailer() = runBlocking {
        val editVehicleTrailer = editVehicleTrailerDb.getAllItem().first()
        val editVehicleTrailerUiState = EditVehicleTrailerUiState(
            makeVehicle = editVehicleTrailer[0].makeVehicle,
            rnVehicle = editVehicleTrailer[0].rnVehicle,
            makeTrailer = editVehicleTrailer[0].makeTrailer,
            rnTrailer = editVehicleTrailer[0].rnTrailer
        )
        uiState.value = uiState.value.copy(dataEditVehicleInfo = editVehicleTrailerUiState)
    }

    private fun loadEditRoute() = runBlocking {
        val editRoute = editRouteDb.getAllItem().first()
        val editReportsRouteUiState = EditReportsRouteUiState(
            route = editRoute[0].route,
            dateDeparture = editRoute[0].dateDeparture,
            dateReturn = editRoute[0].dateReturn,
            dateCrossingDeparture = editRoute[0].dateBorderCrossingDeparture,
            dateCrossingReturn = editRoute[0].dateBorderCrossingReturn,
            speedometerDeparture = editRoute[0].speedometerReadingDeparture,
            speedometerReturn = editRoute[0].speedometerReadingReturn,
            fuelled = editRoute[0].fuelled
        )
        uiState.value = uiState.value.copy(dataEditRoute = editReportsRouteUiState)
    }

    private fun loadEditProgressReports() = runBlocking {
        val editProgressReports = editProgressReportsDb.getAllItem().first()
        for(i in editProgressReports) {
            if (i.isAdd == 1) {
                uiState.value.listDataEditProgressReports.add(
                    EditProgressReportsDetailingUiState(
                        country = i.country,
                        townshipOne = i.townshipOne,
                        townshipTwo = i.townshipTwo,
                        distance = i.distance,
                        cargoWeight = i.weight,
                        date = i.date
                    )
                )
            }
        }
    }

    private fun loadEditExpensesTrip() = runBlocking {
        val editExpensesTrip = editExpensesTripDb.getAllItem().first()
        for(i in editExpensesTrip) {
            if(i.isAdd == 1) {
                uiState.value.listDataEditExpensesTrip.add(
                    EditExpensesTripDetailingUiState(
                        date = i.date,
                        documentNumber = i.documentNumber,
                        expenseItem = i.expenseItem,
                        sum = i.sum,
                        currency = i.currency
                    )
                )
            }
        }
    }

    private fun loadReportId() = runBlocking {
        val a = reportNameDb.getOneItem(fioPreferencesRepository.getEditSelectedId().getOrDefault(0)).first()

        uiState.value = uiState.value.copy(
            editReportsId = EditReportsId(
                mainInfoId = a.id,
                reportNameId = a.id,
                personalInfoId = a.personalInfoId,
                vehicleId = a.vehicleId,
                trailerId = a.trailerId,
                routeId = a.routeId
            )
        )
    }

    fun updatePreviewReportName(reportName: String) {
        uiState.value = uiState.value.copy(reportName = reportName)
    }

    fun saveDataInBd() {

    }

    private fun updatePersonalInfo() = runBlocking {
        personalInfoDb.update(
            PersonalInfo(
                id = uiState.value.editReportsId.personalInfoId,
                lastName = uiState.value.dataEditPersonalInfo.lastName,
                firstName = uiState.value.dataEditPersonalInfo.firstName,
                patronymic = uiState.value.dataEditPersonalInfo.patronymic
            )
        )
    }

    private fun updateVehicle() = runBlocking {
        vehicleDb.update(
            Vehicle(
                id = uiState.value.editReportsId.vehicleId,
                makeVehicle = uiState.value.dataEditVehicleInfo.makeVehicle,
                registrationNumberVehicle = uiState.value.dataEditVehicleInfo.rnVehicle
            )
        )
    }

    private fun updateTrailer() = runBlocking {
        trailerDb.insert(
            Trailer(
                id = uiState.value.editReportsId.trailerId,
                makeTrailer = uiState.value.dataEditVehicleInfo.makeTrailer,
                registrationNumberTrailer = uiState.value.dataEditVehicleInfo.rnTrailer
            )
        )
    }

    private fun updateRoute() = runBlocking {
        routeDb.insert(
            Route(
                id = uiState.value.editReportsId.routeId,
                route = uiState.value.dataEditRoute.route,
                dateDeparture = uiState.value.dataEditRoute.dateDeparture,
                dateReturn = uiState.value.dataEditRoute.dateReturn,
                dateBorderCrossingDeparture = uiState.value.dataEditRoute.dateCrossingDeparture,
                dateBorderCrossingReturn = uiState.value.dataEditRoute.dateCrossingReturn,
                speedometerReadingDeparture = uiState.value.dataEditRoute.speedometerDeparture,
                speedometerReadingReturn = uiState.value.dataEditRoute.speedometerReturn,
                fuelled = uiState.value.dataEditRoute.fuelled
            )
        )
    }

    private fun saveProgressReports() = runBlocking {
        progressReportsDb.deleteAllElementsForReportNameId(uiState.value.editReportsId.reportNameId)
        for(i in uiState.value.listDataEditProgressReports) {
            progressReportsDb.insert(
                ProgressReport(
                    date = i.date,
                    country = i.country,
                    townshipOne = i.townshipOne,
                    townshipTwo = i.townshipTwo,
                    distance = i.distance,
                    weight = i.cargoWeight,
                    reportNameId = uiState.value.editReportsId.reportNameId
                )
            )
        }
    }

    private fun saveExpensesTrip() = runBlocking {
        expensesTripDb.deleteAllElementsForReportNameId(uiState.value.editReportsId.reportNameId)
        if (uiState.value.listDataEditExpensesTrip.isEmpty()) return@runBlocking
        for(i in uiState.value.listDataEditExpensesTrip) {
            expensesTripDb.insert(
                TripExpenses(
                    date = i.date,
                    documentNumber = i.documentNumber,
                    expenseItem = i.expenseItem,
                    sum = i.sum,
                    currency = i.currency,
                    reportNameId = uiState.value.editReportsId.reportNameId
                )
            )
        }
    }

    private fun updateReportName() = runBlocking {
        reportNameDb.updateWaybillMainCityDate(
            waybill = uiState.value.dataEditReportInfo.waybill,
            mainCity = uiState.value.dataEditReportInfo.mainCity,
            date = uiState.value.dataEditReportInfo.date,
            id = uiState.value.editReportsId.reportNameId
        )
    }

    private fun updateMainInfo() = runBlocking {
        mainInfoDb.updateNameReportDateCreateRouteMainInfoForId(
            nameReport = uiState.value.reportName,
            dateCreate = uiState.value.dataEditReportInfo.date,
            routeMainInfo = uiState.value.dataEditRoute.route,
            id = uiState.value.editReportsId.reportNameId
        )
    }

    fun saveFile(context: Context) {
        writeFile(context)
    }

    private fun writeFile(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, "${uiState.value.reportName}.docx")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }
            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

            if(uri  != null) {
                val fileOutputStream = resolver.openOutputStream(uri)
                fileOutputStream?.write(
                    HtmlText.oneEditPageHtml(
                        dataCreateReportInfoDate = uiState.value.dataEditReportInfo.date,
                        dataCreateReportInfoMainCity = uiState.value.dataEditReportInfo.mainCity,
                        lastName = uiState.value.dataEditPersonalInfo.lastName,
                        firstName = uiState.value.dataEditPersonalInfo.firstName,
                        patronymic = uiState.value.dataEditPersonalInfo.patronymic,
                        waybill = uiState.value.dataEditReportInfo.waybill,
                        makeVehicle = uiState.value.dataEditVehicleInfo.makeVehicle,
                        rnVehicle = uiState.value.dataEditVehicleInfo.rnVehicle,
                        makeTrailer = uiState.value.dataEditVehicleInfo.makeTrailer,
                        rnTrailer = uiState.value.dataEditVehicleInfo.rnTrailer,
                        route = uiState.value.dataEditRoute.route,
                        dateDeparture = uiState.value.dataEditRoute.dateDeparture,
                        dateReturn = uiState.value.dataEditRoute.dateReturn,
                        dateCrossingDeparture = uiState.value.dataEditRoute.dateCrossingDeparture,
                        dateCrossingReturn = uiState.value.dataEditRoute.dateCrossingReturn,
                        speedometerDeparture = uiState.value.dataEditRoute.speedometerDeparture,
                        speedometerReturn = uiState.value.dataEditRoute.speedometerReturn,
                        fuelled = uiState.value.dataEditRoute.fuelled,
                        progressReportsList = uiState.value.listDataEditProgressReports,
                        expensesTripList = uiState.value.listDataEditExpensesTrip
                    ).toByteArray()
                )
                fileOutputStream?.close()
            }
        } else {
            try {
                val myFile = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "${uiState.value.reportName}.docx"
                )

                myFile.delete()
                myFile.createNewFile()

                val outputStream = FileOutputStream(myFile)

                outputStream.write(
                    HtmlText.oneEditPageHtml(
                        dataCreateReportInfoDate = uiState.value.dataEditReportInfo.date,
                        dataCreateReportInfoMainCity = uiState.value.dataEditReportInfo.mainCity,
                        lastName = uiState.value.dataEditPersonalInfo.lastName,
                        firstName = uiState.value.dataEditPersonalInfo.firstName,
                        patronymic = uiState.value.dataEditPersonalInfo.patronymic,
                        waybill = uiState.value.dataEditReportInfo.waybill,
                        makeVehicle = uiState.value.dataEditVehicleInfo.makeVehicle,
                        rnVehicle = uiState.value.dataEditVehicleInfo.rnVehicle,
                        makeTrailer = uiState.value.dataEditVehicleInfo.makeTrailer,
                        rnTrailer = uiState.value.dataEditVehicleInfo.rnTrailer,
                        route = uiState.value.dataEditRoute.route,
                        dateDeparture = uiState.value.dataEditRoute.dateDeparture,
                        dateReturn = uiState.value.dataEditRoute.dateReturn,
                        dateCrossingDeparture = uiState.value.dataEditRoute.dateCrossingDeparture,
                        dateCrossingReturn = uiState.value.dataEditRoute.dateCrossingReturn,
                        speedometerDeparture = uiState.value.dataEditRoute.speedometerDeparture,
                        speedometerReturn = uiState.value.dataEditRoute.speedometerReturn,
                        fuelled = uiState.value.dataEditRoute.fuelled,
                        progressReportsList = uiState.value.listDataEditProgressReports,
                        expensesTripList = uiState.value.listDataEditExpensesTrip
                    ).toByteArray()
                )
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun adShowScreen(context: Context) {
        InterstitialAd.load(
            context,
            "ca-app-pub-3940256099942544/1033173712",
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                }

                override fun onAdLoaded(p0: InterstitialAd) {
                    super.onAdLoaded(p0)
                    p0.show(activity)
                }
            }
        )
    }
}


