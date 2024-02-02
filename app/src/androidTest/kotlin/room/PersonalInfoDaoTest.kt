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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named


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
        Log.i("TAG", "test - 1")
        hiltRule.inject()
        Log.i("TAG", "test - 2")
        personalInfoDao = database.personalInfoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsItemDaoIntoDb() = runBlocking {
        addOneElement()
        val allItems = personalInfoDao.getAllItem().first()
        assertEquals(allItems[0], item1)
    }


}