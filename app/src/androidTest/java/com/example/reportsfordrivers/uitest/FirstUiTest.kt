package com.example.reportsfordrivers.uitest

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reportsfordrivers.ui.layouts.firstentry.FirstEntryScreen
import com.example.reportsfordrivers.ui.theme.ReportsForDriversTheme
import com.example.reportsfordrivers.viewmodel.AppViewModelProvider
import org.junit.Rule
import org.junit.Test

class FirstUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test() {
        composeTestRule.setContent {
            ReportsForDriversTheme {
                FirstEntryScreen (viewModel = viewModel(factory = AppViewModelProvider.Factory), {})
            }
        }
        composeTestRule.onNodeWithText("Add")
    }
}