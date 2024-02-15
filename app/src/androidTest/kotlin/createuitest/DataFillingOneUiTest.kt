package createuitest

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.reportsfordrivers.MainActivity
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.navigationtest.helpmethods.onNodeWithStringId
import com.example.reportsfordrivers.ui.layouts.createreports.CreateReportsDataFillingOneScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class DataFillingOneUiTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule(MainActivity::class.java)

    private var lastName = "Ivanov"
    private var firstName = "Ivan"
    private var patronymic = "Ivanovich"

    private var makeVehicle = "DAF"
    private var makeTrailer = "Schmitz"
    private var rnVehicle = "1234TT-5"
    private var rnTrailer = "5678XX-7"

    @Before
    fun init() {
        hiltRule.inject()
        composeRule.activity.setContent {
            CreateReportsDataFillingOneScreen({})
        }
    }

    @Test
    fun writeText_inTextFieldLastName() {
        actionWriteLastName(lastName)
        composeRule.onNodeWithText(lastName).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldFirstName() {
        actionWriteFirstName(firstName)
        composeRule.onNodeWithText(firstName).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldPatronymic() {
        actionWritePatronymic(patronymic)
        composeRule.onNodeWithText(patronymic).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldLastNameFirstNamePatronymic() {
        actionWriteLastName(lastName)
        actionWriteFirstName(firstName)
        actionWritePatronymic(patronymic)
        composeRule.apply {
            onNodeWithText(lastName).assertIsDisplayed()
            onNodeWithText(firstName).assertIsDisplayed()
            onNodeWithText(patronymic).assertIsDisplayed()
        }
    }

    @Test
    fun writeText_inTextFieldMakeVehicle() {
        actionWriteMakeVehicle(makeVehicle)
        composeRule.onNodeWithText(makeVehicle).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldRnVehicle() {
        actionWriteRnVehicle(rnVehicle)
        composeRule.onNodeWithText(rnVehicle).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldMakeTrailer() {
        actionWriteMakeTrailer(makeTrailer)
        composeRule.onNodeWithText(makeTrailer).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldRnTrailer() {
        actionWriteRnTrailer(rnTrailer)
        composeRule.onNodeWithText(rnTrailer).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldsMakeRnVehicleMakeRnTrailer() {
        actionWriteMakeVehicle(makeVehicle)
        actionWriteRnVehicle(rnVehicle)
        actionWriteMakeTrailer(makeTrailer)
        actionWriteRnTrailer(rnTrailer)
        composeRule.apply {
            onNodeWithText(makeVehicle).assertIsDisplayed()
            onNodeWithText(rnVehicle).assertIsDisplayed()
            onNodeWithText(makeTrailer).assertIsDisplayed()
            onNodeWithText(rnTrailer).assertIsDisplayed()
        }
    }

    @Test
    fun isNotDisplayedButtonClear_inLastName() {
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_LAST_NAME).assertDoesNotExist()
    }

    @Test
    fun isNotDisplayedButtonClear_inFirstName() {
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_FIRST_NAME).assertDoesNotExist()
    }

    @Test
    fun isNotDisplayedButtonClear_inPatronymic() {
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_PATRONYMIC).assertDoesNotExist()
    }

    @Test
    fun isNotDisplayedButtonClear_inMakeVehicle() {
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_MAKE_VEHICLE).assertDoesNotExist()
    }

    @Test
    fun isNotDisplayedButtonClear_inRnVehicle() {
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_RN_VEHICLE).assertDoesNotExist()
    }

    @Test
    fun isNotDisplayedButtonClear_inMakeTrailer() {
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_MAKE_TRAILER).assertDoesNotExist()
    }

    @Test
    fun isNotDisplayedButtonClear_inRnTrailer() {
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_RN_TRAILER).assertDoesNotExist()
    }

    @Test
    fun isDisplayedButtonClear_inLastName() {
        actionWriteLastName(lastName)
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_LAST_NAME).assertIsDisplayed()
    }

    @Test
    fun isDisplayedButtonClear_inFirstName() {
        actionWriteFirstName(firstName)
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_FIRST_NAME).assertIsDisplayed()
    }

    @Test
    fun isDisplayedButtonClear_inPatronymic() {
        actionWritePatronymic(patronymic)
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_PATRONYMIC).assertIsDisplayed()
    }

    @Test
    fun isDisplayedButtonClear_inLastNameFirstNamePatronymic() {
        actionWriteLastName(lastName)
        actionWriteFirstName(firstName)
        actionWritePatronymic(patronymic)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_LAST_NAME).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_FIRST_NAME).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_PATRONYMIC).assertIsDisplayed()
        }
    }

    @Test
    fun isDisplayedButtonClear_inMakeVehicle() {
        actionWriteMakeVehicle(makeVehicle)
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_MAKE_VEHICLE).assertIsDisplayed()
    }

    @Test
    fun isDisplayedButtonClear_inRnVehicle() {
        actionWriteRnVehicle(rnVehicle)
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_RN_VEHICLE).assertIsDisplayed()
    }

    @Test
    fun isDisplayedButtonClear_inMakeTrailer() {
        actionWriteMakeTrailer(makeTrailer)
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_MAKE_TRAILER).assertIsDisplayed()
    }

    @Test
    fun isDisplayedButtonClear_inRnTrailer() {
        actionWriteRnTrailer(rnTrailer)
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_RN_TRAILER).assertIsDisplayed()
    }

    @Test
    fun isDisplayedButtonClear_inMakeRnVehicleMakeRnTrailer() {
        actionWriteMakeVehicle(makeVehicle)
        actionWriteRnVehicle(rnVehicle)
        actionWriteMakeTrailer(makeTrailer)
        actionWriteRnTrailer(rnTrailer)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_MAKE_VEHICLE).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_RN_VEHICLE).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_MAKE_TRAILER).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_RN_TRAILER).assertIsDisplayed()
        }
    }

    @Test
    fun isDisplayedButtonClear_inLastNameFirstNamePatronymicMakeRnVehicleMakeRnTrailer() {
        actionWriteLastName(lastName)
        actionWriteFirstName(firstName)
        actionWritePatronymic(patronymic)
        actionWriteMakeVehicle(makeVehicle)
        actionWriteRnVehicle(rnVehicle)
        actionWriteMakeTrailer(makeTrailer)
        actionWriteRnTrailer(rnTrailer)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_LAST_NAME).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_FIRST_NAME).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_PATRONYMIC).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_MAKE_VEHICLE).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_RN_VEHICLE).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_MAKE_TRAILER).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_RN_TRAILER).assertIsDisplayed()
        }
    }

    @Test
    fun isClickButton_inLastName() {
        actionWriteLastName(lastName)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_LAST_NAME).performClick()
            onNodeWithText(lastName).assertDoesNotExist()
        }
    }

    @Test
    fun isClickButton_inFirstName() {
        actionWriteFirstName(firstName)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_FIRST_NAME).performClick()
            onNodeWithText(firstName).assertDoesNotExist()
        }
    }

    @Test
    fun isClickButton_inPatronymic() {
        actionWritePatronymic(patronymic)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_PATRONYMIC).performClick()
            onNodeWithText(patronymic).assertDoesNotExist()
        }
    }

    @Test
    fun isClickButton_inLastNameFirstNamePatronymic() {
        actionWriteLastName(lastName)
        actionWriteFirstName(firstName)
        actionWritePatronymic(patronymic)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_LAST_NAME).performClick()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_FIRST_NAME).performClick()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_PATRONYMIC).performClick()
            onNodeWithText(lastName).assertDoesNotExist()
            onNodeWithText(firstName).assertDoesNotExist()
            onNodeWithText(patronymic).assertDoesNotExist()
        }
    }

    @Test
    fun isClickButton_inMakeVehicle() {
        actionWriteMakeVehicle(makeVehicle)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_MAKE_VEHICLE).performClick()
            onNodeWithText(makeVehicle).assertDoesNotExist()
        }
    }

    @Test
    fun isClickButton_inRnVehicle() {
        actionWriteRnVehicle(rnVehicle)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_RN_VEHICLE).performClick()
            onNodeWithText(rnVehicle).assertDoesNotExist()
        }
    }

    @Test
    fun isClickButton_inMakeTrailer() {
        actionWriteMakeTrailer(makeTrailer)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_MAKE_TRAILER).performClick()
            onNodeWithText(makeTrailer).assertDoesNotExist()
        }
    }

    @Test
    fun isClickButton_inRnTrailer() {
        actionWriteRnTrailer(rnTrailer)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_RN_TRAILER).performClick()
            onNodeWithText(rnTrailer).assertDoesNotExist()
        }
    }

    @Test
    fun isClickButton_inMakeRnVehicleMakeRnTrailer() {
        actionWriteMakeVehicle(makeVehicle)
        actionWriteRnVehicle(rnVehicle)
        actionWriteMakeTrailer(makeTrailer)
        actionWriteRnTrailer(rnTrailer)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_MAKE_VEHICLE).performClick()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_RN_VEHICLE).performClick()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_MAKE_TRAILER).performClick()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_RN_TRAILER).performClick()
            onNodeWithText(makeVehicle).assertDoesNotExist()
            onNodeWithText(rnVehicle).assertDoesNotExist()
            onNodeWithText(makeTrailer).assertDoesNotExist()
            onNodeWithText(rnTrailer).assertDoesNotExist()
        }
    }

    @Test
    fun isClickButton_inLastNameFirstNamePatronymicMakeRnVehicleMakeRnTrailer() {
        actionWriteLastName(lastName)
        actionWriteFirstName(firstName)
        actionWritePatronymic(patronymic)
        actionWriteMakeVehicle(makeVehicle)
        actionWriteRnVehicle(rnVehicle)
        actionWriteMakeTrailer(makeTrailer)
        actionWriteRnTrailer(rnTrailer)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_LAST_NAME).performClick()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_FIRST_NAME).performClick()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_PATRONYMIC).performClick()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_MAKE_VEHICLE).performClick()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_RN_VEHICLE).performClick()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_MAKE_TRAILER).performClick()
            onNodeWithTag(Tags.TAG_TEST_DATA_FILLING_ONE_RN_TRAILER).performClick()
            onNodeWithText(lastName).assertDoesNotExist()
            onNodeWithText(firstName).assertDoesNotExist()
            onNodeWithText(patronymic).assertDoesNotExist()
            onNodeWithText(makeVehicle).assertDoesNotExist()
            onNodeWithText(rnVehicle).assertDoesNotExist()
            onNodeWithText(makeTrailer).assertDoesNotExist()
            onNodeWithText(rnTrailer).assertDoesNotExist()
        }
    }

    private fun actionWriteLastName(lastName: String) {
        composeRule.onNodeWithStringId(R.string.last_name)
            .performClick()
            .performTextInput(lastName)
    }

    private fun actionWriteFirstName(firstName: String) {
        composeRule.onNodeWithStringId(R.string.first_name)
            .performClick()
            .performTextInput(firstName)
    }

    private fun actionWritePatronymic(patronymic: String) {
        composeRule.onNodeWithStringId(R.string.patronymic)
            .performClick()
            .performTextInput(patronymic)
    }

    private fun actionWriteMakeVehicle(makeVehicle: String) {
        composeRule.onNodeWithStringId(R.string.make_vehicle)
            .performClick()
            .performTextInput(makeVehicle)
    }

    private fun actionWriteMakeTrailer(makeTrailer: String) {
        composeRule.onNodeWithStringId(R.string.make_trailer)
            .performClick()
            .performTextInput(makeTrailer)
    }

    private fun actionWriteRnVehicle(rnVehicle: String) {
        composeRule.onNodeWithStringId(R.string.rn_vehicle)
            .performClick()
            .performTextInput(rnVehicle)
    }

    private fun actionWriteRnTrailer(rnTrailer: String) {
        composeRule.onNodeWithStringId(R.string.rn_trailer)
            .performClick()
            .performTextInput(rnTrailer)
    }
}