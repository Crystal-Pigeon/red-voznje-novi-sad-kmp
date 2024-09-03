package org.kmp.experiment

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        //var showContent by remember { mutableStateOf<List<BusLine>>(emptyList()) }
        var showContent by remember { mutableStateOf<Pair<List<LocalTime>, List<LocalTime>?>>(Pair(emptyList(), emptyList())) }
        var lines by remember { mutableStateOf<String>("") }
        LaunchedEffect(Unit) {
            //Greeting().getScheduleByLine()
            // Call your suspend function here
            showContent = Greeting().getScheduleByLine()
            lines = Greeting().getBusLines().toString()
        }
        //Text(text = "Smijer A\n" + showContent.first.toString() + "\n" + "Smijer B\n" + showContent.second.toString())
        Text(text = lines)
    }
}