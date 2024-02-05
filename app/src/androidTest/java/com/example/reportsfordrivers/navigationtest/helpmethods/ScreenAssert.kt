package com.example.reportsfordrivers.navigationtest.helpmethods

import androidx.navigation.NavController
import org.junit.Assert

fun NavController.assertCurrentRouteName(routeName: String) {
    Assert.assertEquals(routeName, currentBackStackEntry?.destination?.route)
}