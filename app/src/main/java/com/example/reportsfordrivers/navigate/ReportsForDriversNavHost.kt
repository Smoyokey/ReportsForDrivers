package com.example.reportsfordrivers.navigate

import android.app.Activity
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
import com.example.reportsfordrivers.ui.layouts.firstentry.FirstEntryOneScreen
import com.example.reportsfordrivers.ui.layouts.firstentry.FirstEntryTwoScreen
import com.example.reportsfordrivers.ui.layouts.hirstoryreports.HistoryReportsScreen
import com.example.reportsfordrivers.ui.layouts.setting.SettingCountriesCitiesScreen
import com.example.reportsfordrivers.ui.layouts.setting.SettingDataVehiclesTrailersScreen
import com.example.reportsfordrivers.ui.layouts.setting.SettingMainScreen
import com.example.reportsfordrivers.ui.layouts.setting.SettingPersonalDataScreen
import com.example.reportsfordrivers.viewmodel.MainMenuViewModel
import com.example.reportsfordrivers.viewmodel.createreports.CreateExpensesTripViewModel
import com.example.reportsfordrivers.viewmodel.createreports.CreatePersonalInfoViewModel
import com.example.reportsfordrivers.viewmodel.createreports.CreatePreviewAndResultViewModel
import com.example.reportsfordrivers.viewmodel.createreports.CreateProgressReportsViewModel
import com.example.reportsfordrivers.viewmodel.createreports.CreateReportInfoViewModel
import com.example.reportsfordrivers.viewmodel.createreports.CreateRouteViewModel
import com.example.reportsfordrivers.viewmodel.createreports.CreateVehicleTrailerViewModel
import com.example.reportsfordrivers.viewmodel.firstentry.FirstEntryViewModel
import com.example.reportsfordrivers.viewmodel.setting.CountriesAndCitiesViewModel

@Composable
fun ReportsForDriversNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    activity: Activity
) {
    val viewModelFirst: FirstEntryViewModel = hiltViewModel<FirstEntryViewModel>()
    val viewModelMain: MainMenuViewModel = hiltViewModel<MainMenuViewModel>()
    val viewModelCountriesAndCities: CountriesAndCitiesViewModel =
        hiltViewModel<CountriesAndCitiesViewModel>()
    val viewModelCreateReportInfo: CreateReportInfoViewModel =
        hiltViewModel<CreateReportInfoViewModel>()
    val viewModelCreatePersonalInfo: CreatePersonalInfoViewModel =
        hiltViewModel<CreatePersonalInfoViewModel>()
    val viewModelCreateVehicleTrailer: CreateVehicleTrailerViewModel =
        hiltViewModel<CreateVehicleTrailerViewModel>()
    val viewModelCreateRoute: CreateRouteViewModel = hiltViewModel<CreateRouteViewModel>()
    val viewModelCreateProgressReports: CreateProgressReportsViewModel =
        hiltViewModel<CreateProgressReportsViewModel>()
    val viewModelCreateExpensesTrip: CreateExpensesTripViewModel =
        hiltViewModel<CreateExpensesTripViewModel>()
    val viewModelCreatePreviewAndResult: CreatePreviewAndResultViewModel =
        hiltViewModel<CreatePreviewAndResultViewModel>()
    viewModelCreatePreviewAndResult.activity = activity

    NavHost(
        navController = navController,
        startDestination = ReportsForDriversSchema.Start.name,
        modifier = modifier
    ) {

        composable(route = ReportsForDriversSchema.Start.name) {

            if (viewModelMain.isFirstEntry()) {
                FirstEntryOneScreen(
                    onFirstEntryTwo = {
                        navController.navigate(ReportsForDriversSchema.FirstEntryTwo.name)
                    },
                    viewModel = viewModelFirst
                )
            } else {
                MainMenuScreen(
                    onCreate = {
                        viewModelCreateReportInfo.startCreateReport()
                        navController.navigate(ReportsForDriversSchema.ReportInfo.name)
                    },
                    onContinued = {
                        when (viewModelMain.selectedPage()) {
                            1 -> {
                                navController.navigate(ReportsForDriversSchema.ReportInfo.name)
                            }
                            2 -> {
                                navController.navigate(ReportsForDriversSchema.PersonalInfo.name)
                            }
                            3 -> {
                                navController.navigate(ReportsForDriversSchema.VehicleInfo.name)
                            }
                            4 -> {
                                navController.navigate(ReportsForDriversSchema.Route.name)
                            }
                            5 -> {
                                navController.navigate(ReportsForDriversSchema.ProgressReport.name)
                            }
                            6 -> {
                                navController.navigate(ReportsForDriversSchema.TripExpenses.name)
                            }
                            7 -> {
                                navController.navigate(ReportsForDriversSchema.Preview.name)
                            }
                            else -> {
                                navController.navigate(ReportsForDriversSchema.ReportInfo.name)
                            }
                        }
                    },
                    onHistory = {
                        navController.navigate(ReportsForDriversSchema.ListHistory.name)
                    },
                    onSetting = {
                        navController.navigate(ReportsForDriversSchema.SettingStart.name)
                    },
                    viewModel = viewModelMain,
                )
            }
        }

        composable(route = ReportsForDriversSchema.ReportInfo.name) {
            if(!viewModelCreateReportInfo.firstOpenReport.value) {
                viewModelCreateReportInfo.startLoadCreateReportInfo()
                viewModelCreateReportInfo.firstOpenReport.value = true
            }
            CreateReportsDataReportInfoScreen(
                viewModel = viewModelCreateReportInfo,
                navController = navController
            )
        }
        composable(route = ReportsForDriversSchema.PersonalInfo.name) {
            if(!viewModelCreatePersonalInfo.firstOpenPersonalScreen.value) {
                viewModelCreatePersonalInfo.startFio()
            }

            CreateReportsDataPersonalInfoScreen(
                viewModel = viewModelCreatePersonalInfo,
                navController = navController
            )
        }
        composable(route = ReportsForDriversSchema.VehicleInfo.name) {
            if(!viewModelCreateVehicleTrailer.firstOpenCreateVehicleTrailer.value) {
                viewModelCreateVehicleTrailer.startLoadCreateVehicleTrailer()
            }
            CreateReportsDataVehicleInfoScreen(
                viewModel = viewModelCreateVehicleTrailer,
                navController = navController
            )
        }
        composable(route = ReportsForDriversSchema.Route.name) {
            if(!viewModelCreateRoute.firstOpenCreateRoute.value) {
                viewModelCreateRoute.startLoadCreateRoute()

            }
            CreateReportsDataFillingTwoScreen(
                viewModel = viewModelCreateRoute,
                navController = navController
            )
        }
        composable(route = ReportsForDriversSchema.ProgressReport.name) {
            CreateReportsProgressReportsScreen(
                viewModel = viewModelCreateProgressReports,
                navController = navController
            )
        }
        composable(route = ReportsForDriversSchema.TripExpenses.name) {
            CreateReportsExpensesScreen(
                viewModel = viewModelCreateExpensesTrip,
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
                viewModel = viewModelCreatePreviewAndResult
            )
        }
        composable(route = ReportsForDriversSchema.Result.name) {
            CreateReportsResultScreen(
                viewModel = viewModelCreatePreviewAndResult
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
                onCountriesCities = {
                    navController.navigate(ReportsForDriversSchema.CountriesCities.name)
                    viewModelCountriesAndCities.openSelectedCountry()

                },
            )
        }
        composable(route = ReportsForDriversSchema.PersonalInformation.name) {
            SettingPersonalDataScreen()
        }
        composable(route = ReportsForDriversSchema.VehicleAndTrailerData.name) {
            SettingDataVehiclesTrailersScreen()
        }
        composable(route = ReportsForDriversSchema.CountriesCities.name) {
            SettingCountriesCitiesScreen( viewModel = viewModelCountriesAndCities )
        }


        composable(route = ReportsForDriversSchema.FirstEntryOne.name) {
            FirstEntryOneScreen(
                onFirstEntryTwo = {
                    navController.navigate(ReportsForDriversSchema.FirstEntryTwo.name)
                }
            )
        }
        composable(route = ReportsForDriversSchema.FirstEntryTwo.name) {
            FirstEntryTwoScreen(
                onMainMenu = {
                    navController.navigate(ReportsForDriversSchema.Start.name)
                },
                onFirstEntryOneScreen = {
                    navController.navigate(ReportsForDriversSchema.FirstEntryOne.name)
                },
                viewModel = viewModelFirst
            )
        }
    }
}