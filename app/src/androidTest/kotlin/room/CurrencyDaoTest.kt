package room

import com.example.reportsfordrivers.data.AppDatabase
import com.example.reportsfordrivers.data.dao.CurrencyDao
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class CurrencyDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var currencyDao: CurrencyDao

    @Before
    fun createDb() {
        hiltRule.inject()
        currencyDao = database.currencyDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }
}