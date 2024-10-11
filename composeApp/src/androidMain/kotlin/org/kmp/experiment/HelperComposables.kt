package org.kmp.experiment

import Strings
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import dev.icerock.moko.resources.ColorResource
import dev.icerock.moko.resources.StringResource

val manropeFontFamily = FontFamily(
    Font(SharedRes.fonts.manrope_bold.fontResourceId, FontWeight.Bold),
    Font(SharedRes.fonts.manrope_regular.fontResourceId, FontWeight.Medium)
)

@Composable
fun colorByResource(id: ColorResource): Color {
    return Color(id.getColor(LocalContext.current))
}
@Composable
fun stringResource(id: StringResource, vararg args: Any): String {
    return Strings(LocalContext.current).get(id, args.toList())
}

@Immutable
data class CustomColorsPalette(
    val primaryBackground: Color = Color.Unspecified,
    val secondaryBackground: Color = Color.Unspecified,
    val tertiaryBackground: Color = Color.Unspecified,
    val primaryText: Color = Color.Unspecified,
    val secondaryText: Color = Color.Unspecified,
    val lineColor: Color = Color.Unspecified,
    val blue: Color = Color.Unspecified
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }
var OnLightCustomColorsPalette: CustomColorsPalette? = null
var OnDarkCustomColorsPalette: CustomColorsPalette? = null

@Composable
fun RedVoznjeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorPalette = if (darkTheme) OnDarkCustomColorsPalette else OnLightCustomColorsPalette

    CompositionLocalProvider(LocalCustomColorsPalette provides colorPalette!!) {
        content() // Pass content through theme
    }
}

object RedVoznjeTheme {
    val colors: CustomColorsPalette
        @Composable
        get() = LocalCustomColorsPalette.current
}