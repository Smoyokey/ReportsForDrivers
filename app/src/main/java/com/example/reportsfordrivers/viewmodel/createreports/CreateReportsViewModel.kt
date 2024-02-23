package com.example.reportsfordrivers.viewmodel.createreports

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Icon
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toAdaptiveIcon
import androidx.core.graphics.drawable.toIcon
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateReports
import com.example.reportsfordrivers.viewmodel.createreports.uistate.DataFillingOne
import com.example.reportsfordrivers.viewmodel.createreports.uistate.DataFillingTwo
import com.example.reportsfordrivers.viewmodel.createreports.uistate.ProgressDetails
import com.example.reportsfordrivers.viewmodel.createreports.uistate.ProgressReports
import com.example.reportsfordrivers.viewmodel.createreports.uistate.TripExpensesDetails
import com.example.reportsfordrivers.viewmodel.createreports.uistate.TripExpensesReports
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

private const val TAG = "DataFillingOneViewModel"

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

    var openDialogDateFillingOne = mutableStateOf(false)

    var openDialogDataFillingTwoDateDeparture = mutableStateOf(false)
    var openDialogDataFillingTwoDateReturn = mutableStateOf(false)
    var openDialogDataFillingTwoDateCrossingDeparture = mutableStateOf(false)
    var openDialogDataFillingTwoDateCrossingReturn = mutableStateOf(false)

    var openDialogProgressReportsDate = mutableStateOf(false)

    var openDialogTripExpenseDate = mutableStateOf(false)

    var tabIndex = mutableStateOf(0)
    val tabs = listOf("1", "2", "3", "4", "5", "6")

//    fun pdfRender(file: File): Icon {
//        val fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
//
//        val pdfRender = PdfRenderer(fileDescriptor)
//
//        val pageCount = pdfRender.pageCount
//
//        val page = pdfRender.openPage(0)
//        val rendererPageWidth = page.width
//        val rendererPageHeight = page.height
//
//        val bitmap = Bitmap.createBitmap(
//            rendererPageWidth,
//            rendererPageHeight,
//            Bitmap.Config.ARGB_8888
//        )
//        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
//        pdfRender.close()
//        fileDescriptor.close()
//        page.close()
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            bitmap.toAdaptiveIcon()
//        } else {
//            TODO("VERSION.SDK_INT < O")
//        }
//    }

    private fun updateDataFillingOne(itemDetails: DataFillingOne) {
        uiState.value = uiState.value.copy(dataFillingOne = itemDetails)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateDataFillingOneDate(date: String) {
        val localDate = parseDateMain(date)
        updateDataFillingOne(uiState.value.dataFillingOne.copy(date = localDate))
    }

    fun updateDataFillingOneLastName(lastName: String) {
        updateDataFillingOne(uiState.value.dataFillingOne.copy(lastName = lastName))
    }

    fun updateDataFillingOneFirstName(firstName: String) {
        updateDataFillingOne(uiState.value.dataFillingOne.copy(firstName = firstName))
    }

    fun updateDataFillingOnePatronymic(patronymic: String) {
        updateDataFillingOne(uiState.value.dataFillingOne.copy(patronymic = patronymic))
    }

    fun updateDataFillingOneWaybill(waybill: String) {
        updateDataFillingOne(uiState.value.dataFillingOne.copy(waybill = waybill))
    }

    fun updateDataFillingOneMakeVehicle(makeVehicle: String) {
        updateDataFillingOne(uiState.value.dataFillingOne.copy(makeVehicle = makeVehicle))
    }

    fun updateDataFillingOneRnVehicle(rnVehicle: String) {
        updateDataFillingOne(uiState.value.dataFillingOne.copy(rnVehicle = rnVehicle))
    }

    fun updateDataFillingOneMakeTrailer(makeTrailer: String) {
        updateDataFillingOne(uiState.value.dataFillingOne.copy(makeTrailer = makeTrailer))
    }

    fun updateDataFillingOneRnTrailer(rnTrailer: String) {
        updateDataFillingOne(uiState.value.dataFillingOne.copy(rnTrailer = rnTrailer))
    }

    fun isNextDataFillingOneValidate(): Boolean {
        return uiState.value.dataFillingOne.date != "" &&
                uiState.value.dataFillingOne.lastName != "" &&
                uiState.value.dataFillingOne.firstName != "" &&
                uiState.value.dataFillingOne.patronymic != "" &&
                uiState.value.dataFillingOne.makeVehicle != "" &&
                uiState.value.dataFillingOne.rnVehicle != ""
    }

    private fun updateDataFillingTwo(itemDetails: DataFillingTwo) {
        uiState.value = uiState.value.copy(dataFillingTwo = itemDetails)
    }

    fun updateDataFillingTwoRoute(route: String) {
        updateDataFillingTwo(uiState.value.dataFillingTwo.copy(route = route))
    }

    fun updateDataFillingTwoDateReturn(dateReturn: String) {
        val parseDate = parseDateDayMonthYear(dateReturn)
        updateDataFillingTwo(uiState.value.dataFillingTwo.copy(dateReturn = parseDate))
    }

    fun updateDataFillingTwoDateDeparture(dateDeparture: String) {
        val parseDate = parseDateDayMonthYear(dateDeparture)
        updateDataFillingTwo(uiState.value.dataFillingTwo.copy(dateDeparture = parseDate))
    }

    fun updateDataFillingTwoDateCrossingDeparture(dateCrossingDeparture: String) {
        val parseDate = parseDateDayMonthYear(dateCrossingDeparture)
        updateDataFillingTwo(
            uiState.value.dataFillingTwo.copy
                (dateCrossingDeparture = parseDate)
        )
    }

    fun updateDataFillingTwoDateCrossingReturn(dateCrossingReturn: String) {
        val parseDate = parseDateDayMonthYear(dateCrossingReturn)
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
        val parseDate = parseDateDayMonth(date)
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
    private fun updateTripExpensesDetails(tripExpensesDetails: TripExpensesDetails) {
        uiStateTripExpenses.value = uiStateTripExpenses.value
            .copy(tripExpensesDetails = tripExpensesDetails)
    }

    fun updateTripExpensesDate(date: String) {
        val parseDate = parseDateDayMonth(date)
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

    fun isValidateNextTripExpenses(): Boolean {
        return uiState.value.listTripExpenses.size > 0
    }

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

    fun saveFile() {
        Log.i(TAG, uiState.value.dataFillingOne.lastName)
        writeFile("download/", "Print.doc")
    }

    fun testShare(context: Context) {
//        createFile(context)
        downloadFile(context)
    }

    private fun downloadFile(context: Context) {
        val filePath: File = File(context.filesDir, "docs")
        filePath.mkdir()
        val newFile = File(filePath, "MyNewFile.doc")
        newFile.delete()
        newFile.createNewFile()
        val contentUri = FileProvider.getUriForFile(
            context,
            "com.example.ActiveReports.fileprovider",
            newFile
        )
        var fileWriter: FileWriter? = null
        try {
            fileWriter = FileWriter(newFile)
            fileWriter.append(onePageHtml())
//            fileWriter.append(twoPageHtml())
        } catch (e: Exception) {
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

    private fun writeFile(filePath: String, fileName: String) {
        try {
            //Создается объект файла, при этом путь к файлу находиться методом Environment
            val myFile = File(
                Environment.getExternalStorageDirectory().toString() + "/" + filePath + fileName
            )
            // Создается файл, если он не был создан
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
        <td class="report-date" style="width: 50%">${uiState.value.dataFillingOne.date} г.</td>
        <td class="report-city">г. Минск</td>
    </tr>
</table>

<table class="general-info">
    <tr>
        <td style="width: 20%">ФИО</td>
        <td>${uiState.value.dataFillingOne.lastName} ${uiState.value.dataFillingOne.firstName} ${uiState.value.dataFillingOne.patronymic}</td>
    </tr>
    <tr>
        <td>Путевой лист №</td>
        <td>${uiState.value.dataFillingOne.waybill}</td>
    </tr>
    <tr>
        <td>авто/м марка, г/н</td>
        <td>${uiState.value.dataFillingOne.makeVehicle}, ${uiState.value.dataFillingOne.rnVehicle}</td>
    </tr>
    <tr>
        <td>прицеп марка, г/н</td>
        <td>${uiState.value.dataFillingOne.makeTrailer}, ${uiState.value.dataFillingOne.rnTrailer}</td>
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
    <meta charset="UTF-8">
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