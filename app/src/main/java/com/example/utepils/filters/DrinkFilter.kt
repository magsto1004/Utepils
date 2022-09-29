package com.example.utepils.filters

import android.util.Log
import com.example.utepils.model.Drink

class DrinkFilter() {
    private val weatherToDrink: HashMap<String, List<String>> = hashMapOf(
        "SpringNice" to listOf<String>("fruktig", "lett", "frisk"),
        "SpringSubpar" to listOf<String>("fruktig", "fyldig", "søt", "tung"),
        "SummerNice" to listOf<String>("lett", "fruktig", "søt", "frisk", "sur", "bitter", "salt"),
        "SummerSubpar" to listOf<String>("lett", "fruktig", "søt", "frisk", "fyldig"),
        "AutumnNice" to listOf<String>("fyldig", "sur", "bitter", "fruktig", "frisk"),
        "AutumnSubpar" to listOf<String>("fyldig", "sur", "bitter", "krydret", "varm", "tung"),
        "WinterNice" to listOf<String>("tung", "fyldig", "krydret", "varm", "bitter"),
        "WinterSubpar" to listOf<String>("tung", "fyldig", "krydret", "varm", "søt"),
    )

     fun sortDrinks(temp: Int, weatherIcon: String, season: String, drinks: List<Drink>): List<Drink> {
        val seasonTags = getTags(temp, weatherIcon, season)
        Log.d("TESTTAGS", seasonTags.toString())
        if (seasonTags != null) {
            drinks.forEach { drink ->
                drink.score = 0
            }
            drinks.forEach { drink ->
                drink.tags.forEach { tag ->
                    if (seasonTags.contains(tag)) drink.score++
                }
            }
        }
        return drinks.sortedByDescending { it.score }
    }

    private fun getTags(temp: Int, weatherIcon: String, season: String): List<String>? {
        when (season) {
            "Winter" -> {
                return if (temp >= -2 && listOf("clear_day", "partly_cloudy_day")
                        .contains(weatherIcon)) weatherToDrink["WinterNice"]
                else weatherToDrink["WinterSubpar"]
            }
            "Spring" -> {
                return if (temp >= 10 && listOf("clear_day", "partly_cloudy_day")
                        .contains(weatherIcon)) weatherToDrink["SpringNice"]
                else weatherToDrink["SpringSubpar"]
            }
            "Summer" -> {
                return if (temp >= 18 && listOf("clear_day", "partly_cloudy_day")
                        .contains(weatherIcon)) weatherToDrink["SummerNice"]
                else weatherToDrink["SummerSubpar"]
            }
            else -> {
                return if (temp >= 7 && listOf("clear_day", "partly_cloudy_day")
                        .contains(weatherIcon)) weatherToDrink["AutumnNice"]
                else weatherToDrink["AutumnSubpar"]
            }
        }
    }
}