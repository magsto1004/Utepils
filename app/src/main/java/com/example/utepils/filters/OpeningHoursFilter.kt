package com.example.utepils.filters

import android.util.Log
import com.example.utepils.model.places.Place
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class OpeningHoursFilter {

    fun format(place: Place): String{

        var number = LocalDate.now().dayOfWeek.value
        if (LocalDateTime.now().hour < 4){
            number -= 1
        }

        var day = "0200" //default verdi ;)
        if (place.opening_hours == null) {
            return "Åpningstider ikke tilgjengelig"
        }
        if (number <= place.opening_hours.periods.size) {
            day = place.opening_hours.periods[number-1].close.time
        } else if (number == 7){
            return "Stengt"
        }

        return "Stenger ${day.subSequence(0,2)}:${day.subSequence(2,4)}"
    }
}