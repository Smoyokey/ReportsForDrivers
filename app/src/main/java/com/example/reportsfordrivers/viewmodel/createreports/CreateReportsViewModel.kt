package com.example.reportsfordrivers.viewmodel.createreports

import android.annotation.SuppressLint
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
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateReports
import com.example.reportsfordrivers.viewmodel.createreports.uistate.DataFillingTwo
import com.example.reportsfordrivers.viewmodel.createreports.uistate.DataPersonalInfo
import com.example.reportsfordrivers.viewmodel.createreports.uistate.DataReportInfo
import com.example.reportsfordrivers.viewmodel.createreports.uistate.DataVehicleInfo
import com.example.reportsfordrivers.viewmodel.createreports.uistate.ProgressDetails
import com.example.reportsfordrivers.viewmodel.createreports.uistate.ProgressReports
import com.example.reportsfordrivers.viewmodel.createreports.uistate.TripExpensesDetails
import com.example.reportsfordrivers.viewmodel.createreports.uistate.TripExpensesReports
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

private const val TAG = "CreateReportsViewModel"

@HiltViewModel
class CreateReportsViewModel @Inject constructor(
    private val fioPreferencesRepository: FioFirstEntryRepository
) : ViewModel() {

    var uiState = mutableStateOf(CreateReports())
        private set

    var uiStateProgressReports = mutableStateOf(ProgressReports())
        private set

    var uiStateTripExpenses = mutableStateOf(TripExpensesReports())
        private set

    var openDialogDataReportInfoDate = mutableStateOf(false)

    var openDialogDataFillingTwoDateDeparture = mutableStateOf(false)
    var openDialogDataFillingTwoDateReturn = mutableStateOf(false)
    var openDialogDataFillingTwoDateCrossingDeparture = mutableStateOf(false)
    var openDialogDataFillingTwoDateCrossingReturn = mutableStateOf(false)

    var openDialogProgressReportsDate = mutableStateOf(false)
    var openDialogProgressReportsDelete = mutableStateOf(false)

    var openDialogTripExpenseDate = mutableStateOf(false)
    var openDialogTripExpensesDelete = mutableStateOf(false)


    private fun updateDataReportInfo(dataReportInfo: DataReportInfo) {
        uiState.value = uiState.value.copy(dataReportInfo = dataReportInfo)
    }

    fun updateDataReportInfoDate(date: String) {
        val parseDate = if (date.isNotEmpty()) parseDateMain(date) else date
        updateDataReportInfo(uiState.value.dataReportInfo.copy(date = parseDate))
    }

    fun updateDataReportInfoMainCity(mainCity: String) {
        updateDataReportInfo(uiState.value.dataReportInfo.copy(mainCity = mainCity))
    }

    fun updateDataReportInfoWaybill(waybill: String) {
        updateDataReportInfo(uiState.value.dataReportInfo.copy(waybill = waybill))
    }

    fun isValidateDataReportInfo(): Boolean {
        return uiState.value.dataReportInfo.date != "" &&
                uiState.value.dataReportInfo.mainCity != "" &&
                uiState.value.dataReportInfo.waybill != ""
    }

    private fun updateDataPersonalInfo(dataPersonalInfo: DataPersonalInfo) {
        uiState.value = uiState.value.copy(dataPersonalInfo = dataPersonalInfo)
    }

    fun updateDataPersonalInfoLastName(lastName: String) {
        updateDataPersonalInfo(uiState.value.dataPersonalInfo.copy(lastName = lastName))
    }

    fun updateDataPersonalInfoFirstName(firstName: String) {
        updateDataPersonalInfo(uiState.value.dataPersonalInfo.copy(firstName = firstName))
    }

    fun updateDataPersonalInfoPatronymic(patronymic: String) {
        updateDataPersonalInfo(uiState.value.dataPersonalInfo.copy(patronymic = patronymic))
    }

    fun isValidateDataPersonalInfo(): Boolean {
        return uiState.value.dataPersonalInfo.lastName != "" &&
                uiState.value.dataPersonalInfo.firstName != "" &&
                uiState.value.dataPersonalInfo.patronymic != ""
    }

    private fun updateDataVehicleInfo(dataVehicleInfo: DataVehicleInfo) {
        uiState.value = uiState.value.copy(dataVehicleInfo = dataVehicleInfo)
    }

    fun updateDataVehicleInfoMakeVehicle(makeVehicle: String) {
        updateDataVehicleInfo(uiState.value.dataVehicleInfo.copy(makeVehicle = makeVehicle))
    }

    fun updateDataVehicleInfoRnVehicle(rnVehicle: String) {
        updateDataVehicleInfo(uiState.value.dataVehicleInfo.copy(rnVehicle = rnVehicle))
    }

    fun updateDataVehicleInfoMakeTrailer(makeTrailer: String) {
        updateDataVehicleInfo(uiState.value.dataVehicleInfo.copy(makeTrailer = makeTrailer))
    }

    fun updateDataVehicleInfoRnTrailer(rnTrailer: String) {
        updateDataVehicleInfo(uiState.value.dataVehicleInfo.copy(rnTrailer = rnTrailer))
    }

    fun isValidateDataVehicleInfo(): Boolean {
        return uiState.value.dataVehicleInfo.makeVehicle != "" &&
                uiState.value.dataVehicleInfo.rnVehicle != "" &&
                uiState.value.dataVehicleInfo.makeTrailer != "" &&
                uiState.value.dataVehicleInfo.rnTrailer != ""
    }

    private fun updateDataFillingTwo(itemDetails: DataFillingTwo) {
        uiState.value = uiState.value.copy(dataFillingTwo = itemDetails)
    }

    fun updateDataFillingTwoRoute(route: String) {
        updateDataFillingTwo(uiState.value.dataFillingTwo.copy(route = route))
    }

    fun updateDataFillingTwoDateReturn(dateReturn: String) {
        val parseDate = if (dateReturn.isNotEmpty())
            parseDateDayMonthYear(dateReturn)
        else
            dateReturn
        updateDataFillingTwo(uiState.value.dataFillingTwo.copy(dateReturn = parseDate))
    }

    fun updateDataFillingTwoDateDeparture(dateDeparture: String) {
        val parseDate = if (dateDeparture.isNotEmpty())
            parseDateDayMonthYear(dateDeparture)
        else
            dateDeparture
        updateDataFillingTwo(uiState.value.dataFillingTwo.copy(dateDeparture = parseDate))
    }

    fun updateDataFillingTwoDateCrossingDeparture(dateCrossingDeparture: String) {
        val parseDate = if (dateCrossingDeparture.isNotEmpty())
            parseDateDayMonthYear(dateCrossingDeparture)
        else
            dateCrossingDeparture
        updateDataFillingTwo(
            uiState.value.dataFillingTwo.copy
                (dateCrossingDeparture = parseDate)
        )
    }

    fun updateDataFillingTwoDateCrossingReturn(dateCrossingReturn: String) {
        val parseDate = if (dateCrossingReturn.isNotEmpty())
            parseDateDayMonthYear(dateCrossingReturn)
        else
            dateCrossingReturn
        updateDataFillingTwo(
            uiState.value.dataFillingTwo.copy
                (dateCrossingReturn = parseDate)
        )
    }

    fun updateDataFillingTwoSpeedometerDeparture(speedometerDeparture: String) {
        updateDataFillingTwo(
            uiState.value.dataFillingTwo.copy
                (speedometerDeparture = speedometerDeparture)
        )
    }

    fun updateDataFillingTwoSpeedometerReturn(speedometerReturn: String) {
        updateDataFillingTwo(
            uiState.value.dataFillingTwo.copy
                (speedometerReturn = speedometerReturn)
        )
    }

    fun updateDataFillingTwoFuelled(fuelled: String) {
        updateDataFillingTwo(uiState.value.dataFillingTwo.copy(fuelled = fuelled))
    }

    fun isNextDataFillingTwoValidate(): Boolean {
        return uiState.value.dataFillingTwo.route != "" &&
                uiState.value.dataFillingTwo.dateDeparture != "" &&
                uiState.value.dataFillingTwo.dateReturn != "" &&
                uiState.value.dataFillingTwo.dateCrossingDeparture != "" &&
                uiState.value.dataFillingTwo.dateCrossingDeparture != "" &&
                uiState.value.dataFillingTwo.speedometerDeparture != "" &&
                uiState.value.dataFillingTwo.speedometerReturn != "" &&
                uiState.value.dataFillingTwo.fuelled != ""
    }

    private fun updateProgressDetails(progressDetails: ProgressDetails) {
        uiStateProgressReports.value = uiStateProgressReports.value
            .copy(progressDetails = progressDetails)
    }

    fun updateProgressReportsCountry(country: String) {
        updateProgressDetails(uiStateProgressReports.value.progressDetails.copy(country = country))
    }

    fun updateProgressReportsTownship(township: String) {
        updateProgressDetails(
            uiStateProgressReports.value.progressDetails
                .copy(township = township)
        )
    }

    fun updateProgressReportsDistance(distance: String) {
        updateProgressDetails(
            uiStateProgressReports.value.progressDetails
                .copy(distance = distance)
        )
    }

    fun updateProgressReportsCargoWeight(cargoWeight: String) {
        updateProgressDetails(
            uiStateProgressReports.value.progressDetails
                .copy(cargoWeight = cargoWeight)
        )
    }

    fun updateProgressReportsDate(date: String) {
        val parseDate = if (date.isNotEmpty()) parseDateDayMonth(date) else date
        updateProgressDetails(uiStateProgressReports.value.progressDetails.copy(date = parseDate))
    }

    fun updateProgressReports() {
        uiState.value.listProgress.add(uiStateProgressReports.value)
        uiStateProgressReports.value = ProgressReports()
    }

    fun validateAddProgressReports(): Boolean {
        return uiStateProgressReports.value.progressDetails.date != "" &&
                uiStateProgressReports.value.progressDetails.country != "" &&
                uiStateProgressReports.value.progressDetails.township != "" &&
                uiStateProgressReports.value.progressDetails.distance != "" &&
                uiStateProgressReports.value.progressDetails.cargoWeight != ""
    }

    fun isValidateNextProgressReports(): Boolean {
        return uiState.value.listProgress.size > 0
    }

    fun deletePositionProgressReports(position: Int) {
        uiState.value.listProgress.removeAt(position)
    }

    private fun updateTripExpensesDetails(tripExpensesDetails: TripExpensesDetails) {
        uiStateTripExpenses.value = uiStateTripExpenses.value
            .copy(tripExpensesDetails = tripExpensesDetails)
    }

    fun updateTripExpensesDate(date: String) {
        val parseDate = if(date.isNotEmpty()) parseDateDayMonth(date) else date
        updateTripExpensesDetails(uiStateTripExpenses.value.tripExpensesDetails.copy(date = parseDate))
    }

    fun updateTripExpensesDocumentNumber(documentNumber: String) {
        updateTripExpensesDetails(
            uiStateTripExpenses.value.tripExpensesDetails
                .copy(documentNumber = documentNumber)
        )
    }

    fun updateTripExpensesExpenseItem(expenseItem: String) {
        updateTripExpensesDetails(
            uiStateTripExpenses.value.tripExpensesDetails
                .copy(expenseItem = expenseItem)
        )
    }

    fun updateTripExpensesSum(sum: String) {
        updateTripExpensesDetails(uiStateTripExpenses.value.tripExpensesDetails.copy(sum = sum))
    }

    fun updateTripExpensesCurrency(currency: String) {
        updateTripExpensesDetails(uiStateTripExpenses.value.tripExpensesDetails.copy(currency = currency))
    }

    fun updateTripExpense() {
        uiState.value.listTripExpenses.add(uiStateTripExpenses.value)
        uiStateTripExpenses.value = TripExpensesReports()
    }

    fun validateAddTripExpenses(): Boolean {
        return uiStateTripExpenses.value.tripExpensesDetails.date != "" &&
                uiStateTripExpenses.value.tripExpensesDetails.documentNumber != "" &&
                uiStateTripExpenses.value.tripExpensesDetails.expenseItem != "" &&
                uiStateTripExpenses.value.tripExpensesDetails.sum != "" &&
                uiStateTripExpenses.value.tripExpensesDetails.currency != ""
    }

    fun deletePositionTripExpense(position: Int) {
        uiState.value.listTripExpenses.removeAt(position)
    }

//    fun isValidateNextTripExpenses(): Boolean {
//        return uiState.value.listTripExpenses.size > 0
//    }

    fun updatePreviewReportName(reportName: String) {
        uiState.value = uiState.value.copy(reportName = reportName)
    }

    @SuppressLint("SimpleDateFormat")
    private fun parseDateMain(date: String): String {
        val format = SimpleDateFormat("\"dd\" MM yyyy")
        return format.format(Date(date.toLong()))
    }

    @SuppressLint("SimpleDateFormat")
    private fun parseDateDayMonthYear(date: String): String {
        val format = SimpleDateFormat("dd.MM.yyyy")
        return format.format(Date(date.toLong()))
    }

    @SuppressLint("SimpleDateFormat")
    private fun parseDateDayMonth(date: String): String {
        val format = SimpleDateFormat("dd.MM")
        return format.format(Date(date.toLong()))
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
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, "${uiState.value.reportName}.docx")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }
            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

            if(uri != null) {
                val fileOutputStream = resolver.openOutputStream(uri)
                fileOutputStream?.write(onePageHtml().toByteArray())
                fileOutputStream?.close()
            }
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
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
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
        <td class="report-date" style="width: 50%">${uiState.value.dataReportInfo.date} г.</td>
        <td class="report-city">г. ${uiState.value.dataReportInfo.mainCity}</td>
    </tr>
</table>

<table class="general-info">
    <tr>
        <td style="width: 20%">ФИО</td>
        <td>${uiState.value.dataPersonalInfo.lastName} ${uiState.value.dataPersonalInfo.firstName} ${uiState.value.dataPersonalInfo.patronymic}</td>
    </tr>
    <tr>
        <td>Путевой лист №</td>
        <td>${uiState.value.dataReportInfo.waybill}</td>
    </tr>
    <tr>
        <td>авто/м марка, г/н</td>
        <td>${uiState.value.dataVehicleInfo.makeVehicle}, ${uiState.value.dataVehicleInfo.rnVehicle}</td>
    </tr>
    <tr>
        <td>прицеп марка, г/н</td>
        <td>${uiState.value.dataVehicleInfo.makeTrailer}, ${uiState.value.dataVehicleInfo.rnTrailer}</td>
    </tr>
</table>

<table class="general-info">
    <tr>
        <td style="width: 40%">Маршрут</td>
        <td>${uiState.value.dataFillingTwo.route}</td>
    </tr>
    <tr>
        <td>Дата выезда</td>
        <td>${uiState.value.dataFillingTwo.dateDeparture}</td>
    </tr>
    <tr>
        <td>Дата возврата</td>
        <td>${uiState.value.dataFillingTwo.dateReturn}</td>
    </tr>
    <tr>
        <td>Дата пересечения границы при отъезде</td>
        <td>${uiState.value.dataFillingTwo.dateCrossingDeparture}</td>
    </tr>
    <tr>
        <td>Дата пересечения при возврате</td>
        <td>${uiState.value.dataFillingTwo.dateCrossingReturn}</td>
    </tr>
    <tr>
        <td>Показания спидометра при выезде</td>
        <td>${uiState.value.dataFillingTwo.speedometerDeparture}</td>
    </tr>
    <tr>
        <td>Показания спидометра при возврате</td>
        <td>${uiState.value.dataFillingTwo.speedometerReturn}</td>
    </tr>
    <tr>
        <td>Заправлено, л</td>
        <td>${uiState.value.dataFillingTwo.fuelled}</td>
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
${if (uiState.value.listTripExpenses.size > 0) expenseTrip() else ""}
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
        for (i in uiState.value.listProgress) {
            a += """<tr>
        <td>${i.progressDetails.date}</td>
        <td>${i.progressDetails.country}</td>
        <td>${i.progressDetails.township}</td>
        <td>${i.progressDetails.distance}</td>
        <td>${i.progressDetails.cargoWeight}</td>
    </tr>"""
        }
        return a
    }

    private fun tableTripExpense(): String {
        var b = ""
        for (i in uiState.value.listTripExpenses) {
            b += """<tr>
        <td>${i.tripExpensesDetails.date}</td>
        <td>${i.tripExpensesDetails.documentNumber}</td>
        <td>${i.tripExpensesDetails.expenseItem}</td>
        <td>${i.tripExpensesDetails.sum}</td>
        <td>${i.tripExpensesDetails.currency}</td>
    </tr>"""
        }
        return b
    }

}