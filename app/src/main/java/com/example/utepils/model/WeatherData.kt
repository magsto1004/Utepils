package com.example.utepils.model

import androidx.compose.runtime.MutableState

data class WeatherData(
    val geometry: Geometry,
    val properties: Properties
    //val type: String
)