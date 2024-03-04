package createuitest

import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.reportsfordrivers.MainActivity
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.navigationtest.helpmethods.onNodeWithStringId
import com.example.reportsfordrivers.ui.layouts.createreports.CreateReportsDataReportInfoScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ReportInfoUiTest {

    private val waybill = "1"
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

    private fun actionWrite(@StringRes label: Int, text: String) {
        composeRule.onNodeWithStringId(label)
            .performClick()
            .performTextInput(text)
    }
}