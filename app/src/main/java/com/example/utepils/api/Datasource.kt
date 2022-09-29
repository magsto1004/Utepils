package com.example.utepils.api

import android.util.Log
import androidx.compose.ui.input.key.Key.Companion.D
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import com.example.utepils.BuildConfig.MAPS_API_KEY
import com.example.utepils.model.*
import com.example.utepils.model.places.Places
import com.example.utepils.model.places.Place
import com.example.utepils.model.places.PlacesDetailsResponse

class Datasource() {

    private var drinkCache: List<Drink>? = null
    private var beerCache: List<Beer>? = null
    private var placeCache = mutableMapOf<String,Place>()
    private val client = HttpClient{install(JsonFeature)}
    val latitude = 59.94229
    val longitude = 10.71674
    private val METPath = "https://api.met.no/weatherapi/locationforecast/2.0/compact.json?lat=59.911491&lon=10.757933"
    private val drinkPath = "https://www.mn.uio.no/ifi/personer/adm/armanbh/drinks.json"
    private val beerPath = "https://www.mn.uio.no/ifi/personer/adm/armanbh/beers.json"


    private val paths: List<String> = listOf(
        "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${latitude},${longitude}&radius=10000&type=bar&key=${MAPS_API_KEY}",
        "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${latitude},${longitude}&radius=10000&type=night_club&key=${MAPS_API_KEY}",
    )
    // alternative types til places search: resturant(youngs),live_music_venue (blaa)
    suspend fun fetchData(): WeatherData {
        val wdata: WeatherData = client.get(METPath)
        return wdata
    }


    suspend fun fetchDrinkJson(): List<Drink> {
        if (drinkCache == null) {
            Log.d("API", "Fetching data from: $drinkPath")
            val res: Drinks = client.get(drinkPath)
            drinkCache = res.drinks
        }else{
            Log.d("API","Fetching data from cache")
        }

        return drinkCache as List<Drink>
    }

    private suspend fun fetchPlaceDetails(placeId: String): Place{
        val response: PlacesDetailsResponse = client.get("https://maps.googleapis.com/maps/api/place/details/json?place_id=${placeId}&fields=name%2Cvicinity%2Cgeometry%2Ctypes%2Crating%2Cprice_level%2Copening_hours%2Cwebsite&key=${MAPS_API_KEY}")
        Log.d("API TEST",response.result.toString())
        placeCache[placeId] = response.result
        return response.result
    }

    private suspend fun fetchPlacesJson(path: String): List<Place>{
        val placesData: Places = client.get(path)

        return placesData.results.map {
            fetchPlaceDetails(it.place_id)
        }
    }

    suspend fun fetchAllPlaces(): List<Place>{
        if (placeCache.isEmpty()) {
            for (p in paths) {
                fetchPlacesJson(p)
            }
        }
        return placeCache.values.toList()
    }

    suspend fun fetchBeerJson(): List<Beer> {
        if (beerCache == null) {
            Log.d("API", "Fetching data from: $beerPath")
            val res: Beers = client.get(beerPath)
            beerCache = res.beers
        }else{
            Log.d("API","Fetching data from cache")
        }
        beerCache!!.forEach { it -> Log.d("BEER API",it.name + " " + it.pictureURL)}
        return beerCache as List<Beer>
    }

}
