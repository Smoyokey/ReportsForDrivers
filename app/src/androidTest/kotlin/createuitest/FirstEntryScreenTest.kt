package createuitest

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import com.example.reportsfordrivers.MainActivity
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.navigationtest.helpmethods.onNodeWithStringId
import com.example.reportsfordrivers.ui.layouts.firstentry.FirstEntryScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class FirstEntryScreenTest {

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
            FirstEntryScreen()
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
    fun writeText_inTextFieldsLastAndFirstName() {
        actionWriteLastName(lastName)
        actionWriteFirstName(firstName)
        composeRule.apply {
            onNodeWithText(lastName).assertIsDisplayed()
            onNodeWithText(firstName).assertIsDisplayed()
        }
    }

    @Test
    fun writeText_inTextFieldsLastNameAndPatronymic() {
        actionWriteLastName(lastName)
        actionWritePatronymic(patronymic)
        composeRule.apply {
            onNodeWithText(lastName)
            onNodeWithText(patronymic)
        }
    }

    @Test
    fun writeText_inTextFieldsFirstNameAndPatronymic() {
        actionWriteFirstName(firstName)
        actionWritePatronymic(patronymic)
        composeRule.apply {
            onNodeWithText(firstName)
            onNodeWithText(patronymic)
        }
    }

    @Test
    fun writeText_inTextFieldsFio() {
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
    fun selectedPosition_inRadioButton_isTrailer() {
        composeRule.onNodeWithContentDescription(R.string.trailer.toString()).performClick()
        composeRule.onNodeWithContentDescription(R.string.trailer.toString()).assertIsSelected()
    }

    @Test
    fun writeText_inTextFieldsMakeVehicle() {
        actionWriteMakeVehicle(makeVehicle)
        composeRule.apply {
            onNodeWithText(makeVehicle).assertIsDisplayed()
        }
    }

    @Test
    fun writeText_inTextFieldsRnVehicle() {
        actionWriteRnVehicle(rnVehicle)
        composeRule.apply {
            onNodeWithText(rnVehicle).assertIsDisplayed()
        }
    }

    @Test
    fun writeText_onTextFieldsMakeAndRnVehicle() {
        actionWriteMakeVehicle(makeVehicle)
        actionWriteRnVehicle(rnVehicle)
        composeRule.apply {
            onNodeWithText(makeVehicle).assertIsDisplayed()
            onNodeWithText(rnVehicle).assertIsDisplayed()
        }
    }

    @Test
    fun writeText_onTextFieldMakeTrailer() {
        composeRule.onNodeWithContentDescription(R.string.trailer.toString()).performClick()
        actionWriteMakeTrailer(makeTrailer)
        composeRule.apply {
            onNodeWithText(makeTrailer).assertIsDisplayed()
        }
    }

    @Test
    fun writeText_onTextFieldRnTrailer() {
        composeRule.onNodeWithContentDescription(R.string.trailer.toString()).performClick()
        actionWriteRnTrailer(rnTrailer)
        composeRule.apply {
            onNodeWithText(rnTrailer).assertIsDisplayed()
        }
    }

    @Test
    fun writeText_onTextFieldsMakeAndRnTrailer() {
        composeRule.onNodeWithContentDescription(R.string.trailer.toString()).performClick()
        actionWriteMakeTrailer(makeTrailer)
        actionWriteRnTrailer(rnTrailer)
        composeRule.apply {
            onNodeWithText(makeTrailer).assertIsDisplayed()
            onNodeWithText(rnTrailer).assertIsDisplayed()
        }
    }

    @Test
    fun writeOrSelectable_onTextFieldsMakeAndRnTrailer() {
        actionWriteMakeVehicle(makeTrailer)
        actionWriteRnVehicle(rnTrailer)
        composeRule.apply {
            onNodeWithContentDescription(R.string.trailer.toString()).performClick()
            onNodeWithText(makeTrailer).assertIsDisplayed()
            onNodeWithText(rnTrailer).assertIsDisplayed()
            onNodeWithContentDescription(R.string.trailer.toString()).assertIsSelected()
        }
    }

    @Test
    fun selectableText_onRadioButton_inTrailerToVehicle() {
        composeRule.apply {
            onNodeWithContentDescription(R.string.trailer.toString()).performClick()
            onNodeWithContentDescription(R.string.vehicle.toString()).performClick()
            onNodeWithContentDescription(R.string.vehicle.toString()).assertIsSelected()
        }
    }

    @Test
    fun addPositionVehicle_onButton() {
        actionWriteMakeVehicle(makeVehicle)
        actionWriteRnVehicle(rnVehicle)
        composeRule.apply {
            onNodeWithStringId(R.string.add).performClick()
            onNodeWithStringId(R.string.vehicle_test).assertIsDisplayed()
        }
    }

    @Test
    fun addPositionTrailer_onButton() {
        composeRule.onNodeWithContentDescription(R.string.trailer.toString()).performClick()
        actionWriteMakeTrailer(makeTrailer)
        actionWriteRnTrailer(rnTrailer)
        composeRule.apply {
            onNodeWithStringId(R.string.add).performClick()
            onNodeWithStringId(R.string.trailer_test).assertIsDisplayed()
        }
    }

    @Test
    fun addPositionTrailerVehicle_onButton() {
        actionWriteMakeVehicle(makeVehicle)
        actionWriteRnVehicle(rnVehicle)
        composeRule.onNodeWithStringId(R.string.add).performClick()
        actionWriteMakeTrailer(makeTrailer)
        actionWriteRnTrailer(rnTrailer)
        composeRule.apply {
            onNodeWithStringId(R.string.add).performClick()
            onNodeWithStringId(R.string.trailer_test).assertIsDisplayed()
            onNodeWithStringId(R.string.vehicle_test).assertIsDisplayed()
        }
    }

    @Test
    fun addAndScroll() {
        addObjectVehicle(makeVehicle, rnVehicle)
        addObjectVehicle(makeVehicle, rnVehicle)
        addObjectTrailer(makeTrailer, rnTrailer)
        composeRule.onNodeWithTag(Tags.TAG_COLUMN_FIRST_ENTRY).performScrollToIndex(R.string.skip)
        composeRule.onNodeWithStringId(R.string.skip).assertExists()
    }

    @Test
    fun isDisplayedButtonClear_inLastName() {
        actionWriteLastName(lastName)
        composeRule.onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_LAST_NAME).assertIsDisplayed()
    }

    @Test
    fun isDisplayedButtonClear_inFirstName() {
        actionWriteFirstName(firstName)
        composeRule.onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_FIRST_NAME).assertIsDisplayed()
    }

    @Test
    fun isDisplayedButtonClear_inPatronymic() {
        actionWritePatronymic(patronymic)
        composeRule.onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_PATRONYMIC).assertIsDisplayed()
    }

    @Test
    fun isDisplayedButtonClear_inLastNameFirstNamePatronymic() {
        actionWriteLastName(lastName)
        actionWriteFirstName(firstName)
        actionWritePatronymic(patronymic)
        composeRule.onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_FIRST_NAME).assertIsDisplayed()
        composeRule.onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_LAST_NAME).assertIsDisplayed()
        composeRule.onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_PATRONYMIC).assertIsDisplayed()
    }

    @Test
    fun isDisplayedButtonClear_inMakeVehicle() {
        actionWriteMakeVehicle(makeVehicle)
        composeRule.onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_MAKE).assertIsDisplayed()
    }

    @Test
    fun isDisplayedButtonClear_inRnVehicle() {
        actionWriteRnVehicle(rnVehicle)
        composeRule.onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_RN).assertIsDisplayed()
    }

    @Test
    fun isDisplayedButtonClear_inMakeRnVehicle() {
        actionWriteMakeVehicle(makeVehicle)
        actionWriteRnVehicle(rnVehicle)
        composeRule.onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_RN).assertIsDisplayed()
        composeRule.onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_MAKE).assertIsDisplayed()
    }

    @Test
    fun isDisplayedButtonClear_inMakeTrailer() {
        composeRule.onNodeWithContentDescription(R.string.trailer.toString()).performClick()
        actionWriteMakeTrailer(makeTrailer)
        composeRule.onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_MAKE).assertIsDisplayed()
    }

    @Test
    fun isDisplayedButtonClear_inRnTrailer() {
        composeRule.onNodeWithContentDescription(R.string.trailer.toString()).performClick()
        actionWriteRnTrailer(rnTrailer)
        composeRule.onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_RN).assertIsDisplayed()
    }

    @Test
    fun isDisplayedButtonClear_inMakeRnTrailer() {
        composeRule.onNodeWithContentDescription(R.string.trailer.toString()).performClick()
        actionWriteMakeTrailer(makeTrailer)
        actionWriteRnTrailer(rnTrailer)
        composeRule.onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_RN).assertIsDisplayed()
        composeRule.onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_MAKE).assertIsDisplayed()
    }

    @Test
    fun deleteTextField_deleteTextInLastName() {
        actionWriteLastName(lastName)
        composeRule.onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_LAST_NAME).performClick()
        composeRule.onNodeWithText(lastName).assertDoesNotExist()
    }

    @Test
    fun deleteTextField_deleteTextInFirstName() {
        actionWriteFirstName(firstName)
        composeRule.onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_FIRST_NAME).performClick()
        composeRule.onNodeWithText(firstName).assertDoesNotExist()
    }

    @Test
    fun deleteTextField_deleteTextInPatronymic() {
        actionWritePatronymic(patronymic)
        composeRule.onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_PATRONYMIC).performClick()
        composeRule.onNodeWithText(patronymic).assertDoesNotExist()
    }

    @Test
    fun deleteTextField_deleteTextInLastNameFirstNamePatronymic() {
        actionWriteLastName(lastName)
        actionWriteFirstName(firstName)
        actionWritePatronymic(patronymic)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_LAST_NAME).performClick()
            onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_FIRST_NAME).performClick()
            onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_PATRONYMIC).performClick()
            onNodeWithText(lastName).assertDoesNotExist()
            onNodeWithText(firstName).assertDoesNotExist()
            onNodeWithText(patronymic).assertDoesNotExist()
        }
    }

    @Test
    fun deleteTextField_deleteTextInMakeVehicle() {
        actionWriteMakeVehicle(makeVehicle)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_MAKE).performClick()
            onNodeWithText(makeVehicle).assertDoesNotExist()
        }
    }

    @Test
    fun deleteTextField_deleteTextInRnVehicle() {
        actionWriteRnVehicle(rnVehicle)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_RN).performClick()
            onNodeWithText(rnVehicle).assertDoesNotExist()
        }
    }

    @Test
    fun deleteTextField_deleteTextInMakeRnVehicle() {
        actionWriteMakeVehicle(makeVehicle)
        actionWriteRnVehicle(rnVehicle)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_MAKE).performClick()
            onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_RN).performClick()
            onNodeWithText(makeVehicle).assertDoesNotExist()
            onNodeWithText(rnVehicle).assertDoesNotExist()
        }
    }

    @Test
    fun deleteTextField_deleteTextInMakeTrailer() {
        composeRule.onNodeWithContentDescription(R.string.trailer.toString()).performClick()
        actionWriteMakeTrailer(makeTrailer)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_MAKE).performClick()
            onNodeWithText(makeTrailer).assertDoesNotExist()
        }
    }

    @Test
    fun deleteTextField_deleteTextInRnTrailer() {
        composeRule.onNodeWithContentDescription(R.string.trailer.toString()).performClick()
        actionWriteRnTrailer(rnTrailer)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_RN).performClick()
            onNodeWithText(rnTrailer).assertDoesNotExist()
        }
    }

    @Test
    fun deleteTextField_deleteTextInMakeRnTrailer() {
        composeRule.onNodeWithContentDescription(R.string.trailer.toString()).performClick()
        actionWriteMakeTrailer(makeTrailer)
        actionWriteRnTrailer(rnTrailer)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_MAKE).performClick()
            onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_RN).performClick()
            onNodeWithText(makeTrailer).assertDoesNotExist()
            onNodeWithText(rnTrailer).assertDoesNotExist()
        }
    }

    @Test
    fun deleteTable_deleteOnePosition() {
        addObjectVehicle(makeVehicle, rnVehicle)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_FIRST_ENTRY_BUTTON_TABLE).performClick()
            onNodeWithStringId(R.string.vehicle_test).assertDoesNotExist()
        }
    }

    @Test
    fun addFull_inButton_icClickable() {
        actionWriteLastName(lastName)
        actionWriteFirstName(firstName)
        actionWritePatronymic(patronymic)
        composeRule.onNodeWithStringId(R.string.save).assertHasClickAction()
    }

    @Test
    fun addFull_inButton_click() {
        actionWriteFirstName(firstName)
        actionWriteLastName(lastName)
        actionWritePatronymic(patronymic)
        addObjectVehicle(makeVehicle, rnVehicle)
        composeRule.onNodeWithStringId(R.string.save).performClick()
        composeRule.onNodeWithStringId(R.string.create_report).assertIsDisplayed()
    }

    private fun addObjectVehicle(make: String, rn: String) {
        composeRule.apply {
            actionWriteMakeVehicle(make)
            actionWriteRnVehicle(rn)
            onNodeWithStringId(R.string.add).performClick()
        }
    }

    private fun addObjectTrailer(make: String, rn: String) {
        composeRule.apply {
            onNodeWithContentDescription(R.string.trailer.toString()).performClick()
            actionWriteMakeTrailer(make)
            actionWriteRnTrailer(rn)
            onNodeWithStringId(R.string.add).performClick()
        }
    }

    private fun actionWriteMakeVehicle(makeVehicle: String) {
        composeRule.onNodeWithStringId(R.string.make_vehicle)
            .performClick()
            .performTextInput(makeVehicle)
    }

    private fun actionWriteRnVehicle(rnVehicle: String) {
        composeRule.onNodeWithStringId(R.string.rn_vehicle)
            .performClick()
            .performTextInput(rnVehicle)
    }

    private fun actionWriteMakeTrailer(makeTrailer: String) {
        composeRule.onNodeWithStringId(R.string.make_trailer)
            .performClick()
            .performTextInput(makeTrailer)
    }

    private fun actionWriteRnTrailer(rnTrailer: String) {
        composeRule.onNodeWithStringId(R.string.rn_trailer)
            .performClick()
            .performTextInput(rnTrailer)
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
}