package org.kmp.experiment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.kmp.ktor.Area
import org.kmp.ktor.BusLine
import org.kmp.ktor.ParsedResponse
import org.koin.androidx.compose.koinViewModel

@Composable
fun BusLinesScreen(vm: TestViewModel = koinViewModel()) {
    var busLines by remember { mutableStateOf<ParsedResponse<List<BusLine>>>(ParsedResponse.Success(emptyList())) }
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = {
        2
    })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    LaunchedEffect(Unit) {
        busLines = vm.busScheduleRepository.getBusLines()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier.fillMaxWidth()
        ) {
            Area.entries.forEachIndexed { index, currentTab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    modifier = Modifier.background(RedVoznjeTheme.colors.blue).clip(RoundedCornerShape(50)),
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(currentTab.ordinal)
                        }
                    },
                    text = { TextRegular(text = currentTab.getStringId().toString(context = LocalContext.current), color = RedVoznjeTheme.colors.primaryBackground, modifier = Modifier.padding(vertical = 8.dp)) },
                )
            }
        }
        when(busLines){
            is ParsedResponse.Error -> {
                TextRegular(busLines.getErrorMessage()?.toString(LocalContext.current) ?: "")
            }
            is ParsedResponse.Success<*> -> {
                HorizontalPager(pagerState) { page ->
                    LazyColumn {
                        items(busLines.getSuccessData()?.filter { it.area == if (page == 0) Area.URBAN else Area.SUBURBAN } ?: emptyList()) { line ->
                            BusLineItem(line) { id, isFavourite, area ->
                                if(isFavourite){
                                    vm.cache.removeFromFavourites(id)
                                }
                                else{
                                    vm.cache.addToFavourites(id, area)
                                }
                            }
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}