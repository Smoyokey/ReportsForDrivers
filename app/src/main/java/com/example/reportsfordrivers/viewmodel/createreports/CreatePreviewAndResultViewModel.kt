package com.example.reportsfordrivers.viewmodel.createreports

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
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
import com.example.reportsfordrivers.data.dao.createreport.CreateExpensesTripDao
import com.example.reportsfordrivers.data.dao.createreport.CreatePersonalInfoDao
import com.example.reportsfordrivers.data.dao.createreport.CreateProgressReportsDao
import com.example.reportsfordrivers.data.dao.createreport.CreateReportInfoDao
import com.example.reportsfordrivers.data.dao.createreport.CreateRouteDao
import com.example.reportsfordrivers.data.dao.createreport.CreateVehicleTrailerDao
import com.example.reportsfordrivers.data.structure.MainInfo
import com.example.reportsfordrivers.data.structure.PersonalInfo
import com.example.reportsfordrivers.data.structure.ProgressReport
import com.example.reportsfordrivers.data.structure.ReportName
import com.example.reportsfordrivers.data.structure.Route
import com.example.reportsfordrivers.data.structure.Trailer
import com.example.reportsfordrivers.data.structure.TripExpenses
import com.example.reportsfordrivers.data.structure.Vehicle
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateExpensesTripDetailingUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreatePersonalInfoUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateProgressReportsDetailingUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateReportInfoUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateReports
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateReportsRouteUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateVehicleTrailerUiState
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

private const val TAG = "CreateReportsViewModel"

@HiltViewModel
class CreatePreviewAndResultViewModel @Inject constructor(
    private val fioPreferencesRepository: FioFirstEntryRepository
) : ViewModel() {

    @Inject
    lateinit var createReportInfoDb: CreateReportInfoDao

    @Inject
    lateinit var createPersonalInfoDb: CreatePersonalInfoDao

    @Inject
    lateinit var createVehicleTrailerDb: CreateVehicleTrailerDao

    @Inject
    lateinit var createRouteDb: CreateRouteDao

    @Inject
    lateinit var createProgressReportsDb: CreateProgressReportsDao

    @Inject
    lateinit var createExpensesTripDb: CreateExpensesTripDao


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

    var uiState = mutableStateOf(CreateReports())
        private set

    var firstOpenReportCreatePreviewAndResult = mutableStateOf(false)

    var isOpenPermissionSaveFile = mutableStateOf(false)

    fun startLoadScreen() = runBlocking {
        loadCreateReportInfo()
        loadCreatePersonalInfo()
        loadCreateVehicleTrailer()
        loadCreateRoute()
        loadCreateProgressReports()
        loadCreateExpensesTrip()
        fioPreferencesRepository.setCreateSelectedPage(7)
        firstOpenReportCreatePreviewAndResult.value = true
    }

    private fun loadCreateReportInfo() =
        runBlocking {
            val createReportInfo = createReportInfoDb.getAllItem().first()
            val createReportInfoUiState = CreateReportInfoUiState(
                date = createReportInfo[0].date,
                waybill = createReportInfo[0].waybill,
                mainCity = createReportInfo[0].mainCity,
            )
            uiState.value = uiState.value.copy(dataCreateReportInfo = createReportInfoUiState)
        }


    private fun loadCreatePersonalInfo() = runBlocking {
        val createPersonalInfo = createPersonalInfoDb.getAllItem().first()
        val createPersonalInfoUiState = CreatePersonalInfoUiState(
            lastName = createPersonalInfo[0].lastName,
            firstName = createPersonalInfo[0].firstName,
            patronymic = createPersonalInfo[0].patronymic
        )
        uiState.value = uiState.value.copy(dataCreatePersonalInfo = createPersonalInfoUiState)
    }

    private fun loadCreateVehicleTrailer() = runBlocking {
        val createVehicleTrailer = createVehicleTrailerDb.getAllItem().first()
        val createVehicleTrailerUiState = CreateVehicleTrailerUiState(
            makeVehicle = createVehicleTrailer[0].makeVehicle,
            rnVehicle = createVehicleTrailer[0].rnVehicle,
            makeTrailer = createVehicleTrailer[0].makeTrailer,
            rnTrailer = createVehicleTrailer[0].rnTrailer
        )
        uiState.value = uiState.value.copy(dataCreateVehicleInfo = createVehicleTrailerUiState)
    }

    private fun loadCreateRoute() = runBlocking {
        val createRoute = createRouteDb.getAllItem().first()
        val createReportsRouteUiState = CreateReportsRouteUiState(
            route = createRoute[0].route,
            dateDeparture = createRoute[0].dateDeparture,
            dateReturn = createRoute[0].dateReturn,
            dateCrossingDeparture = createRoute[0].dateBorderCrossingDeparture,
            dateCrossingReturn = createRoute[0].dateBorderCrossingReturn,
            speedometerDeparture = createRoute[0].speedometerReadingDeparture,
            speedometerReturn = createRoute[0].speedometerReadingReturn,
            fuelled = createRoute[0].fuelled
        )
        uiState.value = uiState.value.copy(dataCreateRoute = createReportsRouteUiState)
    }


    private fun loadCreateProgressReports() = runBlocking {
        val createProgressReports = createProgressReportsDb.getAllItem().first()
        for (i in createProgressReports) {
            if (i.isAdd == 1) {
                uiState.value.listDataCreateProgressReports.add(
                    CreateProgressReportsDetailingUiState(
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

    private fun loadCreateExpensesTrip() = runBlocking {
        val createExpensesTrip = createExpensesTripDb.getAllItem().first()
        for (i in createExpensesTrip) {
            if (i.isAdd == 1) {
                uiState.value.listDataCreateExpensesTrip.add(
                    CreateExpensesTripDetailingUiState(
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


    fun updatePreviewReportName(reportName: String) {
        uiState.value = uiState.value.copy(reportName = reportName)
    }

    fun saveDataInBd() {
        savePersonalInfo()
        saveVehicle()
        saveTrailer()
        saveRoute()
        saveReportName()
        saveProgressReports()
        saveExpensesTrip()
        saveMainInfo()
    }

    private fun savePersonalInfo() = runBlocking {
        personalInfoDb.insert(
            PersonalInfo(
                lastName = uiState.value.dataCreatePersonalInfo.lastName,
                firstName = uiState.value.dataCreatePersonalInfo.firstName,
                patronymic = uiState.value.dataCreatePersonalInfo.patronymic
            )
        )
    }

    private fun saveVehicle() = runBlocking {
        vehicleDb.insert(
            Vehicle(
                makeVehicle = uiState.value.dataCreateVehicleInfo.makeVehicle,
                registrationNumberVehicle = uiState.value.dataCreateVehicleInfo.rnVehicle
            )
        )
    }

    private fun saveTrailer() = runBlocking {
        trailerDb.insert(
            Trailer(
                makeTrailer = uiState.value.dataCreateVehicleInfo.makeTrailer,
                registrationNumberTrailer = uiState.value.dataCreateVehicleInfo.rnTrailer
            )
        )
    }

    private fun saveRoute() = runBlocking {
        routeDb.insert(
            Route(
                route = uiState.value.dataCreateRoute.route,
                dateDeparture = uiState.value.dataCreateRoute.dateDeparture,
                dateReturn = uiState.value.dataCreateRoute.dateReturn,
                dateBorderCrossingDeparture = uiState.value.dataCreateRoute.dateCrossingDeparture,
                dateBorderCrossingReturn = uiState.value.dataCreateRoute.dateCrossingReturn,
                speedometerReadingDeparture = uiState.value.dataCreateRoute.speedometerDeparture,
                speedometerReadingReturn = uiState.value.dataCreateRoute.speedometerReturn,
                fuelled = uiState.value.dataCreateRoute.fuelled
            )
        )
    }

    private fun saveProgressReports() = runBlocking {
        for (i in uiState.value.listDataCreateProgressReports) {
            progressReportsDb.insert(
                ProgressReport(
                    date = i.date,
                    country = i.country,
                    townshipOne = i.townshipOne,
                    townshipTwo = i.townshipTwo,
                    distance = i.distance,
                    weight = i.cargoWeight,
                    reportNameId = reportNameDb.getLastId()
                )
            )
        }
    }

    private fun saveExpensesTrip() = runBlocking {
        if (uiState.value.listDataCreateExpensesTrip.isEmpty()) return@runBlocking
        for (i in uiState.value.listDataCreateExpensesTrip) {
            expensesTripDb.insert(
                TripExpenses(
                    date = i.date,
                    documentNumber = i.documentNumber,
                    expenseItem = i.expenseItem,
                    sum = i.sum,
                    currency = i.currency,
                    reportNameId = reportNameDb.getLastId()
                )
            )
        }
    }

    private fun saveReportName() = runBlocking {
        reportNameDb.insert(
            ReportName(
                waybill = uiState.value.dataCreateReportInfo.waybill,
                mainCity = uiState.value.dataCreateReportInfo.mainCity,
                date = uiState.value.dataCreateReportInfo.date,
                personalInfoId = personalInfoDb.getLastId(),
                vehicleId = vehicleDb.getLastId(),
                trailerId = trailerDb.getLastId(),
                routeId = routeDb.getLastId()
            )
        )
    }

    private fun saveMainInfo() = runBlocking {
        mainInfoDb.insert(
            MainInfo(
                nameReport = uiState.value.reportName,
                dateCreate = uiState.value.dataCreateReportInfo.date,
                routeMainInfo = uiState.value.dataCreateRoute.route,
                reportNameId = reportNameDb.getLastId()
            )
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

            if (uri != null) {
                val fileOutputStream = resolver.openOutputStream(uri)
//                fileOutputStream?.write(onePageHtml().toByteArray())
                fileOutputStream?.write(
                    HtmlText.onePageHtml(
                        dataCreateReportInfoDate = uiState.value.dataCreateReportInfo.date,
                        dataCreateReportInfoMainCity = uiState.value.dataCreateReportInfo.mainCity,
                        lastName = uiState.value.dataCreatePersonalInfo.lastName,
                        firstName = uiState.value.dataCreatePersonalInfo.firstName,
                        patronymic = uiState.value.dataCreatePersonalInfo.patronymic,
                        waybill = uiState.value.dataCreateReportInfo.waybill,
                        makeVehicle = uiState.value.dataCreateVehicleInfo.makeVehicle,
                        rnVehicle = uiState.value.dataCreateVehicleInfo.rnVehicle,
                        makeTrailer = uiState.value.dataCreateVehicleInfo.makeTrailer,
                        rnTrailer = uiState.value.dataCreateVehicleInfo.rnTrailer,
                        route = uiState.value.dataCreateRoute.route,
                        dateDeparture = uiState.value.dataCreateRoute.dateDeparture,
                        dateReturn = uiState.value.dataCreateRoute.dateReturn,
                        dateCrossingDeparture = uiState.value.dataCreateRoute.dateCrossingDeparture,
                        dateCrossingReturn = uiState.value.dataCreateRoute.dateCrossingReturn,
                        speedometerDeparture = uiState.value.dataCreateRoute.speedometerDeparture,
                        speedometerReturn = uiState.value.dataCreateRoute.speedometerReturn,
                        fuelled = uiState.value.dataCreateRoute.fuelled,
                        progressReportsList = uiState.value.listDataCreateProgressReports,
                        expensesTripList = uiState.value.listDataCreateExpensesTrip
                    ).toByteArray()
                )
                fileOutputStream?.close()
            }
            Log.i(TAG, "Save report")
        } else {
            try {
                //Создается объект файла, при этом путь к файлу находиться методом Environment
                val myFile = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "${uiState.value.reportName}.docx"
                )
//            val myFile = File(context.filesDir, fileName)
                // Создается файл, если он не был создан
                myFile.delete()
                myFile.createNewFile()
                // После чего создаем поток для записи
                val outputStream = FileOutputStream(myFile)
                // Производим непосредственно запись
                outputStream.write(
                    HtmlText.onePageHtml(
                        dataCreateReportInfoDate = uiState.value.dataCreateReportInfo.date,
                        dataCreateReportInfoMainCity = uiState.value.dataCreateReportInfo.mainCity,
                        lastName = uiState.value.dataCreatePersonalInfo.lastName,
                        firstName = uiState.value.dataCreatePersonalInfo.firstName,
                        patronymic = uiState.value.dataCreatePersonalInfo.patronymic,
                        waybill = uiState.value.dataCreateReportInfo.waybill,
                        makeVehicle = uiState.value.dataCreateVehicleInfo.makeVehicle,
                        rnVehicle = uiState.value.dataCreateVehicleInfo.rnVehicle,
                        makeTrailer = uiState.value.dataCreateVehicleInfo.makeTrailer,
                        rnTrailer = uiState.value.dataCreateVehicleInfo.rnTrailer,
                        route = uiState.value.dataCreateRoute.route,
                        dateDeparture = uiState.value.dataCreateRoute.dateDeparture,
                        dateReturn = uiState.value.dataCreateRoute.dateReturn,
                        dateCrossingDeparture = uiState.value.dataCreateRoute.dateCrossingDeparture,
                        dateCrossingReturn = uiState.value.dataCreateRoute.dateCrossingReturn,
                        speedometerDeparture = uiState.value.dataCreateRoute.speedometerDeparture,
                        speedometerReturn = uiState.value.dataCreateRoute.speedometerReturn,
                        fuelled = uiState.value.dataCreateRoute.fuelled,
                        progressReportsList = uiState.value.listDataCreateProgressReports,
                        expensesTripList = uiState.value.listDataCreateExpensesTrip
                    ).toByteArray()
                )
                // Закрываем поток
                outputStream.close()
                // Просто для удобства визуального контроля исполнения метода в приложении
                Log.i(TAG, "Поток закрыт")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
//        adShowScreen(context)
    }

    fun adShowScreen(context: Context) {
        Log.i("TAG", "adShowScreen")
        InterstitialAd.load(
            context,
            "ca-app-pub-3940256099942544/1033173712",
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    Log.i("TAG", "Error")
                }

                override fun onAdLoaded(p0: InterstitialAd) {
                    super.onAdLoaded(p0)
                    p0.show(activity)
                    Log.i("TAG", "Complete")
                }
            }
        )
    }
}