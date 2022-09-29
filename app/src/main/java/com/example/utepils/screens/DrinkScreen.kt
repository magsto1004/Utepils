package com.example.utepils.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.utepils.components.BeerCard
import com.example.utepils.components.DrinkCard
import com.example.utepils.model.Beer
import com.example.utepils.model.Drink
import com.example.utepils.ui.theme.*


@Composable
fun DrinkScreen(data: List<Drink>, beers: List<Beer>,onClick: (Drink) -> Any, dates: List<String>, dropDownOnClick: (Int) -> Any, dropDownStartIndex: Int) {
    var tabIndex by remember { mutableStateOf(0)}
    val tabTitles = listOf("Drink", "Øl")

    Column(Modifier.background(FloralWhite).wrapContentSize()){
        Row(Modifier
                .background(DarkSeaGreen)
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            PageDateDropDown(
                dates = dates,
                dropDownOnClick = dropDownOnClick,
                startItemIndex = dropDownStartIndex
            )
            TabRow(
                selectedTabIndex = tabIndex,
                backgroundColor = DarkSeaGreen,
                contentColor = Color.Transparent,
                modifier = Modifier
                    .width(120.dp)
                    .clip(Shapes.medium)
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(modifier = Modifier.background(if (tabIndex == index) KombuGreen else FloralWhite),
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                        content = {
                            Text(
                                text = title,
                                color = if (tabIndex == index) FloralWhite else KombuGreen,
                                style = MaterialTheme.typography.button,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp)
                            )
                        }
                    )
                }
            }
        }
        when (tabIndex){
            0 -> DrinkList(data, onClick)
            1 -> BeerList(beers)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DrinkList(data: List<Drink>, onClick: (Drink) -> Any){
    val recommendedDrinks = data.subList(0, 4)
    val restOfDrinks = data.subList(4, data.size)
    Column() {

        LazyColumn(
            Modifier.background(FloralWhite),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            stickyHeader { PageTitle(text = "Drinker som passer til været") }
            items(recommendedDrinks) { drink ->
                Box(Modifier.padding(horizontal = 10.dp)) {
                    DrinkCard(drink = drink, onClick = onClick)
                }
            }
            stickyHeader { PageTitle(text = "Andre drinker") }
            items(restOfDrinks) { drink ->
                Box(Modifier.padding(horizontal = 10.dp)) {
                    DrinkCard(drink = drink, onClick = onClick)
                }
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BeerList(data: List<Beer>){
    val recommendedBeers = data.subList(0, 4)
    val restOfBeers = data.subList(4, data.size)
    LazyColumn(
        Modifier.background(FloralWhite),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        stickyHeader { PageTitle(text = "Øl som passer til været") }
        items(recommendedBeers) { beer ->
            Box(Modifier.padding(horizontal = 10.dp)) {
                BeerCard(beer)
            }
        }
        stickyHeader { PageTitle(text = "Andre øl") }
        items(restOfBeers) { beer ->
            Box(Modifier.padding(horizontal = 10.dp)) {
                BeerCard(beer)
            }
        }
    }
}


@Composable
fun PageDateDropDown(dates: List<String>, dropDownOnClick: (Int) -> Any, startItemIndex: Int) {

    // State variables
    var dateCache: String by remember { mutableStateOf(dates[startItemIndex]) }
    var expanded by remember { mutableStateOf(false) }

    Box() {
        Row(
            Modifier.clickable { expanded = !expanded },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) { // Anchor view
            Text(
                text = dateCache,
                style = AppType.h2,
                color = FloralWhite,
                modifier = Modifier.padding(end = 8.dp)
            )
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "", tint = FloralWhite)

            //
            DropdownMenu(expanded = expanded, onDismissRequest = {
                expanded = false
            }) {
                dates.forEachIndexed{ index, date->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        dateCache = date

                        dropDownOnClick(index)
                    }) {
                        Text(text = date)
                    }
                }
            }
        }
    }
}

@Composable
fun PageTitle(text: String){
    Text(modifier = Modifier
        .padding(horizontal = 10.dp)
        .background(FloralWhite)
        .fillMaxWidth(),
        text = text,
        style = AppType.h2,
        color = DarkSeaGreen
    )
}

