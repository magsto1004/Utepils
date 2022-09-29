package com.example.utepils.api

import com.example.utepils.model.Beer
import com.example.utepils.model.Drink
import com.example.utepils.model.WeatherData
import com.example.utepils.model.places.Place
import io.ktor.util.reflect.*
import junit.framework.Assert.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class DatasourceTest {
    private val dt = Datasource()
    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // Testing fetchData()
    @Test
    fun fetchDataNotNullTest() = runTest {
        val res: WeatherData = dt.fetchData()
        assertNotNull(res)
    }

    @Test
    fun fetchDataTypeTest() = runTest {
        val res: WeatherData = dt.fetchData()
        assert(res.instanceOf(WeatherData::class))
    }

    // Testing fetchDrinkJson()
    @Test
    fun fetchDrinkJsonListNotEmptyTest() = runTest {
        val res: List<Drink> = dt.fetchDrinkJson()
        assert(res.isNotEmpty())
    }

    @Test
    fun fetchDrinkJsonAllDrinksTest() = runTest {          // Om vi får med alle de satte drinkene
        val res: List<Drink> = dt.fetchDrinkJson()
        assert(res.size == 21)
    }

    // Testing fetchAllPlaces()
    @Test
    fun fetchAllPlacesListNotEmptyTest() = runTest {      // Om vi får noen steder
        val res: List<Place> = dt.fetchAllPlaces()
        assert(res.isNotEmpty())
    }

    // Testing fetchBeerJson()
    @Test
    fun fetchBeerJsonAllBeersTest() = runTest {           // Om vi får med alle ølene
        val res: List<Beer> = dt.fetchBeerJson()
        assert(res.size == 7)
    }
}