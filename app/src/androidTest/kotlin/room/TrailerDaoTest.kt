package room

import com.example.reportsfordrivers.data.AppDatabase
import com.example.reportsfordrivers.data.dao.TrailerDao
import com.example.reportsfordrivers.data.structure.Trailer
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
class TrailerDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var trailerDao: TrailerDao

    private var item1 = Trailer(1, "Schmitz", "P234 II-7")
    private var item2 = Trailer(2, "Schmits", "P678 II-7")

    private suspend fun addOneElement() {
        trailerDao.insert(item1)
    }

    private suspend fun addTwoElements() {
        trailerDao.insert(item1)
        trailerDao.insert(item2)
    }

    @Before
    fun createDb() {
        hiltRule.inject()
        trailerDao = database.trailerDao()
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
        val allItems = trailerDao.getAllItem().first()
        assertEquals(allItems[0], item1)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsAllItemsDaoIntoDb() = runBlocking {
        addTwoElements()
        val allItems = trailerDao.getAllItem().first()
        assertEquals(allItems[0], item1)
        assertEquals(allItems[1], item2)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdatesItems_updatesItemsInDb() = runBlocking {
        addTwoElements()
        trailerDao.update(Trailer(1, "A", "B"))
        trailerDao.update(Trailer(2, "C", "D"))

        val allItems = trailerDao.getAllItem().first()
        assertEquals(allItems[0], Trailer(1, "A", "B"))
        assertEquals(allItems[1], Trailer(2, "C", "D"))
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteItems_deletesItemsInDb() = runBlocking {
        addTwoElements()
        trailerDao.delete(item1)
        trailerDao.delete(item2)

        val allItems = trailerDao.getAllItem().first()
        assertTrue(allItems.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoSelectOneItem_selectItemInDb() = runBlocking {
        addTwoElements()

        val oneItem = trailerDao.getOneItem(1).first()
        assertEquals(oneItem, item1)
    }
}