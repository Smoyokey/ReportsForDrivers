package com.example.reportsfordrivers.navigationtest

import androidx.activity.ComponentActivity
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
class SettingNavigationTest {

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
    fun reportsForDriversNavHost_moveToSettings_inMainSetting() {
        mainSetting()
        navController.assertCurrentRouteName(ReportsForDriversSchema.SettingStart.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToSettings_inPersonalInformation() {
        personalInformation()
        navController.assertCurrentRouteName(ReportsForDriversSchema.PersonalInformation.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToSettings_inVehicleAndTrailerData() {
        vehicleAndTrailerData()
        navController.assertCurrentRouteName(ReportsForDriversSchema.VehicleAndTrailerData.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToSettings_inFeedback() {
        feedback()
        navController.assertCurrentRouteName(ReportsForDriversSchema.Feedback.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToMainMenu_inMainSetting() {
        mainSetting()
        buttonNavigateUp()
        navController.assertCurrentRouteName(ReportsForDriversSchema.Start.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToMainSetting_inPersonalInformation() {
        personalInformation()
        buttonNavigateUp()
        navController.assertCurrentRouteName(ReportsForDriversSchema.SettingStart.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToMainMenu_inPersonalInformation() {
        personalInformation()
        repeat(2) { buttonNavigateUp() }
        navController.assertCurrentRouteName(ReportsForDriversSchema.Start.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToMainSetting_inVehicleAndTrailerData() {
        vehicleAndTrailerData()
        buttonNavigateUp()
        navController.assertCurrentRouteName(ReportsForDriversSchema.SettingStart.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToMainMenu_inVehicleAndTrailerData() {
        vehicleAndTrailerData()
        repeat(2) { buttonNavigateUp() }
        navController.assertCurrentRouteName(ReportsForDriversSchema.Start.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToMainSetting_inFeedback() {
        feedback()
        buttonNavigateUp()
        navController.assertCurrentRouteName(ReportsForDriversSchema.SettingStart.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToMainMenu_inFeedback() {
        feedback()
        repeat(2) { buttonNavigateUp() }
        navController.assertCurrentRouteName(ReportsForDriversSchema.Start.name)
    }

    private fun mainSetting() {
        composeTestRule.onNodeWithStringId(R.string.settings).performClick()
    }

    private fun personalInformation() {
        mainSetting()
        composeTestRule.onNodeWithStringId(R.string.personal_data).performClick()
    }

    private fun vehicleAndTrailerData() {
        mainSetting()
        composeTestRule.onNodeWithStringId(R.string.data_vehicles_trailers).performClick()
    }

    private fun feedback() {
        mainSetting()
        composeTestRule.onNodeWithStringId(R.string.feedback).performClick()
    }

    private fun buttonNavigateUp() {
        val backButton = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backButton).performClick()
    }

    private fun start() {
        try {
            composeTestRule.onNodeWithStringId(R.string.skip).performClick()
        } catch (e: AssertionError) {}
    }
}