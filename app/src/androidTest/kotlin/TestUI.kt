import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.ui.layouts.firstentry.FirstEntryScreen
import com.example.reportsfordrivers.viewmodel.firstentry.FirstEntryViewModel
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named


////@RunWith(AndroidJUnit4::class)
//class TestUAI {
//
//    //    @get:Rule
////    val hiltRule = HiltAndroidRule(this)
//    @get:Rule
//    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
////    @get:Rule
////    val composeTestRule = ComposeTestRule()
//
//    //
//    private lateinit var viewModel: FirstEntryViewModel
//
//    @Before
//    fun init() {
//        composeTestRule.setContent {
//            FirstEntryScreen()
//        }
//
//    }
//
//    @Test
//    fun f() {
////        val b = viewModel.vehicleUiState.value.isSelected.stateRadioGroup
//        assertTrue(true)
//    }
//}