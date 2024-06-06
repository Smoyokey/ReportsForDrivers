package com.example.reportsfordrivers.viewmodel.createreports

import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateExpensesTripDetailingUiState
import com.example.reportsfordrivers.viewmodel.createreports.uistate.CreateProgressReportsDetailingUiState
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditExpensesTripDetailingUiState
import com.example.reportsfordrivers.viewmodel.historyreports.editreport.uistate.EditProgressReportsDetailingUiState

object HtmlText {

    fun onePageHtml(
        dataCreateReportInfoDate: String,
        dataCreateReportInfoMainCity: String,
        lastName: String,
        firstName: String,
        patronymic: String,
        waybill: String,
        makeVehicle: String,
        rnVehicle: String,
        makeTrailer: String,
        rnTrailer: String,
        route: String,
        dateDeparture: String,
        dateReturn: String,
        dateCrossingDeparture: String,
        dateCrossingReturn: String,
        speedometerDeparture: String,
        speedometerReturn: String,
        fuelled: String,
        progressReportsList: List<CreateProgressReportsDetailingUiState>,
        expensesTripList: List<CreateExpensesTripDetailingUiState>
    ): String {
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
        <td class="report-date" style="width: 50%">$dataCreateReportInfoDate г.</td>
        <td class="report-city">г. $dataCreateReportInfoMainCity</td>
    </tr>
</table>

<table class="general-info">
    <tr>
        <td style="width: 20%">ФИО</td>
        <td>
            $lastName
            $firstName
            $patronymic
        </td>
    </tr>
    <tr>
        <td>Путевой лист №</td>
        <td>$waybill</td>
    </tr>
    <tr>
        <td>авто/м марка, г/н</td>
        <td>
            $makeVehicle,
            $rnVehicle
        </td>
    </tr>
    <tr>
        <td>прицеп марка, г/н</td>
        <td>
            $makeTrailer,
            $rnTrailer
        </td>
    </tr>
</table>

<table class="general-info">
    <tr>
        <td style="width: 40%">Маршрут</td>
        <td>$route</td>
    </tr>
    <tr>
        <td>Дата выезда</td>
        <td>$dateDeparture</td>
    </tr>
    <tr>
        <td>Дата возврата</td>
        <td>$dateReturn</td>
    </tr>
    <tr>
        <td>Дата пересечения границы при отъезде</td>
        <td>$dateCrossingDeparture</td>
    </tr>
    <tr>
        <td>Дата пересечения при возврате</td>
        <td>$dateCrossingReturn</td>
    </tr>
    <tr>
        <td>Показания спидометра при выезде</td>
        <td>$speedometerDeparture</td>
    </tr>
    <tr>
        <td>Показания спидометра при возврате</td>
        <td>$speedometerReturn</td>
    </tr>
    <tr>
        <td>Заправлено, л</td>
        <td>$fuelled</td>
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
    ${tableProgressReport(progressReportsList)}
</table>
</body>
<br>
<br>
${if (expensesTripList.isNotEmpty()) expenseTrip(expensesTripList) else ""}
"""

    }

    fun oneEditPageHtml(
        dataCreateReportInfoDate: String,
        dataCreateReportInfoMainCity: String,
        lastName: String,
        firstName: String,
        patronymic: String,
        waybill: String,
        makeVehicle: String,
        rnVehicle: String,
        makeTrailer: String,
        rnTrailer: String,
        route: String,
        dateDeparture: String,
        dateReturn: String,
        dateCrossingDeparture: String,
        dateCrossingReturn: String,
        speedometerDeparture: String,
        speedometerReturn: String,
        fuelled: String,
        progressReportsList: List<EditProgressReportsDetailingUiState>,
        expensesTripList: List<EditExpensesTripDetailingUiState>
    ): String {
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
        <td class="report-date" style="width: 50%">$dataCreateReportInfoDate г.</td>
        <td class="report-city">г. $dataCreateReportInfoMainCity</td>
    </tr>
</table>

<table class="general-info">
    <tr>
        <td style="width: 20%">ФИО</td>
        <td>
            $lastName
            $firstName
            $patronymic
        </td>
    </tr>
    <tr>
        <td>Путевой лист №</td>
        <td>$waybill</td>
    </tr>
    <tr>
        <td>авто/м марка, г/н</td>
        <td>
            $makeVehicle,
            $rnVehicle
        </td>
    </tr>
    <tr>
        <td>прицеп марка, г/н</td>
        <td>
            $makeTrailer,
            $rnTrailer
        </td>
    </tr>
</table>

<table class="general-info">
    <tr>
        <td style="width: 40%">Маршрут</td>
        <td>$route</td>
    </tr>
    <tr>
        <td>Дата выезда</td>
        <td>$dateDeparture</td>
    </tr>
    <tr>
        <td>Дата возврата</td>
        <td>$dateReturn</td>
    </tr>
    <tr>
        <td>Дата пересечения границы при отъезде</td>
        <td>$dateCrossingDeparture</td>
    </tr>
    <tr>
        <td>Дата пересечения при возврате</td>
        <td>$dateCrossingReturn</td>
    </tr>
    <tr>
        <td>Показания спидометра при выезде</td>
        <td>$speedometerDeparture</td>
    </tr>
    <tr>
        <td>Показания спидометра при возврате</td>
        <td>$speedometerReturn</td>
    </tr>
    <tr>
        <td>Заправлено, л</td>
        <td>$fuelled</td>
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
    ${editTableProgressReport(progressReportsList)}
</table>
</body>
<br>
<br>
${if (expensesTripList.isNotEmpty()) editExpenseTrip(expensesTripList) else ""}
"""

    }
}

private fun expenseTrip(
    expensesTripList: List<CreateExpensesTripDetailingUiState>
): String {
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
    ${tableTripExpense(expensesTripList)}
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

private fun tableProgressReport(
    progressReportsList: List<CreateProgressReportsDetailingUiState>
): String {
    var a = ""
    for (i in progressReportsList) {
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

private fun tableTripExpense(
    expensesTripList: List<CreateExpensesTripDetailingUiState>
): String {
    var b = ""
    for (i in expensesTripList) {
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

private fun editExpenseTrip(
    expensesTripList: List<EditExpensesTripDetailingUiState>
): String {
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
    ${editTableTripExpense(expensesTripList)}
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

private fun editTableProgressReport(
    progressReportsList: List<EditProgressReportsDetailingUiState>
): String {
    var a = ""
    for (i in progressReportsList) {
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

private fun editTableTripExpense(
    expensesTripList: List<EditExpensesTripDetailingUiState>
): String {
    var b = ""
    for (i in expensesTripList) {
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