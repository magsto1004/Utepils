package com.example.utepils.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.utepils.MainViewModel
import com.example.utepils.R
import com.example.utepils.filters.DrinkFilter
import com.example.utepils.filters.WeatherFilter
import com.example.utepils.model.Drink
import com.example.utepils.navigation.DrinkScreen
import kotlinx.coroutines.runBlocking

class DrinkFragment : Fragment() {
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        updateDrinkData()
        val dropDownStartIndex = findSelectedDayIndex()
        val wf = this.context?.let { WeatherFilter(it) }

        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                val drinks = model.getDrinkData()
                val beers = model.getBeerData()
                val dates: List<String> = wf?.getDateList(model.getWeatherDays())!!
                DrinkScreen(
                    data = drinks,
                    beers = beers,
                    onClick = ::onClick,
                    dates = dates,
                    dropDownOnClick = ::updateDrinkData,
                    dropDownStartIndex = dropDownStartIndex
                )
            }
        }
    }

    private fun onClick(d: Drink){
        Log.d("DEBUG",d.name+" selected")
        findNavController().navigate(R.id.action_from_drink_to_recipe)
        model.setSelected(d)
    }

    private fun findSelectedDayIndex(): Int {
        val days = model.getWeatherDays()
        val selectedDay = model.getWeatherDay()

        return days.indexOf(selectedDay)
    }

    private fun updateDrinkData() {
        val day = model.getWeatherDay()

        val wf = this.context?.let { WeatherFilter(it) }
        val df = DrinkFilter()

        if (wf != null && day != null) {
            val icon = wf.getIcon(day)
            val season = wf.getSeason(day)
            val temp = wf.getHighestTemp(day)
            val drinks = model.getDrinkData()
            val sortedDrinks = df.sortDrinks(temp, icon, season, drinks)
            Log.d("TESTDRINKSSORTED", sortedDrinks.toString())

            model.setDrinkData(sortedDrinks)
        }
    }

    private fun updateDrinkData(index: Int) {
        val days = model.getWeatherDays()
        val day = days[index]
        model.setWeatherDay(day)

        val wf = this.context?.let { WeatherFilter(it) }
        val df = DrinkFilter()

        if (wf != null && day != null) {
            val icon = wf.getIcon(day)
            val season = wf.getSeason(day)
            val temp = wf.getHighestTemp(day)
            val drinks = model.getDrinkData()
            val sortedDrinks = df.sortDrinks(temp, icon, season, drinks)
            Log.d("TESTDRINKSSORTED", sortedDrinks.toString())

            model.setDrinkData(sortedDrinks)
        }
    }
}


