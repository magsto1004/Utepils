package com.example.utepils

import androidx.fragment.app.activityViewModels
import com.example.utepils.model.Beer
import com.example.utepils.model.Drink
import com.example.utepils.model.WeatherData
import com.example.utepils.model.places.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlin.time.TestTimeSource
import kotlin.time.measureTime

@ExperimentalCoroutinesApi
class MainViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()
    @BeforeEach
    fun setUp() = Dispatchers.setMain(testDispatcher)
    @AfterEach
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun `getWeatherDay Null before fetching`() {
        val viewModel = MainViewModel()
        val res = viewModel.getWeatherDay()
        assertNull(res)
    }

    @Test
    fun `getWeatherDays empty before fetching`() {
        val viewModel = MainViewModel()
        val res = viewModel.getWeatherDays()
        assert(res.isEmpty())
    }

    @Test
    fun `getDrinkData empty before fetching`() {
        val viewModel = MainViewModel()
        val res = viewModel.getDrinkData()
        assert(res.isEmpty())
    }

    @Test
    fun `getBeerData empty before fetching`() {
        val viewModel = MainViewModel()
        val res = viewModel.getBeerData()
        assert(res.isEmpty())
    }

    @Test
    fun `getWeatherData Null before fetching`() {
        val viewModel = MainViewModel()
        val res = viewModel.getWeatherData()
        assertNull(res)
    }

    @Test
    fun `getSelectedDrink Null before fetching`() {
        val viewModel = MainViewModel()
        val res = viewModel.getSelectedDrink()
        assertNull(res)
    }

    @Test
    fun `getWeatherData not Null after fetching`() = runTest {
        val viewModel = MainViewModel()
        var res: WeatherData? = viewModel.getWeatherData()
        assertNull(res)
        viewModel.fetchWeatherData()
        res = viewModel.getWeatherData()
        assert(res != null)
    }

    @Test
    fun `getDrinkData not empty after fetching`() = runTest {
        val viewModel = MainViewModel()
        var res: List<Drink> = viewModel.getDrinkData()
        assert(res.isEmpty())
        suspend {
            viewModel.fetchDrinks()
            res = viewModel.getDrinkData()
            assert(res.isNotEmpty())
        }

    }

    @Test
    fun `getBeerData not Null after fetching`() = runTest {
        val viewModel = MainViewModel()
        var res: List<Beer> = viewModel.getBeerData()
        assert(res.isEmpty())
        suspend {
            viewModel.fetchBeers()
            res = viewModel.getBeerData()
            assert(res.isNotEmpty())
        }
    }

    @Test
    fun `Time test for all fetches`() = runTest {
        val viewModel = MainViewModel()
        var weatherRes: WeatherData?
        var drinkRes: List<Drink>
        var beerRes: List<Beer>
        var placeRes: List<Place>?
        suspend {
            viewModel.fetchWeatherData()
            weatherRes = viewModel.getWeatherData()
            viewModel.fetchDrinks()
            drinkRes = viewModel.getDrinkData()
            viewModel.fetchBeers()
            beerRes = viewModel.getBeerData()
            placeRes = viewModel.placesData.value
            while (weatherRes == null && drinkRes.isEmpty() && beerRes.isEmpty() && placeRes == null)
            assert(weatherRes != null)
            assert(drinkRes.isNotEmpty())
            assert(beerRes.isNotEmpty())
        }
    }
}