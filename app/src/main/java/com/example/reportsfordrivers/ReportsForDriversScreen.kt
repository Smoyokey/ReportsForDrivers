package com.example.reportsfordrivers

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.reportsfordrivers.navigate.ReportsForDriversNavHost
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.ui.layouts.firstentry.AlertDialogDeleteElement

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsForDriversTopBar(
    currentScreen: ReportsForDriversSchema,
    canNavigateBack: Boolean,
    navigate: NavHostController
) {
    val isOpenAlertDialogExit = mutableStateOf(false)

    TopAppBar(
        title = { Text(text = stringResource(currentScreen.title)) },

        navigationIcon = {
            if (navigate.currentDestination?.route != ReportsForDriversSchema.Start.name) {
                IconButton(onClick = {
                    isOpenAlertDialogExit.value = true
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
    AlertDialogToMainMenu(isOpen = isOpenAlertDialogExit, navigate = navigate)
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
                canNavigateBack = navController.currentBackStackEntry != null,
                navigate = navController
            )
        }
    ) { innerPadding ->
        ReportsForDriversNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
fun AlertDialogToMainMenu(isOpen: MutableState<Boolean>, navigate: NavHostController) {
    if(isOpen.value) {
        AlertDialog(
            onDismissRequest = { isOpen.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        navigate.navigate(ReportsForDriversSchema.Start.name,
                            navOptions = NavOptions.Builder()
                                .setPopUpTo(ReportsForDriversSchema.Start.name, true)
                                .build()
                        )
                        isOpen.value = false
                    }
                ) {
                    Text(text = stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { isOpen.value = false }
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            },
            title = {
                Text(text = stringResource(R.string.test))
            },
            text = {
                Text(text = stringResource(R.string.test))
            }
        )
    }
}