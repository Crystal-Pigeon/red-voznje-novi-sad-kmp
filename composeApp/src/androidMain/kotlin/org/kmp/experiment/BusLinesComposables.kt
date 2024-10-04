package org.kmp.experiment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.kmp.ktor.Area
import org.kmp.ktor.BusLine

@Composable
fun BusLineItem(item: BusLine, editFavourites: (id: String, isFavourite: Boolean, area: Area) -> Unit) {
    var isFavourite by remember { mutableStateOf(item.isFavourite) }
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .background(RedVoznjeTheme.colors.primaryBackground)
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                editFavourites(item.id, item.isFavourite, item.area)
                item.isFavourite = !item.isFavourite
                isFavourite = item.isFavourite
            }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(0.9F)
                .padding(end = 16.dp)
        ) {
            TextRegular(
                text = item.number,
                fontWeight = FontWeight.Bold,
                color = RedVoznjeTheme.colors.blue,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            TextRegular(
                text = item.name.replace(" - ", "-").replace("-", " - "),
                color = RedVoznjeTheme.colors.primaryText,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        if (isFavourite) {
            Image(painterResource(SharedRes.images.checkmark.drawableResId), "")
        }
    }
}

@Composable
fun TextRegular(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = RedVoznjeTheme.colors.primaryText,
    fontWeight: FontWeight = FontWeight.Medium,
    fontSize: TextUnit = 16.sp
) {
    Text(
        text = text,
        fontFamily = manropeFontFamily,
        maxLines = 2,
        fontSize = fontSize,
        color = color,
        overflow = TextOverflow.Ellipsis,
        softWrap = true,
        fontWeight = fontWeight,
        modifier = modifier
    )
}