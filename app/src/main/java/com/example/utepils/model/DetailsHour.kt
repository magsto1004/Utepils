package com.example.utepils.model

data class DetailsHour(
    val air_temperature_max: Double,
    val air_temperature_min: Double,
    val precipitation_amount: Double,
    // val precipitation_amount_max: Double,
    // val precipitation_amount_min: Double,
    val probability_of_precipitation: Int,
    // val probability_of_thunder: Double,
    // val ultraviolet_index_clear_sky_max: Int
)