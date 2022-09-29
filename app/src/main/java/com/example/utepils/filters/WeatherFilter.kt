package com.example.utepils.filters

import android.content.Context
import com.example.utepils.R
import com.example.utepils.model.Timesery
import com.example.utepils.model.WeatherData
import com.example.utepils.model.WeatherDay
import java.sql.Timestamp

class WeatherFilter (context: Context){
    private val weekdays = context.resources.getStringArray(R.array.weekDays)
    private val months = context.resources.getStringArray(R.array.months)

    fun slice(wd: WeatherData) : List<WeatherDay> {
        val weatherDays = mutableListOf<WeatherDay>()
        val timestamps = wd.properties.timeseries
        val mem = mutableListOf<Int>()

        timestamps.forEach { timeser ->
            val timeList = mutableListOf<Timesery>()
            val t = stringFormat(timeser.time)
            timeList.add(timeser)
            val date = t.date
            if (!mem.contains(date)) {
                timestamps.forEach {
                    val tn = stringFormat(it.time)
                    val nDate = tn.date
                    if (date == nDate) {
                        timeList.add(it)
                    }
                }
                mem.add(date)
                val w = WeatherDay(timeList)
                weatherDays.add(w)
            }
        }
        return weatherDays
    }

    // String to Timestamp
    private fun stringFormat(s : String): Timestamp {
        var n = s.replace("T", " ")
        n = n.replace("Z", "")
        return Timestamp.valueOf(n)
    }

    //Timestamp to displayString
    private fun displayString(t : Timestamp): String {
        val weekday = weekdays[t.day]
        val month = months[t.month]
        return weekday + " " + t.date + "." + month
    }

    fun getDateList(days: List<WeatherDay>): List<String> {
        val dateList = mutableListOf<String>()
        days.forEach { day ->
            val timestamp = stringFormat(day.times[0].time)
            dateList.add(displayString(timestamp))
        }
        return dateList
    }

    fun getDays(days: List<WeatherDay>): List<String> {
        val dateNumbers = mutableListOf<String>()
        days.forEach { day ->
            val timestamp = stringFormat(day.times[0].time)
            dateNumbers.add(timestamp.date.toString())
            if (dateNumbers.size == 7) return dateNumbers
        }
        return dateNumbers
    }

    fun getTemps(days: List<WeatherDay>): List<String> {
        val temps = mutableListOf<String>()
        days.forEach { day ->
            val highestTemp = getHighestTemp(day)
            temps.add(highestTemp.toString())
        }
        return temps
    }

    fun getIcons(days: List<WeatherDay>): List<String> {
        val icons = mutableListOf<String>()
        days.forEach { day ->
            icons.add(getIcon(day))
        }
        return icons
    }

    fun getIcon(day: WeatherDay): String {
        if (day.times[0].data.next_12_hours == null) { return "" }
        val iconCode = day.times[0].data.next_12_hours.summary.symbol_code
        val fc = iconCode.split("_").toTypedArray()[0]
        return if (listOf("clearsky").contains(fc)) "clear_day"
        else if (listOf("cloudy").contains(fc)) "cloudy"
        else if (listOf("fair", "partlycloudy").contains(fc)) "partly_cloudy_day"
        else if (listOf("fog").contains(fc)) "fog"
        else if (listOf("heavyrain").contains(fc)) "heavy_showers"
        else if (listOf("heavysleetshowers", "heavysleet").contains(fc)) "heavy_sleet"
        else if (listOf("heavysnow", "heavysnowshowers").contains(fc)) "heavy_snow"
        else if (listOf("heavyrainshowers", "lightrain", "lightrainshowers", "rain", "rainshowers").contains(fc)) "showers"
        else if (listOf("lightsleet", "lightsleetshowers", "sleet", "sleetshowers").contains(fc)) "sleet"
        else if (listOf("lightsnow", "lightsnowshowers", "snow", "snowshowers").contains(fc)) "snow"
        else if (listOf("heavyrainandthunder", "heavyrainshowersandthunder", "lightrainandthunder",
                "lightrainshowersandthunder", "rainandthunder", "rainshowersandthunder").contains(fc)) "thunderstorm_showers"
        else if (listOf("heavysleetandthunder", "heavysleetshowersandthunder", "heavysnowandthunder",
                "heavysnowshowersandthunder", "lightsleetandthunder", "lightsleetshowersandthunder",
                "lightsnowandthunder", "lightsnowshowersandthunder", "sleetandthunder",
                "sleetshowersandthunder", "snowandthunder", "snowshowersandthunder").contains(fc)) "thunderstorm_snow"
        else "cloudy"
    }

    fun getGraphData(days: List<WeatherDay>): List<HashMap<Int, Int>> {
        val weekTemp = mutableListOf<HashMap<Int, Int>>()
        days.forEach { day ->
            val dayTemp = mutableMapOf <Int, Int>()
            day.times.forEach{ timesery ->
                val timeStamp = stringFormat(timesery.time)
                val hour = timeStamp.hours
                dayTemp[hour] = timesery.data.instant.details.air_temperature.toInt()
            }
            weekTemp.add(dayTemp as HashMap<Int, Int>)
        }
        return weekTemp
    }

    fun getHighestTemp(day: WeatherDay): Int {
        var highestTemp: Int = day.times[0].data.instant.details.air_temperature.toInt()
        day.times.forEach{ hour ->
            val temp = hour.data.instant.details.air_temperature.toInt()
            if (temp>highestTemp) highestTemp = temp
        }
        return highestTemp
    }

    fun getSeason(day: WeatherDay): String {
        val timestamp = stringFormat(day.times[0].time)
        val  month = timestamp.month
        return if ((month <= 1) || (month == 11)) {
            "Winter"
        } else if (month <= 4) {
            "Spring"
        } else if (month <= 7) {
            "Summer"
        } else {
            "Autumn"
        }
    }
}