package room

import com.example.reportsfordrivers.data.AppDatabase
import com.example.reportsfordrivers.data.dao.VehicleDao
import com.example.reportsfordrivers.data.structure.Vehicle
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
class VehicleDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var vehicleDao: VehicleDao

    private val item1 = Vehicle(1, "DAF", "1234 TE-7")
    private val item2 = Vehicle(2, "Scania", "5678 XX-7")

    private suspend fun addOneElement() {
        vehicleDao.insert(item1)
    }

    private suspend fun addTwoElements() {
        vehicleDao.insert(item1)
        vehicleDao.insert(item2)
    }

    @Before
    fun createDb() {
        hiltRule.inject()
        vehicleDao = database.vehicleDao()
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
        val allItems = vehicleDao.getAllItem().first()
        assertEquals(allItems[0], item1)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsAllItemsDaoIntoDb() = runBlocking {
        addTwoElements()
        val allItems = vehicleDao.getAllItem().first()
        assertEquals(allItems[0], item1)
        assertEquals(allItems[1], item2)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdatesItems_updatesItemsInDb() = runBlocking {
        addTwoElements()
        vehicleDao.update(Vehicle(1, "Iveco", "9999 II-7"))
        vehicleDao.update(Vehicle(2, "MAN", "1111 TT-7"))

        val allItems = vehicleDao.getAllItem().first()
        assertEquals(allItems[0], Vehicle(1, "Iveco", "9999 II-7"))
        assertEquals(allItems[1], Vehicle(2, "MAN", "1111 TT-7"))
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteItems_deletesItemsInDb() = runBlocking {
        addTwoElements()
        vehicleDao.delete(item1)
        vehicleDao.delete(item2)

        val allItems = vehicleDao.getAllItem().first()
        assertTrue(allItems.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoSelectOneItem_selectItemInDb() = runBlocking {
        addTwoElements()

        val oneItem = vehicleDao.getOneItem(1).first()
        assertEquals(oneItem, item1)
    }
}