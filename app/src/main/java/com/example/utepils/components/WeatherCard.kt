package com.example.utepils.components

import android.graphics.Paint
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.utepils.R
import com.example.utepils.model.WeatherDay
import com.example.utepils.ui.theme.*
import me.bytebeats.views.charts.line.LineChart
import me.bytebeats.views.charts.line.LineChartData
import me.bytebeats.views.charts.line.render.line.SolidLineDrawer
import me.bytebeats.views.charts.line.render.point.FilledCircularPointDrawer
import me.bytebeats.views.charts.line.render.xaxis.SimpleXAxisDrawer
import me.bytebeats.views.charts.line.render.yaxis.SimpleYAxisDrawer
import me.bytebeats.views.charts.simpleChartAnimation

@Composable
fun WeatherCard(date: String, temp: String, symbolCode: String, graphData: HashMap<Int, Int>, onClick: (Int) -> Any, index: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(Shapes.medium)
            .border(width = 1.dp, color = FloralWhiteShadow, shape = Shapes.medium)
    ) {
        Column(
            modifier = Modifier
                .background(White)
                .padding(10.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            //WeatherHeader(date)

            //BasicWeatherInfo(temp, symbolCode)
            BasicWeatherInfoAlt(temp = temp, symbolCode = symbolCode, date = date)
            Spacer(Modifier.padding(10.dp))
            WeatherGraph(graphData)
            Spacer(Modifier.padding(10.dp))
            SelectButton(onClick, index)

        }
    }
}

@Composable
fun WeatherHeader(text: String) {
    Text(
        text = text,
        style = AppType.h1,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun BasicWeatherInfo(temp: String, symbolCode: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)

    ) {
        Box(
            modifier = Modifier
                .requiredWidthIn(100.dp, 100.dp)
                .requiredHeightIn(100.dp, 100.dp)
                .background(color = DarkSeaGreen, shape = Shapes.medium)
        ) {
            Image(
                painter = getIcon(symbolCode),
                contentDescription = symbolCode,
                modifier = Modifier
                    .requiredWidthIn(100.dp, 100.dp)
                    .requiredHeightIn(100.dp, 100.dp)
            )
        }

        Text(
            text = temp + "º",
            fontSize = 74.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BasicWeatherInfoAlt(temp: String, symbolCode: String, date: String) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()

    ) {

        Column(){

            Text(
                text = date,
                style = AppType.h2
            )

            Text(
                text = temp + "º",
                fontSize = 50.sp,
                textAlign = TextAlign.Center
            )
        }
        Box(
            modifier = Modifier
                .requiredWidthIn(120.dp, 120.dp)
                .requiredHeightIn(120.dp, 120.dp)
                .background(color = DarkSeaGreen, shape = Shapes.medium)
        ) {
            Image(
                painter = getIcon(symbolCode),
                contentDescription = symbolCode,
                modifier = Modifier
                    .requiredWidthIn(120.dp, 120.dp)
                    .requiredHeightIn(120.dp, 120.dp)
            )
        }


    }
}

@Composable
fun WeatherGraph(tempList: HashMap<Int, Int>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeightIn(180.dp, 180.dp)
    ) {
        val mapSize = tempList.size
        var labelEvery = 1
        if (mapSize > 20) {
            labelEvery = 4
        } else if (mapSize > 18) {
            labelEvery = 3
        } else if (mapSize > 12) {
            labelEvery = 2
        }

        val dataPoints = mutableListOf<LineChartData.Point>()
        tempList.keys.forEach { hour ->
            val temp = tempList[hour]
            if (temp != null) {
                dataPoints.add(LineChartData.Point(temp.toFloat(), (hour).toString()))
            }
        }
        LineChart(
            lineChartData = LineChartData(
                points = dataPoints
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, end = 10.dp, bottom = 10.dp),
            animation = simpleChartAnimation(),
            pointDrawer = FilledCircularPointDrawer(6.dp, KombuGreen),
            lineDrawer = SolidLineDrawer(color = DarkSeaGreen),
            xAxisDrawer = SimpleXAxisDrawer(drawLabelEvery = labelEvery),
            yAxisDrawer = SimpleYAxisDrawer(),
            horizontalOffset = 5f
        )
    }
}

@Composable
fun getIcon(symbolCode: String): Painter = when (symbolCode) {
    "clear_day" -> painterResource(R.drawable.clear_day)
    "cloudy" -> painterResource(R.drawable.cloudy)
    "partly_cloudy_day" -> painterResource(R.drawable.partly_cloudy_day)
    "fog" -> painterResource(R.drawable.fog)
    "heavy_showers" -> painterResource(R.drawable.heavy_showers)
    "heavy_sleet" -> painterResource(R.drawable.heavy_sleet)
    "heavy_snow" -> painterResource(R.drawable.heavy_snow)
    "showers" -> painterResource(R.drawable.showers)
    "sleet" -> painterResource(R.drawable.sleet)
    "snow" -> painterResource(R.drawable.snow)
    "thunderstorm_showers" -> painterResource(R.drawable.thunderstorm_showers)
    "thunderstorm_snow" -> painterResource(R.drawable.thunderstorm_snow)
    else -> painterResource(R.drawable.cloudy)
}

@Composable
fun SelectButton(onClick: (Int) -> Any, index: Int){
    Box(
        Modifier
            .clip(Shapes.medium)
            .clickable { onClick(index) }){

        Box(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(NavajoWhite), contentAlignment = Alignment.Center){
            Text(text = "Velg dag",color = BlackChocolate, style = AppType.button, textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp))
        }
    }

}
/*
@Preview
@Composable
fun PreviewWeatherCard() {
    UtepilsTheme {
        WeatherCard()
    }
}
*/