package com.example.utepils.model

data class Data(
    val instant: Instant,
    val next_12_hours: NextHour,
    val next_1_hours: NextHour,
    val next_6_hours: NextHour
)