package createuitest

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.reportsfordrivers.MainActivity
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.navigationtest.helpmethods.onNodeWithStringId
import com.example.reportsfordrivers.ui.layouts.createreports.CreateReportsDataFillingTwoScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class DataFillingTwoUiTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule(MainActivity::class.java)

    private var route = "Minsk - Kazan"

    private var speedometerDeparture = "1 360 000"
    private var speedometerReturn = "1 365 000"
    private var fuelled = "500"

    @Before
    fun init() {
        hiltRule.inject()
        composeRule.activity.setContent {
            CreateReportsDataFillingTwoScreen ({})
        }
    }

    @Test
    fun writeText_inTextFieldRoute() {
        actionWriteRoute(route)
        composeRule.onNodeWithText(route).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldSpeedometerDeparture() {
        actionWriteSpeedometerDeparture(speedometerDeparture)
        composeRule.onNodeWithText(speedometerDeparture).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldSpeedometerReturn() {
        actionWriteSpeedometerReturn(speedometerReturn)
        composeRule.onNodeWithText(speedometerReturn).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldFuelled() {
        actionWriteFuelled(fuelled)
        composeRule.onNodeWithText(fuelled).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldRouteSpeedometerDepartureReturnAndFuelled() {
        actionWriteRoute(route)
        actionWriteSpeedometerDeparture(speedometerDeparture)
        actionWriteSpeedometerReturn(speedometerReturn)
        actionWriteFuelled(fuelled)
        composeRule.apply {
            onNodeWithText(route).assertIsDisplayed()
            onNodeWithText(speedometerDeparture).assertIsDisplayed()
            onNodeWithText(speedometerReturn).assertIsDisplayed()
            onNodeWithText(fuelled).assertIsDisplayed()
        }
    }

    @Test
    fun isDisplayedButtonClear_inRoute() {
        actionWriteRoute(route)
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_TWO_ROUTE).assertIsDisplayed()
    }

    @Test
    fun isDisplayedButtonClear_inSpeedometerDeparture() {
        actionWriteSpeedometerDeparture(speedometerDeparture)
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_TWO_SPEEDOMETER_DEPARTURE)
            .assertIsDisplayed()
    }

    @Test
    fun isDisplayedButtonClear_inSpeedometerReturn() {
        actionWriteSpeedometerReturn(speedometerReturn)
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_TWO_SPEEDOMETER_RETURN)
            .assertIsDisplayed()
    }

    @Test
    fun isDisplayedButtonClear_inFuelled() {
        actionWriteFuelled(fuelled)
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_TWO_FUELLED).assertIsDisplayed()
    }

    @Test
    fun isDisplayedButtonClear_inRouteSpeedometerDepartureReturnAndFuelled() {
        actionWriteRoute(route)
        actionWriteSpeedometerDeparture(speedometerDeparture)
        actionWriteSpeedometerReturn(speedometerReturn)
        actionWriteFuelled(fuelled)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_TWO_ROUTE).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_TWO_SPEEDOMETER_DEPARTURE).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_TWO_SPEEDOMETER_RETURN).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_TWO_FUELLED).assertIsDisplayed()
        }
    }

    @Test
    fun isButtonClickClear_inRoute() {
        actionWriteRoute(route)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_TWO_ROUTE).performClick()
            onNodeWithText(route).assertDoesNotExist()
        }
    }

    @Test
    fun isButtonClickClear_inSpeedometerDeparture() {
        actionWriteSpeedometerDeparture(speedometerDeparture)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_TWO_SPEEDOMETER_DEPARTURE).performClick()
            onNodeWithText(speedometerDeparture).assertDoesNotExist()
        }
    }

    @Test
    fun isButtonClickClear_inSpeedometerReturn() {
        actionWriteSpeedometerReturn(speedometerReturn)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_TWO_SPEEDOMETER_RETURN).performClick()
            onNodeWithText(speedometerReturn).assertDoesNotExist()
        }
    }

    @Test
    fun isButtonClickClear_inFuelled() {
        actionWriteFuelled(fuelled)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_TWO_FUELLED).performClick()
            onNodeWithText(fuelled).assertDoesNotExist()
        }
    }

    @Test
    fun isButtonClickClear_inRouteSpeedometerDepartureReturnAndFuelled() {
        actionWriteRoute(route)
        actionWriteFuelled(fuelled)
        actionWriteSpeedometerDeparture(speedometerDeparture)
        actionWriteSpeedometerReturn(speedometerReturn)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_TWO_ROUTE).performClick()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_TWO_SPEEDOMETER_DEPARTURE).performClick()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_TWO_SPEEDOMETER_RETURN).performClick()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_TWO_FUELLED).performClick()
            onNodeWithText(route).assertDoesNotExist()
            onNodeWithText(speedometerDeparture).assertDoesNotExist()
            onNodeWithText(speedometerReturn).assertDoesNotExist()
            onNodeWithText(fuelled).assertDoesNotExist()
        }
    }

    @Test
    fun isNotEnabled_inButtonNext() {
        composeRule.onNodeWithStringId(R.string.next).assertIsNotEnabled()
    }

    @Test
    fun isEnable_inButtonNext_writeFullTextFields() {
        actionWriteRoute(route)
        actionWriteFuelled(fuelled)
        actionWriteSpeedometerDeparture(speedometerDeparture)
        actionWriteSpeedometerReturn(speedometerReturn)
        composeRule.onNodeWithStringId(R.string.next).assertIsEnabled()
    }

    @Test
    fun isNotEnabled_inButtonNext_writeAndDeleteTextFields() {
        actionWriteRoute(route)
        actionWriteFuelled(fuelled)
        actionWriteSpeedometerDeparture(speedometerDeparture)
        actionWriteSpeedometerReturn(speedometerReturn)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_TWO_FUELLED).performClick()
            onNodeWithStringId(R.string.next).assertIsNotEnabled()
        }
    }

    private fun actionWriteRoute(route: String) {
        composeRule.onNodeWithStringId(R.string.route)
            .performClick()
            .performTextInput(route)
    }

    private fun actionWriteSpeedometerDeparture(speedometerDeparture: String) {
        composeRule.onNodeWithStringId(R.string.speedometer_reading_departure)
            .performClick()
            .performTextInput(speedometerDeparture)
    }

    private fun actionWriteSpeedometerReturn(speedometerReturn: String) {
        composeRule.onNodeWithStringId(R.string.speedometer_reading_return)
            .performClick()
            .performTextInput(speedometerReturn)
    }

    private fun actionWriteFuelled(fuelled: String) {
        composeRule.onNodeWithStringId(R.string.fuelled)
            .performClick()
            .performTextInput(fuelled)
    }
}