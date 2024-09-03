package org.kmp.experiment

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
import org.kmp.ktor.KtorClient

class Greeting {
    private val platform = getPlatform()
    private val ktorClient = KtorClient()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }

    suspend fun getBusLines(): List<BusLine>{
        val html = ktorClient.getBusLines(area = Area.URBAN, day = DayType.WORKDAY)
        val document: Document = Ksoup.parse(html)

        val options = document.select("select#linija option")

        val busList = mutableListOf<BusLine>()
        for (option in options) {
            busList.add(BusLine(option.attr("value"), option.text()))
        }
        return busList
    }

    suspend fun getScheduleByLine(): Pair<List<LocalTime>, List<LocalTime>?>{
        val html = ktorClient.getScheduleByLine(area = Area.URBAN, day = DayType.WORKDAY, line = "2.")
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