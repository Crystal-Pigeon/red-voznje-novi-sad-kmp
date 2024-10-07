package org.kmp.experiment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.kmp.Repositories.BusSchedule
import java.time.LocalDateTime

@Composable
fun ScheduleColumn(
    direction: String,
    schedule: Map<String, String>,
    shortenedSchedule: MutableList<Pair<String, String>>,
    columnWidth: Int,
    isExpanded: Boolean
) {
    //var textWidth by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .width(with(LocalDensity.current) { columnWidth.toDp() })/*.onGloballyPositioned { coordinates ->
        textWidth = coordinates.size.width
    }*/
    ) {
        Column {
            TextRegular(text = direction, fontSize = 14.sp, modifier = Modifier.padding(bottom = 4.dp))
            Divider(
                color = RedVoznjeTheme.colors.lineColor,
                thickness = 1.dp,
                modifier = Modifier.width(with(LocalDensity.current) { columnWidth.toDp() })
            )
        }
        Column(modifier = Modifier.padding(top = 4.dp)) {
            //val updatedSchedule = if (isExpanded) schedule else null
            for (item in if (isExpanded) schedule.toList() else shortenedSchedule) {
                Row(modifier = Modifier
                    .wrapContentHeight()
                    .padding(top = 4.dp)) {
                    TextRegular(
                        text = item.first + ":",
                        color = if (item.first.toInt() == LocalDateTime.now().hour) RedVoznjeTheme.colors.blue else RedVoznjeTheme.colors.primaryText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    //TextRegular(text = ": ", fontWeight = FontWeight.Bold)
                    Spacer(
                        modifier = Modifier
                            .width(8.dp)
                            .height(2.dp)
                    )
                    TextRegular(
                        text = item.second, fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun FavouriteCard(scheduleData: BusSchedule) {
    val interactionSource = remember { MutableInteractionSource() }

    var isExpanded by remember { mutableStateOf<Boolean>(false) }
    var columnWidth by remember { mutableStateOf(0) }
    Card(
        elevation = 4.dp, modifier = Modifier
            .padding(12.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                isExpanded = !isExpanded
            }) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(36.dp)
                        .background(shape = CircleShape, color = RedVoznjeTheme.colors.blue)
                ) {
                    TextRegular(
                        text = scheduleData.number,
                        color = RedVoznjeTheme.colors.primaryBackground,
                        fontWeight = FontWeight.Bold
                    )
                }
                TextRegular(
                    text = scheduleData.lineName,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, end = 8.dp)
                    .onGloballyPositioned { coordinates ->
                        columnWidth = coordinates.size.width
                    },
                horizontalArrangement = Arrangement.SpaceAround
            ) {//spacedBy
                ScheduleColumn(
                    direction = scheduleData.directionA,
                    schedule = scheduleData.scheduleA,
                    shortenedSchedule = scheduleData.shortenedScheduleA,
                    columnWidth = columnWidth / 2,
                    isExpanded = isExpanded
                )
                //Spacer(Modifier.width(16.dp))
                ScheduleColumn(
                    scheduleData.directionB ?: "",
                    scheduleData.scheduleB ?: emptyMap(),
                    scheduleData.shortenedScheduleB ?: mutableListOf(),
                    columnWidth / 2,
                    isExpanded
                )
            }
        }
    }
}