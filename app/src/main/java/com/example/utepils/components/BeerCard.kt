package com.example.utepils.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.utepils.model.Beer
import com.example.utepils.ui.theme.AppType
import com.example.utepils.ui.theme.FloralWhiteShadow
import com.example.utepils.ui.theme.Shapes

@Composable
fun BeerCard(beer: Beer){
    Box(
        Modifier
            .fillMaxWidth()
            .clip(Shapes.medium)
            .border(width = 1.dp, shape = Shapes.medium, color = FloralWhiteShadow)
    ) {
        Row(Modifier.background(Color.White)){
            CardImage(image = beer.pictureURL, size = 130)
            BeerCardContent(beer)
        }
    }
}

@Composable
fun BeerCardContent(beer: Beer) {
    Box(Modifier.height(130.dp).fillMaxWidth().padding(horizontal = 10.dp)){
        Box(Modifier.align(Alignment.TopStart)){
            Text(text = beer.name, style = AppType.h2)
        }

        Box(Modifier.align(Alignment.CenterStart)){
            Text(text = beer.description, style = AppType.body1)
        }

        Box(Modifier.align(Alignment.BottomStart).padding(bottom = 10.dp)){
            TagBar(beer.tags)
        }
    }
}

