package createuitest

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
import com.example.reportsfordrivers.ui.layouts.createreports.CreateReportsExpensesScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class TripExpensesUiTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule(MainActivity::class.java)

    private var documentNumberOne = "One"
    private var documentNumberTwo = "Two"
    private var documentNumberThree = "Three"

    private var expenseItemOne = "Expense One"
    private var expenseItemTwo = "Expense Two"
    private var expenseItemThree = "Expense Three"

    private var sumOne = "Sum one"
    private var sumTwo = "Sum two"
    private var sumThree = "Sum three"

    private var currencyOne = "Currency one"
    private var currencyTwo = "Currency two"
    private var currencyThree = "Currency three"

    @Before
    fun init() {
        hiltRule.inject()
        composeRule.activity.setContent {
            CreateReportsExpensesScreen()
        }
    }

    @Test
    fun writeText_inTextFieldDocumentNumber() {
        actionWriteDocumentNumber(documentNumberOne)
        composeRule.onNodeWithText(documentNumberOne).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldExpenseItem() {
        actionWriteExpenseItem(expenseItemOne)
        composeRule.onNodeWithText(expenseItemOne).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldSum() {
        actionWriteSum(sumOne)
        composeRule.onNodeWithText(sumOne).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldCurrency() {
        actionWriteCurrency(currencyOne)
        composeRule.onNodeWithText(currencyOne).assertIsDisplayed()
    }

    @Test
    fun writeText_inTextFieldDocumentNumberExpenseItemSumCurrency() {
        actionWriteDocumentNumber(documentNumberOne)
        actionWriteExpenseItem(expenseItemOne)
        actionWriteSum(sumOne)
        actionWriteCurrency(currencyOne)
        composeRule.apply {
            onNodeWithText(documentNumberOne).assertIsDisplayed()
            onNodeWithText(expenseItemOne).assertIsDisplayed()
            onNodeWithText(sumOne).assertIsDisplayed()
            onNodeWithText(currencyOne).assertIsDisplayed()
        }
    }

    @Test
    fun isNotDisplayed_inTextFieldDocumentNumberExpenseItemSumCurrency() {
        composeRule.apply {
            onNodeWithTag(Tags.TAG_TEST_EXPENSES_DOCUMENT_NUMBER).assertDoesNotExist()
            onNodeWithTag(Tags.TAG_TEST_EXPENSES_EXPENSE_ITEM).assertDoesNotExist()
            onNodeWithTag(Tags.TAG_TEST_EXPENSES_SUM).assertDoesNotExist()
            onNodeWithTag(Tags.TAG_TEST_EXPENSES_CURRENCY).assertDoesNotExist()
        }
    }

    @Test
    fun isDisplayed_inTextFieldDocumentNumberExpenseItemSumCurrency() {
        composeRule.apply {
            actionWriteDocumentNumber(documentNumberOne)
            actionWriteExpenseItem(expenseItemOne)
            actionWriteSum(sumOne)
            actionWriteCurrency(currencyOne)
            onNodeWithTag(Tags.TAG_TEST_EXPENSES_DOCUMENT_NUMBER).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_EXPENSES_EXPENSE_ITEM).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_EXPENSES_SUM).assertIsDisplayed()
            onNodeWithTag(Tags.TAG_TEST_EXPENSES_CURRENCY).assertIsDisplayed()
        }
    }

    @Test
    fun clickButton_inTextFieldDocumentNumber() {
        composeRule.apply {
            actionWriteDocumentNumber(documentNumberOne)
            onNodeWithTag(Tags.TAG_TEST_EXPENSES_DOCUMENT_NUMBER).performClick()
            onNodeWithTag(Tags.TAG_TEST_EXPENSES_DOCUMENT_NUMBER).assertDoesNotExist()
            onNodeWithText(documentNumberOne).assertDoesNotExist()
        }
    }

    @Test
    fun clickButton_inTextFieldExpenseItem() {
        composeRule.apply {
            actionWriteExpenseItem(expenseItemOne)
            onNodeWithTag(Tags.TAG_TEST_EXPENSES_EXPENSE_ITEM).performClick()
            onNodeWithTag(Tags.TAG_TEST_EXPENSES_EXPENSE_ITEM).assertDoesNotExist()
            onNodeWithText(expenseItemOne).assertDoesNotExist()
        }
    }

    @Test
    fun clickButton_inTextFieldSum() {
        composeRule.apply {
            actionWriteSum(sumOne)
            onNodeWithTag(Tags.TAG_TEST_EXPENSES_SUM).performClick()
            onNodeWithTag(Tags.TAG_TEST_EXPENSES_SUM).assertDoesNotExist()
            onNodeWithText(sumOne).assertDoesNotExist()
        }
    }

    @Test
    fun clickButton_inTextFieldCurrency() {
        composeRule.apply {
            actionWriteCurrency(currencyOne)
            onNodeWithTag(Tags.TAG_TEST_EXPENSES_CURRENCY).performClick()
            onNodeWithTag(Tags.TAG_TEST_EXPENSES_CURRENCY).assertDoesNotExist()
            onNodeWithText(currencyOne).assertDoesNotExist()
        }
    }

    @Test
    fun clickButton_inTextFieldDocumentNumberExpenseItemSumCurrency() {
        composeRule.apply {
            actionWriteDocumentNumber(documentNumberOne)
            actionWriteExpenseItem(expenseItemOne)
            actionWriteSum(sumOne)
            actionWriteCurrency(currencyOne)

            onNodeWithTag(Tags.TAG_TEST_EXPENSES_DOCUMENT_NUMBER).performClick()
            onNodeWithTag(Tags.TAG_TEST_EXPENSES_EXPENSE_ITEM).performClick()
            onNodeWithTag(Tags.TAG_TEST_EXPENSES_SUM).performClick()
            onNodeWithTag(Tags.TAG_TEST_EXPENSES_CURRENCY).performClick()

            onNodeWithTag(Tags.TAG_TEST_EXPENSES_DOCUMENT_NUMBER).assertDoesNotExist()
            onNodeWithTag(Tags.TAG_TEST_EXPENSES_EXPENSE_ITEM).assertDoesNotExist()
            onNodeWithTag(Tags.TAG_TEST_EXPENSES_SUM).assertDoesNotExist()
            onNodeWithTag(Tags.TAG_TEST_EXPENSES_CURRENCY).assertDoesNotExist()

            onNodeWithText(documentNumberOne).assertDoesNotExist()
            onNodeWithText(expenseItemOne).assertDoesNotExist()
            onNodeWithText(sumOne).assertDoesNotExist()
            onNodeWithText(currencyOne).assertDoesNotExist()
        }
    }

    private fun actionWriteDocumentNumber(documentNumber: String) {
        composeRule.onNodeWithStringId(R.string.document_number)
            .performClick()
            .performTextInput(documentNumber)
    }

    private fun actionWriteExpenseItem(expenseItem: String) {
        composeRule.onNodeWithStringId(R.string.expense_item)
            .performClick()
            .performTextInput(expenseItem)
    }

    private fun actionWriteSum(sum: String) {
        composeRule.onNodeWithStringId(R.string.sum)
            .performClick()
            .performTextInput(sum)
    }

    private fun actionWriteCurrency(currency: String) {
        composeRule.onNodeWithStringId(R.string.currency)
            .performClick()
            .performTextInput(currency)
    }

}