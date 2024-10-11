package org.kmp.experiment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.kmp.Repositories.BusSchedule
import org.kmp.ktor.DayType
import org.kmp.ktor.ParsedResponse
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    onNavigateToBusLines: () -> Unit,
    vm: TestViewModel = koinViewModel()
) {
    var favourites by remember { mutableStateOf<ParsedResponse<Map<DayType, List<BusSchedule?>>?>>(ParsedResponse.Success(null)) }
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = {
        3
    })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    LaunchedEffect(Unit) {
        favourites = vm.busScheduleRepository.getFavourites()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                modifier = Modifier.fillMaxWidth()
            ) {
                DayType.entries.forEachIndexed { index, currentTab ->
                    Tab(
                        selected = selectedTabIndex.value == index,
                        modifier = Modifier
                            .background(RedVoznjeTheme.colors.blue)
                            .clip(RoundedCornerShape(50)),
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(currentTab.ordinal)
                            }
                        },
                        text = {
                            TextRegular(
                                text = currentTab.getStringId().toString(context = LocalContext.current),
                                color = RedVoznjeTheme.colors.primaryBackground,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        },
                    )
                }
            }
            when {
                favourites.isSuccess() ->{
                    val favouritesData = favourites.getSuccessData() ?: emptyMap()
                    HorizontalPager(pagerState) { page ->
                        LazyColumn() {
                            items(
                                favouritesData[when (page) {
                                    0 -> DayType.WORKDAY
                                    1 -> DayType.SATURDAY
                                    else -> DayType.SUNDAY
                                }]?.filterNotNull() ?: emptyList()
                            ) {
                                FavouriteCard(it)
                            }
                        }
                    }
                }
                favourites.isError() ->{
                    TextRegular(favourites.getErrorMessage()?.toString(LocalContext.current) ?: "")
                }
            }
        }
        val interactionSource = remember { MutableInteractionSource() }
        Box(
            modifier = Modifier
                .align(
                    Alignment.BottomEnd
                )
                .padding(bottom = 32.dp, end = 16.dp)
                .size(56.dp)
                .background(shape = CircleShape, color = RedVoznjeTheme.colors.blue)
                .wrapContentSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    onNavigateToBusLines()
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(SharedRes.images.plus.drawableResId),
                contentDescription = "",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}