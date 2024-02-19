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
    val testStringHtml = """<table>
  <tr>
    <th>Фамилия</th>
    <th>Имя</th>
  </tr>

  <tr>
    <td>Пермикин</td>
    <td>Олег</td>
  </tr>

  <tr>
    <td>Пугач</td>
    <td>Полина</td>
  </tr>
</table>"""

    fun testClick() {
        writeFile("download/", "Hello.doc", testStringHtml)
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

//    @OptIn(InternalPlatformTextApi::class)
//    fun table() {
//        val document = SegmentType.Document
//    }
}