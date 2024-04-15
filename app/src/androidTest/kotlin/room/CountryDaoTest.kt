package room

import android.util.Log
import com.example.reportsfordrivers.data.AppDatabase
import com.example.reportsfordrivers.data.dao.CountryDao
import com.example.reportsfordrivers.data.structure.Country
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

private const val TAG = "CountryDaoTest"

@HiltAndroidTest
class CountryDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var countryDao: CountryDao


    @Before
    fun createDb() {
        hiltRule.inject()
        countryDao = database.countryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun getSortNameRus() = runBlocking {
        val list = countryDao.getSortNameRus()
        assertTrue(list[0].fullNameCountryRus == "Беларусь")
        assertTrue(list.size == 7)
    }

    @Test
    fun getSortNameEng() = runBlocking {
        val list = countryDao.getSortNameEng()
        assertTrue(list[0].fullNameCountryEng == "Belarus")
        assertTrue(list.size == 7)
    }

    @Test
    fun getSortRatingRus() = runBlocking {
        val list = countryDao.getSortRatingRus()
        assertTrue(list[0].fullNameCountryRus == "Россия")
        assertTrue(list.size == 7)
    }

    @Test
    fun getSortRatingEng() = runBlocking {
        val list = countryDao.getSortRatingEng()
        assertTrue(list[0].fullNameCountryEng == "Russia")
        assertTrue(list.size == 7)
    }

    @Test
    fun getFavoriteSortNameRus() = runBlocking {
        val list = countryDao.getFavoriteSortNameRus()
        assertTrue(list[0].fullNameCountryRus == "Беларусь")
        assertTrue(list.size == 3)
    }

    @Test
    fun getFavoriteSortNameEng() = runBlocking {
        val list = countryDao.getFavoriteSortNameEng()
        assertTrue(list[0].fullNameCountryEng == "Belarus")
        assertTrue(list.size == 3)
    }

    @Test
    fun getFavoriteSortRatingRus() = runBlocking {
        val list = countryDao.getFavoriteSortRatingRus()
        assertTrue(list[0].fullNameCountryRus == "Россия")
        assertTrue(list.size == 3)
    }

    @Test
    fun getFavoriteSortRatingEng() = runBlocking {
        val list = countryDao.getFavoriteSortRatingEng()
        assertTrue(list[0].fullNameCountryEng == "Russia")
        assertTrue(list.size == 3)
    }

    @Test
    fun getNameCountrySortNameRus() = runBlocking {
        val list = countryDao.getNameCountrySortNameRus("Б")
        Log.i(TAG, list.size.toString())
        assertTrue(list[0].fullNameCountryRus == "Беларусь")
        assertTrue(list.size == 2)
    }

    @Test
    fun getNameCountrySortNameEng() = runBlocking {
        val list = countryDao.getNameCountrySortNameEng("B")
        assertTrue(list[0].fullNameCountryEng == "Belarus")
        assertTrue(list.size == 2)
    }

    @Test
    fun getNameCountrySortRatingRus() = runBlocking {
        val list = countryDao.getNameCountrySortRatingRus("Б")
        assertTrue(list[0].fullNameCountryRus == "Беларусь")
        assertTrue(list.size == 2)
    }

    @Test
    fun getNameCountrySortRatingEng() = runBlocking {
        val list = countryDao.getNameCountrySortRatingEng("B")
        assertTrue(list[0].fullNameCountryEng == "Belarus")
        assertTrue(list.size == 2)
    }

    @Test
    fun getNameCountryFavoriteSortNameRus() = runBlocking {
        val list = countryDao.getNameCountryFavoriteSortNameRus("Б")
        assertTrue(list[0].fullNameCountryRus == "Беларусь")
        assertTrue(list.size == 1)
    }

    @Test
    fun getNameCountryFavoriteSortNameEng() = runBlocking {
        val list = countryDao.getNameCountryFavoriteSortNameEng("L")
        assertTrue(list[0].fullNameCountryEng == "Litva")
        assertTrue(list.size == 1)
    }

    @Test
    fun getNameCountryFavoriteSortRatingRus() = runBlocking {
        val list = countryDao.getNameCountryFavoriteSortRatingRus("Л")
        assertTrue(list[0].fullNameCountryRus == "Литва")
        assertTrue(list.size == 1)
    }

    @Test
    fun getNameCountryFavoriteSortRatingEng() = runBlocking {
        val list = countryDao.getNameCountryFavoriteSortRatingEng("L")
        assertTrue(list[0].fullNameCountryEng == "Litva")
        assertTrue(list.size == 1)
    }

    @Test
    fun getNameCountrySortNameRus_isEmpty() = runBlocking {
        val list = countryDao.getNameCountrySortNameRus("LLLLL")
        assertTrue(list.isEmpty())
    }

    @Test
    fun getNameCountrySortNameEng_isEmpty() = runBlocking {
        val list = countryDao.getNameCountrySortNameEng("LLLLL")
        assertTrue(list.isEmpty())
    }


}