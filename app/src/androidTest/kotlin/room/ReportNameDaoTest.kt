package room

import com.example.reportsfordrivers.data.AppDatabase
import com.example.reportsfordrivers.data.dao.ReportNameDao
import com.example.reportsfordrivers.data.structure.ReportName
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
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class ReportNameDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var reportNameDao: ReportNameDao

    private var item1 = ReportName(
        1,
        "1",
        "2",
        "3",
        1,
        1,
        1,
        1
    )

    private var item2 = ReportName(
        2,
        "1",
        "2",
        "3",
        2,
        2,
        2,
        2
    )

    private var item3 = ReportName(
        1,
        "One",
        "two",
        "Three",
        3,
        3,
        3,
        3
    )

    private var item4 = ReportName(
        2,
        "Three",
        "Three",
        "Three",
        4,
        4,
        4,
        4
    )

    private suspend fun addOneElement() {
        reportNameDao.insert(item1)
    }

    private suspend fun addTwoElements() {
        reportNameDao.insert(item1)
        reportNameDao.insert(item2)
    }

    @Before
    fun createDb() {
        hiltRule.inject()
        reportNameDao = database.reportNameDao()
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
        val allItems = reportNameDao.getAllItem().first()
        assertEquals(allItems[0], item1)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsAllItemsDaoIntoDb() = runBlocking {
        addTwoElements()
        val allItems = reportNameDao.getAllItem().first()
        assertEquals(allItems[0], item1)
        assertEquals(allItems[1], item2)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdatesItems_updatesItemsInDn() = runBlocking {
        addTwoElements()
        reportNameDao.update(item3)
        reportNameDao.update(item4)

        val allItems = reportNameDao.getAllItem().first()
        assertEquals(allItems[0], item3)
        assertEquals(allItems[1], item4)
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteItems_deletesItemsInDb() = runBlocking {
        addTwoElements()
        reportNameDao.delete(item1)
        reportNameDao.delete(item2)

        val allItems = reportNameDao.getAllItem().first()
        assertTrue(allItems.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoSelectOneItem_selectItemInDb() = runBlocking {
        addTwoElements()

        val oneItem = reportNameDao.getOneItem(1).first()
        assertEquals(oneItem, item1)
    }
}