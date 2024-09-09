package org.kmp.experiment

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.kmp.Repositories.BusSchedule
import org.kmp.ktor.Area
import org.kmp.ktor.BusLine
import org.kmp.ktor.DayType
import org.koin.androidx.compose.koinViewModel
import red_voznje_novi_sad_kmp.composeapp.generated.resources.Res
import red_voznje_novi_sad_kmp.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {
        TestScreen()
    }
}

@Composable
fun TestScreen(vm: TestViewModel = koinViewModel()) {
    //var showContent by remember { mutableStateOf<List<BusLine>>(emptyList()) }
    //var showContent by remember { mutableStateOf<List<BusSchedule>?>(null) }
    var showContent by remember { mutableStateOf<Map<DayType,List<BusSchedule>>>(mapOf()) }
    val scroll = rememberScrollState(0)


    /*var showContent by remember {
        mutableStateOf<Pair<List<LocalTime>, List<LocalTime>?>>(
            Pair(
                emptyList(),
                emptyList()
            )
        )
    }*/
    LaunchedEffect(Unit) {
        //showContent = vm.busScheduleRepository.getBusLines()
        //showContent = vm.busScheduleRepository.getScheduleByLine()
        //showContent = vm.busScheduleRepository.getSchedulesByDayType(listOf("14","2."), Area.URBAN, DayType.WORKDAY)
        //val a = vm.busScheduleRepository.getBusLines(areaType = Area.SUBURBAN)
        //vm.cache.urbanFavourites = listOf("2.","3.")
        //vm.cache.suburbanFavourites = listOf("42","43")
        //val a = vm.busScheduleRepository.getBusLines()
        //Greeting().getScheduleByLine()
        // Call your suspend function here
        //showContent = vm.busScheduleRepository.getScheduleByLine()
        showContent = vm.busScheduleRepository.getFavourites()

    }
    Text(showContent.toString(), Modifier.verticalScroll(scroll))
    //Text(text = "Smijer A\n" + showContent.first.toString() + "\n" + "Smijer B\n" + showContent.second.toString())
}