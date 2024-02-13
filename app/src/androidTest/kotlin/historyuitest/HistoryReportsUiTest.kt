package historyuitest

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.reportsfordrivers.MainActivity
import com.example.reportsfordrivers.ui.layouts.createreports.CreateReportsDataFillingTwoScreen
import com.example.reportsfordrivers.ui.layouts.hirstoryreports.HistoryReportsScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule

@HiltAndroidTest
class HistoryReportsUiTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule(MainActivity::class.java)

    @Before
    fun init() {
        hiltRule.inject()
        composeRule.activity.setContent {
            HistoryReportsScreen()
        }
    }
}