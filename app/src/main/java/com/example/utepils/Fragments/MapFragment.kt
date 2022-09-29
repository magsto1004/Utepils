package com.example.utepils.Fragments

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import com.example.utepils.MainViewModel
import com.example.utepils.R
import com.example.utepils.model.places.Place
import com.example.utepils.screens.MapScreen
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import androidx.core.content.ContextCompat.getSystemService

import android.location.LocationManager
import androidx.core.content.ContextCompat


class MapFragment : Fragment() {

    private val model: MainViewModel by activityViewModels()
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                val places = model.placesData.value
                MapScreen(places, ::onClickNavigate, ::onClickWebsite)
            }
        }
    }

    private fun onClickNavigate(place: Place){
        val gmmIntentUri =
            Uri.parse("google.navigation:q=${place.vicinity},+Oslo+Norway&mode=w")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    private  fun onClickWebsite(place: Place){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(place.website))
        startActivity(browserIntent)
    }


}