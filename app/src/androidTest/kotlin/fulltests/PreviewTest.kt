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
            onNodeWithStringId(R.string.current_date).performClick()
            onNodeWithText("Date").performClick().performTextInput("02282024")
            onNodeWithText("Ok").performClick()
            stringIdClickInput(R.string.last_name, lastName)
            stringIdClickInput(R.string.first_name, firstName)
            stringIdClickInput(R.string.patronymic, patronymic)
            stringIdClickInput(R.string.make_vehicle, makeVehicle)
            stringIdClickInput(R.string.rn_vehicle, rnVehicle)
            stringIdClickInput(R.string.make_trailer, makeTrailer)
            stringIdClickInput(R.string.rn_trailer, rnTrailer)
            onNodeWithStringId(R.string.next).performClick()

            //DataFillingTwo
            clickDate(Tags.TAG_TEST_DATE_DEPARTURE, "02102024")
            clickDate(Tags.TAG_TEST_DATE_RETURN, "02282024")
            clickDate(Tags.TAG_TEST_DATE_CROSSING_DEPARTURE, "02102024")
            clickDate(Tags.TAG_TEST_DATE_CROSSING_RETURN, "02282024")
            stringIdClickInput(R.string.route, route)
            stringIdClickInput(R.string.speedometer_reading_departure, speedometerDeparture)
            stringIdClickInput(R.string.speedometer_reading_return, speedometerReturn)
            stringIdClickInput(R.string.fuelled, fuelled)
            onNodeWithStringId(R.string.next).performClick()

            //ProgressReports
            enterProgressReport("02112024", "BY", "MINSK", "360", "18")
            enterProgressReport("02132024", "RU", "KAZAN", "450", "18")
            enterProgressReport("02152024", "RU", "MOSKVA", "1000", "4")
            enterProgressReport("02172024", "RU", "Smolensk", "400", "3")
            enterProgressReport("02192024", "BY", "MINSK", "350", "15")
            onNodeWithStringId(R.string.next).performClick()

            //TripExpenses
//            enterTripExpenses("02112024", "1", "WATER", "40", "EUR")
//            enterTripExpenses("02132024", "2", "MILK", "30", "BYN")
//            enterTripExpenses("02152024", "3", "APPLE", "20", "RUB")
//            enterTripExpenses("02162024", "4", "BEER", "10", "DOL")
//            enterTripExpenses("02172024", "5", "WATER", "3", "EUR")
            onNodeWithStringId(R.string.next).performClick()
            onNodeWithStringId(R.string.next).performClick()
            onNodeWithStringId(R.string.test).performClick()
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

    private fun clickDate(tag: String, number: String) {
        composeRule.apply {
            onNodeWithTag(tag).performClick()
            onNodeWithText("Date").performClick().performTextInput(number)
            onNodeWithText("Ok").performClick()
        }
    }

    private fun enterTripExpenses(date: String, documentNumber: String, expenseItem: String, sum: String, currency: String) {
        composeRule.apply {
            clickDate(Tags.TAG_TEST_TRIP_EXPENSES_DATE, date)
            stringIdClickInput(R.string.document_number, documentNumber)
            stringIdClickInput(R.string.expense_item, expenseItem)
            stringIdClickInput(R.string.sum, sum)
            stringIdClickInput(R.string.currency, currency)
            onNodeWithStringId(R.string.add).performClick()
        }
    }

    private fun enterProgressReport(date: String, country: String, township: String, distance: String, cargoWeight: String) {
        composeRule.apply {
            clickDate(Tags.TAG_TEST_PROGRESS_REPORTS_DATE, date)
            stringIdClickInput(R.string.country, country)
            stringIdClickInput(R.string.township, township)
            stringIdClickInput(R.string.distance, distance)
            stringIdClickInput(R.string.cargo_weight, cargoWeight)
            onNodeWithStringId(R.string.add).performClick()
        }
    }
}