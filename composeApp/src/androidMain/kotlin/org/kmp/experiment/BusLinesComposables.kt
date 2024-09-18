package org.kmp.experiment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.kmp.ktor.BusLine

@Composable
fun BusLineItem(item: BusLine, editFavourites: (id: String, isFavourite: Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .background(RedVoznjeTheme.colors.primaryBackground)
            .fillMaxWidth()
            .clickable { editFavourites(item.id, !item.isFavourite) }
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
            TextRegular(text = item.number, fontWeight = FontWeight.Bold)
            TextRegular(text = item.name.replace(" - ", "-").replace("-", " - "), color = RedVoznjeTheme.colors.primaryText)
        }
        if (item.isFavourite) {
            Image(painterResource(SharedRes.images.checkmark.drawableResId), "")
        }
    }
}

@Composable
fun TextRegular(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = RedVoznjeTheme.colors.blue,
    fontWeight: FontWeight = FontWeight.Medium
) {
    Text(
        text = text,
        fontFamily = manropeFontFamily,
        maxLines = 2,
        fontSize = 16.sp,
        color = color,
        overflow = TextOverflow.Ellipsis,
        softWrap = true,
        fontWeight = fontWeight,
        modifier = modifier.padding(vertical = 8.dp)
    )
}