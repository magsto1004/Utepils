package com.example.utepils.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.utepils.model.Drink
import com.example.utepils.navigation.PageTitle
import com.example.utepils.R
import com.example.utepils.ui.theme.*

@Composable
fun DrinkRecipeScreen(drink: Drink, onNavigate: ()->Any) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .background(FloralWhite)
            .fillMaxSize()
            .padding(10.dp)

    ) {
        DrinkRecipeHeader(title = drink.name, onNavigate = onNavigate)
        Box(Modifier.border(width = 1.dp, shape = Shapes.medium, color = FloralWhiteShadow)) {
            PageImage(image = drink.pictureURL)
        }
        Box(Modifier
            .clip(RoundedCornerShape(10.dp))
            .border(width = 1.dp, shape = Shapes.medium, color = FloralWhiteShadow)){
            DrinkRecipeTabs(drink)
        }
    }
}

@Composable
fun DrinkRecipeTabs(drink: Drink){
    var tabIndex by remember { mutableStateOf(0)}
    val tabTitles = listOf("Ingredienser", "Fremgangsmåte")
    Column(
        Modifier
            .background(Color.White)
            .fillMaxHeight()){
        TabRow(selectedTabIndex = tabIndex, backgroundColor = NavajoWhite, contentColor = BlackChocolate){
            tabTitles.forEachIndexed{ index, title ->
                Tab(
                    selected = tabIndex == index,
                    content = {Text(text = title, color = BlackChocolate, style = MaterialTheme.typography.button,modifier = Modifier.padding(10.dp))},
                    onClick = {tabIndex = index}
                )
            }
        }
        when (tabIndex){
            0 -> DrinkIngredientsList(drink)
            1 -> DrinkRecipeDescription(drink)
        }
    }
}

@Composable
fun DrinkIngredientsList(drink: Drink){
    Text(text = "Passer til 2 personer", style = AppType.overline, modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp))
    /*Box(Modifier.clip(RoundedCornerShape(24.dp))){
        Row(Modifier.background(DarkSeaGreen), verticalAlignment = Alignment.CenterVertically){
            Text(text = "Passer til 2 personer", style = AppType.overline, modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp))
            IconButton(onClick = { *//*TODO*//* }) {
                Icon(painterResource(R.drawable.plus_icon), contentDescription = "Plus icon", tint = FloralWhite)
            }
            IconButton(onClick = { *//*TODO*//* }) {
                Icon(painterResource(R.drawable.plus_icon), contentDescription = "Plus icon", tint = FloralWhite)
            }
        }
    }*/
    LazyColumn(
        Modifier.fillMaxWidth()
    ) {
        items(drink.ingredients) { ing ->
            Row(Modifier.padding(horizontal = 10.dp)) {
                Text(text = ing, style = AppType.body1)
            }
        }
    }
}


@Composable
fun DrinkRecipeDescription(drink: Drink){
    LazyColumn(Modifier.fillMaxWidth(),verticalArrangement = Arrangement.spacedBy(3.dp)){
        items(drink.directions){dir ->
            Row(Modifier.padding(horizontal = 10.dp)){
                Text(text= (drink.directions.indexOf(dir)+1).toString() + ".\t", style = AppType.body1)
                Text(text = dir, style = AppType.body1)
            }
        }
    }
}

@Composable
fun PageImage(image: String) {
    Box(Modifier.clip(RoundedCornerShape(12.dp))) {
        Image(
            painter = rememberAsyncImagePainter(image),
            contentDescription = "Drink Photo",
            modifier = Modifier
                .requiredHeightIn(300.dp, 300.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }

}

@Composable
fun DrinkRecipeHeader(title: String, onNavigate: ()->Any){
    Row(verticalAlignment = Alignment.CenterVertically){
        IconButton(onClick = { onNavigate() }) {
            Icon(painterResource(
                id = R.drawable.back_arrow_icon),
                contentDescription = "Back arrow",
                tint = DarkSeaGreen)
        }
        PageTitle(text = title)
    }
}