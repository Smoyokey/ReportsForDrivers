package com.example.reportsfordrivers.viewmodel.createreports

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.compose.ui.text.android.InternalPlatformTextApi
import androidx.compose.ui.text.android.animation.SegmentType
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter

class ResultViewModel(
): ViewModel() {
    val testStringHtml = """<head>
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
    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
    </tr>
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

    fun testClick() {
        writeFile("download/", "Print.doc", testStringHtml)
    }

    fun testShare(context: Context) {
//        createFile(context)
        downloadFile(context)
    }

    fun downloadFile(context: Context) {
        val filePath: File = File(context.filesDir, "docs")
        filePath.mkdir()
        val newFile = File(filePath, "MyNewFile.doc")
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
            fileWriter.append(testStringHtml)
        } catch (e: Exception) {}
        fileWriter!!.close()

        createFile(contentUri, context)
    }

    fun createFile(pickerInitialUri: Uri, context: Context) {
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, pickerInitialUri)
            type = "text/plain"
            setDataAndType(pickerInitialUri, context.contentResolver.getType(pickerInitialUri))
        }
        context.startActivity(Intent.createChooser(shareIntent, "My First Doc"))
    }

    fun writeFile(filePath: String, fileName: String, text: String) {
        try {
            //Создается объект файла, при этом путь к файлу находиться методом Environment
            val myFile = File(Environment.getExternalStorageDirectory().toString() + "/" + filePath + fileName)
            // Создается файл, если он не был создан
            myFile.createNewFile()
            // После чего создаем поток для записи
            val outputStream = FileOutputStream(myFile)
            // Производим непосредственно запись
            outputStream.write(text.toByteArray())
            // Закрываем поток
            outputStream.close()
            // Просто для удобства визуального контроля исполнения метода в приложении
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}