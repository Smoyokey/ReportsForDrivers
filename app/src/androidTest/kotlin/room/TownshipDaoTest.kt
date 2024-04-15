package room

import android.util.Log
import com.example.reportsfordrivers.data.AppDatabase
import com.example.reportsfordrivers.data.dao.TownshipDao
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

private const val TAG = "TownshipDaoTest"

@HiltAndroidTest
class TownshipDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var townshipDao: TownshipDao

    @Before
    fun createDb() {
        hiltRule.inject()
        townshipDao = database.townshipDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun getSortNameRus() = runBlocking {
        val list = townshipDao.getSortNameRus()
        assertTrue(list[0].townshipRus == "Астана")
        assertTrue(list.size == 17)
    }

    @Test
    fun getSortNameEng() = runBlocking {
        val list = townshipDao.getSortNameEng()
        assertTrue(list[0].townshipEng == "Astana")
        assertTrue(list.size == 17)
    }

    @Test
    fun getSortRatingRus() = runBlocking {
        val list = townshipDao.getSortRatingRus()
        assertTrue(list[0].townshipRus == "Гродно")
        assertTrue(list.size == 17)
    }

    @Test
    fun getSortRatingEng() = runBlocking {
        val list = townshipDao.getSortRatingEng()
        assertTrue(list[0].townshipEng == "Grodno")
        assertTrue(list.size == 17)
    }

    @Test
    fun getFavoriteSortNameRus() = runBlocking {
        val list = townshipDao.getFavoriteSortNameRus()
        assertTrue(list[0].townshipRus == "Брест")
        assertTrue(list.size == 6)
    }

    @Test
    fun getFavoriteSortNameEng() = runBlocking {
        val list = townshipDao.getFavoriteSortNameEng()
        assertTrue(list[0].townshipEng == "Brest")
        assertTrue(list.size == 6)
    }

    @Test
    fun getFavoriteSortRatingRus() = runBlocking {
        val list = townshipDao.getFavoriteSortRatingRus()
        assertTrue(list[0].townshipRus == "Минск")
        assertTrue(list.size == 6)
    }

    @Test
    fun getFavoriteSortRatingEng() = runBlocking {
        val list = townshipDao.getFavoriteSortRatingEng()
        assertTrue(list[0].townshipEng == "Minsk")
        assertTrue(list.size == 6)
    }

    @Test
    fun getNameTownshipSortNameRus() = runBlocking {
        val list = townshipDao.getNameTownshipSortNameRus("В")
        assertTrue(list[0].townshipRus == "Варшава")
        assertTrue(list.size == 3)
    }

    @Test
    fun getNameTownshipSortNameEng() = runBlocking {
        val list = townshipDao.getNameTownshipSortNameEng("V")
        assertTrue(list[0].townshipEng == "Varshava")
        assertTrue(list.size == 3)
    }

    @Test
    fun getNameTownshipSortRatingRus() = runBlocking {
        val list = townshipDao.getNameTownshipSortRatingRus("В")
        assertTrue(list[0].townshipRus == "Вильнюс")
        assertTrue(list.size == 3)
    }

    @Test
    fun getNameTownshipSortRatingEng() = runBlocking {
        val list = townshipDao.getNameTownshipSortRatingEng("V")
        assertTrue(list[0].townshipEng == "Vilnus")
        assertTrue(list.size == 3)
    }

    @Test
    fun getCountryIdTownshipSortNameRus() = runBlocking {
        val list = townshipDao.getCountryIdTownshipSortNameRus(2)
        assertTrue(list[0].townshipRus == "Владивосток")
        assertTrue(list.size == 6)
    }

    @Test
    fun getCountryIdTownshipSortNameEng() = runBlocking {
        val list = townshipDao.getCountryIdTownshipSortNameEng(2)
        Log.i(TAG, list[0].townshipEng)
        assertTrue(list[0].townshipEng == "Kazan")
        assertTrue(list.size == 6)
    }

    @Test
    fun getCountryIdTownshipSortRatingRus() = runBlocking {
        val list = townshipDao.getCountryIdTownshipSortRatingRus(2)
        assertTrue(list[0].townshipRus == "Москва")
        assertTrue(list.size == 6)
    }

    @Test
    fun getCountryIdTownshipSortRatingEng() = runBlocking {
        val list = townshipDao.getCountryIdTownshipSortRatingEng(2)
        assertTrue(list[0].townshipEng == "Moskva")
        assertTrue(list.size == 6)
    }

    @Test
    fun getCountryIdNameTownshipSortNameRus() = runBlocking {
        val list = townshipDao.getCountryIdNameTownshipSortNameRus(1, "Г")
        assertTrue(list[0].townshipRus == "Гомель")
        assertTrue(list.size == 2)
    }

    @Test
    fun getCountryIdNameTownshipSortNameEng() = runBlocking {
        val list = townshipDao.getCountryIdNameTownshipSortNameEng(1, "G")
        assertTrue(list[0].townshipEng == "Gomel")
        assertTrue(list.size == 2)
    }

    @Test
    fun getCountryIdNameTownshipSortRatingRus() = runBlocking {
        val list = townshipDao.getCountryIdNameTownshipSortRatingRus(1, "Г")
        assertTrue(list[0].townshipRus == "Гродно")
        assertTrue(list.size == 2)
    }

    @Test
    fun getCountryIdNameTownshipSortRatingEng() = runBlocking {
        val list = townshipDao.getCountryIdNameTownshipSortRatingEng(1, "G")
        assertTrue(list[0].townshipEng == "Grodno")
        assertTrue(list.size == 2)
    }

    @Test
    fun getCountryIdFavoriteSortNameRus() = runBlocking {
        val list = townshipDao.getCountryIdFavoriteSortNameRus(1)
        assertTrue(list[0].townshipRus == "Брест")
        assertTrue(list.size == 3)
    }

    @Test
    fun getCountryIdFavoriteSortNameEng() = runBlocking {
        val list = townshipDao.getCountryIdFavoriteSortNameEng(1)
        assertTrue(list[0].townshipEng == "Brest")
        assertTrue(list.size == 3)
    }

    @Test
    fun getCountryIdFavoriteSortRatingRus() = runBlocking {
        val list = townshipDao.getCountryIdFavoriteSortRatingRus(1)
        assertTrue(list[0].townshipRus == "Минск")
        assertTrue(list.size == 3)
    }

    @Test
    fun getCountryIdFavoriteSortRatingEng() = runBlocking {
        val list = townshipDao.getCountryIdFavoriteSortRatingEng(1)
        assertTrue(list[0].townshipEng == "Minsk")
        assertTrue(list.size == 3)
    }

    @Test
    fun getCountryIdNameTownshipFavoriteSortNameRus() = runBlocking {
        val list = townshipDao.getCountryIdNameTownshipFavoriteSortNameRus(1, "М")
        assertTrue(list[0].townshipRus == "Минск")
        assertTrue(list.size == 2)
    }

    @Test
    fun getCountryIdNameTownshipFavoriteSortNameEng() = runBlocking {
        val list = townshipDao.getCountryIdNameTownshipFavoriteSortNameEng(1, "M")
        assertTrue(list[0].townshipEng == "Minsk")
        assertTrue(list.size == 2)
    }

    @Test
    fun getCountryIdNameTownshipFavoriteSortRatingRus() = runBlocking {
        val list = townshipDao.getCountryIdNameTownshipFavoriteSortRatingRus(1, "М")
        assertTrue(list[0].townshipRus == "Минск")
        assertTrue(list.size == 2)
    }

    @Test
    fun getCountryIdNameTownshipFavoriteSortRatingEng() = runBlocking {
        val list = townshipDao.getCountryIdNameTownshipFavoriteSortRatingEng(1, "M")
        Log.i(TAG, list[0].townshipEng)
        assertTrue(list[0].townshipEng == "Minsk")
        assertTrue(list.size == 2)
    }
}