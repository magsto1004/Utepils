package com.example.utepils.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.utepils.model.Drink
import com.example.utepils.ui.theme.*

@Composable
fun DrinkCard(drink: Drink, onClick: (Drink)->Any){
    Box(
        Modifier
            .fillMaxWidth()
            .clip(Shapes.medium)
            .clickable { onClick(drink) }
            .border(width = 1.dp, shape = Shapes.medium, color = FloralWhiteShadow)
    ) {
        Row(Modifier.background(Color.White)){
            CardImage(image = drink.pictureURL, size = 130)
            DrinkCardContent(drink)
        }
    }
}

@Composable
fun DrinkCardContent(drink: Drink) {
    Box(
        Modifier
            .height(130.dp)
            .fillMaxWidth()
            .padding(horizontal = 10.dp)){
        Box(Modifier.align(Alignment.TopStart)){
            Text(text = drink.name, style = AppType.h2)
        }

        Box(Modifier.align(Alignment.CenterStart)){
            Text(text = drink.description, style = AppType.body1)
        }

        Box(
            Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 10.dp)){
            TagBar(drink.tags)
        }
    }
}

