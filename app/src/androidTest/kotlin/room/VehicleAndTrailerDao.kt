package room

import com.example.reportsfordrivers.data.AppDatabase
import com.example.reportsfordrivers.data.dao.VehicleAndTrailerSaveDataDao
import com.example.reportsfordrivers.data.structure.VehicleAndTrailer
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
class VehicleAndTrailerDao {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var vehicleAndTrailerDao: VehicleAndTrailerSaveDataDao

    private var item1 = VehicleAndTrailer(1, "1", "1", "!")
    private var item2 = VehicleAndTrailer(2, "2", "2", "2")
    private var item3 = VehicleAndTrailer(1, "3", "3", "3")
    private var item4 = VehicleAndTrailer(2, "4", "4", "4")

    private suspend fun addOneElement() {
        vehicleAndTrailerDao.insert(item1)
    }

    private suspend fun addTwoElements() {
        vehicleAndTrailerDao.insert(item1)
        vehicleAndTrailerDao.insert(item2)
    }

    @Before
    fun createDb() {
        hiltRule.inject()
        vehicleAndTrailerDao = database.vehicleAndTrailerDao()
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
        val allItems = vehicleAndTrailerDao.getAllItem().first()
        assertEquals(allItems[0], item1)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsAllItemsDaoIntoDb() = runBlocking {
        addTwoElements()
        val allItems = vehicleAndTrailerDao.getAllItem().first()
        assertEquals(allItems[0], item1)
        assertEquals(allItems[1], item2)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdatesItems_updatesItemsInDb() = runBlocking {
        addTwoElements()
        vehicleAndTrailerDao.update(item3)
        vehicleAndTrailerDao.update(item4)

        val allItems = vehicleAndTrailerDao.getAllItem().first()
        assertEquals(allItems[0], item3)
        assertEquals(allItems[1], item4)
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteItems_deletesItemsInDb() = runBlocking {
        addTwoElements()
        vehicleAndTrailerDao.delete(item1)
        vehicleAndTrailerDao.delete(item2)

        val allItems = vehicleAndTrailerDao.getAllItem().first()
        assertTrue(allItems.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoSelectOneItem_selectItemInDb() = runBlocking {
        addTwoElements()

        val oneItem = vehicleAndTrailerDao.getOneItem(1).first()
        assertEquals(oneItem, item1)
    }
}