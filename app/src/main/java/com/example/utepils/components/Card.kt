package com.example.utepils.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.utepils.ui.theme.AppType
import com.example.utepils.ui.theme.DarkSeaGreen
import com.example.utepils.ui.theme.Shapes

@Composable
fun ImageCardExpanding(element: @Composable() () -> Unit) {
    Card(
        Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        elevation = 10.dp,
        shape = Shapes.medium
    ) {
        element()
    }
}

@Composable
fun ImageCardStatic(image: @Composable() () -> Unit, content :@Composable() () -> Unit){
    Box(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
    ) {
        Row(Modifier.background(Color.White)){
            image()
            content()
        }
    }
}

@Composable
fun CardImage(image: String, size: Int) {
    Image(
        painter = rememberAsyncImagePainter(image),
        contentDescription = "Photo of a beverage",
        modifier = Modifier
            .requiredWidthIn(size.dp, size.dp)
            .requiredHeightIn(size.dp, size.dp)
            .clip(RoundedCornerShape(12.dp)),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun CardImageWide(image: String, size: Int) {
    Image(
        painter = rememberAsyncImagePainter(image),
        contentDescription = "Photo of a cocktail",
        modifier = Modifier
            .requiredHeightIn(size.dp, size.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun TagBar(tags: List<String>) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        for (i in 0 .. 2){
            if (i <= tags.size-1)
                Text("#${tags[i]} ", style = AppType.button, color = DarkSeaGreen)
        }
    }

}

