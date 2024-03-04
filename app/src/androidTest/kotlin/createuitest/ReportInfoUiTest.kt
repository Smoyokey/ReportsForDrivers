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
import com.example.reportsfordrivers.ui.layouts.createreports.CreateReportsDataReportInfoScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date

@HiltAndroidTest
class ReportInfoUiTest {

    private val waybill = "123"
    private val township = "Minsk"

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule(MainActivity::class.java)

    @Before
    fun init() {
        hiltRule.inject()
        composeRule.activity.setContent {
            CreateReportsDataReportInfoScreen()
        }
    }

    @Test
    fun writeText_inTextFieldWaybill() {
        actionWrite(R.string.waybill, waybill)
        composeRule.onNodeWithText(waybill).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldCity() {
        actionWrite(R.string.township, township)
        composeRule.onNodeWithText(township).assertIsDisplayed()
    }

    @Test
    fun clickDate_inDateCreateReport() {
        composeRule.apply {
            clickDate()
            onNodeWithText(currentDate()).assertIsDisplayed()
        }
    }

    @Test
    fun textFields_inWaybillCityDate() {
        composeRule.apply {
            actionWrite(R.string.waybill, waybill)
            actionWrite(R.string.township, township)
            onNodeWithText(waybill).assertIsDisplayed()
            onNodeWithText(township).assertIsDisplayed()
            clickDate()
            onNodeWithText(currentDate()).assertIsDisplayed()
        }
    }

    @Test
    fun isVisible_buttonClear_inTextFieldWaybill() {
        actionWrite(R.string.waybill, waybill)
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_REPORT_INFO_WAYBILL).assertIsDisplayed()
    }

    @Test
    fun isVisible_buttonClear_inTextFieldCity() {
        actionWrite(R.string.township, township)
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_REPORT_INFO_MAIN_CITY).assertIsDisplayed()
    }

    @Test
    fun isVisible_buttonClear_inTextFieldDate() {
        clickDate()
        composeRule.onNodeWithTag(Tags.TAG_TEST_DATA_REPORT_INFO_DATE).assertIsDisplayed()
    }

    @Test
    fun isVisible_buttonClear_inTextFieldWaybillCityDate() {
        actionWrite(R.string.waybill, waybill)
        actionWrite(R.string.township, township)
        clickDate()
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_REPORT_INFO_WAYBILL).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_DATA_REPORT_INFO_MAIN_CITY).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_DATA_REPORT_INFO_DATE).assertIsDisplayed()
        }
    }

    @Test
    fun clickButton_inTextFieldWaybill() {
        actionWrite(R.string.waybill, waybill)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_REPORT_INFO_WAYBILL).performClick()
            onNodeWithText(waybill).assertDoesNotExist()
        }
    }

    @Test
    fun clickButton_inTextFieldCity() {
        actionWrite(R.string.township, township)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_REPORT_INFO_MAIN_CITY).performClick()
            onNodeWithText(township).assertDoesNotExist()
        }
    }

    @Test
    fun clickButton_inTextFieldDate() {
        clickDate()
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_REPORT_INFO_DATE).performClick()
            onNodeWithText(currentDate()).assertDoesNotExist()
        }
    }

    @Test
    fun clickButtons_inTextFieldWaybillCityDate() {
        actionWrite(R.string.waybill, waybill)
        actionWrite(R.string.township, township)
        clickDate()
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_DATA_REPORT_INFO_WAYBILL).performClick()
            onNodeWithText(waybill).assertDoesNotExist()
            onNodeWithTag(Tags.TAG_TEST_DATA_REPORT_INFO_MAIN_CITY).performClick()
            onNodeWithText(township).assertDoesNotExist()
            onNodeWithTag(Tags.TAG_TEST_DATA_REPORT_INFO_DATE).performClick()
            onNodeWithText(currentDate()).assertDoesNotExist()
        }
    }

    private fun actionWrite(@StringRes label: Int, text: String) {
        composeRule.onNodeWithStringId(label)
            .performClick()
            .performTextInput(text)
    }

    private fun clickDate() {
        composeRule.apply {
            onNodeWithStringId(R.string.date_create_report).performClick()
            onNodeWithStringId(R.string.ok).performClick()
        }
    }

    private fun currentDate(): String {
        val date = Date().time
        val format = SimpleDateFormat("\"dd\" MM yyyy")
        return format.format(date)
    }
}