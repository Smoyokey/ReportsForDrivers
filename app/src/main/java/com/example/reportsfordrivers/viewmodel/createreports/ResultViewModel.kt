package com.example.reportsfordrivers.viewmodel.createreports

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

const val CREATE_FILE = 1

class ResultViewModel(
): ViewModel() {


    private fun createFile(context: Context) {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
//            addCategory(Intent.CATEGORY_OPENABLE)
//            addCategory(Intent.ACTION_SEND)
            type = "file/*"
//            putExtra(Intent.EXTRA_TEXT, "TEST")
            putExtra(Intent.EXTRA_TITLE, "test3.pdf")
//            putExtra(Intent.EXTRA_PROCESS_TEXT, "terteeeeeee")
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
//            }
//            putExtra(Intent.EXTRA_HTML_TEXT, "fwfff")

        }
        context.startActivity(intent)
    }

    private fun shareFile(context: Context) {
        val filePath = File(context.filesDir, "Test")
        val newFile = File(filePath, "Test.doc")
        val contentUri: Uri = Uri.

    }

//    private fun shareFile(context: Context) {
//        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
//            addCategory()
//            type = "application/pdf"
//            putExtra(Intent.EXTRA_TITLE, "test2.pdf")
//        }
//        context.startActivity(intent)
//    }

//    private fun createFileTwo() {
//        try (val ab: FileOutputStream = FileOutputStream("Test.doc"){
//            ab = FileOutputStream("test.pdf")
//            val text = "Hello, my name test"
//            ab.write(text.toByteArray())
//        } catch(e: IOException) {
//            Log.e("TEST", "ERROR")
//        } finally {  }
//    }

    fun testClick(context: Context) {
//        createFile(context)
        writeFile("download/", "Hello.doc", "TEST number 1")
    }

    fun testShare(context: Context) {
        createFile(context)
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
//            Toast.makeText(this, "File is write", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
//            Toast.makeText(this, "File is not write", Toast.LENGTH_SHORT).show()
        }
    }
}