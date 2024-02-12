package com.example.reportsfordrivers.viewmodel.createreports

import androidx.lifecycle.ViewModel
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "DataFillingOneViewModel"

@HiltViewModel
class DataFillingOneViewModel @Inject constructor (
    private val fioPreferencesRepository: FioFirstEntryRepository
): ViewModel() {

}