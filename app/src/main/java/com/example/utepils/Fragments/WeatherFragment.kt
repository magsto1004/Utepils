package com.example.utepils.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.utepils.MainViewModel
import com.example.utepils.R
import com.example.utepils.filters.DrinkFilter
import com.example.utepils.filters.WeatherFilter
import com.example.utepils.model.WeatherData
import com.example.utepils.model.WeatherDay
import com.example.utepils.navigation.DrinkScreen
import com.example.utepils.screens.WeatherScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
class WeatherFragment : Fragment() {
    private val model: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val context = requireContext()
        var wdays = listOf<WeatherDay>()

        return ComposeView(requireContext()).apply {
            setContent {
                val wd = model.getWeatherData()
                if (wd != null) wdays = WeatherFilter(context).slice(wd)
                if (wdays.isNotEmpty()) WeatherScreen( wdays, context, ::handleButtonClick )
            }
        }
    }

    private fun handleButtonClick(index: Int) {
        val days = model.getWeatherDays()
        val day = days[index]
        model.setWeatherDay(day)

        val navController = findNavController()
        navController.popBackStack(R.id.mapFragment, false)
        navController.navigate(R.id.action_mapFragment_to_drinkFragment)
    }
}
