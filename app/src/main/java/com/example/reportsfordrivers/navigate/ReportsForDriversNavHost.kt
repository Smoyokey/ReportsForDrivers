package com.example.reportsfordrivers.navigate

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.reportsfordrivers.ui.layouts.MainMenuScreen
import com.example.reportsfordrivers.ui.layouts.createreports.CreateReportsDataFillingTwoScreen
import com.example.reportsfordrivers.ui.layouts.createreports.CreateReportsDataPersonalInfoScreen
import com.example.reportsfordrivers.ui.layouts.createreports.CreateReportsDataReportInfoScreen
import com.example.reportsfordrivers.ui.layouts.createreports.CreateReportsDataVehicleInfoScreen
import com.example.reportsfordrivers.ui.layouts.createreports.CreateReportsExpensesScreen
import com.example.reportsfordrivers.ui.layouts.createreports.CreateReportsPreviewScreen
import com.example.reportsfordrivers.ui.layouts.createreports.CreateReportsProgressReportsScreen
import com.example.reportsfordrivers.ui.layouts.createreports.CreateReportsResultScreen
import com.example.reportsfordrivers.ui.layouts.firstentry.FirstEntryScreen
import com.example.reportsfordrivers.ui.layouts.hirstoryreports.HistoryReportsScreen
import com.example.reportsfordrivers.ui.layouts.setting.SettingDataVehiclesTrailersScreen
import com.example.reportsfordrivers.ui.layouts.setting.SettingFeedbackScreen
import com.example.reportsfordrivers.ui.layouts.setting.SettingMainScreen
import com.example.reportsfordrivers.ui.layouts.setting.SettingPersonalDataScreen
import com.example.reportsfordrivers.viewmodel.MainMenuViewModel
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportsViewModel
import com.example.reportsfordrivers.viewmodel.firstentry.FirstEntryViewModel

@Composable
fun ReportsForDriversNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModelFirst: FirstEntryViewModel = hiltViewModel<FirstEntryViewModel>(),
    viewModelMain: MainMenuViewModel = hiltViewModel<MainMenuViewModel>(),
    viewModelCreateReports: CreateReportsViewModel = hiltViewModel<CreateReportsViewModel>()
) {
    NavHost(
        navController = navController,
        startDestination = ReportsForDriversSchema.Start.name,
        modifier = modifier
    ) {

        composable(route = ReportsForDriversSchema.Start.name) {

            if (viewModelMain.isFirstEntry()) {
                FirstEntryScreen(
                    onMainMenu = {
                        navController.navigate(ReportsForDriversSchema.Start.name)
                        viewModelFirst.onFirstEntry()
                    },
                    viewModel = viewModelFirst
                )
            } else {
                MainMenuScreen(
                    onCreateReport = {
                        navController.navigate(ReportsForDriversSchema.ReportInfo.name)
                    },
                    onHistoryReports = {
                        navController.navigate(ReportsForDriversSchema.ListHistory.name)
                    },
                    onSetting = {
                        navController.navigate(ReportsForDriversSchema.SettingStart.name)
                    },
                    viewModel = viewModelMain
                )
            }
        }

        composable(route = ReportsForDriversSchema.ReportInfo.name) {
            CreateReportsDataReportInfoScreen(
                viewModel = viewModelCreateReports,
                navController = navController
            )
        }
        composable(route = ReportsForDriversSchema.PersonalInfo.name) {
            CreateReportsDataPersonalInfoScreen(
                viewModel = viewModelCreateReports,
                navController = navController
            )
        }
        composable(route = ReportsForDriversSchema.VehicleInfo.name) {
            CreateReportsDataVehicleInfoScreen(
                viewModel = viewModelCreateReports,
                navController = navController
            )
        }
        composable(route = ReportsForDriversSchema.FillingDataTwo.name) {
            CreateReportsDataFillingTwoScreen(
                viewModel = viewModelCreateReports,
                navController = navController
            )
        }
        composable(route = ReportsForDriversSchema.ProgressReport.name) {
            CreateReportsProgressReportsScreen(
                viewModel = viewModelCreateReports,
                navController = navController
            )
        }
        composable(route = ReportsForDriversSchema.TripExpenses.name) {
            CreateReportsExpensesScreen(
                viewModel = viewModelCreateReports,
                navController = navController
            )
        }
        composable(route = ReportsForDriversSchema.Preview.name) {
            CreateReportsPreviewScreen(
                onNext = {
                    navController.navigate(ReportsForDriversSchema.Result.name)
                },
                onBack = {
                    navController.navigateUp()
                },
                viewModel = viewModelCreateReports
            )
        }
        composable(route = ReportsForDriversSchema.Result.name) {
            CreateReportsResultScreen(
                viewModel = viewModelCreateReports
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