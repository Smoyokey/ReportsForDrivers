package com.example.reportsfordrivers.navigationtest.helpmethods

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule

fun <A: ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithStringId(
    @StringRes id: Int, ignoreCase: Boolean = false
): SemanticsNodeInteraction = onNodeWithText(activity.getString(id), ignoreCase = ignoreCase)