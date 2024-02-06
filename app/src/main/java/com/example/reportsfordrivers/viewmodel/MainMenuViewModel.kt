package com.example.reportsfordrivers.viewmodel

import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.datastore.FirstEntryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    private val firstEntryPreferencesRepository: FirstEntryRepository
): ViewModel() {

}