package settinguitest

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
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
import com.example.reportsfordrivers.ui.layouts.setting.SettingPersonalDataScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class PersonalDataUiTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule(MainActivity::class.java)

    private var lastName = "Ivanov"
    private var firstName = "Ivan"
    private var patronymic = "Ivanovich"

    @Before
    fun init() {
        hiltRule.inject()
        composeRule.activity.setContent {
            SettingPersonalDataScreen()
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
    fun isButtonClear_inTextFieldLastName() {
        actionWriteLastName(lastName)
        composeRule.onNodeWithTag(Tags.TAG_TEST_SETTING_PERSONAL_DATA_LAST_NAME).assertIsDisplayed()
    }

    @Test
    fun isButtonClear_inTextFieldFirstName() {
        actionWriteFirstName(firstName)
        composeRule.onNodeWithTag(Tags.TAG_TEST_SETTING_PERSONAL_DATA_FIRST_NAME).assertIsDisplayed()
    }

    @Test
    fun isButtonClear_inTextFieldPatronymic() {
        actionWritePatronymic(patronymic)
        composeRule.onNodeWithTag(Tags.TAG_TEST_SETTING_PERSONAL_DATA_PATRONYMIC).assertIsDisplayed()
    }

    @Test
    fun buttonClick_inTextFieldLastName() {
        actionWriteLastName(lastName)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_SETTING_PERSONAL_DATA_LAST_NAME).performClick()
            onNodeWithText(lastName).assertDoesNotExist()
        }
    }

    @Test
    fun buttonClick_inTextFieldFirstName() {
        actionWriteFirstName(firstName)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_SETTING_PERSONAL_DATA_FIRST_NAME).performClick()
            onNodeWithText(firstName).assertDoesNotExist()
        }
    }

    @Test
    fun buttonClick_inTextFieldPatronymic() {
        actionWritePatronymic(patronymic)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_SETTING_PERSONAL_DATA_PATRONYMIC).performClick()
            onNodeWithText(patronymic).assertDoesNotExist()
        }
    }

    @Test
    fun buttonClick_inTextFieldLastNameFirstNamePatronymic() {
        actionWriteLastName(lastName)
        actionWriteFirstName(firstName)
        actionWritePatronymic(patronymic)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_SETTING_PERSONAL_DATA_LAST_NAME).performClick()
            onNodeWithTag(Tags.TAG_TEST_SETTING_PERSONAL_DATA_FIRST_NAME).performClick()
            onNodeWithTag(Tags.TAG_TEST_SETTING_PERSONAL_DATA_PATRONYMIC).performClick()
            onNodeWithText(lastName).assertDoesNotExist()
            onNodeWithText(firstName).assertDoesNotExist()
            onNodeWithText(patronymic).assertDoesNotExist()
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
}