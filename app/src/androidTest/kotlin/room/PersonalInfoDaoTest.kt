package room

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.reportsfordrivers.data.AppDatabase
import com.example.reportsfordrivers.data.dao.PersonalInfoDao
import com.example.reportsfordrivers.data.structure.PersonalInfo
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

/**
 * Класс тестирования SQL запросов класса [PersonalInfoDao].
 */
@HiltAndroidTest
class PersonalInfoDaoTest{

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var personalInfoDao: PersonalInfoDao

    private var item1 = PersonalInfo(1, "Ivanov", "Ivan", "Ivanovich")
    private var item2 = PersonalInfo(2, "Petrov", "Petr", "Petrovich")

    private suspend fun addOneElement() {
        personalInfoDao.insert(item1)
    }

    private suspend fun addTwoElements() {
        personalInfoDao.insert(item1)
        personalInfoDao.insert(item2)
    }

    @Before
    fun createDb() {
        hiltRule.inject()
        personalInfoDao = database.personalInfoDao()
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
        val allItems = personalInfoDao.getAllItem().first()
        assertEquals(allItems[0], item1)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsAllItemsDaoIntoDb() = runBlocking {
        addTwoElements()
        val allItems = personalInfoDao.getAllItem().first()
        assertEquals(allItems[0], item1)
        assertEquals(allItems[1], item2)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdatesItems_updatesItemsInDb() = runBlocking {
        addTwoElements()
        personalInfoDao.update(PersonalInfo(1, "One", "Two", "Three"))
        personalInfoDao.update(PersonalInfo(2, "One", "Two", "Three"))

        val allItems = personalInfoDao.getAllItem().first()
        assertEquals(allItems[0], PersonalInfo(1, "One", "Two", "Three"))
        assertEquals(allItems[1], PersonalInfo(2, "One", "Two", "Three"))
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteItems_deletesItemsInDb() = runBlocking {
        addTwoElements()
        personalInfoDao.delete(item1)
        personalInfoDao.delete(item2)

        val allItems = personalInfoDao.getAllItem().first()
        assertTrue(allItems.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoSelectOneItem_selectItemInDb() = runBlocking {
        addTwoElements()

        val oneItem = personalInfoDao.getOneItem(1).first()
        assertEquals(oneItem, item1)
    }

    /**
     * Вопрос, нужен ли этот тест, что будет происходить при неверном вытягивании
     */
    @Test
    @Throws(Exception::class)
    fun daoSelectOneItem_selectItemInDb_exception() = runBlocking {
        val oneItem = personalInfoDao.getOneItem(1).first()
        assertNotEquals(oneItem, item1)
    }

}