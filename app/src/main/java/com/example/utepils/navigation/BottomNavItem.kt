package com.example.utepils.navigation

import com.example.utepils.R

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){
    object Drinks : BottomNavItem("Drinks", R.drawable.drink_icon,"drinks")
    object Map: BottomNavItem("Map", R.drawable.ic_map2,"map")
    object Weather: BottomNavItem("weather", R.drawable.ic_list,"weather")
}
