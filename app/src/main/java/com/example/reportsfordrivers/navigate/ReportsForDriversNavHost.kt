package com.example.reportsfordrivers.navigate

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.reportsfordrivers.ui.layouts.MainMenuScreen

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
            MainMenuScreen()
        }
    }
}