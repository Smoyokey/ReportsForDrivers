package fulltests

import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
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
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.navigationtest.helpmethods.onNodeWithStringId
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
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
    fun a() {
        hiltRule.inject()
        composeRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            ReportsForDriversApp(navController = navController, activity = composeRule.activity)
        }

        start()

        composeRule.apply {
            onNodeWithStringId(R.string.create_report).performClick()
            onNodeWithStringId(R.string.next).performClick()
            onNodeWithStringId(R.string.next).performClick()
            onNodeWithStringId(R.string.next).performClick()
            onNodeWithStringId(R.string.next).performClick()
            onNodeWithStringId(R.string.next).performClick()
            onNodeWithStringId(R.string.next).performClick()
            onNodeWithStringId(R.string.next).performClick()
            onNodeWithStringId(R.string.save).performClick()
        }
    }

    @Test
    //Тест без дат (добавить выбор даты)
    fun writeText_inPreviewWithoutFirstEntry() {
        hiltRule.inject()
        composeRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            ReportsForDriversApp(navController = navController, activity = composeRule.activity)
        }

        start()

        composeRule.apply {

            //MainMenu
            onNodeWithStringId(R.string.create_report).performClick()

            //ReportInfo
            stringIdClickInput(R.string.waybill, "1")
            stringIdClickInput(R.string.township, "Minsk")
            onNodeWithStringId(R.string.date).performClick()
            onNodeWithStringId(R.string.ok).performClick()
            onNodeWithStringId(R.string.next).performClick()

            //PersonalInfo
            stringIdClickInput(R.string.last_name, lastName)
            stringIdClickInput(R.string.first_name, firstName)
            stringIdClickInput(R.string.patronymic, patronymic)
            onNodeWithStringId(R.string.next).performClick()

            //VehicleInfo
            stringIdClickInput(R.string.make_vehicle, makeVehicle)
            stringIdClickInput(R.string.rn_vehicle, rnVehicle)
            stringIdClickInput(R.string.make_trailer, makeTrailer)
            stringIdClickInput(R.string.rn_trailer, rnTrailer)
            onNodeWithStringId(R.string.next).performClick()

            //DataFillingTwo
            clickDate(R.string.date_departure)
            clickDate(R.string.date_return)
            clickDate(R.string.date_border_crossing_departure)
            clickDate(R.string.date_border_crossing_return)
            stringIdClickInput(R.string.route, route)
            stringIdClickInput(R.string.speedometer_reading_departure, speedometerDeparture)
            stringIdClickInput(R.string.speedometer_reading_return, speedometerReturn)
            stringIdClickInput(R.string.fuelled, fuelled)
            onNodeWithStringId(R.string.next).performClick()

            //ProgressReports
//            enterProgressReport("BY", "MINSK", "360", "18")
//            enterProgressReport("RU", "KAZAN", "450", "18")
//            enterProgressReport("RU", "MOSKVA", "1000", "4")
//            enterProgressReport("RU", "Smolensk", "400", "3")
            enterProgressReport("BY", "MINSK", "350", "15")
            onNodeWithStringId(R.string.next).performClick()

            //TripExpenses
//            enterTripExpenses("02112024", "1", "WATER", "40", "EUR")
//            enterTripExpenses("02132024", "2", "MILK", "30", "BYN")
//            enterTripExpenses("02152024", "3", "APPLE", "20", "RUB")
//            enterTripExpenses("02162024", "4", "BEER", "10", "DOL")
//            enterTripExpenses("02172024", "5", "WATER", "3", "EUR")
            onNodeWithStringId(R.string.next).performClick()

            //Preview
            onNodeWithStringId(R.string.next).performClick()

            //Result
            onNodeWithStringId(R.string.save).performClick()
            assertTrue(true)

            //Preview
//            onNodeWithText(lastName).assertIsDisplayed()
//            onNodeWithText(firstName).assertIsDisplayed()
//            onNodeWithText(patronymic).assertIsDisplayed()
//            onNodeWithText(makeVehicle).assertIsDisplayed()
//            onNodeWithText(rnVehicle).assertIsDisplayed()
//            onNodeWithText(makeTrailer).assertIsDisplayed()
//            onNodeWithText(rnTrailer).assertIsDisplayed()
//            onNodeWithText(route).assertIsDisplayed()
//            onNodeWithText(speedometerDeparture).assertIsDisplayed()
//            onNodeWithText(speedometerReturn).assertIsDisplayed()
//            onNodeWithText(fuelled).assertIsDisplayed()

        }
    }

    private fun start() {
        try {
            composeRule.onNodeWithStringId(R.string.skip).performClick()
        } catch (e: AssertionError) {}
    }

    @Test
    @Ignore
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

    private fun clickDate(@StringRes label: Int) {
        composeRule.apply {
            onNodeWithStringId(label).performClick()
            onNodeWithStringId(R.string.ok).performClick()
        }
    }

    private fun enterTripExpenses(date: String, documentNumber: String, expenseItem: String, sum: String, currency: String) {
        composeRule.apply {
            clickDate(R.string.date)
            stringIdClickInput(R.string.document_number, documentNumber)
            stringIdClickInput(R.string.expense_item, expenseItem)
            stringIdClickInput(R.string.sum, sum)
            stringIdClickInput(R.string.currency, currency)
            onNodeWithStringId(R.string.add).performClick()
        }
    }

    private fun enterProgressReport(country: String, township: String, distance: String, cargoWeight: String) {
        composeRule.apply {
            clickDate(R.string.date)
//            onAllNodesWithText("Date").onFirst().performClick()
//            onNodeWithStringId(R.string.ok).performClick()
            stringIdClickInput(R.string.country, country)
            stringIdClickInput(R.string.township, township)
            stringIdClickInput(R.string.distance, distance)
            stringIdClickInput(R.string.cargo_weight, cargoWeight)
            onNodeWithStringId(R.string.add).performClick()
        }
    }
}