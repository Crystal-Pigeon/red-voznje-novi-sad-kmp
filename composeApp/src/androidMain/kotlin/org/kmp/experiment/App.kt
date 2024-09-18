package org.kmp.experiment

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.kmp.Repositories.BusSchedule
import org.kmp.ktor.*
import org.koin.androidx.compose.koinViewModel
import red_voznje_novi_sad_kmp.composeapp.generated.resources.Res
import red_voznje_novi_sad_kmp.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    OnLightCustomColorsPalette = CustomColorsPalette(
        primaryBackground = colorByResource(id = SharedRes.colors.primary_background),
        secondaryBackground = colorByResource(id = SharedRes.colors.secondary_background),
        tertiaryBackground = colorByResource(id = SharedRes.colors.tertiary_background),
        primaryText = colorByResource(id = SharedRes.colors.primary_text),
        secondaryText = colorByResource(id = SharedRes.colors.secondary_text),
        lineColor = colorByResource(id = SharedRes.colors.line_color),
        blue = colorByResource(id = SharedRes.colors.brand)
    )

    OnDarkCustomColorsPalette = CustomColorsPalette(
        primaryBackground = colorByResource(id = SharedRes.colors.primary_background),
        secondaryBackground = colorByResource(id = SharedRes.colors.secondary_background),
        tertiaryBackground = colorByResource(id = SharedRes.colors.tertiary_background),
        primaryText = colorByResource(id = SharedRes.colors.primary_text),
        secondaryText = colorByResource(id = SharedRes.colors.secondary_text),
        lineColor = colorByResource(id = SharedRes.colors.line_color),
        blue = colorByResource(id = SharedRes.colors.brand)
    )

    RedVoznjeTheme {
        //TestScreen()
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(
            color = RedVoznjeTheme.colors.blue
        )
        systemUiController.setNavigationBarColor(
            color = RedVoznjeTheme.colors.primaryBackground
        )
        BusLines()
    }
}

@Composable
fun TestScreen(vm: TestViewModel = koinViewModel()) {
    var busLines by remember { mutableStateOf<List<BusLine>>(emptyList()) }
    //var showContent by remember { mutableStateOf<List<BusSchedule>?>(null) }
    var favourites by remember { mutableStateOf<Map<DayType, List<BusSchedule>>>(mapOf()) }
    val scroll = rememberScrollState(0)
    var test by remember { mutableStateOf<List<String>>(emptyList()) }
    var schedule by remember { mutableStateOf<ScheduleStartDateResponse?>(null) }
    val response = remember { mutableStateOf<ApiResponse<ScheduleStartDateResponseList>?>(null) }


    /*var showContent by remember {
        mutableStateOf<Pair<List<LocalTime>, List<LocalTime>?>>(
            Pair(
                emptyList(),
                emptyList()
            )
        )
    }*/
    LaunchedEffect(Unit) {
        //vm.fetchScheduleStartDates()
        busLines = vm.busScheduleRepository.getBusLines()
        //showContent = vm.busScheduleRepository.getScheduleByLine()
        //showContent = vm.busScheduleRepository.getSchedulesByDayType(listOf("14","2."), Area.URBAN, DayType.WORKDAY)
        //val a = vm.busScheduleRepository.getBusLines(areaType = Area.SUBURBAN)
        //vm.cache.urbanFavourites = listOf("2.","3.")
        //vm.cache.suburbanFavourites = listOf("42","43")
        //vm.cache.addToFavourites("3.", Area.URBAN)
        //test = vm.cache.favourites
        //schedule = vm.busScheduleRepository.getScheduleStartDate()
        //val a = vm.busScheduleRepository.getBusLines()
        //Greeting().getScheduleByLine()
        // Call your suspend function here
        //showContent = vm.busScheduleRepository.getScheduleByLine()
        //favourites = vm.busScheduleRepository.getFavourites()
        //vm.cache.removeFromFavourites("43")
        //vm.scheduleData = vm.busScheduleRepository.getScheduleStartDate()
        response.value = vm.busScheduleRepository.getScheduleStartDate()
    }
    //Text(showContent.toString(), Modifier.verticalScroll(scroll))
    //Text(vm.scheduleData.toString())
    //Text(busLines.toString(), Modifier.verticalScroll(scroll))
    LazyColumn {
        items(busLines){ line ->
            BusLineItem(line){ _, _ ->
            }
            Spacer(Modifier.height(8.dp))
        }
    }

    val pagerState = rememberPagerState(pageCount = {
        2
    })

    /*when (val result = response.value) {
        is ApiResponse.Success -> {
            // Display the data
            Text(result.data.toString())
        }

        is ApiResponse.Error -> {
            // Display the error message
            Text("Error: ${result.message}")
        }

        else -> {
        }
    }*/
    //Text(text = "Smijer A\n" + showContent.first.toString() + "\n" + "Smijer B\n" + showContent.second.toString())
}