package room

import com.example.reportsfordrivers.data.AppDatabase
import com.example.reportsfordrivers.data.dao.ProgressReportDao
import com.example.reportsfordrivers.data.structure.ProgressReport
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
class ProgressReportDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var progressReportDao: ProgressReportDao

    private var item1 = ProgressReport(
        1,
        "1",
        "1",
        "1",
        "1",
        "1",
        1
    )
    private var item2 = ProgressReport(
        2,
        "2",
        "2",
        "2",
        "2",
        "2",
        2
    )
    private var item3 = ProgressReport(
        1,
        "3",
        "3",
        "3",
        "3",
        "3",
        3
    )
    private var item4 = ProgressReport(
        2,
        "4",
        "4",
        "4",
        "4",
        "4",
        4
    )

    private suspend fun addOneElement() {
        progressReportDao.insert(item1)
    }

    private suspend fun addTwoElements() {
        progressReportDao.insert(item1)
        progressReportDao.insert(item2)
    }

    @Before
    fun createDb() {
        hiltRule.inject()
        progressReportDao = database.progressReportDao()
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
        val allItems = progressReportDao.getAllItem().first()
        assertEquals(allItems[0], item1)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsAllItemsDaoInfoDb() = runBlocking {
        addTwoElements()
        val allItems = progressReportDao.getAllItem().first()
        assertEquals(allItems[0], item1)
        assertEquals(allItems[1], item2)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdatesItems_updatesItemsInDb() = runBlocking {
        addTwoElements()
        progressReportDao.update(item3)
        progressReportDao.update(item4)

        val allItems = progressReportDao.getAllItem().first()
        assertEquals(allItems[0], item3)
        assertEquals(allItems[1], item4)
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteItems_deletesItemsInDb() = runBlocking {
        addTwoElements()
        progressReportDao.delete(item1)
        progressReportDao.delete(item2)

        val allItems = progressReportDao.getAllItem().first()
        assertTrue(allItems.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoSelectOneItem_selectItemInDb() = runBlocking {
        addTwoElements()

        val oneItem = progressReportDao.getOneItem(1).first()
        assertEquals(oneItem, item1)
    }
}