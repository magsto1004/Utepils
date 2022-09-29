package com.example.utepils.components

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.utepils.R
import com.example.utepils.filters.DistanceCalculator
import com.example.utepils.filters.OpeningHoursFilter
import com.example.utepils.model.places.Place
import com.example.utepils.ui.theme.*
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import kotlin.math.floor
import kotlin.math.round
import kotlin.math.roundToInt

@Composable
fun PlaceCard(place: Place?, onNavigateClick: (Place)->Any,onClickWebsite: (Place)-> Any){
    var expanded by remember { mutableStateOf(false)}

    if (place != null) {
        Box(
            Modifier
                .clip(Shapes.medium)
                .border(width = 1.dp, shape = Shapes.medium, color = FloralWhiteShadow)
                .clickable { expanded = !expanded })
        {
            Column(
                Modifier
                    .background(White)
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {

                PlaceCardHeader(place)

                AnimatedVisibility(visible = expanded) {
                    Column {
                        TagBar(tags = place.types.map { t ->
                            when (t) {
                                "bar" -> "Bar"
                                "night_club" -> "Nattklubb"
                                "cafe" -> "Kafé"
                                "restaurant" -> "Resturant"
                                else -> "NA"
                            }
                        }.filter { t -> t != "NA" }
                        )
                        Spacer(Modifier.padding(5.dp))

                        OpeningHours(place)

                        LinkButton("Nettside", place, onClickWebsite)
                        Spacer(Modifier.padding(5.dp))
                        NavButton("Veibeskrivelse", place, onNavigateClick)

                    }
                }
            }
        }
    }

}

@Composable
fun PlaceCardHeader(place: Place){
    Box(Modifier.fillMaxSize()){
        Column{
            Text(place.name, style = AppType.h2)
            Text(place.vicinity, style = AppType.overline)
            InfoBar(place = place)
        }
    }
}

@Composable
fun InfoBar(place: Place){
    Row(Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically){
        Row(verticalAlignment = Alignment.CenterVertically) {
            PriceChip(place = place)
            Spacer(Modifier.padding(horizontal = 5.dp))
            StarRatingBar(place = place)

        }
        DistanceView(place = place)
    }
}

@Composable
fun PriceChip(place: Place){
    var level = place.price_level
    if (level == 0){
        level = 2
    }
    Row(
        Modifier
            .width(45.dp)
            .height(20.dp)
            .clip(Shapes.medium)){
        Box(
            Modifier
                .background(DarkSeaGreen)
                .fillMaxSize(), contentAlignment = Alignment.Center){
                    Text("$".repeat(level), style = AppType.button, color = White)
        }
    }
}

@Composable
fun StarRatingBar(place: Place){
    var rating = floor(place.rating).toInt()
    Row(){
        if (rating == 0){
            rating = 3
        }
        for (i in 0 until rating) {
            Icon(
                painterResource(
                    id = R.drawable.star_icon
                ),
                contentDescription = "Star",
                tint = NavajoWhite
            )
        }
    }

}

@Composable
fun NavButton(text: String,place: Place, onClick: (Place)->Any){
    Box(
        Modifier
            .clip(Shapes.medium)
            .clickable {
                onClick(place)
            }){

        Box(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(NavajoWhite), contentAlignment = Alignment.Center){
            Text(text = text,color = BlackChocolate, style = AppType.button, textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp))
        }
    }
}

@Composable
fun LinkButton(text: String,place: Place, onClick: (Place)->Any){
    if (place.website != null) {
        Box(
            Modifier
                .clip(Shapes.medium)
                .border(width = 1.dp, shape = Shapes.medium, color = KombuGreen)
                .clickable {
                    onClick(place)
                }) {

            Box(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    color = KombuGreen,
                    style = AppType.button,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun DistanceView(place: Place){
    var dist = DistanceCalculator().measure(
        place.geometry.location.lat,
        place.geometry.location.lng,
        59.94376464973046,
        10.718339438327781)
    var str = ""
    if (dist > 1000){
        dist /= 1000
        dist = String.format("%.1f",dist).toDouble()
        str = "$dist km unna"
    }else{
        val dist1 = String.format("%.0f",dist).toInt()
        str = "$dist1 m unna"
    }

    Text(str , style =  AppType.h3)
}

@Composable
fun OpeningHours(place: Place){
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        Text(
            text = OpeningHoursFilter().format(place),
            style = AppType.h3,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TopLineModal() {
    Column(modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)) {
        Box(
            modifier = Modifier.size(height = 4.dp, width = 70.dp).clip(RoundedCornerShape(5.dp)).background(
                FloralWhiteShadow)
        )
    }
}

@Preview
@Composable
fun ComposablePreview() {
    TopLineModal()
}

fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // draw it onto the bitmap
    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}
