package com.example.reportsfordrivers.navigationtest

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.ReportsForDriversApp
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.navigationtest.helpmethods.assertCurrentRouteName
import com.example.reportsfordrivers.navigationtest.helpmethods.onNodeWithStringId
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HistoryReportNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupReportsForDriversApp() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            ReportsForDriversApp(navController = navController)
        }
    }

    @Test
    fun reportsForDriversNavHost_verifyStartScreen() {
        navController.assertCurrentRouteName(ReportsForDriversSchema.Start.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToHistoryReports_inHistoryReport() {
        historyReports()
        navController.assertCurrentRouteName(ReportsForDriversSchema.ListHistory.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToMainMenu_inHistoryReport() {
        historyReports()
        buttonNavigateUp()
        navController.assertCurrentRouteName(ReportsForDriversSchema.Start.name)
    }

    private fun historyReports() {
        composeTestRule.onNodeWithStringId(R.string.history_report).performClick()
    }

    private fun buttonNavigateUp() {
        val backButton = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backButton).performClick()
    }
}