package com.example.reportsfordrivers.navigationtest

import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.reportsfordrivers.MainActivity
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.ReportsForDriversApp
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.navigationtest.helpmethods.assertCurrentRouteName
import com.example.reportsfordrivers.navigationtest.helpmethods.onNodeWithStringId
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HistoryReportNavigationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupReportsForDriversApp() {
        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            ReportsForDriversApp(navController = navController, activity = composeTestRule.activity)
        }
        start()
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

    private fun start() {
        try {
            composeTestRule.onNodeWithStringId(R.string.skip).performClick()
        } catch (e: AssertionError) {
            Log.e("History", "Object skip not found")
        }

    }

    private fun historyReports() {
        composeTestRule.onNodeWithStringId(R.string.history_report).performClick()
    }

    private fun buttonNavigateUp() {
        val backButton = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backButton).performClick()
    }
}