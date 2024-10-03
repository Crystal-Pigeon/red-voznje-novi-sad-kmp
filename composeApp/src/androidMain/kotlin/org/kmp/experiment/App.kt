package org.kmp.experiment

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.icerock.moko.resources.desc.StringDesc
import getString
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Serializable
object Home

@Serializable
object BusLines

@OptIn(ExperimentalMaterial3Api::class)
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
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(
            color = RedVoznjeTheme.colors.blue
        )
        systemUiController.setNavigationBarColor(
            color = RedVoznjeTheme.colors.primaryBackground
        )
        val navController = rememberNavController()

        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

            topBar = {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination?.route
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = RedVoznjeTheme.colors.blue,
                        titleContentColor = RedVoznjeTheme.colors.primaryText,
                        scrolledContainerColor = RedVoznjeTheme.colors.blue
                    ),
                    title = {
                        getScreenName(currentDestination)?.let {
                            TextRegular(
                                text = it.toString(context = LocalContext.current),
                                color = RedVoznjeTheme.colors.primaryBackground,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    },
                    navigationIcon =
                        {
                            if (currentDestination != Home::class.qualifiedName) {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Localized description",
                                            tint = RedVoznjeTheme.colors.primaryBackground
                                        )
                                        getScreenName(navController.previousBackStackEntry?.destination?.route)?.let {
                                            TextRegular(
                                                text = it.toString(context = LocalContext.current),
                                                fontSize = 14.sp,
                                                color = RedVoznjeTheme.colors.primaryBackground,
                                            )
                                        }
                                    }
                                }
                            }
                        },
                    actions = {
                        if (currentDestination == Home::class.qualifiedName) {
                            IconButton(onClick = { /* do something */ }) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "",
                                    tint = RedVoznjeTheme.colors.primaryBackground
                                )
                            }
                        }
                    },
                    scrollBehavior = scrollBehavior,
                )
            },
        ) { _ ->
            NavHost(navController, startDestination = Home) {
                composable<Home> {
                    HomeScreen(onNavigateToBusLines = {
                        navController.navigate(
                            route = BusLines
                        )
                    })
                }
                composable<BusLines> {
                    BusLinesScreen()
                }
            }
        }
    }
}

fun getScreenName(destination: String?): StringDesc? {
    return when (destination) {
        Home::class.qualifiedName -> getString(SharedRes.strings.home_title)
        BusLines::class.qualifiedName -> getString(SharedRes.strings.bus_lines_title)
        else -> null
    }
}