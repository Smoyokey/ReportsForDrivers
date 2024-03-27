package com.example.reportsfordrivers.navigate

import androidx.annotation.StringRes
import com.example.reportsfordrivers.R

enum class ReportsForDriversSchema(@StringRes val title: Int) {
    FirstEntry(title = R.string.driver_report),
    Start(title = R.string.driver_report),

    ReportInfo(title = R.string.report_information),
    PersonalInfo(title = R.string.personal_information),
    VehicleInfo(title = R.string.vehicle_trailer_information),
    FillingDataTwo(title = R.string.data),
    ProgressReport(title = R.string.stages_movement),
    TripExpenses(title = R.string.business_trip_expenses),
    Preview(title = R.string.preview),
    Result(title = R.string.result),

    ListHistory(title = R.string.history_report),
    SelectElementHistory(title = R.string.history_report),

    SettingStart(title = R.string.settings),
    PersonalInformation(title = R.string.personal_data),
    VehicleAndTrailerData(title = R.string.settings),
    CountriesCities(title = R.string.countries_cities),
    Feedback(title = R.string.settings)
}