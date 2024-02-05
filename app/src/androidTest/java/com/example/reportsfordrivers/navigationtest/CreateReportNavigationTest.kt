package com.example.reportsfordrivers.navigationtest

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
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

class CreateReportNavigationTest {

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
    fun reportsForDriversNavHost_moveToCreateReport_inSelectedLayout() {
        selectLayout()
        navController.assertCurrentRouteName(ReportsForDriversSchema.SelectLayout.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToCreateReport_inFillingDataOne() {
        fillingDataOne()
        navController.assertCurrentRouteName(ReportsForDriversSchema.FillingDataOne.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToCreateReport_inFillingDataTwo() {
        fillingDataTwo()
        navController.assertCurrentRouteName(ReportsForDriversSchema.FillingDataTwo.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToCreateReport_inProgressReport() {
        progressReport()
        navController.assertCurrentRouteName(ReportsForDriversSchema.ProgressReport.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToCreateReport_inPreview() {
        preview()
        navController.assertCurrentRouteName(ReportsForDriversSchema.Preview.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToCreateReport_inResult() {
        result()
        navController.assertCurrentRouteName(ReportsForDriversSchema.Result.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToMainMenu_inSelectLayout() {
        selectLayout()
        buttonNavigateUp()
        navController.assertCurrentRouteName(ReportsForDriversSchema.Start.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToSelectLayout_inFillingDataOne() {
        fillingDataOne()
        buttonNavigateUp()
        navController.assertCurrentRouteName(ReportsForDriversSchema.SelectLayout.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToMainMenu_inFillingDataOne() {
        fillingDataOne()
        repeat(2) { buttonNavigateUp() }
        navController.assertCurrentRouteName(ReportsForDriversSchema.Start.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToFillingDataOne_inFillingDataTwo() {
        fillingDataTwo()
        buttonNavigateUp()
        navController.assertCurrentRouteName(ReportsForDriversSchema.FillingDataOne.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToSelectLayout_inFillingDataTwo() {
        fillingDataTwo()
        repeat(2) { buttonNavigateUp() }
        navController.assertCurrentRouteName(ReportsForDriversSchema.SelectLayout.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToMainMenu_inFillingDataTwo() {
        fillingDataTwo()
        repeat(3) { buttonNavigateUp() }
        navController.assertCurrentRouteName(ReportsForDriversSchema.Start.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToFillingDataTwo_inProgressReport() {
        progressReport()
        buttonNavigateUp()
        navController.assertCurrentRouteName(ReportsForDriversSchema.FillingDataTwo.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToFillingDataOne_inProgressReport() {
        progressReport()
        repeat(2) { buttonNavigateUp() }
        navController.assertCurrentRouteName(ReportsForDriversSchema.FillingDataOne.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToSelectLayout_inProgressReport() {
        progressReport()
        repeat(3) { buttonNavigateUp() }
        navController.assertCurrentRouteName(ReportsForDriversSchema.SelectLayout.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToMainMenu_inProgressReport() {
        progressReport()
        repeat(4) { buttonNavigateUp() }
        navController.assertCurrentRouteName(ReportsForDriversSchema.Start.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToProgressReport_inPreview() {
        preview()
        buttonNavigateUp()
        navController.assertCurrentRouteName(ReportsForDriversSchema.ProgressReport.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToFillingDataTwo_inPreview() {
        preview()
        repeat(2) { buttonNavigateUp() }
        navController.assertCurrentRouteName(ReportsForDriversSchema.FillingDataTwo.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToFillingDataOne_inPreview() {
        preview()
        repeat(3) { buttonNavigateUp() }
        navController.assertCurrentRouteName(ReportsForDriversSchema.FillingDataOne.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToSelectLayout_inPreview() {
        preview()
        repeat(4) { buttonNavigateUp() }
        navController.assertCurrentRouteName(ReportsForDriversSchema.SelectLayout.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToMainMenu_inPreview() {
        preview()
        repeat(5) { buttonNavigateUp() }
        navController.assertCurrentRouteName(ReportsForDriversSchema.Start.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToPreview_inResult() {
        result()
        buttonNavigateUp()
        navController.assertCurrentRouteName(ReportsForDriversSchema.Preview.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToProgressReport_inResult() {
        result()
        repeat(2) { buttonNavigateUp() }
        navController.assertCurrentRouteName(ReportsForDriversSchema.ProgressReport.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToFillingDataTwo_inResult() {
        result()
        repeat(3) { buttonNavigateUp() }
        navController.assertCurrentRouteName(ReportsForDriversSchema.FillingDataTwo.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToFillingDataOne_inResult() {
        result()
        repeat(4) { buttonNavigateUp() }
        navController.assertCurrentRouteName(ReportsForDriversSchema.FillingDataOne.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToSelectLayout_inResult() {
        result()
        repeat(5) { buttonNavigateUp() }
        navController.assertCurrentRouteName(ReportsForDriversSchema.SelectLayout.name)
    }

    @Test
    fun reportsForDriversNavHost_moveToMainMenu_inResult() {
        result()
        repeat(6) { buttonNavigateUp() }
        navController.assertCurrentRouteName(ReportsForDriversSchema.Start.name)
    }



    private fun selectLayout() {
        composeTestRule.onNodeWithStringId(R.string.create_report).performClick()
    }

    private fun fillingDataOne() {
        selectLayout()
        composeTestRule.onNodeWithStringId(R.string.next).performClick()
    }

    private fun fillingDataTwo() {
        fillingDataOne()
        composeTestRule.onNodeWithStringId(R.string.next).performClick()
    }

    private fun progressReport() {
        fillingDataTwo()
        composeTestRule.onNodeWithStringId(R.string.next).performClick()
    }

    private fun preview() {
        progressReport()
        composeTestRule.onNodeWithStringId(R.string.next).performClick()
    }

    private fun result() {
        preview()
        composeTestRule.onNodeWithStringId(R.string.next).performClick()
    }

    private fun buttonNavigateUp() {
        val backButton = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backButton).performClick()
    }
}