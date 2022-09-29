package com.example.utepils.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.utepils.ui.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun TabRowBar(pagerState: PagerState, dates: List<String>) {
    val scope = CoroutineScope(Dispatchers.Main)

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = White,
        divider = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(CircleShape)
            .border(width = 1.dp, shape = CircleShape, color = FloralWhiteShadow),
        indicator = {
                tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier
                    .pagerTabIndicatorOffset(pagerState = pagerState, tabPositions = tabPositions)
                    .fillMaxSize()
                    .zIndex(1f)
                    .clip(CircleShape),
                color = NavajoWhite
            )
        }
    ) {
        dates.forEachIndexed {index, date ->
            Date(pagerState, date, index, scope)
        }
    }
}

@ExperimentalPagerApi
@Composable
fun Date(pagerState: PagerState, date: String, index: Int, scope: CoroutineScope) {
    Tab(selected = pagerState.currentPage == index,
        onClick = {
            scope.launch{
                pagerState.scrollToPage(index)
            }
        },
        modifier = Modifier
            .zIndex(2f)
            .fillMaxSize()
            .clip(CircleShape),
        text = {
            Text(
                text = date,
                color = BlackChocolate
            )
        }
    )
}