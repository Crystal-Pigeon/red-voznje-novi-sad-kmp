package org.kmp.Repositories

import org.kmp.ktor.KtorClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.nodes.Document
import com.fleeksoft.ksoup.nodes.Element
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.kmp.ktor.Area
import org.kmp.ktor.BusLine
import org.kmp.ktor.DayType

class BusScheduleRepository : KoinComponent {
    private val ktorClient: KtorClient by inject()

    suspend fun getBusLines(areaType: Area, dayType: DayType): List<BusLine> {
        val html = ktorClient.getBusLines(area = areaType, day = dayType)
        val document: Document = Ksoup.parse(html)

        val options = document.select("select#linija option")

        val busList = mutableListOf<BusLine>()
        for (option in options) {
            busList.add(BusLine(option.attr("value"), option.text()))
        }
        return busList
    }

    suspend fun getScheduleByLine(areaType: Area, dayType: DayType, busLine:String): Pair<List<LocalTime>, List<LocalTime>?> {
        val html = ktorClient.getScheduleByLine(area = areaType, day = dayType, line = busLine)
        /*val document: Document = Ksoup.parse(html)

        // Select the table cell containing times for the first direction
        val timeCell = document.select("td[valign='top']").first()

        // To hold the final result
        val result = mutableListOf<String>()

        timeCell?.let { cell ->
            val elements = cell.children()

            var currentHour = ""
            val minutes = mutableListOf<String>()

            for (element in elements) {
                when {
                    element.tagName() == "b" -> {
                        // If we have a new hour, save the previous one
                        if (currentHour.isNotEmpty()) {
                            result.add("$currentHour ${minutes.joinToString(" ")}")
                            minutes.clear()
                        }
                        // Update the current hour
                        currentHour = element.text()
                    }
                    element.tagName() == "sup" -> {
                        // Collect minutes
                        val minute = element.text()
                        minutes.add(minute)
                    }
                }
            }

            // Add the last hour and minutes
            if (currentHour.isNotEmpty()) {
                result.add("$currentHour ${minutes.joinToString(" ")}")
            }
        }

        // Print the result
        //result.forEach { println(it) }
        return result.toString()*/
        val document: Document = Ksoup.parse(html)

        // Select the table cells containing times for both directions
        val timeCells = document.select("td[valign='top']")

        // Parse times for both directions
        val directionA = parseDirection(timeCells.getOrNull(0))
        val directionB = parseDirection(timeCells.getOrNull(1))

        return Pair(directionA, directionB)
    }

    private fun parseDirection(cell: Element?): List<LocalTime> {
        val times = mutableListOf<LocalTime>()
        cell?.let {
            val elements = it.children()

            var currentHour = ""
            val minutes = mutableListOf<String>()

            for (element in elements) {
                when {
                    element.tagName() == "b" -> {
                        // If we have a new hour, save the previous one
                        if (currentHour.isNotEmpty()) {
                            minutes.clear()
                        }
                        // Update the current hour
                        currentHour = element.text()
                    }

                    element.tagName() == "sup" -> {
                        // Collect minutes
                        val minute = element.text()
                        minutes.add(minute)
                        times.add(LocalTime.parse("$currentHour:$minute"))
                    }
                }
            }
        }
        return times
    }
}