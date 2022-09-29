package com.example.utepils.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.utepils.R
import com.example.utepils.components.*
import com.example.utepils.filters.DistanceCalculator
import com.example.utepils.model.places.Place
import com.example.utepils.ui.theme.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun MapScreen(places: List<Place>?, onNavigateClick: (Place) -> Any,onClickWebsite: (Place) -> Any) {

    Box(
        Modifier
            .fillMaxSize()
            .padding()) {
        // Modal Bottom Sheet
        Map(places = places, onNavigateClick, onClickWebsite)

    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Map(places: List<Place>?, onNavigateClick: (Place) -> Any,onClickWebsite: (Place) -> Any) {
    val oslo = LatLng(59.94229, 10.71674)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(oslo, 13f)
    }

    val uiSettings by remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = true))
    }

    // isMyLocationEnabled = true
    val properties by remember {
        mutableStateOf(MapProperties(isMyLocationEnabled = true, mapType = MapType.NORMAL))
    }

    val scope = rememberCoroutineScope()

    val scaffoldState = rememberBottomSheetScaffoldState (
        bottomSheetState = rememberBottomSheetState(
            initialValue = BottomSheetValue.Collapsed
        )
    )
    var placeHeight by remember {
        mutableStateOf(0.dp)
    }
    var placesHeight by remember {
        mutableStateOf(150.dp)
    }

    var placeModal by remember {
        mutableStateOf(places?.get(0))
    }

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(
            initialValue = BottomSheetValue.Collapsed
        )
    )

    BottomSheetScaffold(
        modifier = Modifier.border(width = 1.dp, shape = Shapes.medium, color = FloralWhiteShadow),
        sheetContent = {
            PlaceModal(place = placeModal,onNavigateClick, onClickWebsite)
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = placeHeight,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = colorResource(id = R.color.FloralWhite)
    ) {
        BottomSheetScaffold(
            modifier = Modifier.border(width = 1.dp, shape = Shapes.medium, color = FloralWhiteShadow),
            sheetContent = {
                BottomSheetContent(places, onNavigateClick, onClickWebsite)
            },
            scaffoldState = bottomSheetScaffoldState,
            sheetPeekHeight = placesHeight,
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetBackgroundColor = colorResource(id = R.color.FloralWhite)
        ) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 130.dp),
                properties = properties,
                uiSettings = uiSettings,
                cameraPositionState = cameraPositionState
            ) {

                val iconNotFilled = bitmapDescriptorFromVector(
                    LocalContext.current, R.drawable.ic_marker_2
                )
                val iconFilled = bitmapDescriptorFromVector(
                    LocalContext.current, R.drawable.ic_marker_1
                )

                if (places != null) {
                    for (place in places) {
                        Marker(
                            position = LatLng(
                                place.geometry.location.lat,
                                place.geometry.location.lng
                            ),
                            title = place.name,
                            icon = iconNotFilled,
                            onClick = {
                                scope.launch {
                                    placesHeight = 0.dp
                                    scaffoldState.bottomSheetState.expand()
                                    it.setIcon(iconFilled)
                                    placeHeight = 150.dp
                                    placeModal = place
                                }
                                false
                            },
                            onInfoWindowClose = {
                                it.setIcon(iconNotFilled)
                                scope.launch {
                                    scaffoldState.bottomSheetState.collapse()
                                    placeHeight = 0.dp
                                    placesHeight = 150.dp
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlaceModal(place: Place?, onNavigateClick: (Place) -> Any, onClickWebsite: (Place) -> Any) {
    LazyColumn(
        Modifier
            .background(FloralWhite)
            .padding(vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (place != null) {
            item {
                Box(Modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp)) {
                    PlaceCard(place = place, onNavigateClick, onClickWebsite)
                }
            }
        }
    }
}

@Composable
fun BottomSheetContent(places: List<Place>?,onNavigateClick: (Place) -> Any,onClickWebsite: (Place) -> Any) {
    LazyColumn(
        Modifier
            .background(FloralWhite)
            .padding(vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            TopLineModal()
            PlacesTitle(text = "Steder i nærheten")
        }
        if (places != null) {
            items(places.sortedBy {
                DistanceCalculator().measure(
                it.geometry.location.lat,
                it.geometry.location.lng,
                59.94376464973046,
                10.718339438327781)
            }) { place ->
                Box(Modifier.padding(horizontal = 10.dp)){
                    PlaceCard(place = place, onNavigateClick, onClickWebsite)
                }
            }
        }
    }
}

@Composable
fun PlacesTitle(text: String){
    Text(modifier = Modifier
        .padding(start = 10.dp, top = 10.dp, bottom = 5.dp)
        .background(FloralWhite)
        .fillMaxWidth(),
        text = text,
        style = AppType.h4,
        color = BlackChocolate
    )
}

