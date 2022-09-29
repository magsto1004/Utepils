package com.example.utepils.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.utepils.components.TabRowBar
import com.example.utepils.components.WeatherCard
import com.example.utepils.filters.WeatherFilter
import com.example.utepils.model.WeatherDay
import com.example.utepils.ui.theme.AppType
import com.example.utepils.ui.theme.DarkSeaGreen
import com.example.utepils.ui.theme.FloralWhite
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

const val NUM_PAGES = 7

@ExperimentalPagerApi
@Composable
fun WeatherScreen(wd: List<WeatherDay>, context: Context, onClick: (Int) -> Any) {
    val pagerState = rememberPagerState()
    val wf = WeatherFilter(context)

    val dateList = wf.getDateList(wd)
    val dateNumbers = wf.getDays(wd)
    val highestTemps = wf.getTemps(wd)
    val dateIcons = wf.getIcons(wd)
    val graphData = wf.getGraphData(wd)

    Column(
        modifier = Modifier
            .background(FloralWhite)
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        WeatherScreenHeader("Værmeldingen")

        HorizontalPager(
            count = NUM_PAGES,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .background(FloralWhite),
            state = pagerState,
            //contentPadding = PaddingValues(end = 32.dp)
            ) {
                page -> WeatherCard(
                    date = dateList[currentPage],
                    temp = highestTemps[currentPage],
                    symbolCode = dateIcons[currentPage],
                    graphData = graphData[currentPage],
                    onClick = onClick,
                    index = currentPage
                )
            }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight())
            {
                TabRowBar(pagerState, dateNumbers)
            }
    }
}
@Composable
fun WeatherScreenHeader(text: String) {
    Text(
        text = text,
        style = AppType.h1,
        textAlign = TextAlign.Start,
        color = DarkSeaGreen,
        modifier = Modifier
            .fillMaxWidth()
    )
}

/*
@ExperimentalPagerApi
@Preview
@Composable
fun PreviewWeatherScreen() {
    UtepilsTheme {
        WeatherScreen()
    }
}
*/