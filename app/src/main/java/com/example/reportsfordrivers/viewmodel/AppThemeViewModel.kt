package com.example.reportsfordrivers.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

private const val TAG = "AppThemeViewModel"

@HiltViewModel
class AppThemeViewModel @Inject constructor(
    private val preferencesRepository: FioFirstEntryRepository
) : ViewModel() {

    /*TODO*/
    val selectedTheme: MutableState<String> = onSelectedTheme()

    fun onSelectedTheme(): MutableState<String> = runBlocking {
        if(preferencesRepository.getThemeApp().getOrDefault("DE") == "") {
            preferencesRepository.setThemeApp("DE")
        }
        Log.i(TAG, preferencesRepository.getThemeApp().getOrDefault("DEFAULT"))
        return@runBlocking mutableStateOf(preferencesRepository.getThemeApp().getOrDefault("L"))
    }
}