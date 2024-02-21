package com.example.reportsfordrivers.navigate

import androidx.annotation.StringRes
import com.example.reportsfordrivers.R

enum class ReportsForDriversSchema(@StringRes val title: Int) {
    FirstEntry(title = R.string.driver_report),
    Start(title = R.string.driver_report),

    SelectLayout(title = R.string.choosing_layout),
    FillingDataOne(title = R.string.data),
    FillingDataTwo(title = R.string.data),
    ProgressReport(title = R.string.stages_movement),
    TripExpenses(title = R.string.business_trip_expenses),
    Preview(title = R.string.preview),
    Result(title = R.string.result),

    ListHistory(title = R.string.history_report),
    SelectElementHistory(title = R.string.history_report),

    SettingStart(title = R.string.setting),
    PersonalInformation(title = R.string.setting),
    VehicleAndTrailerData(title = R.string.setting),
    Feedback(title = R.string.setting)
}