package com.example.reportsfordrivers

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.reportsfordrivers.navigate.ReportsForDriversNavHost
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsForDriversTopBar(
    currentScreen: ReportsForDriversSchema,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(currentScreen.title)) },

        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReportsForDriversApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = ReportsForDriversSchema.valueOf(
        backStackEntry?.destination?.route ?: ReportsForDriversSchema.Start.name
    )

    Scaffold(
        topBar = {
            ReportsForDriversTopBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.currentBackStackEntry != null
            ) { navController.navigateUp() }
        }
    ) { innerPadding ->
        ReportsForDriversNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
        )
    }
}