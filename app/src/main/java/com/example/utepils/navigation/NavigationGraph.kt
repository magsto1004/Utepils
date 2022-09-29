package com.example.utepils.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.utepils.screens.WeatherScreen
import com.google.accompanist.pager.ExperimentalPagerApi
@ExperimentalPagerApi
@Composable
fun NavigationGraph(navController: NavHostController) {

    NavHost(navController, startDestination = BottomNavItem.Map.screen_route) {
        composable(BottomNavItem.Drinks.screen_route) {
            //DrinkScreen(listOf()) {}
        }
        composable(BottomNavItem.Map.screen_route) {
            //MapScreen()
        }
        composable(BottomNavItem.Weather.screen_route) {
            //WeatherScreen()
        }

    }
}
