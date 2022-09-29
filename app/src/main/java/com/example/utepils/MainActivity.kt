package com.example.utepils

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.utepils.filters.WeatherFilter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.runBlocking

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val model = MainViewModel()
        val wf = WeatherFilter(this)
        runBlocking {
            model.fetchWeatherData()
            model.fetchDrinks()
            model.fetchBeers()
        }

        val wd = model.getWeatherData()
        val days = wd?.let { wf.slice(it) }
        if (days != null) {
            model.setWeatherDays(days)
            model.setWeatherDay(days[0])
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation_bar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)
    }
}

