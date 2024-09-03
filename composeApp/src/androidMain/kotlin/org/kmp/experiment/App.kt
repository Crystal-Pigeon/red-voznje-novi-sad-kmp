package org.kmp.experiment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.kmp.ktor.BusLine

import red_voznje_novi_sad_kmp.composeapp.generated.resources.Res
import red_voznje_novi_sad_kmp.composeapp.generated.resources.compose_multiplatform
@Composable
@Preview
fun App() {
    MaterialTheme {
        //var showContent by remember { mutableStateOf<List<BusLine>>(emptyList()) }
        var showContent by remember { mutableStateOf<Pair<List<LocalTime>, List<LocalTime>?>>(Pair(emptyList(), emptyList())) }
        LaunchedEffect(Unit) {
            //Greeting().getScheduleByLine()
            // Call your suspend function here
            showContent = Greeting().getScheduleByLine()
        }
        Text(text = "Smijer A\n" + showContent.first.toString() + "\n" + "Smijer B\n" + showContent.second.toString())
    }
}