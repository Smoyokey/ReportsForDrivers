package com.example.reportsfordrivers.viewmodel.firstentry

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reportsfordrivers.data.AppDatabase
import com.example.reportsfordrivers.data.dao.CountryDao
import com.example.reportsfordrivers.data.dao.CurrencyDao
import com.example.reportsfordrivers.data.dao.TownshipDao
import com.example.reportsfordrivers.data.dao.VehicleAndTrailerSaveDataDao
import com.example.reportsfordrivers.data.repositories.VehicleAndTrailerSaveDataRepository
import com.example.reportsfordrivers.data.structure.Country
import com.example.reportsfordrivers.data.structure.CountryRus
import com.example.reportsfordrivers.data.structure.Currency
import com.example.reportsfordrivers.data.structure.Township
import com.example.reportsfordrivers.data.structure.TownshipRus
import com.example.reportsfordrivers.data.structure.VehicleAndTrailer
import com.example.reportsfordrivers.datastore.fiofirstentry.FioFirstEntryRepository
import com.example.reportsfordrivers.viewmodel.ObjectVehicle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import java.util.Locale
import javax.inject.Inject

private const val TAG = "FirstEntryViewModel"

@HiltViewModel
class FirstEntryViewModel @Inject constructor(
    private val fioFirstEntryPreferencesRepository: FioFirstEntryRepository,

    ) : ViewModel() {


    @Inject
    lateinit var vehicleAndTrailer: VehicleAndTrailerSaveDataDao

    @Inject
    lateinit var currencyDb: CurrencyDao

    @Inject
    lateinit var countryDb: CountryDao

    @Inject
    lateinit var townshipDb: TownshipDao

    /**
     * [uiState] - хранилище ФИО и списка машин с прицепами типа [FirstEntryUiState()].
     * [vehicleUiState] - Хранилище введенных данных о машине или прицепе типа [VehicleObject()]
     */
    var uiState = mutableStateOf(FirstEntryUiState())
        private set
    var vehicleUiState = mutableStateOf(VehicleObject())
        private set

    var listCountriesUiState = mutableStateOf(Countries())
    var listTownshipsUiState = mutableStateOf(Townships())
    var addCityUiState = mutableStateOf(AddCity())


    var isOpenDialog = mutableStateOf(false)

    var listCurrency = mutableStateOf(listOf<Currency>())


    /**
     * Метод для удаления элемента в списке транспорта (хрен знает как работает, но работает)
     */
    fun deletePositionVehicle(item: ObjectVehicle) {
        uiState.value.listVehicles.remove(item)
    }

    /**
     * Метод для обновления значения ФИО в текстовых полях
     */
    private fun updateFio(itemDetails: FioItemDetails) {
        uiState.value = uiState.value.copy(fioItemDetails = itemDetails)
    }

    fun updateLastName(lastName: String) {
        updateFio(uiState.value.fioItemDetails.copy(lastName = lastName))
    }

    fun updateFirstName(firstName: String) {
        updateFio(uiState.value.fioItemDetails.copy(firstName = firstName))
    }

    fun updatePatronymic(patronymic: String) {
        updateFio(uiState.value.fioItemDetails.copy(patronymic = patronymic))
    }

    /**
     * Метод для выбора VEHICLE(true) или TRAILER(false)
     */
    fun selectedPosition(isSelected: IsSelectedVehicleAndTrailer) {
        vehicleUiState.value = vehicleUiState.value.copy(isSelected = isSelected)
    }

    /**
     * Метод для обновления значения данных в текстовых полях MAKE и RN
     */
    private fun updateMakeRn(makeRnItemDetails: MakeRnItemDetails) {
        vehicleUiState.value = vehicleUiState.value.copy(makeRnItemDetails = makeRnItemDetails)
    }

    fun updateMake(make: String) {
        updateMakeRn(vehicleUiState.value.makeRnItemDetails.copy(make = make))
    }

    fun updateRn(rn: String) {
        updateMakeRn(vehicleUiState.value.makeRnItemDetails.copy(rn = rn))
    }

    /**
     * Метод для активации кнопки Add при условии если текстовые поля MAKE и RN заполненные
     */
    fun validateAddVehicle(): Boolean {
        return vehicleUiState.value.makeRnItemDetails.make != "" && vehicleUiState.value.makeRnItemDetails.rn != ""
    }


    /**
     * Добавление элемента в список [uiState] при условии что [validateAddVehicle()] возвращает true
     * Так же после добавления элемента полностью обновляются поля MAKE и RN, а так же RadioButton
     * сбрасывается до значения VEHICLE
     */
    fun addElementVehicle() {
        uiState.value.listVehicles.add(createObjectVehicle())
        resetVehicle()
        Log.i(TAG, "Add Element vehicle")
    }

    /**
     * Метод, который сбрасывает значения текстовых полей MAKE и RN, а так же RadioButton сбрасывается
     * до значения VEHICLE, путем создания нового объекта VehicleObject()
     */
    private fun resetVehicle() {
        vehicleUiState.value = VehicleObject()
    }

    /**
     * Создает объект для списка [uiState]
     */
    private fun createObjectVehicle(): ObjectVehicle {
        return ObjectVehicle(
            make = vehicleUiState.value.makeRnItemDetails.make,
            rn = vehicleUiState.value.makeRnItemDetails.rn,
            type = if (vehicleUiState.value.isSelected.stateRadioGroup)
                VehicleOrTrailer.VEHICLE.name
            else
                VehicleOrTrailer.TRAILER.name
        )
    }

    fun onFirstEntry() = runBlocking {
        fioFirstEntryPreferencesRepository.setFirstEntry(false)
    }

    //////////////////////////////////////////////////////
    //FirstEntryTwoScreen
    //////////////////////////////////////////////////////

    /**
     * Состояние открытия меню по добавлению городов и стран в избранное. True - меню открыто,
     * False - меню скрыто
     */
    var openSelectedCountriesAndCities = mutableStateOf(false)

    /**
     * Состояние открытия BottomSheet по выбору валюты по умолчанию. True - BottomSheet открыто,
     * False - bottomSheet закрыто
     */
    var openBottomSheetCurrency = mutableStateOf(false)

    /**
     * Состояние открытия меню по добавлению города в БД. True - Меню открыто, False - Меню закрыто
     */
    var openBottomSheetAddCity = mutableStateOf(false)

    var selectedCountryInAddCity = mutableStateOf(false)
    val state = mutableStateOf(0)

    /**
     * Поисковик пробный
     */
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()
    private val _countriesList = MutableStateFlow((listCountriesUiState.value.listCountries))
    var countriesList = countriesFilter()

    private fun countriesFilter() : StateFlow<List<CountryDetailing>> {
        return searchText
            .combine(_countriesList) { text, countries ->
                if (text.isBlank()) {
                    countries
                }
                countries.filter { country ->
                    country.country.uppercase().contains(text.trim().uppercase())
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(1000),
                initialValue = _countriesList.value
            )
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onToogleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }


    /**
     * При клике по кнопке *Выбрать валюту* открывается BottomSheet и при первом открытии
     * выгружается валюты в список
     */
    fun openBottomSheetCurrency() = runBlocking {
        Log.i(TAG, "CLICK")
        openBottomSheetCurrency.value = true
        if (listCurrency.value.isEmpty()) {
            listCurrency.value = currencyDb.getAllItem()
            Log.i(TAG, listCurrency.value.size.toString())
        }
    }


    /**
     * Функция для выгрузки списка стран из БД  и добавление в UIState.
     * Если локализация телефона русская, то выгружаются страны на русском, а если другая локализация,
     * то на английском
     */
    fun openSelectedCountry() = runBlocking {
        openSelectedCountriesAndCities.value = !openSelectedCountriesAndCities.value
        if (listCountriesUiState.value.listCountries.isEmpty()) {
            if (Locale.getDefault().language == "ru") {
                val a = countryDb.getSortNameRus()
                a.forEach {
                    listCountriesUiState.value.listCountries.add(
                        CountryDetailing(
                            id = it.id,
                            country = it.fullNameCountryRus,
                            shortCountry = it.shortNameCountry,
                            rating = it.rating,
                            favorite = it.favorite
                        )
                    )
                }
            } else {
                val a = countryDb.getSortNameEng()
                a.forEach {
                    listCountriesUiState.value.listCountries.add(
                        CountryDetailing(
                            id = it.id,
                            country = it.fullNameCountryEng,
                            shortCountry = it.shortNameCountry,
                            rating = it.rating,
                            favorite = it.favorite
                        )
                    )
                }
            }
        }
    }

    /**
     * Добавление и удаление элементов из списка избранных СТРАН
     */
    fun updateItemCountry(element: CountryDetailing) = runBlocking {
        val index = idList(element)
        listCountriesUiState.value.listCountries[index] = listCountriesUiState.value.listCountries[index].copy(
            favorite = if(listCountriesUiState.value.listCountries[index].favorite == 0) 1 else 0
        )
        if (listCountriesUiState.value.listCountries[index].favorite == 1) {
            uiState.value.selectedCountriesAndCities.listFavoriteCountry.add(
                listCountriesUiState.value.listCountries[index]
            )
        } else {
            uiState.value.selectedCountriesAndCities.listFavoriteCountry.removeAll {
                element.id == it.id
            }
        }
    }

    /**
     * Получение порядкового номера в основном списке по элементу из поискового списка
     */
    fun idList(countryDetailing: CountryDetailing): Int {
        var a = -1
        for(i in listCountriesUiState.value.listCountries.indices) {
            if(countryDetailing.id == listCountriesUiState.value.listCountries[i].id) {
                a = i
            }
        }
        return a
    }

    /**
     * Функция для выгрузки списка городов из БД и добавление в UIState
     */
    fun openSelectedTownship() = runBlocking {
        if (listTownshipsUiState.value.listTownships.isEmpty()) {
            if (Locale.getDefault().language == "ru") {
                val a = townshipDb.getSortNameRus()
                a.forEach {
                    listTownshipsUiState.value.listTownships.add(
                        TownshipDetailing(
                            id = it.id,
                            township = it.townshipRus,
                            countryId = it.countryId,
                            rating = it.rating,
                            favorite = it.favorite
                        )
                    )
                }
            } else {
                val a = townshipDb.getSortNameEng()
                a.forEach {
                    listTownshipsUiState.value.listTownships.add(
                        TownshipDetailing(
                            id = it.id,
                            township = it.townshipEng,
                            countryId = it.countryId,
                            rating = it.rating,
                            favorite = it.favorite
                        )
                    )
                }
            }
        }
    }

    /**
     * Добавление и удаление элементов из списка избранных ГОРОДОВ
     */
    fun updateItemTownship(index: Int, element: TownshipDetailing) {
        listTownshipsUiState.value.listTownships[index] =
            listTownshipsUiState.value.listTownships[index].copy(
                favorite = if (listTownshipsUiState.value.listTownships[index].favorite == 0) 1 else 0
            )
        if (listTownshipsUiState.value.listTownships[index].favorite == 1) {
            uiState.value.selectedCountriesAndCities.listFavoriteTownship.add(
                listTownshipsUiState.value.listTownships[index]
            )
        } else {
            uiState.value.selectedCountriesAndCities.listFavoriteTownship.removeAll {
                element.id == it.id
            }
        }
    }


    /**
     * Добавление городов в БД
     */
    fun updateAddCityNameCity(nameCity: String) {
        addCityUiState.value = addCityUiState.value.copy(nameCity = nameCity)
    }

    fun updateAddCityNameCountry(nameCountry: String) {
        addCityUiState.value = addCityUiState.value.copy(nameCountry = nameCountry)
    }

    fun updateAddCityCountry(country: CountryDetailing) {
        addCityUiState.value = addCityUiState.value.copy(country = country)
        Log.i(TAG, addCityUiState.value.country.toString())
    }

    fun validateAddCity(): Boolean {
        return addCityUiState.value.country.country.isNotEmpty() &&
                addCityUiState.value.nameCity.isNotEmpty()
    }

    /**
     * Сохранение города в БД, закрытие BottomSheet
     */
    fun saveAddCity() = runBlocking {
        townshipDb.insert(
            Township(
                townshipRus = addCityUiState.value.nameCity,
                townshipEng = addCityUiState.value.nameCity,
                countryId = addCityUiState.value.country.id,
                rating = 0,
                favorite = 0
            )
        )
        val a = townshipDb.getOneItemName(addCityUiState.value.nameCity)
        listTownshipsUiState.value.listTownships.add(
            TownshipDetailing(
                id = a.id,
                township = a.townshipEng,
                countryId = a.countryId,
                rating = a.rating,
                favorite = a.favorite
            )
        )
    }

    /**
     * Сохранение всех параметров и выход в MainMenu
     */
    fun saveButton() = runBlocking {
        saveFirstName()
        saveLastName()
        savePatronymic()

        saveListVehicle()

        saveLanguage()
        saveCurrency()

        saveListFavoriteCountry()
        saveListFavoriteTownship()
    }

    /**
     * Сохранение firstName в DataStore
     */
    private fun saveFirstName() = runBlocking {
        if (uiState.value.fioItemDetails.firstName.isNotEmpty()) {
            fioFirstEntryPreferencesRepository.setFirstName(uiState.value.fioItemDetails.firstName)
        }
    }

    /**
     * Сохранение lastName в DataStore
     */
    private fun saveLastName() = runBlocking {
        if (uiState.value.fioItemDetails.lastName.isNotEmpty()) {
            fioFirstEntryPreferencesRepository.setLastName(uiState.value.fioItemDetails.lastName)
        }
    }

    /**
     * Сохранение patronymic в DataStore
     */
    private fun savePatronymic() = runBlocking {
        if (uiState.value.fioItemDetails.patronymic.isNotEmpty()) {
            fioFirstEntryPreferencesRepository.setPatronymic(uiState.value.fioItemDetails.patronymic)
        }
    }

    /**
     * Сохранение listVehicle в БД
     */
    private fun saveListVehicle() = runBlocking {
        if (uiState.value.listVehicles.size > 0) {
            for (i in uiState.value.listVehicles) {
                val a = VehicleAndTrailer(
                    vehicleOrTrailer = i.type,
                    make = i.make,
                    registrationNumber = i.rn
                )
                vehicleAndTrailer.insert(a)
            }
        }
    }

    /**
     * Сохранение языка в DataStore
     */
    private fun saveLanguage() {

    }

    /**
     * Сохранение валюты по умолчанию в DataStore
     */
    private fun saveCurrency() {

    }

    /**
     * Сохранение listFavoriteCountry в БД
     */
    private fun saveListFavoriteCountry() = runBlocking {
        if (uiState.value.selectedCountriesAndCities.listFavoriteCountry.isNotEmpty()) {
            Log.i(
                TAG,
                uiState.value.selectedCountriesAndCities.listFavoriteCountry.joinToString("::")
            )
            for (i in uiState.value.selectedCountriesAndCities.listFavoriteCountry) {
                countryDb.updateFavorite(i.favorite, i.id)
            }
        }
    }

    /**
     * Сохранение listFavoriteTownship в БД
     */
    private fun saveListFavoriteTownship() = runBlocking {
        if (uiState.value.selectedCountriesAndCities.listFavoriteTownship.isNotEmpty()) {
            for (i in uiState.value.selectedCountriesAndCities.listFavoriteTownship) {
                townshipDb.updateFavoriteName(i.favorite, i.township)
            }
        }
    }
}
