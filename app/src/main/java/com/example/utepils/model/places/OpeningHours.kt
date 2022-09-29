package com.example.utepils.model.places

data class OpeningHours(
    val open_now: Boolean,
    val periods: List<OpeningHoursPeriods>,
    val weekday_text: List<String>
)

data class OpeningHoursPeriods(
    val open: PlaceOpeningHoursPeriodDetail,
    val close: PlaceOpeningHoursPeriodDetail
)

data class PlaceOpeningHoursPeriodDetail(
    val day: Int,
    val time: String
)