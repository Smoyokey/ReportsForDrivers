package room

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.reportsfordrivers.MainActivity
import com.example.reportsfordrivers.ReportsForDriversApp
import com.example.reportsfordrivers.data.AppDatabase
import com.example.reportsfordrivers.data.dao.RouteDao
import com.example.reportsfordrivers.data.structure.Route
import com.example.reportsfordrivers.ui.layouts.firstentry.FirstEntryScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import runner.HiltTestRunner
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class RouteDaoTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var routeDao: RouteDao

    private var item1 = Route(
        1,
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        8.0
    )
    private var item2 = Route(
        2,
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        8.0
    )

    private var item3 = Route(
        1,
        "One",
        "Two",
        "Three",
        "Four",
        "Five",
        "Six",
        "Seven",
        10.0
    )

    private var item4 = Route(
        2,
        "One",
        "Two",
        "Three",
        "Four",
        "Five",
        "Six",
        "Seven",
        10.0
    )

    private suspend fun addOneElement() {
        routeDao.insert(item1)
    }

    private suspend fun addTwoElements() {
        routeDao.insert(item1)
        routeDao.insert(item2)
    }

    @Before
    fun createDb() {
        hiltRule.inject()
        routeDao = database.routeDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsOneItemDaoIntoDb() = runBlocking {
        addOneElement()
        val allItems = routeDao.getAllItem().first()
        assertEquals(allItems[0], item1)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsAllItemsDaoIntoDb() = runBlocking {
        addTwoElements()
        val allItems = routeDao.getAllItem().first()
        assertEquals(allItems[0], item1)
        assertEquals(allItems[1], item2)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdatesItems_updatesItemsInDb() = runBlocking {
        addTwoElements()
        routeDao.update(item3)
        routeDao.update(item4)

        val allItems = routeDao.getAllItem().first()
        assertEquals(allItems[0], item3)
        assertEquals(allItems[1], item4)
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteItems_deletesItemsInDb() = runBlocking {
        addTwoElements()
        routeDao.delete(item1)
        routeDao.delete(item2)

        val allItems = routeDao.getAllItem().first()
        assertTrue(allItems.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoSelectOneItem_selectItemInDb() = runBlocking {
        addTwoElements()

        val oneItem = routeDao.getOneItem(1).first()
        assertEquals(oneItem, item1)
    }
}