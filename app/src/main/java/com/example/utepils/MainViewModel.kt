package com.example.utepils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.utepils.api.Datasource
import com.example.utepils.model.*
import com.example.utepils.model.places.Place
import kotlinx.coroutines.launch

class MainViewModel(): ViewModel() {
    private val datasource = Datasource()

    companion object Data {
        val selectedDay: MutableState<WeatherDay?> = mutableStateOf(null)
        val weatherDays: MutableState<List<WeatherDay>> = mutableStateOf(listOf())
        val drinkData: MutableState<List<Drink>> = mutableStateOf(listOf())
        val beerData: MutableState<List<Beer>> = mutableStateOf(listOf())
        val weatherData: MutableState<WeatherData?> = mutableStateOf(null)
        val selectedDrinkData: MutableState<Drink?> = mutableStateOf(null)
    }

    // selected weatherday for drinkpage
    fun setWeatherDay(wd: WeatherDay) {
        selectedDay.value = wd
    }
    fun getWeatherDay(): WeatherDay? { return selectedDay.value }

    // all weatherdays
    fun setWeatherDays(wd: List<WeatherDay>) {
        weatherDays.value = wd
    }

    fun getWeatherDays(): List<WeatherDay> { return weatherDays.value }

    // drink data
    suspend fun fetchDrinks() {
        viewModelScope.launch {
            val res = datasource.fetchDrinkJson()
            drinkData.value = res
        }
    }

    suspend fun fetchBeers() {
        viewModelScope.launch {
            val res = datasource.fetchBeerJson()
            beerData.value = res
        }
    }

    fun setDrinkData(drinks: List<Drink>) {
        drinkData.value = drinks
    }
    fun getDrinkData(): List<Drink> { return drinkData.value }


    fun setBeerData(beers: List<Beer>) {
        beerData.value = beers
    }
    fun getBeerData(): List<Beer> { return beerData.value }

    // weather data
    suspend fun fetchWeatherData() {
        val res = datasource.fetchData()
        weatherData.value = res
    }


    fun getWeatherData(): WeatherData? { return weatherData.value }

    // GOOGLE PLACES API
    val placesData: MutableState<List<Place>?> = mutableStateOf(null)
    init{
        viewModelScope.launch {
            val res = datasource.fetchAllPlaces()
            placesData.value = res
        }
    }

    // selected drink for recipe
    fun setSelected(drink: Drink){
        selectedDrinkData.value = drink
    }
    fun getSelectedDrink(): Drink? { return selectedDrinkData.value }
}
