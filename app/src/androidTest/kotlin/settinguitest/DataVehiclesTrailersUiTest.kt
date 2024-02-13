package settinguitest

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsSelected
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
import com.example.reportsfordrivers.ui.layouts.setting.SettingDataVehiclesTrailersScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class DataVehiclesTrailersUiTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule(MainActivity::class.java)

    private var makeVehicleOne = "DAF"
    private var makeVehicleTwo = "Scania"
    private var rnVehicleOne = "1111II-1"
    private var rnVehicleTwo = "2222TT-2"

    private var makeTrailerOne = "Trailer one"
    private var makeTrailerTwo = "Trailer two"
    private var rnTrailerOne = "3333PP-3"
    private var rnTrailerTwo = "4444OO-4"

    @Before
    fun init() {
        hiltRule.inject()
        composeRule.activity.setContent {
            SettingDataVehiclesTrailersScreen()
        }
    }

    @Test
    fun writeText_inTextFieldMakeVehicle() {
        actionWriteMakeVehicle(makeVehicleOne)
        composeRule.onNodeWithText(makeVehicleOne).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldRnVehicle() {
        actionWriteRnVehicle(rnVehicleOne)
        composeRule.onNodeWithText(rnVehicleOne).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldMakeRnVehicle() {
        actionWriteMakeVehicle(makeVehicleOne)
        actionWriteRnVehicle(rnVehicleOne)
        composeRule.apply {
            onNodeWithText(makeVehicleOne).assertIsDisplayed()
            onNodeWithText(rnVehicleOne).assertIsDisplayed()
        }
    }

    @Test
    fun writeText_inTextFieldMakeTrailer() {
        composeRule.onNodeWithTag(Tags.TAG_TEST_SETTING_DATA_RADIO_BUTTON_TRAILER).performClick()
        actionWriteMakeTrailer(makeTrailerOne)
        composeRule.onNodeWithText(makeTrailerOne).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldRnTrailer() {
        composeRule.onNodeWithTag(Tags.TAG_TEST_SETTING_DATA_RADIO_BUTTON_TRAILER).performClick()
        actionWriteRnTrailer(rnTrailerOne)
        composeRule.onNodeWithText(rnTrailerOne).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldMakeRnTrailer() {
        composeRule.onNodeWithTag(Tags.TAG_TEST_SETTING_DATA_RADIO_BUTTON_TRAILER).performClick()
        actionWriteMakeTrailer(makeTrailerOne)
        actionWriteRnTrailer(rnTrailerOne)
        composeRule.apply {
            onNodeWithText(makeTrailerOne).assertIsDisplayed()
            onNodeWithText(rnTrailerOne).assertIsDisplayed()
        }
    }

    @Test
    fun isSelected_inRadioButton_trailer() {
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_SETTING_DATA_RADIO_BUTTON_TRAILER).performClick()
            onNodeWithTag(Tags.TAG_TEST_SETTING_DATA_RADIO_BUTTON_TRAILER).assertIsSelected()
        }
    }

    @Test
    fun isSelected_inRadioButton_vehicleTrailerVehicle() {
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_SETTING_DATA_RADIO_BUTTON_TRAILER).performClick()
            onNodeWithTag(Tags.TAG_TEST_SETTING_DATA_RADIO_BUTTON_VEHICLE).performClick()
            onNodeWithTag(Tags.TAG_TEST_SETTING_DATA_RADIO_BUTTON_VEHICLE).assertIsSelected()
        }
    }

    @Test
    fun isNotEnabled_inButtonAddVehicle() {
        composeRule.onNodeWithStringId(R.string.add).assertIsNotEnabled()
    }

    @Test
    fun isNotEnabled_inButtonAddTrailer() {
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_SETTING_DATA_RADIO_BUTTON_TRAILER).performClick()
            onNodeWithStringId(R.string.add).assertIsNotEnabled()
        }
    }

    @Test
    fun isEnabled_inButtonAddVehicle() {
        actionWriteMakeVehicle(makeVehicleOne)
        actionWriteRnVehicle(rnVehicleOne)
        composeRule.onNodeWithStringId(R.string.add).assertIsEnabled()
    }

    @Test
    fun isNotEnabled_inButtonAddVehicle_afterWriteAndClear() {
        actionWriteMakeVehicle(makeVehicleOne)
        actionWriteRnVehicle(rnVehicleOne)
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_SETTING_DATA_VEHICLE_MAKE).performClick()
            onNodeWithStringId(R.string.add).assertIsNotEnabled()
        }
    }

    @Test
    fun isEnabled_inButtonAddTrailer() {
        composeRule.onNodeWithTag(Tags.TAG_TEST_SETTING_DATA_RADIO_BUTTON_TRAILER).performClick()
        actionWriteMakeTrailer(makeTrailerOne)
        actionWriteRnTrailer(rnTrailerOne)
        composeRule.onNodeWithStringId(R.string.add).assertIsEnabled()
    }

    @Test
    fun isNotEnabled_inButtonAddTrailer_afterWriteAndClear() {
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_SETTING_DATA_RADIO_BUTTON_TRAILER).performClick()
            actionWriteMakeTrailer(makeTrailerOne)
            actionWriteRnTrailer(rnTrailerOne)
            onNodeWithTag(Tags.TAG_TEST_SETTING_DATA_VEHICLE_MAKE).performClick()
            onNodeWithStringId(R.string.add).assertIsNotEnabled()
        }
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