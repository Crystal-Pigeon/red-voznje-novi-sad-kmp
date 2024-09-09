package org.kmp.experiment

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.kmp.Repositories.BusSchedule
import org.kmp.ktor.BusLine
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
    var showContent by remember { mutableStateOf<BusSchedule?>(null) }

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
        showContent = vm.busScheduleRepository.getScheduleByLine()
        //Greeting().getScheduleByLine()
        // Call your suspend function here
        //showContent = vm.busScheduleRepository.getScheduleByLine()

    }
    Text(showContent.toString())
    //Text(text = "Smijer A\n" + showContent.first.toString() + "\n" + "Smijer B\n" + showContent.second.toString())
}