package com.example.reportsfordrivers.viewmodel.createreports

import android.annotation.SuppressLint
import android.app.Activity
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
import com.example.reportsfordrivers.data.dao.MainInfoDao
import com.example.reportsfordrivers.data.dao.PersonalInfoDao
import com.example.reportsfordrivers.data.dao.ProgressReportDao
import com.example.reportsfordrivers.data.dao.ReportNameDao
import com.example.reportsfordrivers.data.dao.RouteDao
import com.example.reportsfordrivers.data.dao.TrailerDao
import com.example.reportsfordrivers.data.dao.TripExpensesDao
import com.example.reportsfordrivers.data.dao.VehicleDao
import com.example.reportsfordrivers.data.dao.createReport.CreateExpensesTripDao
import com.example.reportsfordrivers.data.dao.createReport.CreatePersonalInfoDao
import com.example.reportsfordrivers.data.dao.createReport.CreateProgressReportsDao
import com.example.reportsfordrivers.data.dao.createReport.CreateReportInfoDao
import com.example.reportsfordrivers.data.dao.createReport.CreateRouteDao
import com.example.reportsfordrivers.data.dao.createReport.CreateVehicleTrailerDao
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
import java.io.FileWriter
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
        for(i in uiState.value.listDataCreateProgressReports) {
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
        if(uiState.value.listDataCreateExpensesTrip.isEmpty()) return@runBlocking
        for(i in uiState.value.listDataCreateExpensesTrip) {
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

    fun testShare(context: Context) {
        downloadFile(context)
    }

    private fun downloadFile(context: Context) {
        val filePath = File(context.filesDir, "docs")
        filePath.mkdir()
        val newFile = File(filePath, "${uiState.value.reportName}.docx")
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
            fileWriter.append(onePageHtml())
        } catch (e: Exception) {
            Log.e(TAG, e.printStackTrace().toString())
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
                fileOutputStream?.write(onePageHtml().toByteArray())
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
                outputStream.write(onePageHtml().toByteArray())
                // Закрываем поток
                outputStream.close()
                // Просто для удобства визуального контроля исполнения метода в приложении
                Log.i(TAG, "Поток закрыт")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
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

    private fun onePageHtml(): String {
        return """<head>
    <meta charset="UTF-8">
    <style>
        body, h1 {
            margin: 0;
        }

        body {
            padding: 7% 5% 7% 15%;
        }

        h1 {
            font-size: 18px;
            font-weight:normal;
            text-align: center;
            margin-bottom: 14px;
        }

        .report-header {
            width: 100%;
            margin-bottom: 12px;
        }

        .report-city {
            text-align: right;
        }

        .general-info {
            font-size: 14px;
            margin-bottom: 14px;
            width: 100%;
        }

        .movement-stages {
            font-size: 14px;
            width: 100%;
        }

        .movement-stages caption {
            text-align: left;
        }

        .movement-stages, .movement-stages td {
            border: 1px solid #000000;
            border-collapse: collapse;
        }
    </style>
</head>

<body>
<h1>Отчет о командировке</h1>
<table class="report-header">
    <tr>
        <td class="report-date" style="width: 50%">${uiState.value.dataCreateReportInfo.date} г.</td>
        <td class="report-city">г. ${uiState.value.dataCreateReportInfo.mainCity}</td>
    </tr>
</table>

<table class="general-info">
    <tr>
        <td style="width: 20%">ФИО</td>
        <td>
            ${uiState.value.dataCreatePersonalInfo.lastName}
            ${uiState.value.dataCreatePersonalInfo.firstName}
            ${uiState.value.dataCreatePersonalInfo.patronymic}
        </td>
    </tr>
    <tr>
        <td>Путевой лист №</td>
        <td>${uiState.value.dataCreateReportInfo.waybill}</td>
    </tr>
    <tr>
        <td>авто/м марка, г/н</td>
        <td>
            ${uiState.value.dataCreateVehicleInfo.makeVehicle},
            ${uiState.value.dataCreateVehicleInfo.rnVehicle}
        </td>
    </tr>
    <tr>
        <td>прицеп марка, г/н</td>
        <td>
            ${uiState.value.dataCreateVehicleInfo.makeTrailer},
            ${uiState.value.dataCreateVehicleInfo.rnTrailer}
        </td>
    </tr>
</table>

<table class="general-info">
    <tr>
        <td style="width: 40%">Маршрут</td>
        <td>${uiState.value.dataCreateRoute.route}</td>
    </tr>
    <tr>
        <td>Дата выезда</td>
        <td>${uiState.value.dataCreateRoute.dateDeparture}</td>
    </tr>
    <tr>
        <td>Дата возврата</td>
        <td>${uiState.value.dataCreateRoute.dateReturn}</td>
    </tr>
    <tr>
        <td>Дата пересечения границы при отъезде</td>
        <td>${uiState.value.dataCreateRoute.dateCrossingDeparture}</td>
    </tr>
    <tr>
        <td>Дата пересечения при возврате</td>
        <td>${uiState.value.dataCreateRoute.dateCrossingReturn}</td>
    </tr>
    <tr>
        <td>Показания спидометра при выезде</td>
        <td>${uiState.value.dataCreateRoute.speedometerDeparture}</td>
    </tr>
    <tr>
        <td>Показания спидометра при возврате</td>
        <td>${uiState.value.dataCreateRoute.speedometerReturn}</td>
    </tr>
    <tr>
        <td>Заправлено, л</td>
        <td>${uiState.value.dataCreateRoute.fuelled}</td>
    </tr>
</table>

<table class="movement-stages">
    <caption>Отчет об этапах движения по п/л</caption>
    <tr>
        <td style="width: 15%">Дата</td>
        <td style="width: 25%">Страна</td>
        <td style="width: 30%">Населенные пункты</td>
        <td style="width: 15%">Расстояние, км</td>
        <td style="width: 15%">Вес груза, т</td>
    </tr>
    ${tableProgressReport()}
</table>
</body>
<br>
<br>
${if (uiState.value.listDataCreateExpensesTrip.size > 0) expenseTrip() else ""}
"""
    }

    private fun expenseTrip(): String {
        return """<head>
    <meta charset="UTF-8">r
    <style>
        * {
            font-size: 14px;
        }

        .expenses {
            border: 1px solid black;
            border-collapse: collapse;
            margin-bottom: 42px;
        }

        .expenses td {
            border: 1px solid black;
            border-collapse: collapse;
        }

        table {
            width: 100%;
            table-layout: fixed;
        }

        tr {
            height: 18px;
        }

        caption {
            text-align: left;
            margin-bottom: 14px;
        }

        .space-out {
            width 20%
        }

        .text {
            width: 15%;
        }

        .space-in {
            width: 5%;
        }

        .line {
            border-bottom: 1px solid black;
            width: 20%;
        }
    </style>
</head>
<table class="expenses">
    <caption>Расходы по командировке</caption>
    <tr>
        <td>Дата</td>
        <td>№ документа</td>
        <td>Статья расходов</td>
        <td>Сумма</td>
        <td>Валюта</td>
    </tr>
    ${tableTripExpense()}
</table>
<table class="signature">
    <tr>
        <td class="space-out"></td>
        <td class="text">Подпись подотчётного лица</td>
        <td class="line"></td>
        <td class="space-in"></td>
        <td class="line"></td>
        <td class="space-out"></td>
    </tr>
</table>"""
    }

    private fun tableProgressReport(): String {
        var a = ""
        for (i in uiState.value.listDataCreateProgressReports) {
            a += """<tr>
        <td>${i.date}</td>
        <td>${i.country}</td>
        <td>${i.townshipOne} - ${i.townshipTwo}</td>
        <td>${i.distance}</td>
        <td>${i.cargoWeight}</td>
    </tr>"""
        }
        return a
    }

    private fun tableTripExpense(): String {
        var b = ""
        for (i in uiState.value.listDataCreateExpensesTrip) {
            b += """<tr>
        <td>${i.date}</td>
        <td>${i.documentNumber}</td>
        <td>${i.expenseItem}</td>
        <td>${i.sum}</td>
        <td>${i.currency}</td>
    </tr>"""
        }
        return b
    }

}