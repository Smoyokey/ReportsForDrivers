package createuitest

import androidx.activity.compose.setContent
import androidx.annotation.StringRes
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
import com.example.reportsfordrivers.ui.layouts.createreports.CreateReportsDataPersonalInfoScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class PersonalInfoUiTest {

    private val lastName = "Ivanov"
    private val firstName = "Ivan"
    private val patronymic = "Ivanovich"

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule(MainActivity::class.java)

    @Before
    fun init() {
        hiltRule.inject()
        composeRule.activity.setContent {
            CreateReportsDataPersonalInfoScreen()
        }
    }

    @Test
    fun writeText_inTextFieldLastName() {
        actionWrite(R.string.last_name, lastName)
        composeRule.onNodeWithText(lastName).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldFirstName() {
        actionWrite(R.string.first_name, firstName)
        composeRule.onNodeWithText(firstName).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldPatronymic() {
        actionWrite(R.string.patronymic, patronymic)
        composeRule.onNodeWithText(patronymic).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldLastNameFirstNamePatronymic() {
        actionWrite(R.string.last_name, lastName)
        actionWrite(R.string.first_name, firstName)
        actionWrite(R.string.patronymic, patronymic)
        composeRule.apply {
            onNodeWithText(lastName).assertIsDisplayed()
            onNodeWithText(firstName).assertIsDisplayed()
            onNodeWithText(patronymic).assertIsDisplayed()
        }
    }

    @Test
    fun isVisible_buttonClear_onTextFieldLastName() {
        actionWrite(R.string.last_name, lastName)
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_PERSONAL_INFO_LAST_NAME).assertIsDisplayed()
    }

    @Test
    fun isVisible_buttonClear_onTextFieldFirstName() {
        actionWrite(R.string.first_name, firstName)
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_PERSONAL_INFO_FIRST_NAME).assertIsDisplayed()
    }

    @Test
    fun isVisible_buttonClear_onTextFieldPatronymic() {
        actionWrite(R.string.patronymic, patronymic)
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_PERSONAL_INFO_PATRONYMIC).assertIsDisplayed()
    }

    @Test
    fun isVisible_buttonClear_onTextFieldLastNameFirstNamePatronymic() {
        actionWrite(R.string.last_name, lastName)
        actionWrite(R.string.first_name, firstName)
        actionWrite(R.string.patronymic, patronymic)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_PERSONAL_INFO_LAST_NAME).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_DATA_PERSONAL_INFO_FIRST_NAME).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_DATA_PERSONAL_INFO_PATRONYMIC).assertIsDisplayed()
        }
    }

    @Test
    fun clickButton_inTextFieldLastName() {
        actionWrite(R.string.last_name, lastName)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_PERSONAL_INFO_LAST_NAME).performClick()
            onNodeWithText(lastName).assertDoesNotExist()
        }
    }

    @Test
    fun clickButton_inTextFieldFirstName() {
        actionWrite(R.string.first_name, firstName)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_PERSONAL_INFO_FIRST_NAME).performClick()
            onNodeWithText(firstName).assertDoesNotExist()
        }
    }

    @Test
    fun clickButton_inTextFieldPatronymic() {
        actionWrite(R.string.patronymic, patronymic)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_PERSONAL_INFO_PATRONYMIC).performClick()
            onNodeWithText(patronymic).assertDoesNotExist()
        }
    }

    @Test
    fun clickButtons_inTextFieldLastNameFirstNamePatronymic() {
        actionWrite(R.string.last_name, lastName)
        actionWrite(R.string.first_name, firstName)
        actionWrite(R.string.patronymic, patronymic)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_PERSONAL_INFO_LAST_NAME).performClick()
            onNodeWithTag(Tags.TAG_TEST_DATA_PERSONAL_INFO_FIRST_NAME).performClick()
            onNodeWithTag(Tags.TAG_TEST_DATA_PERSONAL_INFO_PATRONYMIC).performClick()
            onNodeWithText(lastName).assertDoesNotExist()
            onNodeWithText(firstName).assertDoesNotExist()
            onNodeWithText(patronymic).assertDoesNotExist()
        }
    }
    private fun actionWrite(@StringRes label: Int, text: String) {
        composeRule.onNodeWithStringId(label)
            .performClick()
            .performTextInput(text)
    }
}