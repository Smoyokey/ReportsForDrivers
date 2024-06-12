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
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class FeedbackUiTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule(MainActivity::class.java)

    private var yourName = "Ivan"
    private var yourEmail = "ivan@gmail.com"
    private var text = "Hello, any text"

//    @Before
//    fun init() {
//        hiltRule.inject()
//        composeRule.activity.setContent {
//            SettingFeedbackScreen()
//        }
//    }

    @Test
    fun writeText_inTextFieldYourName() {
        actionWriteYourName(yourName)
        composeRule.onNodeWithText(yourName).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldYourEmail() {
        actionWriteYourEmail(yourEmail)
        composeRule.onNodeWithText(yourEmail).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldText() {
        actionWriteText(text)
        composeRule.onNodeWithText(text).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldYourNameEmailText() {
        actionWriteYourName(yourName)
        actionWriteYourEmail(yourEmail)
        actionWriteText(text)
        composeRule.apply {
            onNodeWithText(yourName).assertIsDisplayed()
            onNodeWithText(yourEmail).assertIsDisplayed()
            onNodeWithText(text).assertIsDisplayed()
        }
    }

    @Test
    fun isButtonClear_inTextFieldYourName() {
        actionWriteYourName(yourName)
        composeRule.onNodeWithTag(Tags.TAG_TEST_SETTING_FEEDBACK_YOUR_NAME).assertIsDisplayed()
    }

    @Test
    fun isButtonClear_inTextFieldYourEmail() {
        actionWriteYourEmail(yourEmail)
        composeRule.onNodeWithTag(Tags.TAG_TEST_SETTING_FEEDBACK_YOUR_EMAIL).assertIsDisplayed()
    }

    @Test
    fun isButtonClear_inTextFieldText() {
        actionWriteText(text)
        composeRule.onNodeWithTag(Tags.TAG_TEST_SETTING_FEEDBACK_TEXT).assertIsDisplayed()
    }

    @Test
    fun isButtonClear_inTextFieldYourNameEmailText() {
        actionWriteText(text)
        actionWriteYourEmail(yourEmail)
        actionWriteYourName(yourName)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_SETTING_FEEDBACK_YOUR_NAME).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_SETTING_FEEDBACK_YOUR_EMAIL).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_SETTING_FEEDBACK_TEXT).assertIsDisplayed()
        }
    }

    @Test
    fun clickButton_inTextFieldYourName() {
        actionWriteYourName(yourName)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_SETTING_FEEDBACK_YOUR_NAME).performClick()
            onNodeWithText(yourName).assertDoesNotExist()
        }
    }

    @Test
    fun clickButton_inTextFieldYourEmail() {
        actionWriteYourEmail(yourEmail)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_SETTING_FEEDBACK_YOUR_EMAIL).performClick()
            onNodeWithText(yourEmail).assertDoesNotExist()
        }
    }

    @Test
    fun clickButton_inTextFieldText() {
        actionWriteText(text)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_SETTING_FEEDBACK_TEXT).performClick()
            onNodeWithText(text).assertDoesNotExist()
        }
    }

    @Test
    fun clickButton_inTextFieldYourNameEmailText() {
        actionWriteYourName(yourName)
        actionWriteYourEmail(yourEmail)
        actionWriteText(text)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_SETTING_FEEDBACK_YOUR_NAME).performClick()
            onNodeWithTag(Tags.TAG_TEST_SETTING_FEEDBACK_YOUR_EMAIL).performClick()
            onNodeWithTag(Tags.TAG_TEST_SETTING_FEEDBACK_TEXT).performClick()
            onNodeWithText(yourName).assertDoesNotExist()
            onNodeWithText(yourEmail).assertDoesNotExist()
            onNodeWithText(text).assertDoesNotExist()
        }
    }

    private fun actionWriteYourName(yourName: String) {
        composeRule.onNodeWithStringId(R.string.your_name)
            .performClick()
            .performTextInput(yourName)
    }

    private fun actionWriteYourEmail(yourEmail: String) {
        composeRule.onNodeWithStringId(R.string.your_contact_email)
            .performClick()
            .performTextInput(yourEmail)
    }

    private fun actionWriteText(text: String) {
        composeRule.onNodeWithStringId(R.string.text_appeal)
            .performClick()
            .performTextInput(text)
    }
}