package com.example.reportsfordrivers.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AppThemeViewModel @Inject constructor(
    private val preferencesRepository: FioFirstEntryRepository
) : ViewModel() {

    /*TODO*/

    fun onSelectedTheme(): MutableState<String> = runBlocking {
        return@runBlocking mutableStateOf(preferencesRepository.getThemeApp().getOrDefault("DE"))
    }
}