package uitest

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
import com.example.reportsfordrivers.ui.layouts.createreports.CreateReportsProgressReportsScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ProgressReportsUiTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule(MainActivity::class.java)

    private var countryOne = "Belarus"
    private var countryTwo = "Russia"
    private var countryThree = "Poland"

    private var townshipOne = "Minsk"
    private var townshipTwo = "Smolensk"
    private var townshipThree = "Krakow"

    private var distanceOne = "950"
    private var distanceTwo = "1100"
    private var distanceThree = "1250"

    private var cargoWeightOne = "10"
    private var cargoWeightTwo = "20"
    private var cargoWeightThree = "15"

    @Before
    fun init() {
        hiltRule.inject()
        composeRule.activity.setContent {
            CreateReportsProgressReportsScreen {}
        }
    }

    @Test
    fun writeText_inTextFieldCountry() {
        actionWriteCountry(countryOne)
        composeRule.onNodeWithText(countryOne).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldTownship() {
        actionWriteTownship(townshipOne)
        composeRule.onNodeWithText(townshipOne).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldDistance() {
        actionWriteDistance(distanceOne)
        composeRule.onNodeWithText(distanceOne).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldCargoWeight() {
        actionWriteCargoWeight(cargoWeightOne)
        composeRule.onNodeWithText(cargoWeightOne).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldCountryTownshipDistanceCargoWeight() {
        actionWriteCountry(countryOne)
        actionWriteTownship(townshipOne)
        actionWriteDistance(distanceOne)
        actionWriteCargoWeight(cargoWeightOne)
        composeRule.apply {
            onNodeWithText(countryOne).assertIsDisplayed()
            onNodeWithText(townshipOne).assertIsDisplayed()
            onNodeWithText(distanceOne).assertIsDisplayed()
            onNodeWithText(cargoWeightOne).assertIsDisplayed()
        }
    }

    @Test
    fun isButtonSkip_inCountry() {
        actionWriteCountry(countryOne)
        composeRule.onNodeWithTag(Tags.TAG_TEST_PROGRESS_REPORTS_COUNTRY).assertIsDisplayed()
    }

    @Test
    fun isButtonSkip_inTownship() {
        actionWriteTownship(townshipOne)
        composeRule.onNodeWithTag(Tags.TAG_TEST_PROGRESS_REPORTS_TOWNSHIP).assertIsDisplayed()
    }

    @Test
    fun isButtonSkip_inDistance() {
        actionWriteDistance(distanceOne)
        composeRule.onNodeWithTag(Tags.TAG_TEST_PROGRESS_REPORTS_DISTANCE).assertIsDisplayed()
    }

    @Test
    fun isButtonSkip_inCargoWeight() {
        actionWriteCargoWeight(cargoWeightOne)
        composeRule.onNodeWithTag(Tags.TAG_TEST_PROGRESS_REPORTS_CARGO_WEIGHT).assertIsDisplayed()
    }

    @Test
    fun isButtonSkip_inCountryTownshipDistanceCargoWeight() {
        actionWriteCountry(countryOne)
        actionWriteTownship(townshipOne)
        actionWriteDistance(distanceOne)
        actionWriteCargoWeight(cargoWeightOne)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_PROGRESS_REPORTS_COUNTRY).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_PROGRESS_REPORTS_TOWNSHIP).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_PROGRESS_REPORTS_DISTANCE).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_PROGRESS_REPORTS_CARGO_WEIGHT).assertIsDisplayed()
        }
    }

    @Test
    fun clickButtonClear_inCountry() {
        actionWriteCountry(countryOne)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_PROGRESS_REPORTS_COUNTRY).performClick()
            onNodeWithText(countryOne).assertDoesNotExist()
        }
    }

    @Test
    fun clickButtonClear_inTownship() {
        actionWriteTownship(townshipOne)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_PROGRESS_REPORTS_TOWNSHIP).performClick()
            onNodeWithText(townshipOne).assertDoesNotExist()
        }
    }

    @Test
    fun clickButtonClear_inDistance() {
        actionWriteDistance(distanceOne)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_PROGRESS_REPORTS_DISTANCE).performClick()
            onNodeWithText(distanceOne).assertDoesNotExist()
        }
    }

    @Test
    fun clickButtonClear_inCargoWeight() {
        actionWriteCargoWeight(cargoWeightOne)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_PROGRESS_REPORTS_CARGO_WEIGHT).performClick()
            onNodeWithText(cargoWeightOne).assertDoesNotExist()
        }
    }

    @Test
    fun clickButtonClear_inCountryTownshipDistanceCargoWeight() {
        actionWriteCountry(countryOne)
        actionWriteTownship(townshipOne)
        actionWriteDistance(distanceOne)
        actionWriteCargoWeight(cargoWeightOne)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_PROGRESS_REPORTS_COUNTRY).performClick()
            onNodeWithTag(Tags.TAG_TEST_PROGRESS_REPORTS_TOWNSHIP).performClick()
            onNodeWithTag(Tags.TAG_TEST_PROGRESS_REPORTS_DISTANCE).performClick()
            onNodeWithTag(Tags.TAG_TEST_PROGRESS_REPORTS_CARGO_WEIGHT).performClick()
            onNodeWithText(countryOne).assertDoesNotExist()
            onNodeWithText(townshipOne).assertDoesNotExist()
            onNodeWithText(distanceOne).assertDoesNotExist()
            onNodeWithText(cargoWeightOne).assertDoesNotExist()
        }
    }

    @Test
    fun isNotEnabled_buttonAdd_isTextFieldsEmpty() {
        composeRule.onNodeWithStringId(R.string.add).assertIsNotEnabled()
    }

    @Test
    fun isNotEnabled_buttonNext_isTextFieldsEmpty() {
        composeRule.onNodeWithStringId(R.string.next).assertIsNotEnabled()
    }

    @Test
    fun isEnabled_buttonAdd_isTextFieldsFull() {
        actionWriteCountry(countryOne)
        actionWriteTownship(townshipOne)
        actionWriteDistance(distanceOne)
        actionWriteCargoWeight(cargoWeightOne)
        composeRule.onNodeWithStringId(R.string.add).assertIsEnabled()
    }

    @Test
    @Ignore
    fun isEnabled_buttonNext_isTextFieldsAdd() {
        actionWriteCountry(countryOne)
        actionWriteTownship(townshipOne)
        actionWriteDistance(distanceOne)
        actionWriteCargoWeight(cargoWeightOne)
        composeRule.onNodeWithStringId(R.string.add).performClick()
    }

    private fun actionWriteCountry(country: String) {
        composeRule.onNodeWithStringId(R.string.country)
            .performClick()
            .performTextInput(country)
    }

    private fun actionWriteTownship(township: String) {
        composeRule.onNodeWithStringId(R.string.township)
            .performClick()
            .performTextInput(township)
    }

    private fun actionWriteDistance(distance: String) {
        composeRule.onNodeWithStringId(R.string.distance)
            .performClick()
            .performTextInput(distance)
    }

    private fun actionWriteCargoWeight(cargoWeight: String) {
        composeRule.onNodeWithStringId(R.string.cargo_weight)
            .performClick()
            .performTextInput(cargoWeight)
    }
}