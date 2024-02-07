package com.example.reportsfordrivers.navigate

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.reportsfordrivers.ui.layouts.MainMenuScreen
import com.example.reportsfordrivers.ui.layouts.createreports.CreateReportsDataFillingOneScreen
import com.example.reportsfordrivers.ui.layouts.createreports.CreateReportsDataFillingTwoScreen
import com.example.reportsfordrivers.ui.layouts.createreports.CreateReportsPreviewScreen
import com.example.reportsfordrivers.ui.layouts.createreports.CreateReportsProgressReportsScreen
import com.example.reportsfordrivers.ui.layouts.createreports.CreateReportsResultScreen
import com.example.reportsfordrivers.ui.layouts.createreports.CreateReportsSelectedMaketScreen
import com.example.reportsfordrivers.ui.layouts.firstentry.FirstEntryScreen
import com.example.reportsfordrivers.ui.layouts.hirstoryreports.HistoryReportsScreen
import com.example.reportsfordrivers.ui.layouts.setting.SettingDataVehiclesTrailersScreen
import com.example.reportsfordrivers.ui.layouts.setting.SettingFeedbackScreen
import com.example.reportsfordrivers.ui.layouts.setting.SettingMainScreen
import com.example.reportsfordrivers.ui.layouts.setting.SettingPersonalDataScreen
import com.example.reportsfordrivers.viewmodel.MainMenuViewModel
import com.example.reportsfordrivers.viewmodel.firstentry.FirstEntryViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun ReportsForDriversNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = ReportsForDriversSchema.Start.name,
        modifier = modifier
    ) {
        composable(route = ReportsForDriversSchema.Start.name) {
            val viewModel = hiltViewModel<MainMenuViewModel>()

            if(viewModel.isFirstEntry()) {
                val viewModelFirstEntry = hiltViewModel<FirstEntryViewModel>()
                FirstEntryScreen(
                    onMainMenu = {
                        navController.navigate(ReportsForDriversSchema.Start.name)
                        viewModelFirstEntry.onFirstEntry()
                    },
                    viewModel = viewModelFirstEntry
                )
            } else {
                MainMenuScreen(onCreateReport = {
                    navController.navigate(ReportsForDriversSchema.SelectLayout.name)
                },
                    onHistoryReports = {
                        navController.navigate(ReportsForDriversSchema.ListHistory.name)
                    },
                    onSetting = {
                        navController.navigate(ReportsForDriversSchema.SettingStart.name)
                    },
                    viewModel = viewModel
                )
            }
        }
        composable(route = ReportsForDriversSchema.SelectLayout.name) {
            CreateReportsSelectedMaketScreen(
                onFillingDataOne = {
                    navController.navigate(ReportsForDriversSchema.FillingDataOne.name)
                }
            )
        }
        composable(route = ReportsForDriversSchema.ListHistory.name) {
            HistoryReportsScreen()
        }
        composable(route = ReportsForDriversSchema.SettingStart.name) {
            SettingMainScreen(
                onPersonalData = {
                    navController.navigate(ReportsForDriversSchema.PersonalInformation.name)
                },
                onVehicleAndTrailerDate = {
                    navController.navigate(ReportsForDriversSchema.VehicleAndTrailerData.name)
                },
                onFeedback = {
                    navController.navigate(ReportsForDriversSchema.Feedback.name)
                }
            )
        }
        composable(route = ReportsForDriversSchema.FillingDataOne.name) {
            CreateReportsDataFillingOneScreen(
                onDataFillingTwo = {
                    navController.navigate(ReportsForDriversSchema.FillingDataTwo.name)
                }
            )
        }
        composable(route = ReportsForDriversSchema.FillingDataTwo.name) {
            CreateReportsDataFillingTwoScreen(
                onProgressReport = {
                    navController.navigate(ReportsForDriversSchema.ProgressReport.name)
                }
            )
        }
        composable(route = ReportsForDriversSchema.ProgressReport.name) {
            CreateReportsProgressReportsScreen(
                onPreview = {
                    navController.navigate(ReportsForDriversSchema.Preview.name)
                }
            )
        }
        composable(route = ReportsForDriversSchema.Preview.name) {
            CreateReportsPreviewScreen(
                onResult = {
                    navController.navigate(ReportsForDriversSchema.Result.name)
                }
            )
        }
        composable(route = ReportsForDriversSchema.Result.name) {
            CreateReportsResultScreen()
        }
        composable(route = ReportsForDriversSchema.PersonalInformation.name) {
            SettingPersonalDataScreen()
        }
        composable(route = ReportsForDriversSchema.VehicleAndTrailerData.name) {
            SettingDataVehiclesTrailersScreen()
        }
        composable(route = ReportsForDriversSchema.Feedback.name) {
            SettingFeedbackScreen()
        }

        composable(route = ReportsForDriversSchema.FirstEntry.name) {
            FirstEntryScreen(
                onMainMenu = {
                    navController.navigate(ReportsForDriversSchema.Start.name)
                }
            )
        }
    }
}