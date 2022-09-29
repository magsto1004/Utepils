package com.example.utepils.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.utepils.MainViewModel
import com.example.utepils.R
import com.example.utepils.screens.DrinkRecipeScreen
import com.example.utepils.ui.theme.*

class DrinkRecipeFragment() : Fragment() {
    val model: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                val drink = model.getSelectedDrink()

                if (drink != null){
                    DrinkRecipeScreen(drink = drink, onNavigate = ::onNavigate)
                }else{
                    ErrorPrompt(message = "Tilbake")
                }

            }
        }
    }
    fun onNavigate(){
        findNavController().navigate(R.id.action_from_recipe_to_drink)
    }

    @Composable
    fun ErrorPrompt(message: String){
        Box(Modifier.fillMaxSize().padding(10.dp)){
            Column(Modifier
                    .align(Alignment.Center)
                    .clip(Shapes.medium)
                    .background(FloralWhite)
                    .padding(30.dp),
                horizontalAlignment = CenterHorizontally){
                Text("Feil ved innlasting av oppskrift...", style = AppType.body1)
                Box(Modifier.clickable { onNavigate() }.clip(Shapes.medium)){
                    Box(Modifier.background(DarkSeaGreen).padding(10.dp)){
                        Text(message, style = AppType.button)
                    }

                }
            }
        }
    }
}