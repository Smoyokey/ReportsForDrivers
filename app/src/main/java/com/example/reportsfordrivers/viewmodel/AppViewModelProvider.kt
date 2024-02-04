package com.example.reportsfordrivers.viewmodel

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.reportsfordrivers.viewmodel.firstentry.FirstEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory{
        initializer {
            FirstEntryViewModel()
        }
    }
}