package com.example.reportsfordrivers.navigate

import androidx.annotation.StringRes
import com.example.reportsfordrivers.R

enum class ReportsForDriversSchema(@StringRes val title: Int) {
    FirstEntry(title = R.string.driver_report),
    Start(title = R.string.driver_report),

    SelectLayout(title = R.string.create_report),
    FillingDataOne(title = R.string.create_report),
    FillingDataTwo(title = R.string.create_report),
    ProgressReport(title = R.string.create_report),
    Preview(title = R.string.create_report),
    Result(title = R.string.create_report),

    ListHistory(title = R.string.history_report),
    SelectElementHistory(title = R.string.history_report),

    SettingStart(title = R.string.setting),
    PersonalInformation(title = R.string.setting),
    VehicleAndTrailerData(title = R.string.setting),
    Feedback(title = R.string.setting)
}