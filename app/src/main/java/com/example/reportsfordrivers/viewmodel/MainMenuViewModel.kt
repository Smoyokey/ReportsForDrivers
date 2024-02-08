package com.example.reportsfordrivers.viewmodel

import android.util.Log
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

    fun logs() {
        Log.i(TAG, "MainMenuViewModel is ready")
    }

    fun isFirstEntry(): Boolean = runBlocking {
        return@runBlocking firstEntryPreferencesRepository.getFirstEntry().getOrNull() ?: false
    }
}