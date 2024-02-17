package fulltests

import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.reportsfordrivers.MainActivity
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.ReportsForDriversApp
import com.example.reportsfordrivers.navigationtest.helpmethods.onNodeWithStringId
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class PreviewTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule(MainActivity::class.java)

    private var lastName = "Ivanov"
    private var firstName = "Ivan"
    private var patronymic = "Ivanovich"
    private var makeVehicle = "DAF"
    private var rnVehicle = "1234 IT-7"
    private var makeTrailer = "Schmitz"
    private var rnTrailer = "5678 II-1"

    private var route = "Minsk - Kazan"
    private var speedometerDeparture = "1 360 000"
    private var speedometerReturn = "1 365 000"
    private var fuelled = "500"

    private lateinit var navController: TestNavHostController

    @Test
    //Тест без дат (добавить выбор даты)
    fun writeText_inPreviewWithoutFirstEntry() {
        hiltRule.inject()
        composeRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            ReportsForDriversApp(navController = navController)
        }

        start()

        composeRule.apply {

            //MainMenu
            onNodeWithStringId(R.string.create_report).performClick()

            //Select maket
            onNodeWithStringId(R.string.next).performClick()

            //DataFillingOne
            stringIdClickInput(R.string.last_name, lastName)
            stringIdClickInput(R.string.first_name, firstName)
            stringIdClickInput(R.string.patronymic, patronymic)
            stringIdClickInput(R.string.make_vehicle, makeVehicle)
            stringIdClickInput(R.string.rn_vehicle, rnVehicle)
            stringIdClickInput(R.string.make_trailer, makeTrailer)
            stringIdClickInput(R.string.rn_trailer, rnTrailer)
            onNodeWithStringId(R.string.next).performClick()

            //DataFillingTwo
            stringIdClickInput(R.string.route, route)
            stringIdClickInput(R.string.speedometer_reading_departure, speedometerDeparture)
            stringIdClickInput(R.string.speedometer_reading_return, speedometerReturn)
            stringIdClickInput(R.string.fuelled, fuelled)
            onNodeWithStringId(R.string.next).performClick()

            //ProgressReports
            onNodeWithStringId(R.string.next).performClick()

            //Preview
            onNodeWithText(lastName).assertIsDisplayed()
            onNodeWithText(firstName).assertIsDisplayed()
            onNodeWithText(patronymic).assertIsDisplayed()
            onNodeWithText(makeVehicle).assertIsDisplayed()
            onNodeWithText(rnVehicle).assertIsDisplayed()
            onNodeWithText(makeTrailer).assertIsDisplayed()
            onNodeWithText(rnTrailer).assertIsDisplayed()
            onNodeWithText(route).assertIsDisplayed()
            onNodeWithText(speedometerDeparture).assertIsDisplayed()
            onNodeWithText(speedometerReturn).assertIsDisplayed()
            onNodeWithText(fuelled).assertIsDisplayed()
        }
    }

    private fun start() {
        try {
            composeRule.onNodeWithStringId(R.string.skip).performClick()
        } catch (e: AssertionError) {}
    }

    @Test
    fun writeText_inPreviewWithFirstEntry() {
        composeRule.apply {
            //FirstEntry
            stringIdClickInput(R.string.last_name, lastName)
            stringIdClickInput(R.string.first_name, firstName)
            stringIdClickInput(R.string.patronymic, patronymic)
            stringIdClickInput(R.string.make_vehicle, makeVehicle)
            stringIdClickInput(R.string.rn_vehicle, rnVehicle)
            onNodeWithStringId(R.string.add).performClick()
            onNodeWithContentDescription(R.string.trailer.toString()).performClick()
            stringIdClickInput(R.string.make_trailer, makeTrailer)
            stringIdClickInput(R.string.rn_trailer, rnTrailer)
            onNodeWithStringId(R.string.save).performClick()

            //MainMenu
            onNodeWithStringId(R.string.create_report).performClick()

            //Select maket
            onNodeWithStringId(R.string.next).performClick()

            //DataFillingOne

        }
    }

    private fun stringIdClickInput(@StringRes id: Int, textInput: String) {
        composeRule.onNodeWithStringId(id).performClick().performTextInput(textInput)
    }
}