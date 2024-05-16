package com.example.reportsfordrivers.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

private const val TAG = "MainMenuViewModel"

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    private val firstEntryPreferencesRepository: FioFirstEntryRepository
): ViewModel() {

    val isToBeContinued = mutableStateOf(isToBeContinued())

    val openSnackBarSaveReport = mutableStateOf(false)

    fun isFirstEntry(): Boolean = runBlocking {
        return@runBlocking firstEntryPreferencesRepository.getFirstEntry().getOrNull() ?: false
    }

    private fun isToBeContinued(): Boolean = runBlocking {
        return@runBlocking firstEntryPreferencesRepository.getStartCreateReport().getOrNull() ?: false
    }

    fun selectedPage(): Int = runBlocking {
        return@runBlocking firstEntryPreferencesRepository.getCreateSelectedPage().getOrNull() ?: 0
    }

}