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
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.kmp.ktor.Area
import org.kmp.ktor.BusLine
import org.koin.androidx.compose.koinViewModel

@Composable
fun BusLines(vm: TestViewModel = koinViewModel()) {
    var busLines by remember { mutableStateOf<List<BusLine>>(emptyList()) }
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
            AreaTabs.entries.forEachIndexed { index, currentTab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    modifier = Modifier.background(RedVoznjeTheme.colors.blue).clip(RoundedCornerShape(50)),
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(currentTab.ordinal)
                        }
                    },
                    text = { TextRegular(text = currentTab.title, color = RedVoznjeTheme.colors.primaryBackground) },
                )
            }
        }
        HorizontalPager(pagerState) { page ->
            LazyColumn {
                items(busLines.filter { it.area == if (page == 0) Area.URBAN else Area.SUBURBAN }) { line ->
                    BusLineItem(line) { _, _ ->
                        //TODO onClick
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

enum class AreaTabs(
    val title: String
) {
    URBAN("Urban"),
    SUBURBAN("Suburban")
}